package airbnb.service;

import airbnb.model.CookieApEntity;
import airbnb.model.RenterEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Σταυρίνα on 30/9/2017.
 */
@Service("cookieService")
public interface CookieService {
    public  void saveCookieAp(RenterEntity renterEntity, Integer apart_id);
    public void removeAllCookieApbyRenter(RenterEntity renter);

    public  void saveCookieSearch(RenterEntity renterEntity, Optional<Integer> num, Optional<String> country, Optional<String> town, Optional<String> area);
    public void removeAllCookieSearchbyRenter(RenterEntity renter);

    public ArrayList<CookieApEntity> findByRenterOrderByTimesDesc(RenterEntity renterEntity);
}
