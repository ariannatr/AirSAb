package airbnb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Arianna on 22/8/2017.
 */
@Controller
public class MainController {

    @RequestMapping(value={"/", "/index"}, method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index");
        return modelAndView;
    }
}


