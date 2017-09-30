package airbnb.service;

import airbnb.model.*;
import airbnb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;


/**
 * Created by Σταυρίνα on 30/9/2017.
 */
@Service("cookieService")
public class CookieServiceImpl implements CookieService{
    //@Qualifier("renterRepository")
    @Autowired
    private RenterRepository renterRepository;

    //@Qualifier("cookieRepository")
    @Autowired
    private CookieApRepository cookieApRepository;

    @Qualifier("cookieSearchRepository")
    @Autowired
    private CookieSearchRepository cookieSearchRepository;

    @Override
    public  void saveCookieAp(RenterEntity renterEntity, Integer apart_id)
    {
        CookieApEntity cookieEntity=cookieApRepository. findByRenterAndApartmentid(renterEntity,apart_id);
        if(cookieEntity!=null) {
            cookieEntity.setTimes(cookieEntity.getTimes() + 1);
            cookieApRepository.save(cookieEntity);
        }
        else
        {
            CookieApEntity newcookie=new CookieApEntity();
            newcookie.setRenter(renterEntity);
            newcookie.setApartmentid(apart_id);
            newcookie.setTimes(1);
            cookieApRepository.save(newcookie);
            Set<CookieApEntity> cookieEntities=renterEntity.getCookieAp();
            cookieEntities.add(newcookie);
            renterRepository.save(renterEntity);
        }

    }

    public void removeAllCookieApbyRenter(RenterEntity renter)
    {
        ArrayList<CookieApEntity> cookieApEntities=cookieApRepository.findAllByRenter(renter);
        for(CookieApEntity cookieApEntity :cookieApEntities)
        {
            Integer c_id=cookieApEntity.getId();
            cookieApRepository.deleteById(c_id);
        }
    }

    public  void saveCookieSearch(RenterEntity renterEntity, Optional<Integer> num, Optional<String> country, Optional<String> town, Optional<String> area)
    {
        if((!num.isPresent()) && (!country.isPresent()) && (!town.isPresent()) && (!area.isPresent()))
            return;

        CookieSearchEntity newcookie=new CookieSearchEntity();
        String towhere=new String("") ;
        if(num.isPresent())
           newcookie.setNum(num.get());
        else
            newcookie.setNum(1);
        if(country.isPresent() && !country.get().replaceAll(" ","").equals(""))
            towhere=towhere.concat(country.get());
        towhere=towhere.concat(";");
        if(town.isPresent() && !town.get().replaceAll(" ","").equals(""))
            towhere=towhere.concat(town.get());
        towhere=towhere.concat(";");
        if(area.isPresent() && !area.get().replaceAll(" ","").equals(""))
            towhere=towhere.concat(area.get());

        newcookie.setLocation(towhere);
        newcookie.setRenter(renterEntity);

        cookieSearchRepository.save(newcookie);

        Set<CookieSearchEntity> cookieEntities=renterEntity.getCookieSearch();
        cookieEntities.add(newcookie);
        renterRepository.save(renterEntity);

    }


    public void removeAllCookieSearchbyRenter(RenterEntity renter)
    {
        ArrayList<CookieSearchEntity> cookieEntities=cookieSearchRepository.findAllByRenter(renter);
        for(CookieSearchEntity cookieEntity :cookieEntities)
        {
            Integer c_id=cookieEntity.getId();
            cookieSearchRepository.deleteById(c_id);
        }
    }
}

