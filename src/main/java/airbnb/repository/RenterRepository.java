package airbnb.repository;

import airbnb.model.CommentsEntity;
import airbnb.model.RenterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.ArrayList;

/**
 * Created by Arianna on 24/8/2017.
 */
@Repository("renterRepository")
public interface RenterRepository extends PagingAndSortingRepository<RenterEntity,String> {
    RenterEntity findByUsersUsername(String username);


    ArrayList<RenterEntity > findAll();


}

