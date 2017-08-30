package airbnb.repository;

import airbnb.model.ApartmentEntity;
import airbnb.model.OwnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Σταυρίνα on 28/8/2017.
*/

@Repository("apartmentRepository")
public interface ApartmentRepository extends PagingAndSortingRepository<ApartmentEntity,Integer> {
    ApartmentEntity findById(Integer id);
    Page<ApartmentEntity> findAllByOwner(OwnerEntity owner, Pageable pageable);
}

