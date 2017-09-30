package airbnb.repository;

import airbnb.model.MessagesEntity;
import airbnb.model.RenterEntity;
import airbnb.model.OwnerEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Σταυρίνα on 21/9/2017.
 */

@Repository("messagesRepository")
public interface MessagesRepository extends PagingAndSortingRepository<MessagesEntity,Integer>{
     MessagesEntity findById(Integer id);

     @Transactional
     void deleteById(Integer id);

     @Query("select p from MessagesEntity p where p.renterfrom=?1  order by id desc  ")
     Page<MessagesEntity> findAllByRenter(RenterEntity renter, Pageable pageable);

     @Query("select p from MessagesEntity p where p.ownerto=?1  order by  id  desc ")
     Page<MessagesEntity> findByAllByOwner(OwnerEntity owner, Pageable pageable);
}
