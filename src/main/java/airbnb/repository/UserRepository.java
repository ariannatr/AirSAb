package airbnb.repository;

import airbnb.model.UsersEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Created by Arianna on 23/8/2017.
 */
@Repository("userRepository")
public interface UserRepository extends PagingAndSortingRepository<UsersEntity,String>{
    UsersEntity findByUsername(String username);
}
