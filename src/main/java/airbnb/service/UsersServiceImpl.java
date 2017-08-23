package airbnb.service;

import airbnb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import airbnb.model.*;

import java.util.*;

@Service("usersService")
public class UsersServiceImpl implements UsersService {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

   /* @Qualifier("renterRepository")
    @Autowired
    private RenterRepository renterRepository;*/

    /*@Qualifier("ownerRepository")
    @Autowired
    private OwnerRepository ownerRepository;*/


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UsersEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
        //return null;
    }
/*
    @Override
    public  ParentEntity findRenter(RenterPK renterPKPk){
        RenterEntity parent = renterRepository.findOne(renterPk);
        return parent;
    }

    @Override
    public OwnerEntity findProvider(OwnerPK ownerPk){
        OwnerEntity owner = ownerRepository.findOne(providerPk);
        return owner;
    }
*/
    @Override
    public UsersEntity findByUsernamePassword(String username,String password){
        UsersEntity userExists = findByUsername(username);
        if (userExists != null)
        {
            if (bCryptPasswordEncoder.matches((password),userExists.getPassword()))//
            {
                System.out.println("Same password");
                return userExists;
            }
            else
            {
                System.out.print("Username "+userExists.getUsername()+" pass in base "+userExists.getPassword()+" pass given "+password);
                return null;
            }
        }
        else
        {
            System.out.println("There is no user registered with this uname");
            return null;
        }
    }

    @Override
    public void saveUser(UsersEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println("Creating user..." + user.getPassword());
        user.setType(1);

        userRepository.save(user);
        //CookiesEntity cookie = new CookiesEntity();
        System.out.println("Creating parent user...");

        //renter.setUser(user);
      //  renter.setPhoto(photopath);

        //cookieRepository.save(cookie);
        System.out.println("Done.");
    }

  /*  @Override
    public Page<UsersEntity> findUsers(){
        return userRepository.findAll();
    }*/

    /*@Override
    public void saveUserProvider(UserEntity user, ProviderEntity provider) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println("Creating user...");
        user.setType(2);

        //user.setProviderByUserId(provider);
        userRepository.save(user);
        System.out.println("Creating provider user...");
        System.out.println("Trying to add " + provider.getName() + " " + provider.getSurname() + " " + provider.getEmail());
        provider.setUser(user);
        providerRepository.save(provider);
        System.out.println("Done.");
    }*/

/*
    @Override
    public void updateUserParent(ParentEntity parenton,ParentEntity parent,UserEntity useron,UserEntity user){
        if(!parent.getEmail().replaceAll(" ","").equals("")){
            parenton.setEmail(parent.getEmail());
        }
        if(!parent.getName().replaceAll(" ","").equals(""))
        {
            parenton.setName(parent.getName());
        }
        if(!parent.getSurname().replaceAll(" ","").equals(""))
        {
            parenton.setSurname(parent.getSurname());
        }

        if(!parent.getTelephone().replaceAll(" ","").equals(""))
        {
            parenton.setTelephone(parent.getTelephone());
        }
        if(!user.getPassword().replaceAll(" ","").equals(""))
        {
            useron.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(useron);
        parentRepository.save(parenton);
    }
*/


/*
    public List<RenterEntity> getRenters(){
        return renterRepository.findAll();
    }

    public List<OwnerEntity> getOwners(){
        return (List<OwnerEntity>) ownerRepository.findAll();
    }

   */

/*
    @Override
    public void approveProvider(OwnerEntity ownerEntity)
    {
        ownerEntity.setApproval(1);
        ownerRepository.save(ownerEntity);
    }*/
}
