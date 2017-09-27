package airbnb.repository;


import airbnb.model.ApartmentEntity;
import airbnb.model.RenterEntity;
import airbnb.model.RenterEntity;
import airbnb.model.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by Arianna on 21/9/2017.
 */
@Repository("reservationRepository")
public interface ReservationRepository extends PagingAndSortingRepository<ReservationEntity,Long> {

    Page<ReservationEntity> findAll(Pageable pageable);
    ArrayList<ReservationEntity> findAll();

    Page<ReservationEntity> findAllByRenter(RenterEntity renterEntity, Pageable pageable);
    ArrayList<ReservationEntity> findAllByRenter(RenterEntity renterEntity);

}
