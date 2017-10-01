package airbnb.repository;

import airbnb.model.CookieApEntity;
import airbnb.model.RenterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

/**
 * Created by Σταυρίνα on 30/9/2017.
 */
@Repository("cookieApRepository")
public interface CookieApRepository extends PagingAndSortingRepository<CookieApEntity,Integer>{
    CookieApEntity findById(Integer id);

    ArrayList<CookieApEntity> findAllByRenter(RenterEntity renterEntity);

    CookieApEntity findByRenterAndApartmentid(RenterEntity renterEntity,Integer apartmentid);

    ArrayList<CookieApEntity> findAllByRenterOrderByTimesDesc(RenterEntity renterEntity);

    @Transactional
    void deleteById(Integer id);
}
