package airbnb.repository;

import airbnb.model.OwnerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arianna on 24/8/2017.
 */
@Repository("ownerRepository")
public interface OwnerRepository extends PagingAndSortingRepository<OwnerEntity,String> {
    OwnerEntity findByUsersByUsersUsername(String username);

}

