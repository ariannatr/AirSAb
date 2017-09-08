package airbnb.service;

import airbnb.model.ApartmentEntity;
import airbnb.model.OwnerEntity;
import airbnb.repository.ApartmentRepository;
import airbnb.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Arianna on 29/8/2017.
 */
@Service("apartmentService")
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private OwnerRepository ownerRepository;


    @Override
    public ApartmentEntity findById(Integer id){
        return  apartmentRepository.findById(id);
    }

    @Override
    public  void saveApartment(ApartmentEntity apartmentEntity, OwnerEntity ownerEntity)
    {
        apartmentEntity.setOwnerByOwner(ownerEntity);
        apartmentEntity.setPhoto(" ");
        apartmentRepository.save(apartmentEntity);
        Set<ApartmentEntity> apartmentEntitySet=ownerEntity.getApartments();
        apartmentEntitySet.add(apartmentEntity);
        ownerRepository.save(ownerEntity);
    }

    @Override
    public ArrayList<String> getFeatures(ApartmentEntity apartmentEntity){
        ArrayList<String> features=new ArrayList<String>();
        if(apartmentEntity.getParking()==1) features.add("Parking");
        if(apartmentEntity.getInternet()==1) features.add("Internet");
        if(apartmentEntity.getAc()==1) features.add("Air Condition");
        if(apartmentEntity.getElevator()==1) features.add("Elevator");
        if(apartmentEntity.getTv()==1) features.add("Tv");
        if(apartmentEntity.getKitchen()==1) features.add("Kitchen");
        if(apartmentEntity.getHeating()==1) features.add("Central Heating");
        if(apartmentEntity.getLivingroom()==1) features.add("Living Room");
        if(apartmentEntity.getPets()==1) features.add("Pets Allowed");
        if(apartmentEntity.getSmoking()==1) features.add("Smoking Allowed");
        if(apartmentEntity.getEvents()==1) features.add("For Events");
        return features;
    }


    @Override
    public String getType(ApartmentEntity apartmentEntity)
    {
        int type=apartmentEntity.getType();
        if(type==0)
            return "Private Residence";
        else if(type==1)
            return "Public Residence";
        else if(type==2)
            return "Whole Residence";
        else
            return "Unknown";
    }



    @Override
    public void updateApartment(ApartmentEntity ap, ApartmentEntity old){
        if(!old.getDescription().replaceAll(" ","").equals("")){
            ap.setDescription(old.getDescription());
        }
        if(!old.getName().replaceAll(" ","").equals(""))
        {
            ap.setName(old.getName());
        }
        if(!old.getCountry().replaceAll(" ","").equals(""))
        {
            ap.setCountry(old.getCountry());
        }
        if(!old.getTown().replaceAll(" ","").equals(""))
        {
            ap.setTown(old.getTown());
        }
        if(!old.getArea().replaceAll(" ","").equals(""))
        {
            ap.setArea(old.getArea());
        }
        if(!old.getStartdate().replaceAll(" ","").equals(""))
        {
            ap.setStartdate(old.getStartdate());
        }
        if(!old.getFinaldate().replaceAll(" ","").equals(""))
        {
            ap.setFinaldate(old.getFinaldate());
        }

        if(!String.valueOf(old.getPrice()).replaceAll(" ","").equals(""))
        {
            ap.setPrice(old.getPrice());
        }
        if(!String.valueOf(old.getFloor()).replaceAll(" ","").equals(""))
        {
            ap.setFloor(old.getFloor());
        }
        if(!String.valueOf(old.getCapacity()).replaceAll(" ","").equals(""))
        {
            ap.setCapacity(old.getCapacity());
        }

        if(!String.valueOf(old.getSpaceArea()).replaceAll(" ","").equals(""))
        {
            ap.setSpaceArea(old.getSpaceArea());
        }
        if(!String.valueOf(old.getMinimumres()).replaceAll(" ","").equals(""))
        {
            ap.setMinimumres(old.getMinimumres());
        }
        if(!String.valueOf(old.getBaths()).replaceAll(" ","").equals(""))
        {
            ap.setBaths(old.getBaths());
        }
        ap.setParking(old.getParking());
        ap.setInternet(old.getInternet());
        ap.setAc(old.getAc());
        ap.setElevator(old.getElevator());
        ap.setTv(old.getTv());
        ap.setKitchen(old.getKitchen());
        ap.setHeating(old.getHeating());
        ap.setLivingroom(old.getLivingroom());

        ap.setPets(old.getPets());
        ap.setSmoking(old.getSmoking());
        ap.setEvents(old.getEvents());
        ap.setType(old.getType());
        apartmentRepository.save(ap);

    }

    @Override
    public Page<ApartmentEntity> findOwnersAparts(OwnerEntity ownerEntity, Pageable pageable){
              return apartmentRepository.findAllByOwner(ownerEntity,pageable);
    }
}
