package airbnb.controller;

import airbnb.authentication.IAuthenticationFacade;
import airbnb.model.*;
import airbnb.service.ApartmentService;
import airbnb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Σταυρίνα on 28/8/2017.
 */
@Controller
public class ApartmentController {
    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = { 5, 10, 20 };

    @Autowired
    private UsersService userService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";

    @RequestMapping(value={"/apartment_reg"}, method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView apartment_reg(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/apartment_reg");
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
            OwnerEntity owner = userService.findOwnerByUsername(authentication.getName());
            if(owner.getApproval()==0)
                modelAndView.addObject("approval","false");
            else
                modelAndView.addObject("approval","true");
        }
        return modelAndView;
    }

    @RequestMapping(value ="/apartment_reg", method = RequestMethod.POST)
    public ModelAndView createNewApartment(@ModelAttribute("apartment") @Valid ApartmentEntity ap, RedirectAttributes redirectAttributes,@RequestParam("uploadingFile") MultipartFile uploadingFile) throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = authenticationFacade.getAuthentication();

     //   ApartmentEntity app=apartmentService.findByUsername(authentication.getName());
       OwnerEntity owner = userService.findOwnerByUsername(authentication.getName());

//        if (userExists != null) {
//            System.out.println("this user already exists");
//            redirectAttributes.addFlashAttribute("success","false");
//            modelAndView.setViewName("redirect:/register");
//        }
//        else
//        {
//
//
//            System.out.println("apothikeuw ton xristi me username "+user.getUsername()+" kai type "+user.getType());
//
        File theDir=new File(uploadingdir);
        if(!theDir.exists())
            theDir.mkdir();

        if (!uploadingFile.isEmpty()) {
            File file = new File(uploadingdir + uploadingFile.getOriginalFilename());
            apartmentService.saveApartment(ap,owner,  "/image/" + uploadingFile.getOriginalFilename());
            uploadingFile.transferTo(file);
        }
        else
            apartmentService.saveApartment(ap,owner,"");
//        }
        modelAndView.setViewName("redirect:/apartment_reg");
        modelAndView.addObject("success","true");

        //redirectAttributes.addFlashAttribute("success","false");
        return modelAndView;
    }


    @RequestMapping(value="/apartment/{apartmentID}", method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView apartment_prof( @PathVariable("apartmentID") int apartmentID){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/apartment");
        int user_type=-1;
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS= userService.findByUsername(authentication.getName());
            user_type=userS.getType();
            modelAndView.addObject("type",String.valueOf( userS.getType()));
            /*Set<ApartmentEntity> aps=owner.getApartments();
            ApartmentEntity ap1=aps.iterator().next();*/
            if(userS.getType()==1 || userS.getType()==3) {
                OwnerEntity owner = userService.findOwnerByUsername(authentication.getName());
                if (owner.getApproval() == 0)
                    modelAndView.addObject("approval", "false");
            }


        }
        ApartmentEntity ap1=apartmentService.findById(apartmentID);
        OwnerEntity ownerEntity=ap1.getOwner();
        Set<CommentsEntity> comments=ap1.getComments();
        UsersEntity user_owner=userService.findByUsername(ownerEntity.getUsersUsername());
        modelAndView.addObject("owner", user_owner);
        modelAndView.addObject("ap",ap1);
        modelAndView.addObject("comments",comments);
        if (!authentication.getName().equals("anonymousUser") && ap1.getOwner().getUsersUsername().equals(authentication.getName())) {
           System.out.println("einai diko mou");
            modelAndView.addObject("mine","true");
        }
        if(!authentication.getName().equals("anonymousUser") && !ap1.getOwner().getUsersUsername().equals(authentication.getName())&&( user_type==2 || user_type==3)){
            modelAndView.addObject("renter","true");
        }
        modelAndView.addObject("ap_type",apartmentService.getType(ap1));
        ArrayList<String> features=apartmentService.getFeatures(ap1);
        modelAndView.addObject("features",features);
        return modelAndView;
    }

    @RequestMapping(value="/apartment_update/{apartmentID}", method = RequestMethod.POST)
    public ModelAndView apartment_update(@PathVariable("apartmentID") int apartmentID,@ModelAttribute("apartment") @Valid ApartmentEntity ap,RedirectAttributes redirectAttributes, @RequestParam("uploadingFile") MultipartFile uploadingFile,
                                         @RequestParam("uploadingFile2") MultipartFile uploadingFile2,@RequestParam("uploadingFile3") MultipartFile uploadingFile3,@RequestParam("uploadingFile4") MultipartFile uploadingFile4) throws IOException {

        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());

            OwnerEntity owner = userService.findOwnerByUsername(authentication.getName());
            int user_type = owner.getUsersByUsersUsername().getType();
            modelAndView.addObject("type", String.valueOf(user_type));

          //  Set<ApartmentEntity> aps = owner.getApartments();
            ApartmentEntity ap_old = apartmentService.findById(apartmentID);

            apartmentService.updateApartment( ap_old,ap);
            ap=apartmentService.findById(apartmentID);

            File theDir=new File(uploadingdir);
            if(!theDir.exists())
                theDir.mkdir();

            if (!uploadingFile.isEmpty()) {
                File file = new File(uploadingdir + uploadingFile.getOriginalFilename());

                apartmentService.uploadPhoto(ap, "/image/" + uploadingFile.getOriginalFilename());
                uploadingFile.transferTo(file);
            }
            if (!uploadingFile2.isEmpty()){
                File file = new File(uploadingdir + uploadingFile2.getOriginalFilename());
                apartmentService.uploadPhoto2(ap, "/image/" + uploadingFile2.getOriginalFilename());
                uploadingFile2.transferTo(file);

            }
            if (!uploadingFile3.isEmpty()){
                File file = new File(uploadingdir + uploadingFile3.getOriginalFilename());
                apartmentService.uploadPhoto3(ap, "/image/"+uploadingFile3.getOriginalFilename());
                uploadingFile3.transferTo(file);

            }

            if (!uploadingFile4.isEmpty()){
                File file = new File(uploadingdir + uploadingFile4.getOriginalFilename());
                apartmentService.uploadPhoto4(ap, "/image/" + uploadingFile4.getOriginalFilename());
                uploadingFile4.transferTo(file);

            }
            modelAndView.addObject("ap", ap);
            modelAndView.addObject("ap_type", apartmentService.getType(ap));
            ArrayList<String> features = apartmentService.getFeatures(ap);
            modelAndView.addObject("features", features);
            //redirectAttributes.addFlashAttribute("success", "true");
            modelAndView.setViewName("redirect:/apartment/"+apartmentID);
            return modelAndView;
        } else {
            System.out.println("not allowed here");
            redirectAttributes.addFlashAttribute("success", "false");
            modelAndView.setViewName("redirect:/register");
            return modelAndView;
        }
    }



    @RequestMapping(value={"/aparts"}, method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView apartments(@RequestParam("pageSize") Optional<Integer> pageSize,
                                   @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/aparts");
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

        }
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Page<ApartmentEntity> aparts=null;
        Pager pager=null;
        OwnerEntity owner=userService.findOwnerByUsername(authentication.getName());
        aparts= apartmentService.findOwnersAparts(owner,new PageRequest(evalPage, evalPageSize));
        pager= new Pager(aparts.getTotalPages(), aparts.getNumber(), BUTTONS_TO_SHOW);
        if(aparts.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", aparts);
        }
        modelAndView.addObject("url","aparts");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("owner", owner.getUsersByUsersUsername());
        return modelAndView;
    }


    @RequestMapping(value="/removephoto/{num}/{apartmentID}", method = RequestMethod.POST)
    public ModelAndView removephoto( @PathVariable String num,@PathVariable String apartmentID){
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        modelAndView.addObject("uname", authentication.getName());
        UsersEntity userS = userService.findByUsername(authentication.getName());
        modelAndView.addObject("type", String.valueOf(userS.getType()));

        Integer apartmetID_ = Integer.parseInt(apartmentID);
        Integer num_ = Integer.parseInt(num);
        ApartmentEntity ap=apartmentService.findById(apartmetID_);

        apartmentService.removephoto(ap,num_);

        modelAndView.setViewName("redirect:/apartment/"+apartmentID);
        return modelAndView;
    }


}
