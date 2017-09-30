package airbnb.controller;

import airbnb.authentication.IAuthenticationFacade;
import airbnb.model.*;
import airbnb.repository.RenterRepository;
import airbnb.repository.ReservationRepository;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

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

                ArrayList<UsersEntity> renterList = userService.findAllRenters();
                Recommend recommend=new Recommend();
                LinkedHashSet<ApartmentEntity> ap_recoms;
                if(!userService.checkforRenterActivity(renter))
                    ap_recoms= getRecommendationsRes(renter,renterList);
                else
                    ap_recoms= getRecommendationsCookie(renter,renterList);
                for(ApartmentEntity ap:ap_recoms)
                    System.out.println(ap.getName());
                if(ap_recoms.size()>0)
                    modelAndView.addObject("recommendations",ap_recoms);
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

    public LinkedHashSet<ApartmentEntity> getRecommendationsRes(RenterEntity renterEntity,ArrayList<UsersEntity> renterList) {
        LinkedHashSet<ApartmentEntity> rec = new LinkedHashSet<ApartmentEntity>();
        Set<ReservationEntity> res = renterEntity.getReservationsByUsersUsername();
        //  ArrayList<UsersEntity> renterList = userService.findAllRenters();

        HashMap<UsersEntity, Integer> to_Rec = new HashMap<UsersEntity, Integer>();
        for (UsersEntity u : renterList) {
            Integer amount = 0;
            ArrayList<ReservationEntity> allu=reservationRepository.findAllByRenter(u.getRenterByUsername());
            for (ReservationEntity ur : allu) {
                for (ReservationEntity myr : res) {
                    if (myr.getApartment().getId() == ur.getApartment().getId()) {
                        amount++;
                    }
                }
            }
            to_Rec.put(u, amount);
        }

        Integer i1 = 0;
        Integer i2 = 0;
        Integer i3 = 0;
        Integer i4 = 0;
        Integer i5 = 0;
        UsersEntity test_user = new UsersEntity();
        HashMap<UsersEntity, Integer> train_set = new HashMap<UsersEntity, Integer>();
        for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i1) {
                i1 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i1);
        to_Rec.remove(test_user, i1);
        for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i2) {
                i2 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i2);
        to_Rec.remove(test_user, i2);
        for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i3) {
                i2 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i3);
        to_Rec.remove(test_user, i3);
        for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i4) {
                i2 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i4);
        to_Rec.remove(test_user, i4);
        for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i5) {
                i2 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i5);
        to_Rec.remove(test_user, i5);
        int k = 0;

        sentiment sentiment=new sentiment();
        for (HashMap.Entry<UsersEntity, Integer> entry : train_set.entrySet()) {
            UsersEntity u = entry.getKey();
            ArrayList<ReservationEntity> allu=reservationRepository.findAllByRenter(u.getRenterByUsername());
            for (ReservationEntity ur : allu) {
                for (ReservationEntity bb : res) {
                    Set<CommentsEntity> comments = ur.getApartment().getComments();
                    for (CommentsEntity com : comments) {
                        Set<CommentsEntity> caat = bb.getApartment().getComments();
                        for (CommentsEntity ct : caat) {
                            if  ((sentiment.getsentiment(ct.getComment())==sentiment.getsentiment(com.getComment())) || (abs(ct.getRating()-com.getRating()) <=1) ) {
                                //System.out.println("passed that");
                                if (!(ur.getApartmentOwner().getUsersUsername()).equals(renterEntity.getUsersUsername())
                                        && ur.getApartment().getId() != bb.getApartment().getId()) {
                                   // System.out.println("mpike edw");
                                    if (k < 6) {
                                        rec.add(ur.getApartment());
                                        k++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return rec;
    }



public LinkedHashSet<ApartmentEntity> getRecommendationsCookie(RenterEntity renterEntity, ArrayList<UsersEntity> renterList) {
    LinkedHashSet<ApartmentEntity> rec = new LinkedHashSet<ApartmentEntity>();
    Set<CookieApEntity> res = renterEntity.getCookieAp();
    //  ArrayList<RenterEntity> renters=renterRepository.findAll();

    HashMap<UsersEntity, Integer> to_Rec = new HashMap<UsersEntity, Integer>();
    for (UsersEntity u : renterList) {
        Integer amount = 0;
        //System.out.println("Epestrepsa aut "+u.getRenterByUsername());
        RenterEntity renter=renterRepository.findByUsersUsername(u.getUsername());
        ArrayList<ReservationEntity> allu=reservationRepository.findAllByRenter(renter);
        for (ReservationEntity ur : allu) {
            for (CookieApEntity myr : res) {
                if (myr.getApartmentid() == ur.getApartment().getId()) {
                    if(myr.getTimes()>1)
                        amount+=2;
                    else
                        amount++;
                }
            }
        }
        to_Rec.put(u, amount);
    }

    Integer i1 = 0;
    Integer i2 = 0;
    Integer i3 = 0;
    Integer i4 = 0;
    Integer i5 = 0;
    UsersEntity test_user = new UsersEntity();
    HashMap<UsersEntity, Integer> train_set = new HashMap<UsersEntity, Integer>();
    for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
        if (entry.getValue() > i1) {
            i1 = entry.getValue();
            test_user = entry.getKey();
        }
    }
    train_set.put(test_user, i1);
    to_Rec.remove(test_user, i1);
    for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
        if (entry.getValue() > i2) {
            i2 = entry.getValue();
            test_user = entry.getKey();
        }
    }
    train_set.put(test_user, i2);
    to_Rec.remove(test_user, i2);
    for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
        if (entry.getValue() > i3) {
            i3 = entry.getValue();
            test_user = entry.getKey();
        }
    }
    train_set.put(test_user, i3);
    to_Rec.remove(test_user, i3);
    for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
        if (entry.getValue() > i4) {
            i4 = entry.getValue();
            test_user = entry.getKey();
        }
    }
    train_set.put(test_user, i4);
    to_Rec.remove(test_user, i4);
    for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
        if (entry.getValue() > i5) {
            i5 = entry.getValue();
            test_user = entry.getKey();
        }
    }
    train_set.put(test_user, i5);
    to_Rec.remove(test_user, i5);
    int k = 0;

    Set<CookieSearchEntity> res2=renterEntity.getCookieSearch();
    for (HashMap.Entry<UsersEntity, Integer> entry : train_set.entrySet()) {
        UsersEntity u = entry.getKey();
        ArrayList<ReservationEntity> allu=reservationRepository.findAllByRenter(u.getRenterByUsername());
        for (ReservationEntity ur : allu) {
            for (CookieSearchEntity bb : res2) {

             //   String[] words=bb.getLocation().split(";");
                int score=0;

                   /* Set<CommentsEntity> comments = ur.getApartment().getComments();
                    for (CommentsEntity com : comments) {
                        Set<CommentsEntity> caat = bb.getApartment().getComments();
                        for (CommentsEntity ct : caat) {
                            if  ((sentiment.getsentiment(ct.getComment())==sentiment.getsentiment(com.getComment())) || (abs(ct.getRating()-com.getRating()) <=1) ) {
                                //System.out.println("passed that");*/
                if (!(ur.getApartmentOwner().getUsersUsername()).equals(renterEntity.getUsersUsername())
                        && ur.getApartment().getCapacity() >= bb.getNum()) {

                    if(   bb.getLocation().toLowerCase().contains(ur.getApartment().getCountry().toLowerCase()))
                        score+=2;
                    if(   bb.getLocation().toLowerCase().contains(ur.getApartment().getTown().toLowerCase()))
                        score+=1;
                    if(    bb.getLocation().toLowerCase().contains(ur.getApartment().getArea().toLowerCase()))
                        score+=1;
                    if (k < 6 && score>=2) {
                        rec.add(ur.getApartment());
                        k++;
                    }
                }
            }
        }
    }
    //}
    //}
    //}
    return rec;
}
}
