package airbnb.controller;

import airbnb.authentication.IAuthenticationFacade;
import airbnb.model.ApartmentEntity;
import airbnb.model.OwnerEntity;
import airbnb.model.UsersEntity;
import airbnb.model.Pager;
import airbnb.service.ApartmentService;
import airbnb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.Valid;
import java.util.Set;
import java.util.Optional;
import java.util.ArrayList;

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
           apartmentService.saveApartment(ap,owner);
            redirectAttributes.addFlashAttribute("success","true");
//            modelAndView.addObject("uname", user.getUsername());
//            modelAndView.setViewName("redirect:/register");
//        }
        modelAndView.setViewName("redirect:/apartment_reg");
        redirectAttributes.addFlashAttribute("success","false");
        return modelAndView;
    }


    @RequestMapping(value="/apartment/{apartmentID}", method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView apartment_prof( @PathVariable("apartmentID") int apartmentID){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/apartment");
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            OwnerEntity owner = userService.findOwnerByUsername(authentication.getName());
            UsersEntity user=owner.getUsersByUsersUsername();
            modelAndView.addObject("type",String.valueOf( user.getType()));
            /*Set<ApartmentEntity> aps=owner.getApartments();
            ApartmentEntity ap1=aps.iterator().next();*/
            ApartmentEntity ap1=apartmentService.findById(apartmentID);
            modelAndView.addObject("ap",ap1);
            modelAndView.addObject("ap_type",apartmentService.getType(ap1));
            modelAndView.addObject("owner", user);
            ArrayList<String> features=apartmentService.getFeatures(ap1);
            modelAndView.addObject("features",features);
        }
        return modelAndView;
    }

    @RequestMapping(value="/apartment_update/{apartmentID}", method = RequestMethod.POST)
    public ModelAndView apartment_update(@PathVariable("apartmentID") int apartmentID,@ModelAttribute("apartment") @Valid ApartmentEntity ap,RedirectAttributes redirectAttributes ) {
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
            modelAndView.addObject("ap", ap);
            modelAndView.addObject("ap_type", apartmentService.getType(ap));
            ArrayList<String> features = apartmentService.getFeatures(ap);
            modelAndView.addObject("features", features);
            redirectAttributes.addFlashAttribute("success", "true");
           // System.out.println("eftasa edw");
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
}
