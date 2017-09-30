package airbnb.service;

import airbnb.model.ApartmentEntity;
import airbnb.model.RenterEntity;
import airbnb.model.ReservationEntity;
import airbnb.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Arianna on 28/9/2017.
 */
@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;


    @Override
    public void approveReservation(ReservationEntity reservation){
        reservation.setApproval(1);
        reservationRepository.save(reservation);
    }

    @Override
    public ReservationEntity findByReservationId(Integer id){
        return reservationRepository.findById(id);
    }

    @Override
    public boolean hasReserved(RenterEntity renter, ApartmentEntity apartment){
        if(!reservationRepository.findAllByApartmentAndRenter(apartment,renter).isEmpty())
            return true;
        return false;
    }

    @Override
    public ArrayList<ReservationEntity> findAllByRenter(RenterEntity renterEntity)
    {
        return  reservationRepository.findAllByRenter(renterEntity);
    }

}
