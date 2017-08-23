package airbnb.controller;

import airbnb.model.UsersEntity;
import airbnb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.io.IOException;

/**
 * Created by Arianna on 23/8/2017.
 */
@Controller
public class RegisterController {

    @Autowired
    private UsersService userService;

    @RequestMapping(value={"/register"}, method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/register");
        return modelAndView;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute("users") @Valid UsersEntity user, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.print("tha apothikeusouem ton xristi "+user.getUsername());
        UsersEntity userExists = userService.findByUsername(user.getUsername());
        if (userExists != null) {
            System.out.println("this user already exists");
            redirectAttributes.addFlashAttribute("success","false");
            modelAndView.setViewName("redirect:/register");
        }
        else
        {

           /* if (!uploadingFile.isEmpty()) {
                File file = new File(uploadingdir + uploadingFile.getOriginalFilename());

                userService.saveUser(user, parent, "/image/" + uploadingFile.getOriginalFilename());
                uploadingFile.transferTo(file);
            }
            else
                userService.saveUser(user, parent,"");*/

            userService.saveUser(user);

            System.out.println("apothikeuw ton xristi me username "+user.getUsername());
         //   Authentication authentication = authenticationFacade.getAuthentication();
           // System.out.println("Authentication name is"+authentication.getName());

            /*if(!authentication.getName().equals("anonymousUser")) {
                modelAndView.addObject("uname", authentication.getName());
                UsersEntity userS = userService.findByUsername(authentication.getName());
                modelAndView.addObject("type", String.valueOf(userS.getType()));
            }*/

            redirectAttributes.addFlashAttribute("success","true");
            modelAndView.setViewName("redirect:/register");
        }
        return modelAndView;
    }
}
