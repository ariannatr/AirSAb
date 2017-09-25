package airbnb.repository;

import airbnb.model.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Arianna on 23/8/2017.
 */
@Repository("userRepository")
public interface UserRepository extends PagingAndSortingRepository<UsersEntity,String>{
    UsersEntity findByUsername(String username);
    @Query("select p from UsersEntity p where p.type=?1 or p.type=?2")
    Page<UsersEntity> findByType(int type, int type2, Pageable pageable);

    ArrayList<UsersEntity> findAll();

    @Query("select p from UsersEntity p where p.type=?1 or p.type=?2")
    ArrayList<UsersEntity> findByType(int type,int type2);
}
