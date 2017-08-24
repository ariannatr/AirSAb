package airbnb.controller;

import airbnb.authentication.IAuthenticationFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import airbnb.model.UsersEntity;
import airbnb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import java.io.IOException;

/**
 * Created by Arianna on 22/8/2017.
 */
@Controller
public class MainController {
    @Autowired
    private UsersService userService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @RequestMapping(value={"/", "/index"}, method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index");
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
        }
        return modelAndView;
    }

    @RequestMapping(value={"/profile"}, method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView profile(@ModelAttribute("users") @Valid UsersEntity user){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/profile");
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS = userService.findByUsername(authentication.getName());

            modelAndView.addObject("name", userS.getName());
            modelAndView.addObject("surname", userS.getSurname());
            modelAndView.addObject("email", userS.getEmail());
            modelAndView.addObject("telephone", String.valueOf(userS.getTelephone()));
            int type=userS.getType();
                if(type==1)
                    modelAndView.addObject("type", "Renter");
                else if(type==2)
                    modelAndView.addObject("type", "Owner");
                else if(type==3)
                    modelAndView.addObject("type", "Owner and Renter");
                else
                    modelAndView.addObject("type", "Admin");


        }
        return modelAndView;
    }


    @RequestMapping(value="/update", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("users") @Valid UsersEntity user,RedirectAttributes redirectAttributes )  {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("Authentication name is " + authentication.getName());
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());

            UsersEntity useron = userService.findByUsername(authentication.getName());
            userService.updateUser(useron,user);

            modelAndView.addObject("name", useron.getName());
            modelAndView.addObject("surname", useron.getSurname());
            modelAndView.addObject("email", useron.getEmail());
            modelAndView.addObject("telephone", String.valueOf(useron.getTelephone()));
            int type=useron.getType();
            if(type==1)
                modelAndView.addObject("type", "Renter");
            else if(type==2)
                modelAndView.addObject("type", "Owner");
            else if(type==3)
                modelAndView.addObject("type", "Owner and Renter");
            else
                modelAndView.addObject("type", "Admin");
            modelAndView.setViewName("redirect:/profile");
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
}
