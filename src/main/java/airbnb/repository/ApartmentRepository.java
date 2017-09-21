package airbnb.repository;

import airbnb.model.ApartmentEntity;
import airbnb.model.OwnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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

    Page<ApartmentEntity> findAllByPriceLessThanEqual(Float price ,Pageable pageable);

    Page<ApartmentEntity> findAllByKitchen(Integer kitchen,Pageable pageable);

    Page<ApartmentEntity> findAllByHeating(Integer heating,Pageable pageable);

    Page<ApartmentEntity> findAllByAc(Integer ac,Pageable pageable);

    Page<ApartmentEntity> findAllByInternet(Integer internet,Pageable pageable);

    Page<ApartmentEntity> findAllByParking(Integer parking,Pageable pageable);

    Page<ApartmentEntity> findAllByType(Integer type,Pageable pageable);

    Page<ApartmentEntity> findAllByElevator(Integer elevator,Pageable pageable);

    Page<ApartmentEntity> findAllByTv(Integer tv,Pageable pageable);

    Page<ApartmentEntity> findAllByCountry(String country,Pageable pageable);

    Page<ApartmentEntity> findAllByTown(String town,Pageable pageable);

    Page<ApartmentEntity> findAllByArea(String area,Pageable pageable);

    Page<ApartmentEntity> findAllByCapacityIsGreaterThanEqual(Integer capacity,Pageable pageable);

    @Query("select p from ApartmentEntity p where  p.startdate<=?1")
    Page<ApartmentEntity> findAllByStartDate(String date,Pageable pageable);

    @Query("select p from ApartmentEntity p where  p.finaldate>=?1")
    Page<ApartmentEntity> findAllByFinalDate(String date,Pageable pageable);
}

