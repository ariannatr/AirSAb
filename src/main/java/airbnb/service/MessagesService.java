package airbnb.service;

import airbnb.model.MessagesEntity;
import airbnb.model.RenterEntity;
import airbnb.model.OwnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.hibernate.validator.internal.util.logging.Messages;
import org.springframework.stereotype.Service;

/**
 * Created by Σταυρίνα on 21/9/2017.
 */

@Service("messagesService")
public interface MessagesService {
    public  void saveMessages(String message, RenterEntity renterEntity, Integer apart_id,String apart_name,OwnerEntity ownerEntity);

}
