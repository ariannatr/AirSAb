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

/**
 * Created by Σταυρίνα on 28/8/2017.
*/

@Repository("apartmentRepository")
public interface ApartmentRepository extends PagingAndSortingRepository<ApartmentEntity,Integer> {
    ApartmentEntity findById(Integer id);
    Page<ApartmentEntity> findAllByOwner(OwnerEntity owner, Pageable pageable);

    @Query("select p from ApartmentEntity p order by price")
    Page<ApartmentEntity> findAllOrderByPrice(Pageable pageable);

    Page<ApartmentEntity> findAllByPrice(Float price,Pageable pageable);

    @Query("select p from ApartmentEntity p where p.price<=?1 order by price")
    Page<ApartmentEntity> findAllByPriceLessThanEqual(Float price ,Pageable pageable);

    @Query("select p from ApartmentEntity p where p.kitchen=?1 order by price")
    Page<ApartmentEntity> findAllByKitchen(Integer kitchen,Pageable pageable);

    @Query("select p from ApartmentEntity p where p.heating=?1 order by price")
    Page<ApartmentEntity> findAllByHeating(Integer heating,Pageable pageable);

    @Query("select p from ApartmentEntity p where p.ac=?1 order by price")
    Page<ApartmentEntity> findAllByAc(Integer ac,Pageable pageable);

    @Query("select p from ApartmentEntity  p where p.internet=?1 order by price")
    Page<ApartmentEntity> findAllByInternet(Integer internet,Pageable pageable);

    @Query("select p from ApartmentEntity p where p.parking=?1 order by price")
    Page<ApartmentEntity> findAllByParking(Integer parking,Pageable pageable);

    @Query("select p from ApartmentEntity p where p.type=?1 order by price")
    Page<ApartmentEntity> findAllByType(Integer type,Pageable pageable);


    @Query("select p from ApartmentEntity p where p.elevator=?1 order by price")
    Page<ApartmentEntity> findAllByElevator(Integer elevator,Pageable pageable);

    @Query("select p from ApartmentEntity p where p.tv=?1 order by price")
    Page<ApartmentEntity> findAllByTv(Integer tv,Pageable pageable);

    @Query("select p from ApartmentEntity p where p.country=?1 order by price")
    Page<ApartmentEntity> findAllByCountry(String country,Pageable pageable);

    @Query("select p from ApartmentEntity p where p.town=?1 order by price")
    Page<ApartmentEntity> findAllByTown(String town,Pageable pageable);

    @Query("select p from ApartmentEntity p where p.area=?1 order by price")
    Page<ApartmentEntity> findAllByArea(String area,Pageable pageable);

    @Query("select p from ApartmentEntity p where p.capacity>=?1 order by price")
    Page<ApartmentEntity> findAllByCapacityIsGreaterThanEqual(Integer capacity,Pageable pageable);

    @Query("select p from ApartmentEntity p where  p.startdate<=?1 order by price")
    Page<ApartmentEntity> findAllByStartDate(String date,Pageable pageable);

    @Query("select p from ApartmentEntity p where  p.finaldate>=?1 order by price")
    Page<ApartmentEntity> findAllByFinalDate(String date,Pageable pageable);

    ArrayList<ApartmentEntity> findAll();
}

