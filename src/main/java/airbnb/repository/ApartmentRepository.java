package airbnb.repository;

import airbnb.model.ApartmentEntity;
import airbnb.model.OwnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Σταυρίνα on 28/8/2017.
*/

@Repository("apartmentRepository")
public interface ApartmentRepository extends PagingAndSortingRepository<ApartmentEntity,Integer> {
    ApartmentEntity findById(Integer id);

    List<ApartmentEntity> findAll();
    Page<ApartmentEntity> findAllByOwner(OwnerEntity owner, Pageable pageable);

    @Query("select p from ApartmentEntity p order by price")
    Page<ApartmentEntity> findAllOrderByPrice(Pageable pageable);

    @Query("select p from ApartmentEntity p order by price")
    List<ApartmentEntity> findAllOrderByPrice();

    List<ApartmentEntity> findAllByPrice(Float price);

    @Query("select p from ApartmentEntity p where p.price<=?1 order by price")
    List<ApartmentEntity> findAllByPriceLessThanEqual(Float price);

    @Query("select p from ApartmentEntity p where p.kitchen=?1 order by price")
    List<ApartmentEntity> findAllByKitchen(Integer kitchen);

    @Query("select p from ApartmentEntity p where p.heating=?1 order by price")
    List<ApartmentEntity> findAllByHeating(Integer heating);

    @Query("select p from ApartmentEntity p where p.ac=?1 order by price")
    List<ApartmentEntity> findAllByAc(Integer ac);

    @Query("select p from ApartmentEntity  p where p.internet=?1 order by price")
    List<ApartmentEntity> findAllByInternet(Integer internet);

    @Query("select p from ApartmentEntity p where p.parking=?1 order by price")
    List<ApartmentEntity> findAllByParking(Integer parking);

    @Query("select p from ApartmentEntity p where p.type=?1 order by price")
    List<ApartmentEntity> findAllByType(Integer type);


    @Query("select p from ApartmentEntity p where p.elevator=?1 order by price")
    List<ApartmentEntity> findAllByElevator(Integer elevator);

    @Query("select p from ApartmentEntity p where p.tv=?1 order by price")
    List<ApartmentEntity> findAllByTv(Integer tv);

    @Query("select p from ApartmentEntity p where p.country=?1 order by price")
    List<ApartmentEntity> findAllByCountry(String country);

    @Query("select p from ApartmentEntity p where p.town=?1 order by price")
    List<ApartmentEntity> findAllByTown(String town);

    @Query("select p from ApartmentEntity p where p.area=?1 order by price")
    List<ApartmentEntity> findAllByArea(String area);

    @Query("select p from ApartmentEntity p where p.capacity>=?1 order by price")
    List<ApartmentEntity> findAllByCapacityIsGreaterThanEqual(Integer capacity);

    @Query("select p from ApartmentEntity p where  p.startdate<=?1 order by price")
    List<ApartmentEntity> findAllByStartDate(String date);

    @Query("select p from ApartmentEntity p where  p.finaldate>=?1 order by price")
    List<ApartmentEntity> findAllByFinalDate(String date);

//    @Query("select p from ApartmentEntity p where p.capacity=?1 order by price")
//    List<ApartmentEntity> findByCapacity(Integer capacity);

}

