package airbnb.repository;

import airbnb.model.CommentsEntity;
import airbnb.model.MessagesEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arianna on 25/9/2017.
 */
@Repository("commentsRepository")
public interface CommentsRepository extends PagingAndSortingRepository<CommentsEntity,Integer> {

}
