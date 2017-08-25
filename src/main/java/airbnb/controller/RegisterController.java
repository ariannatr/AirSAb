package airbnb.controller;

import airbnb.authentication.IAuthenticationFacade;
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
import java.io.IOException;

/**
 * Created by Arianna on 23/8/2017.
 */
@Controller
public class RegisterController {

    @Autowired
    private UsersService userService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;


    @RequestMapping(value={"/register"}, method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/register");
        return modelAndView;
    }


    @RequestMapping(value ="/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute("user") @Valid UsersEntity user, RedirectAttributes redirectAttributes) {
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
            System.out.println("apothikeuw ton xristi me username "+user.getUsername()+" kai type "+user.getType());

            userService.saveUser(user);

            redirectAttributes.addFlashAttribute("success","true");
            modelAndView.addObject("uname", user.getUsername());
            modelAndView.setViewName("redirect:/register");
        }
        return modelAndView;
    }
}
