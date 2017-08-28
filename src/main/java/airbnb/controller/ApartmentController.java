package airbnb.controller;

import airbnb.authentication.IAuthenticationFacade;
import airbnb.model.UsersEntity;
import airbnb.model.ApartmentEntity;
import airbnb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by Σταυρίνα on 28/8/2017.
 */
@Controller
public class ApartmentController {


    @Autowired
    private UsersService userService;



    @Autowired
    private IAuthenticationFacade authenticationFacade;


    @RequestMapping(value={"/apartment_reg"}, method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView apartment_reg(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/apartment_reg");
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
        }
        return modelAndView;
    }

    @RequestMapping(value ="/apartment_reg", method = RequestMethod.POST)
    public ModelAndView createNewApartment(@ModelAttribute("apartment") @Valid ApartmentEntity ap, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/apartment_reg");
        Authentication authentication = authenticationFacade.getAuthentication();
     //   ApartmentEntity app=apartmentService.findByUsername(authentication.getName());
        /*System.out.print("tha apothikeusouem ton xristi "+ap.getUsername());
        UsersEntity userExists = userService.findByUsername(ap.getUsername());
        if (userExists != null) {
            System.out.println("this user already exists");
            redirectAttributes.addFlashAttribute("success","false");
            modelAndView.setViewName("redirect:/register");
        }
        else
        {


            System.out.println("apothikeuw ton xristi me username "+user.getUsername()+" kai type "+user.getType());

            userService.saveUser(user);

            redirectAttributes.addFlashAttribute("success","true");
            modelAndView.addObject("uname", user.getUsername());
            modelAndView.setViewName("redirect:/register");
        }*/
        redirectAttributes.addFlashAttribute("success","false");
        return modelAndView;
    }
}
