package airbnb.repository;


import airbnb.model.*;
import airbnb.model.RenterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Arianna on 21/9/2017.
 */
@Repository("reservationRepository")
public interface ReservationRepository extends PagingAndSortingRepository<ReservationEntity,Integer> {

    Page<ReservationEntity> findAll(Pageable pageable);
    ArrayList<ReservationEntity> findAll();

    Page<ReservationEntity> findAllByRenter(RenterEntity renterEntity, Pageable pageable);
    ArrayList<ReservationEntity> findAllByRenter(RenterEntity renterEntity);

    Set<ReservationEntity> findAllByApartmentOwner(OwnerEntity ownerEntity);

    ReservationEntity findById(Integer id);

}
