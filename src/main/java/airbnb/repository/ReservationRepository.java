package airbnb.repository;

import airbnb.model.ReservationEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arianna on 21/9/2017.
 */
@Repository("reservationRepository")
public interface ReservationRepository extends PagingAndSortingRepository<ReservationEntity,Long> {
}
