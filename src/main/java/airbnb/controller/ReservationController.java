package airbnb.controller;

import airbnb.authentication.IAuthenticationFacade;
import airbnb.model.ApartmentEntity;
import airbnb.model.RenterEntity;
import airbnb.model.ReservationEntity;
import airbnb.repository.ReservationRepository;
import airbnb.service.ApartmentService;
import airbnb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Set;

/**
 * Created by Arianna on 21/9/2017.
 */
@Controller
public class ReservationController {

    @Autowired
    private UsersService userService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private ApartmentService apartmentService;

    @RequestMapping(value = "/reservation/{apartmentID}", method = RequestMethod.POST)
    public ModelAndView makeReservation(@ModelAttribute("reservation") @Valid ReservationEntity reservation,@PathVariable("apartmentID") int apartmentID) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        modelAndView.setViewName("redirect:/apartment/"+apartmentID);
        if (!authentication.getName().equals("anonymousUser")) {
            ApartmentEntity apart=apartmentService.findById(apartmentID);
            RenterEntity renter=userService.findRenterByUsername(authentication.getName());
            System.out.println("Reserve from "+reservation.getStartdate()+" , to"+reservation.getFinaldate());
            if(apartmentService.makeReservation(reservation,apart,renter)<0)
            {
                System.out.println("kati pige lathos stin kratisi");
                modelAndView.addObject("success","false");
            }
            else {
                modelAndView.addObject("success", "true");
                System.out.println("ola good");
            }
        }
        else{
            System.out.println("You are not allowed to make a reservation");
        }
        return modelAndView;
    }

}
