package airbnb.service;

import airbnb.model.ApartmentEntity;
import airbnb.model.OwnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import java.security.acl.Owner;


@Service("apartmentService")
public interface ApartmentService {
    public ApartmentEntity findById(Integer id);
    public  void saveApartment(ApartmentEntity apartmentEntity, OwnerEntity ownerEntity,String photograph);
    public ArrayList<String> getFeatures(ApartmentEntity apartmentEntity);
    public String getType(ApartmentEntity apartmentEntity);
    public void updateApartment(ApartmentEntity ap, ApartmentEntity old);
    public Page<ApartmentEntity> findOwnersAparts(OwnerEntity ownerEntity,Pageable pageable);
    public void uploadPhoto(ApartmentEntity ap,String photo);
}