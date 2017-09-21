package airbnb.repository;

import airbnb.model.RenterEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arianna on 24/8/2017.
 */
@Repository("renterRepository")
public interface RenterRepository extends PagingAndSortingRepository<RenterEntity,String> {
    RenterEntity findByUsersUsername(String username);

}

