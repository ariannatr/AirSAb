package airbnb.controller;

import airbnb.authentication.IAuthenticationFacade;
import airbnb.model.*;
import airbnb.repository.ReservationRepository;
import airbnb.service.ApartmentService;
import airbnb.service.CommentsService;
import airbnb.service.ReservationService;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Arianna on 21/9/2017.
 */
@Controller
public class ReservationController {

    @Autowired
    private UsersService userService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private ReservationService reservationService;

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
            int temp=apartmentService.makeReservation(reservation,apart,renter);
            if(temp==-1)
            {
                System.out.println("not available");
                modelAndView.addObject("success","false");
            }
            else if(temp==-2)
            {
                modelAndView.addObject("success","min");
            }
            else if(temp==-3)
            {
                modelAndView.addObject("success","invalid");

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

    @RequestMapping(value = "/reserves", method = RequestMethod.GET)
    public ModelAndView viewReservations() throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        modelAndView.setViewName("/reserves");
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
            RenterEntity renter=userService.findRenterByUsername(authentication.getName());
            Set<ReservationEntity> reservations=renter.getReservationsByUsersUsername();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            String currDate1 = dtf.format(localDate).toString();
             if(reservations!=null) {
                 List<ReservationEntity> currReservations = new ArrayList<>();
                 List<ApartmentEntity> currAparts = new ArrayList<>();
                 List<ReservationEntity> pastReservations = new ArrayList<>();
                 List<ApartmentEntity> pastAparts = new ArrayList<>();
                 for (ReservationEntity res : reservations) {
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                     try {
                         ApartmentEntity apartmentEntity = res.getApartment();
                         Date currDate = sdf.parse(currDate1);
                         Date reservationDate = sdf.parse(res.getStartdate());
                         if (reservationDate.after(currDate)) {
                             currAparts.add(apartmentEntity);
                             currReservations.add(res);
                         } else {
                             pastAparts.add(apartmentEntity);
                             pastReservations.add(res);
                         }
                     } catch (ParseException e) {

                     }
                 }

                // if (pastReservations.size() > 0)
                     modelAndView.addObject("pastRes", pastReservations);
                 //if (currReservations.size() > 0)
                     modelAndView.addObject("currRes", currReservations);
               //  if (pastAparts.size() > 0)
                     modelAndView.addObject("pastAparts", pastAparts);
                 //if (currAparts.size() > 0)
                     modelAndView.addObject("currAparts", currAparts);
             }

        }
        else{
            System.out.println("You are not allowed here");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/comment/{apartmentID}", method = RequestMethod.POST)
    public ModelAndView makeReservation(@ModelAttribute("comments") @Valid CommentsEntity comments, @PathVariable("apartmentID") int apartmentID) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        modelAndView.setViewName("redirect:/reserves");
        if (!authentication.getName().equals("anonymousUser")) {
            ApartmentEntity apart = apartmentService.findById(apartmentID);
            RenterEntity renter = userService.findRenterByUsername(authentication.getName());
            commentsService.saveComment(comments,renter,apart);
        }
        else{
            System.out.println("You are not allowed to make a reservation");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/apartReserves", method = RequestMethod.GET)
    public ModelAndView viewApartReservations() throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        modelAndView.setViewName("/reserves");
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
            OwnerEntity owner=userService.findOwnerByUsername(authentication.getName());
            Set<ReservationEntity> reservations=apartmentService.findAllReservationByOwner(owner);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            String currDate1 = dtf.format(localDate).toString();
            if(reservations!=null) {
                List<ReservationEntity> currReservations = new ArrayList<>();
                List<ApartmentEntity> currAparts = new ArrayList<>();
                List<ReservationEntity> pastReservations = new ArrayList<>();
                List<ApartmentEntity> pastAparts = new ArrayList<>();
                for (ReservationEntity res : reservations) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        ApartmentEntity apartmentEntity = res.getApartment();
                        Date currDate = sdf.parse(currDate1);
                        Date reservationDate = sdf.parse(res.getStartdate());
                        if (reservationDate.after(currDate)) {
                            currAparts.add(apartmentEntity);
                            currReservations.add(res);
                        } else {
                            pastAparts.add(apartmentEntity);
                            pastReservations.add(res);
                        }
                    } catch (ParseException e) {

                    }
                }

                // if (pastReservations.size() > 0)
                modelAndView.addObject("pastRes", pastReservations);
                //if (currReservations.size() > 0)
                modelAndView.addObject("currRes", currReservations);
                //  if (pastAparts.size() > 0)
                modelAndView.addObject("pastAparts", pastAparts);
                //if (currAparts.size() > 0)
                modelAndView.addObject("currAparts", currAparts);
                modelAndView.addObject("approveOption", "true");
            }

        }
        else{
            System.out.println("You are not allowed here");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/approveReserve/{reserveID}", method = RequestMethod.POST)
    public ModelAndView makeReservation(@PathVariable("reserveID") int reserveID) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        modelAndView.setViewName("redirect:/apartReserves");
        if (!authentication.getName().equals("anonymousUser")) {
            ReservationEntity reservationEntity=reservationService.findByReservationId(reserveID);
            reservationService.approveReservation(reservationEntity);
        }
        else{
            System.out.println("You are not allowed to make a reservation");
        }
        return modelAndView;
    }
}
