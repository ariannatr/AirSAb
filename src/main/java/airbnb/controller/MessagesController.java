package airbnb.controller;

import airbnb.authentication.IAuthenticationFacade;
import airbnb.model.ApartmentEntity;
import airbnb.model.OwnerEntity;
import airbnb.model.RenterEntity;
import airbnb.model.MessagesEntity;
import airbnb.model.UsersEntity;
import airbnb.model.Pager;
import airbnb.repository.MessagesRepository;
import airbnb.repository.OwnerRepository;
import airbnb.repository.RenterRepository;
import airbnb.service.ApartmentService;
import airbnb.service.UsersService;
import airbnb.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Σταυρίνα on 21/9/2017.
 */
@Controller
public class MessagesController {
    @Autowired
    private UsersService userService;

    /*@Autowired
    private MessagesService messagesService;*/

    @Qualifier("messagesRepository")
    @Autowired
    private MessagesRepository messagesRepository;

    @Qualifier("renterRepository")
    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    @Qualifier("ownerRepository")
    private OwnerRepository ownerRepository;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @RequestMapping(value="/question/{apartmentID}", method = RequestMethod.POST)
    public ModelAndView question( @PathVariable String apartmentID, @RequestParam("question") String Question){
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        modelAndView.addObject("uname", authentication.getName());
        UsersEntity userS = userService.findByUsername(authentication.getName());
        modelAndView.addObject("type", String.valueOf(userS.getType()));

        Integer apartmentID_ = Integer.parseInt(apartmentID);
        ApartmentEntity ap = apartmentService.findById(apartmentID_);

        RenterEntity renter = userService.findRenterByUsername(userS.getUsername());
        OwnerEntity owner=ap.getOwner();
        saveMessage(Question,renter,apartmentID_,ap.getName(),owner);


        System.out.println("prosthesa to message");


        modelAndView.setViewName("redirect:/apartment/"+apartmentID);
        return modelAndView;
    }

    private void saveMessage(String Question,RenterEntity renter,Integer apartmentID_,String apartment_name,OwnerEntity owner)
    {

        MessagesEntity messagesEntity=new MessagesEntity();
        messagesEntity.setQuestion(Question);
        messagesEntity.setOwner(owner);
        messagesEntity.setRenter(renter);
        messagesEntity.setApart_id(apartmentID_);
        messagesEntity.setApart_name(apartment_name);
        messagesRepository.save(messagesEntity);

        Set<MessagesEntity> messsagesEntitySet1= owner.getMessages();
        messsagesEntitySet1.add(messagesEntity);
        ownerRepository.save(owner);
        Set<MessagesEntity> messsagesEntitySet2= renter.getMessages();
        messsagesEntitySet2.add(messagesEntity);
        renterRepository.save(renter);
    }
}

