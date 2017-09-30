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

                ArrayList<RenterEntity> renterList22 = userService.findAllRentersEntity();
                Recommend recommend=new Recommend();

                LinkedHashSet<ApartmentEntity> ap_recoms;
                if(!userService.checkforRenterActivity(renter))
                    ap_recoms= recommend.getRecommendationsRes(renter,renterList22);
                else
                    ap_recoms= recommend.getRecommendationsCookie(renter,renterList22);
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


}
