package airbnb.repository;

import airbnb.model.CommentsEntity;
import airbnb.model.OwnerEntity;
import airbnb.model.RenterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by Arianna on 25/9/2017.
 */
@Repository("commentsRepository")
public interface CommentsRepository extends PagingAndSortingRepository<CommentsEntity,Integer> {

    Page<CommentsEntity> findAllByRenter(RenterEntity renter, Pageable pageable);
    Page<CommentsEntity> findAllByApartmentOwner(OwnerEntity owner, Pageable pageable);

    ArrayList<CommentsEntity> findAllByRenter(RenterEntity renter);
    ArrayList<CommentsEntity> findAllByApartmentOwner(OwnerEntity owner);
}
