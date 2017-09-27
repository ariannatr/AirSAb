package airbnb.service;

import airbnb.model.ApartmentEntity;
import airbnb.model.OwnerEntity;
import airbnb.model.RenterEntity;
import airbnb.model.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.ParseException;
import java.util.ArrayList;

import java.security.acl.Owner;
import java.util.Date;
import java.util.Optional;


@Service("apartmentService")
public interface ApartmentService {
    public ApartmentEntity findById(Integer id);
    public  void saveApartment(ApartmentEntity apartmentEntity, OwnerEntity ownerEntity,String photograph);
    public ArrayList<String> getFeatures(ApartmentEntity apartmentEntity);
    public String getType(ApartmentEntity apartmentEntity);
    public void updateApartment(ApartmentEntity ap, ApartmentEntity old);
    public Page<ApartmentEntity> findOwnersAparts(OwnerEntity ownerEntity,Pageable pageable);
    public void uploadPhoto(ApartmentEntity ap,String photo);
    public void uploadPhoto2(ApartmentEntity ap,String photo);
    public void uploadPhoto3(ApartmentEntity ap,String photo);
    public void uploadPhoto4(ApartmentEntity ap,String photo);
    public Page<ApartmentEntity> findAparts(Pageable pageable);
    public Page<ApartmentEntity> findAparts(Optional<String> arrivalDate,Optional<String> departureDate,Optional<Integer> people,Optional<String> town,Optional<String> area,Optional<String> country,Optional<Integer>heating,Optional<Float> maxPrice,Optional<Integer> kitchen,Optional<Integer> tv,Optional<Integer> type,Optional<Integer> elevator,Optional<Integer> ac,Optional<Integer> internet,Optional<Integer> parking,Pageable pageable) throws ParseException;
    public Page<ApartmentEntity> findAparts(Optional<String> country,Optional<String> town,Optional<String> area,Optional<String> arrivalDate,Optional<String> departureDate,Optional< Integer> people, Pageable pageable) throws ParseException;
    public  int makeReservation(ReservationEntity reservation, ApartmentEntity apart, RenterEntity renter) throws ParseException;
    public void removephoto(ApartmentEntity apartmentEntity, Integer num);
    public Page<ApartmentEntity> findAllPageable(Pageable pageable);

    public ArrayList<ApartmentEntity> findAll();
    public Page<ReservationEntity> findAllReservations(Pageable pageable);
    public Page<ReservationEntity> findAllReservationsByRenter(RenterEntity renter,Pageable pageable);

    public ArrayList<ReservationEntity> findAllReservations();
    public ArrayList<ReservationEntity> findAllReservationsByRenter(RenterEntity renter);
}