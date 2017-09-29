package airbnb.controller;

import airbnb.authentication.IAuthenticationFacade;
import airbnb.model.*;
import airbnb.repository.CommentsRepository;
import airbnb.service.ApartmentService;
import airbnb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Arianna on 25/8/2017.
 */
@Controller
public class AdminController {

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

    @Autowired
    private CommentsRepository commentsRepository;

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ModelAndView users(@RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/users");
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
        Page<UsersEntity> users=null;
        Pager pager=null;
        users= userService.findAllPageable(new PageRequest(evalPage, evalPageSize));
        pager= new Pager(users.getTotalPages(), users.getNumber(), BUTTONS_TO_SHOW);
        if(users.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", users);
        }
        //List<UsersEntity> users=userService.find
        modelAndView.addObject("url","users");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("ftype", "users");
        return modelAndView;
    }

    @RequestMapping(value="/renters", method = RequestMethod.GET)
    public ModelAndView renters(@RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/users");
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
        Page<UsersEntity> users=null;
        Pager pager=null;
        users= userService.findAllRenters(new PageRequest(evalPage, evalPageSize));
        pager= new Pager(users.getTotalPages(), users.getNumber(), BUTTONS_TO_SHOW);
        if(users.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", users);
        }
        //List<UsersEntity> users=userService.find
        modelAndView.addObject("url","renters");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("ftype", "renters");
        return modelAndView;
    }

    @RequestMapping(value="/owners", method = RequestMethod.GET)
    public ModelAndView owners(@RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/users");
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
        Page<UsersEntity> users=null;
        Pager pager=null;
        users= userService.findAllOwners(new PageRequest(evalPage, evalPageSize));
        pager= new Pager(users.getTotalPages(), users.getNumber(), BUTTONS_TO_SHOW);
        if(users.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", users);
        }
        //List<UsersEntity> users=userService.find
        modelAndView.addObject("url","owners");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("ftype", "owners");
        return modelAndView;
    }

    @RequestMapping(value="/accept", method = RequestMethod.GET)
    public ModelAndView requests(@RequestParam("pageSize") Optional<Integer> pageSize,
                                @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/users");
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
        Page<OwnerEntity> users=null;
        Pager pager=null;
        users= userService.findAllnotApproved(new PageRequest(evalPage, evalPageSize));
        pager= new Pager(users.getTotalPages(), users.getNumber(), BUTTONS_TO_SHOW);
        if(users.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", users);
        }
        //List<UsersEntity> users=userService.find
        modelAndView.addObject("requests","true");
        modelAndView.addObject("url","accept");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        return modelAndView;
    }

    @RequestMapping(value="/accept/{userID}", method = RequestMethod.POST)
    public ModelAndView approve(@PathVariable("userID") String userID) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
//        modelAndView.addObject("uname", authentication.getName());
//        UsersEntity userS = userService.findByUsername(authentication.getName());
//        modelAndView.addObject("type", String.valueOf(userS.getType()));
        OwnerEntity owner = userService.findOwnerByUsername(userID);
        userService.approveOwner(owner);
        modelAndView.setViewName("redirect:/accept");
        return modelAndView;
    }



    @RequestMapping(value="/apartslist", method = RequestMethod.GET)
    public ModelAndView apartslist(@RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/apartslist");
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
        aparts= apartmentService.findAllPageable(new PageRequest(evalPage, evalPageSize));
        pager= new Pager(aparts.getTotalPages(), aparts.getNumber(), BUTTONS_TO_SHOW);
        if(aparts.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", aparts);

            ArrayList<String> owners=new ArrayList<>(0);
            for (ApartmentEntity ap:aparts) {
                owners.add(ap.getOwner().getUsersUsername());
            }
            modelAndView.addObject("items2", owners);
        }

        modelAndView.addObject("url","apartslist");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        return modelAndView;
    }

    @RequestMapping(value="/reservationslist", method = RequestMethod.GET)
    public ModelAndView reservationslist(@RequestParam("pageSize") Optional<Integer> pageSize,
                                   @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/reservationslist");
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
        Page<ReservationEntity> reservations=null;
        Pager pager=null;
        reservations= apartmentService.findAllReservations(new PageRequest(evalPage, evalPageSize));
        pager= new Pager(reservations.getTotalPages(), reservations.getNumber(), BUTTONS_TO_SHOW);
        if(reservations.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", reservations);

            ArrayList<String> owners=new ArrayList<>(0);
            ArrayList<String> renters=new ArrayList<>(0);
            ArrayList<String> apartsnames=new ArrayList<>(0);
            for (ReservationEntity res:reservations) {
                apartsnames.add(res.getApartment().getName());
                owners.add(res.getApartmentOwner().getUsersUsername());
                renters.add(res.getRenter().getUsersUsername());
            }
            modelAndView.addObject("owners", owners);
            modelAndView.addObject("renters", renters);
            modelAndView.addObject("aparts", apartsnames);
        }

        modelAndView.addObject("url","reservationslist");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);

        modelAndView.addObject("renter","all");
        return modelAndView;
    }


    @RequestMapping(value="/resbyrenter", method = RequestMethod.POST)
    public ModelAndView resbyrenter(  @RequestParam("renter") String Renter,@RequestParam("pageSize") Optional<Integer> pageSize,
                                      @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/reservationslist");
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
        }

        RenterEntity renterEntity=userService.findRenterByUsername(Renter);
        if(renterEntity!=null)
            modelAndView.addObject("renter",Renter);
        else
            modelAndView.addObject("renter","false");
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Page<ReservationEntity> reservations=null;
        Pager pager=null;
        reservations= apartmentService.findAllReservationsByRenter(renterEntity,new PageRequest(evalPage, evalPageSize));
        pager= new Pager(reservations.getTotalPages(), reservations.getNumber(), BUTTONS_TO_SHOW);
        if(reservations.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", reservations);

            ArrayList<String> owners=new ArrayList<>(0);
            ArrayList<String> renters=new ArrayList<>(0);
            ArrayList<String> apartsnames=new ArrayList<>(0);
            for (ReservationEntity res:reservations) {
                apartsnames.add(res.getApartment().getName());
                owners.add(res.getApartmentOwner().getUsersUsername());
                renters.add(res.getRenter().getUsersUsername());
            }
            modelAndView.addObject("owners", owners);
            modelAndView.addObject("renters", renters);
            modelAndView.addObject("aparts", apartsnames);
        }

        modelAndView.addObject("url","reservationslist");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        return modelAndView;
    }

    @RequestMapping(value="/critbyrenter", method = RequestMethod.POST)
    public ModelAndView critbyrenter(  @RequestParam("renter") String Renter,@RequestParam("pageSize") Optional<Integer> pageSize,
                                      @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/criticslist");
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
        }

        RenterEntity renterEntity=userService.findRenterByUsername(Renter);
        if(renterEntity!=null)
            modelAndView.addObject("renter",Renter);
        else
            modelAndView.addObject("renter","false");
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Page<CommentsEntity> critics=null;
        Pager pager=null;

        critics=commentsRepository.findAllByRenter(renterEntity,new PageRequest(evalPage, evalPageSize));
        //reservations= apartmentService.findAllReservationsByRenter(renterEntity,new PageRequest(evalPage, evalPageSize));
        pager= new Pager(critics.getTotalPages(), critics.getNumber(), BUTTONS_TO_SHOW);
        if(critics.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", critics);

            ArrayList<String> owners=new ArrayList<>(0);
            ArrayList<String> renters=new ArrayList<>(0);
            ArrayList<String> apartsnames=new ArrayList<>(0);
            for (CommentsEntity res:critics) {
                apartsnames.add(res.getApartmentEntity().getName());
                owners.add(res.getApartmentOwner().getUsersUsername());
                renters.add(res.getRenter().getUsersUsername());
            }
            modelAndView.addObject("owners", owners);
            modelAndView.addObject("renters", renters);
            modelAndView.addObject("aparts", apartsnames);
        }

        modelAndView.addObject("url","reservationslist");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        return modelAndView;
    }


    @RequestMapping(value="/critforowner", method = RequestMethod.POST)
    public ModelAndView critforowner (  @RequestParam("owner") String Owner,@RequestParam("pageSize") Optional<Integer> pageSize,
                                       @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/criticslist");
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UsersEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
        }

        OwnerEntity ownerEntity=userService.findOwnerByUsername(Owner);
        if(ownerEntity!=null)
            modelAndView.addObject("owner",Owner);
        else
            modelAndView.addObject("owner","false");
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Page<CommentsEntity> critics=null;
        Pager pager=null;

        critics=commentsRepository.findAllByApartmentOwner(ownerEntity,new PageRequest(evalPage, evalPageSize));
        //reservations= apartmentService.findAllReservationsByRenter(renterEntity,new PageRequest(evalPage, evalPageSize));
        pager= new Pager(critics.getTotalPages(), critics.getNumber(), BUTTONS_TO_SHOW);
        if(critics.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", critics);

            ArrayList<String> owners=new ArrayList<>(0);
            ArrayList<String> renters=new ArrayList<>(0);
            ArrayList<String> apartsnames=new ArrayList<>(0);
            for (CommentsEntity res:critics) {
                apartsnames.add(res.getApartmentEntity().getName());
                owners.add(res.getApartmentOwner().getUsersUsername());
                renters.add(res.getRenter().getUsersUsername());
            }
            modelAndView.addObject("owners", owners);
            modelAndView.addObject("renters", renters);
            modelAndView.addObject("aparts", apartsnames);
        }

        modelAndView.addObject("url","reservationslist");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        return modelAndView;
    }

}
