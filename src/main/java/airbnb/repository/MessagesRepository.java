package airbnb.repository;

import airbnb.model.MessagesEntity;
import airbnb.model.RenterEntity;
import airbnb.model.OwnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Σταυρίνα on 21/9/2017.
 */

@Repository("messagesRepository")
public interface MessagesRepository extends PagingAndSortingRepository<MessagesEntity,Integer>{
     MessagesEntity findById(Integer id);
     Page<MessagesEntity> findAllByRenter(RenterEntity renter, Pageable pageable);
     //Page<MessagesEntity> findByOwner(OwnerEntity owner, Pageable pageable);
}
