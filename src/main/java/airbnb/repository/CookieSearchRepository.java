package airbnb.repository;

import airbnb.model.CookieApEntity;
import airbnb.model.CookieSearchEntity;
import airbnb.model.RenterEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

/**
 * Created by Σταυρίνα on 30/9/2017.
 */
@Repository("cookieSearchRepository")
public interface CookieSearchRepository extends PagingAndSortingRepository<CookieSearchEntity,Integer> {
    CookieSearchEntity findById(Integer id);

    ArrayList<CookieSearchEntity> findAllByRenter(RenterEntity renterEntity);

    @Transactional
    void deleteById(Integer id);
}
