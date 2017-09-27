package airbnb.service;

import airbnb.model.ApartmentEntity;
import airbnb.model.CommentsEntity;
import airbnb.model.RenterEntity;
import airbnb.repository.ApartmentRepository;
import airbnb.repository.CommentsRepository;
import airbnb.repository.MessagesRepository;
import airbnb.repository.RenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Arianna on 25/9/2017.
 */
@Service("commentsService")
public class CommentsServiceImpl implements CommentsService {

    @Qualifier("commentsRepository")
    @Autowired
    private CommentsRepository commentsRepository;

    @Qualifier("renterRepository")
    @Autowired
    private RenterRepository renterRepository;

    @Qualifier("apartmentRepository")
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public  void saveComment(CommentsEntity comment, RenterEntity renter, ApartmentEntity apartment){
        comment.setApartmentEntity(apartment);
        comment.setRenter(renter);
        comment.setApartmentOwner(apartment.getOwner());
        commentsRepository.save(comment);


        Float rating=apartment.getRating()*apartment.getReviews();
        apartment.setReviews(apartment.getReviews()+1);
        rating=(rating+comment.getRating())/apartment.getReviews();
        apartment.setRating(rating);
        Set<CommentsEntity> commentsEntitySet=apartment.getComments();
        commentsEntitySet.add(comment);
        apartment.setComments(commentsEntitySet);
        apartmentRepository.save(apartment);

        Set<CommentsEntity> renterComments=renter.getComments();
        renterComments.add(comment);
        renter.setComments(renterComments);
        renterRepository.save(renter);
    }

}
