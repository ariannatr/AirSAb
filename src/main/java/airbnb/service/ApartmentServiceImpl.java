package airbnb.service;

import airbnb.model.ApartmentEntity;
import airbnb.model.OwnerEntity;
import airbnb.repository.ApartmentRepository;
import airbnb.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
