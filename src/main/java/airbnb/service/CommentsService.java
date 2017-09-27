package airbnb.service;

import airbnb.model.ApartmentEntity;
import airbnb.model.CommentsEntity;
import airbnb.model.RenterEntity;
import org.springframework.stereotype.Service;

/**
 * Created by Arianna on 25/9/2017.
 */
@Service("commentsService")
public interface CommentsService {
    public  void saveComment(CommentsEntity comment, RenterEntity renter, ApartmentEntity apartment);

}
