package airbnb.service;

import airbnb.model.RenterEntity;
import airbnb.model.OwnerEntity;
import airbnb.model.MessagesEntity;
import airbnb.repository.MessagesRepository;
import airbnb.repository.OwnerRepository;
import airbnb.repository.RenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * Created by Σταυρίνα on 21/9/2017.
 */

public class MessagesServiceImpl implements MessagesService {

    @Qualifier("messagesRepository")
    @Autowired
    private MessagesRepository messagesRepository;

    @Qualifier("renterRepository")
    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    @Qualifier("ownerRepository")
    private OwnerRepository ownerRepository;

    @Override
    public  void saveMessages(String message, RenterEntity renterEntity, Integer apart_id,String apart_name,OwnerEntity ownerEntity){
        MessagesEntity messagesEntity=new MessagesEntity();
        messagesEntity.setQuestion(message);
        messagesEntity.setOwner(ownerEntity);
        messagesEntity.setRenter(renterEntity);
        messagesEntity.setApart_id(apart_id);
        messagesEntity.setApart_name(apart_name);
        messagesRepository.save(messagesEntity);

        Set<MessagesEntity> messsagesEntitySet1= ownerEntity.getMessages();
        messsagesEntitySet1.add(messagesEntity);
        ownerRepository.save(ownerEntity);
        Set<MessagesEntity> messsagesEntitySet2= renterEntity.getMessages();
        messsagesEntitySet2.add(messagesEntity);
        renterRepository.save(renterEntity);
    }
}
