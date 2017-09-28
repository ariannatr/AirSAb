package airbnb.service;

import airbnb.model.ReservationEntity;
import org.springframework.stereotype.Service;

/**
 * Created by Arianna on 28/9/2017.
 */
@Service("reservationService")
public interface ReservationService  {
    public void approveReservation(ReservationEntity reservation);
    public ReservationEntity findByReservationId(Integer id);
}
