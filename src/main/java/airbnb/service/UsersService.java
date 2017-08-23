package airbnb.service;

import airbnb.model.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("usersService")
public interface UsersService {
    public UsersEntity findByUsername(String username);
   // public void saveUser(UsersEntity user, ParentEntity parent,String photopath);
   public void saveUser(UsersEntity user);
   // public void saveUserProvider(UserEntity user, ProviderEntity provider);
 //  public void saveUserOwner(UsersEntity user, OwnerEntity owner);
    public UsersEntity findByUsernamePassword(String username, String Password);
    //public List<UsersEntity> findUsers();
  //  public List<RenterEntity> getRenters();
    //public List<OwnerEntity> getOwners();

   // public  RenterEntity findRenter(RenterPK renterPk);
   // public OwnerEntity findProvider(OwnerPK ownerPk);

  //  public void updateUser(ParentEntity parenton,ParentEntity parent,UsersEntity useron,UsersEntity user);

    //public void updateUserProvider(ProviderEntity provideron,ProviderEntity provider,UserEntity useron,UserEntity user);
    // public void approveProvider(OwnerEntity ownerEntity);
   // public void uploadPhoto(ParentEntity parenton,ParentEntity parent,UserEntity useron,UserEntity user,String photopath);
}