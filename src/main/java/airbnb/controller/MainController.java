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

    @RequestMapping(value="/profile", method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView profile(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/profile");
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS = userService.findByUsername(authentication.getName());

            modelAndView.addObject("user",userS);
            int type=userS.getType();
                if(type==1)
                    modelAndView.addObject("type1", "Renter");
                else if(type==2)
                    modelAndView.addObject("type1", "Owner");
                else if(type==3)
                    modelAndView.addObject("type1", "Owner and Renter");
                else
                    modelAndView.addObject("type1", "Admin");
        }
        return modelAndView;
    }


    @RequestMapping(value="/update", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("user") @Valid UsersEntity user,RedirectAttributes redirectAttributes )  {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());

            UsersEntity useron = userService.findByUsername(authentication.getName());
            userService.updateUser(useron,user);
            modelAndView.addObject("user",useron);
            modelAndView.addObject("type1",userService.getType(useron));
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
