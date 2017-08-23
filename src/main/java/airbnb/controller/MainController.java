package airbnb.controller;

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
   /* @Autowired
    private IAuthenticationFacade authenticationFacade;*/

    @RequestMapping(value={"/", "/index"}, method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute("users") @Valid UsersEntity user, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        UsersEntity userExists = userService.findByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("success","false");
            modelAndView.setViewName("redirect:/register");
        } else {

           /* if (!uploadingFile.isEmpty()) {
                File file = new File(uploadingdir + uploadingFile.getOriginalFilename());

                userService.saveUser(user, parent, "/image/" + uploadingFile.getOriginalFilename());
                uploadingFile.transferTo(file);
            }
            else
                userService.saveUser(user, parent,""); */

            userService.saveUser(user);

            Authentication authentication = authenticationFacade.getAuthentication();
            System.out.println("Authentication name is"+authentication.getName());

            if(!authentication.getName().equals("anonymousUser")) {
                modelAndView.addObject("uname", authentication.getName());
                UsersEntity userS = userService.findByUsername(authentication.getName());
                modelAndView.addObject("type", String.valueOf(userS.getType()));
            }

            redirectAttributes.addFlashAttribute("success","true");
            modelAndView.setViewName("redirect:/register");
        }
        return modelAndView;
    }
}


