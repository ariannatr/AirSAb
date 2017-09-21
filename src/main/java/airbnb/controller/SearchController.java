package airbnb.controller;

import airbnb.authentication.IAuthenticationFacade;
import airbnb.model.ApartmentEntity;
import airbnb.model.OwnerEntity;
import airbnb.model.Pager;
import airbnb.model.UsersEntity;
import airbnb.service.ApartmentService;
import airbnb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * Created by Arianna on 20/9/2017.
 */
@Controller
public class SearchController {

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 10;
    private static final int[] PAGE_SIZES = { 5, 10, 20 };

    @Autowired
    private UsersService userService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";


    @RequestMapping(value={"/search"}, method = RequestMethod.POST/*, produces= "application/javascript"*/)
    public ModelAndView search(@RequestParam("pageSize") Optional<Integer> pageSize,
                                   @RequestParam("page") Optional<Integer> page,@RequestParam("country") Optional<String> country,@RequestParam("town") Optional<String> town,@RequestParam("area") Optional<String> area,@RequestParam("arrivalDate") Optional<String> arrivalDate,@RequestParam("departureDate") Optional<String> departureDate,@RequestParam("people") Optional<Integer> people){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/searchAparts");
        System.out.println("Country "+country+" , town "+town+" , area "+area+" ,arrival date " +arrivalDate+ " ,depdate "+departureDate +" ,people "+people);

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
        aparts= apartmentService.findAparts(country,town,area,arrivalDate,departureDate,people,new PageRequest(evalPage, evalPageSize));
        pager= new Pager(aparts.getTotalPages(), aparts.getNumber(), BUTTONS_TO_SHOW);
        if(aparts.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", aparts);
        }
        modelAndView.addObject("url","search");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        return modelAndView;
    }

    @RequestMapping(value={"/search"}, method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView getsearch(@RequestParam("pageSize") Optional<Integer> pageSize,
                               @RequestParam("page") Optional<Integer> page,@RequestParam("country") Optional<String> country,@RequestParam("town") Optional<String> town,@RequestParam("area") Optional<String> area,@RequestParam("arrivalDate") Optional<String> arrivalDate,@RequestParam("departureDate") Optional<String> departureDate,@RequestParam("people") Optional<Integer> people){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/searchAparts");
        System.out.println("Country "+country+" , town "+town+" , area "+area+" ,arrival date " +arrivalDate+ " ,depdate "+departureDate +" ,people "+people);
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
        aparts= apartmentService.findAparts(country,town,area,arrivalDate,departureDate,people,new PageRequest(evalPage, evalPageSize));
        pager= new Pager(aparts.getTotalPages(), aparts.getNumber(), BUTTONS_TO_SHOW);
        if(aparts.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", aparts);
        }
        modelAndView.addObject("url","search");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        return modelAndView;
    }

    @RequestMapping(value={"/search2"}, method = RequestMethod.POST/*, produces= "application/javascript"*/)
    public ModelAndView search2(@RequestParam("pageSize") Optional<Integer> pageSize,
                                  @RequestParam("page") Optional<Integer> page,@RequestParam("heating") Optional<Integer> heating,@RequestParam("ac") Optional<Integer> ac,@RequestParam("internet") Optional<Integer> internet,@RequestParam("maxPrice") Optional<Float> maxPrice,@RequestParam("type") Optional<Integer> type,@RequestParam("kitchen") Optional<Integer> kitchen,@RequestParam("parking") Optional<Integer> parking,@RequestParam("elevator") Optional<Integer> elevator,@RequestParam("tv") Optional<Integer> tv){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/searchAparts");
        System.out.println("Max price "+maxPrice+" ,heating "+heating+" ,kitchen "+kitchen+" ,tv "+tv+" ,type "+type+" ,elevator "+elevator+" ,ac "+ac+" ,parking "+parking+" ,internet "+ internet);
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
        aparts= apartmentService.findAparts(heating,maxPrice,kitchen,tv,type,elevator,ac,internet,parking,new PageRequest(evalPage, evalPageSize));
        pager= new Pager(aparts.getTotalPages(), aparts.getNumber(), BUTTONS_TO_SHOW);
        if(aparts.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", aparts);
        }
        modelAndView.addObject("url","search2");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        return modelAndView;
    }

    @RequestMapping(value={"/search2"}, method = RequestMethod.GET/*, produces= "application/javascript"*/)
    public ModelAndView getsearch2(@RequestParam("pageSize") Optional<Integer> pageSize,
                                @RequestParam("page") Optional<Integer> page,@RequestParam("heating") Optional<Integer> heating,@RequestParam("ac") Optional<Integer> ac,@RequestParam("internet") Optional<Integer> internet,@RequestParam("maxPrice") Optional<Float> maxPrice,@RequestParam("type") Optional<Integer> type,@RequestParam("kitchen") Optional<Integer> kitchen,@RequestParam("parking") Optional<Integer> parking,@RequestParam("elevator") Optional<Integer> elevator,@RequestParam("tv") Optional<Integer> tv){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/searchAparts");
        System.out.println("Max price "+maxPrice+" ,heating "+heating+" ,kitchen "+kitchen+" ,tv "+tv+" ,type "+type+" ,elevator "+elevator+" ,ac "+ac+" ,heating "+heating+" ,internet "+ internet);
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
        aparts= apartmentService.findAparts(heating,maxPrice,kitchen,tv,type,elevator,ac,internet,parking,new PageRequest(evalPage, evalPageSize));
        pager= new Pager(aparts.getTotalPages(), aparts.getNumber(), BUTTONS_TO_SHOW);
        if(aparts.getTotalElements()!=0){
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("items", aparts);
        }
        modelAndView.addObject("url","search2");
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        return modelAndView;
    }
}
