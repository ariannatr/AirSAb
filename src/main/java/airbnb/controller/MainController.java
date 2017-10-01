package airbnb.controller;

import airbnb.authentication.IAuthenticationFacade;
import airbnb.knn.Instance;
import airbnb.knn.Knn;
import airbnb.knn.Neighbor;
import airbnb.model.*;
import airbnb.repository.RenterRepository;
import airbnb.repository.ReservationRepository;
import airbnb.service.ApartmentService;
import airbnb.service.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import airbnb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import javax.validation.Valid;
import org.springframework.ui.Model;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import airbnb.sentimental.sentiment;

import static java.lang.Math.abs;

/**
 * Created by Arianna on 22/8/2017.
 */
@Controller
public class MainController {
    @Autowired
    private UsersService userService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private CookieService cookieService;

    @RequestMapping(value={"/", "/index"}, method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index");
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
            if(userS.getType()==1 || userS.getType()==3) {
                OwnerEntity owner = userService.findOwnerByUsername(authentication.getName());
                if (owner.getApproval() == 0)
                    modelAndView.addObject("approval", "false");
            }
            if(userS.getType()==2 || userS.getType()==3)
            {
                RenterEntity renter=userS.getRenterByUsername();

                ArrayList<RenterEntity> renterList = userService.findAllRentersEntity();

                int apart_num=apartmentService.findAll().size();
                System.out.println("Exw "+apart_num+" apartments in base");
                Knn knn=new Knn();
                /*----------------------------RECOMMEND--------------------------------------*/

               /* Recommend recommend=new Recommend();
                LinkedHashSet<ApartmentEntity> ap_recoms;
                if(!userService.checkforRenterActivity(renter))
                    ap_recoms= recommend.getRecommendationsRes(renter,renterList);
                else
                    ap_recoms= recommend.getRecommendationsCookie(renter,renterList);
                for(ApartmentEntity ap:ap_recoms)
                    System.out.println(ap.getName());
                if(ap_recoms.size()>0)
                    modelAndView.addObject("recommendations",ap_recoms);*/


                /*---------------------------------KNN----------------------------------------*/


                if(!userService.checkforRenterActivity(renter)) {
                    ArrayList<Neighbor> neighbors=knn.doKnn(1,renter,renterList,apart_num);
                    if(neighbors.size()!=0)
                    {
                        ArrayList<ApartmentEntity> apartments_torecommend=findRecommendations1(renter,neighbors);
                        apartments_torecommend=removedoubles(apartments_torecommend);
                          if(apartments_torecommend.size()>0)
                          {
                              System.out.println(" proteinomena "+apartments_torecommend);
                            modelAndView.addObject("recommendations",apartments_torecommend);
                          }
                    }
                }
                else {
                    ArrayList<Neighbor> neighbors=knn.doKnn(2,renter,renterList,apart_num);
                    if(neighbors.size()!=0)
                    {
                        ArrayList<ApartmentEntity> apartments_torecommend=findRecommendations2(renter,neighbors);
                        apartments_torecommend=removedoubles(apartments_torecommend);
                        if(apartments_torecommend.size()>0) {
                            System.out.println(" proteinomena " + apartments_torecommend);
                            modelAndView.addObject("recommendations", apartments_torecommend);
                        }
                    }
                }
            }
        }

         return modelAndView;
    }

    @RequestMapping(value="/profile", method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView profile(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/profile");
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
            modelAndView.addObject("user",userS);
            int type=userS.getType();
                if(type==1)
                    modelAndView.addObject("type1", "Owner");
                else if(type==2)
                    modelAndView.addObject("type1", "Renter");
                else if(type==3)
                    modelAndView.addObject("type1", "Owner and Renter");
                else
                    modelAndView.addObject("type1", "Admin");
            if(userS.getType()==1 || userS.getType()==3) {
                OwnerEntity owner = userService.findOwnerByUsername(authentication.getName());
                if (owner.getApproval() == 0)
                    modelAndView.addObject("approval", "false");
            }
        }
        return modelAndView;
    }




    @RequestMapping(value="/update", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("user") @Valid UsersEntity user,RedirectAttributes redirectAttributes,@RequestParam("uploadingFile") MultipartFile uploadingFile ) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());

            UsersEntity useron = userService.findByUsername(authentication.getName());
            userService.updateUser(useron,user);

            File theDir=new File(uploadingdir);
            if(!theDir.exists())
                theDir.mkdir();

            if (!uploadingFile.isEmpty()) {
                File file = new File(uploadingdir + uploadingFile.getOriginalFilename());

                userService.uploadPhoto(useron, "/image/" + uploadingFile.getOriginalFilename());
                uploadingFile.transferTo(file);
            }

            modelAndView.setViewName("redirect:/profile");
            modelAndView.addObject("user",useron);
            modelAndView.addObject("type1",userService.getType(useron));
            modelAndView.addObject("type", String.valueOf(useron.getType()));
            if(useron.getType()==1 || useron.getType()==3) {
                OwnerEntity owner = userService.findOwnerByUsername(authentication.getName());
                if (owner.getApproval() == 0)
                    modelAndView.addObject("approval", "false");
            }
            return modelAndView;
        }
        else
        {
            System.out.println("not allowed here");
            redirectAttributes.addFlashAttribute("success","false");
            modelAndView.setViewName("redirect:/register");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {

        File serverFile = new File(uploadingdir + imageName +".jpg");
        if(!serverFile.exists())
            serverFile=new File(uploadingdir + imageName +".png");
        // System.out.println("Psaxnw to "+uploadingdir+imageName);
        return Files.readAllBytes(serverFile.toPath());
    }


    ArrayList<ApartmentEntity> findRecommendations1(RenterEntity renterEntity, ArrayList<Neighbor> neighbors)
    {
        int i = 0;
        int nindex;
        ApartmentEntity apartmentEntity;
        Set<ReservationEntity> res;
        ArrayList <ApartmentEntity> apids=new ArrayList<ApartmentEntity>(0);
        for(Neighbor neighbor : neighbors) {
            nindex=neighbor.getInstance().getMaxIndex();
            apartmentEntity=apartmentService.findById(nindex+1);
            res=reservationRepository.findAllByApartmentAndRenter(apartmentEntity,renterEntity);
            if( res.size()==0)                //if renter hasn't made a reservation on it yet
                apids.add(apartmentEntity);		//bc index starts from 0 where ids start from 1
            else
            {
                nindex=neighbor.getInstance().getSecondMaxIndex();
                if(nindex!=-1) {
                    apartmentEntity = apartmentService.findById(nindex + 1);
                    res = reservationRepository.findAllByApartmentAndRenter(apartmentEntity, renterEntity);
                    if (res.size() == 0)
                        apids.add(apartmentEntity);        //bc index starts from 0 where ids start from 1
                    //else no recommendation from that neighbor
                }
            }
        }
        return apids;
    }


    ArrayList<ApartmentEntity> findRecommendations2(RenterEntity renterEntity, ArrayList<Neighbor> neighbors)
    {
        ApartmentEntity apartmentEntity;
        ArrayList <ApartmentEntity> apids=new ArrayList<ApartmentEntity>(0);
        for(Neighbor neighbor : neighbors) {
            RenterEntity renterNeighbor=userService.findRenterByUsername(neighbor.getInstance().getUuid());
            if(renterNeighbor.getReservationsByUsersUsername().size()==0)       //neighbor with no reservations
            {
                //2 p exei mpei perissoteres fores o renterEntity
                ArrayList<CookieApEntity> cookieApEntityArrayList=cookieService.findByRenterOrderByTimesDesc(renterEntity);
                if(cookieApEntityArrayList.size()>0)
                {
                    apartmentEntity=apartmentService.findById(cookieApEntityArrayList.get(0).getApartmentid());
                    if(!apartmentEntity.getOwner().getUsersUsername().equals(renterEntity.getUsersUsername()))
                        apids.add(apartmentEntity);
                }
                if(cookieApEntityArrayList.size()>1) {
                    apartmentEntity = apartmentService.findById(cookieApEntityArrayList.get(1).getApartmentid());
                    if (!apartmentEntity.getOwner().getUsersUsername().equals(renterEntity.getUsersUsername()))
                        apids.add(apartmentEntity);
                }
            }
            else //neighbor with reservations
            {
                //apartment me stoixeia p einai kai sto search tou user
                Set<CookieSearchEntity> cookieSearchEntities=renterEntity.getCookieSearch();
                Set<ReservationEntity> neighborReservations= renterNeighbor.getReservationsByUsersUsername();
                int k=0;
                for(ReservationEntity res:neighborReservations)
                {
                    for (CookieSearchEntity bb : cookieSearchEntities) {
                        int score = 0;
                        if (!(res.getApartmentOwner().getUsersUsername()).equals(renterEntity.getUsersUsername())
                                && res.getApartment().getCapacity() >= bb.getNum()) {

                            if (bb.getLocation().toLowerCase().contains(res.getApartment().getCountry().toLowerCase()))
                                score += 2;
                            if (bb.getLocation().toLowerCase().contains(res.getApartment().getTown().toLowerCase()))
                                score += 1;
                            if (bb.getLocation().toLowerCase().contains(res.getApartment().getArea().toLowerCase()))
                                score += 1;
                            if (k < 2 && score >= 2) {
                                apids.add(res.getApartment());
                                k++;
                            }
                        }
                    }
                }


            }
        }
        return apids;
    }


    private  ArrayList<ApartmentEntity> removedoubles(ArrayList<ApartmentEntity> apartments)
    {
        Set<ApartmentEntity> hs = new HashSet<ApartmentEntity>(apartments);
        hs.addAll(apartments);
        ArrayList<ApartmentEntity> aa= new ArrayList<>();
        aa.addAll(hs);
        return  aa;
    }
}
