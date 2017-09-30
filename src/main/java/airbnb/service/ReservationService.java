package airbnb.service;

import airbnb.model.ApartmentEntity;
import airbnb.model.RenterEntity;
import airbnb.model.ReservationEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Arianna on 28/9/2017.
 */
@Service("reservationService")
public interface ReservationService  {
    public void approveReservation(ReservationEntity reservation);
    public ReservationEntity findByReservationId(Integer id);
    public boolean hasReserved(RenterEntity renter, ApartmentEntity apartment);
   // public ArrayList<ReservationEntity> findByRenter
}
