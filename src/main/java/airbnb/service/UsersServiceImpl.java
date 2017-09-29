package airbnb.service;

import airbnb.repository.*;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import airbnb.model.*;

import java.util.*;

@Service("usersService")
public class UsersServiceImpl implements UsersService {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("renterRepository")
    @Autowired
    private RenterRepository renterRepository;

    @Qualifier("ownerRepository")
    @Autowired
    private OwnerRepository ownerRepository;


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
    public void saveUser(UsersEntity user,String photopath) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println("Creating user..." + user.getPassword());

        user.setPhoto(photopath);
        userRepository.save(user);
        if(user.getType()==1)
        {
            OwnerEntity owner=new OwnerEntity();
            owner.setUsersByUsersUsername(user);
            owner.setUsersUsername(user.getUsername());
            owner.setApproval(0);
            ownerRepository.save(owner);
        }
        else if(user.getType()==2)
        {
            RenterEntity renter=new RenterEntity();
            renter.setUsersByUsersUsername(user);
            renter.setUsersUsername(user.getUsername());
            renterRepository.save(renter);
        }
        else if(user.getType()==3){
            OwnerEntity owner=new OwnerEntity();
            owner.setUsersByUsersUsername(user);
            owner.setUsersUsername(user.getUsername());
            owner.setApproval(0);
            RenterEntity renter=new RenterEntity();
            renter.setUsersUsername(user.getUsername());
            renter.setUsersByUsersUsername(user);

            Set<ReservationEntity> res0=new HashSet<ReservationEntity>(0);
            renter.setReservationsByUsersUsername(res0);
            ownerRepository.save(owner);
            renterRepository.save(renter);
        }
        //CookiesEntity cookie = new CookiesEntity();
        System.out.println("Creating  user...");

        //cookieRepository.save(cookie);
        System.out.println("Done.");
    }

    @Override
    public Page<OwnerEntity> findAllnotApproved(Pageable pageable){
        return ownerRepository.findAllByApproval(0,pageable);
    }

    @Override
    public OwnerEntity findOwnerByUsername(String username){
        return ownerRepository.findByUsersUsername(username);
    }

    @Override
    public RenterEntity findRenterByUsername(String username){return renterRepository.findByUsersUsername(username); }

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


    @Override
    public void updateUser(UsersEntity useron,UsersEntity user){
        if(!user.getEmail().replaceAll(" ","").equals("")){
            useron.setEmail(user.getEmail());
        }
        if(!user.getName().replaceAll(" ","").equals(""))
        {
            useron.setName(user.getName());
        }
        if(!user.getSurname().replaceAll(" ","").equals(""))
        {
            useron.setSurname(user.getSurname());
        }

        if(!String.valueOf(user.getTelephone()).replaceAll(" ","").equals(""))
        {
            useron.setTelephone(user.getTelephone());
        }
        if(!user.getPassword().replaceAll(" ","").equals(""))
        {
            useron.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(useron);

    }

    @Override
    public Page<UsersEntity> findAllPageable(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<UsersEntity> findAllRenters(Pageable pageable)
    {
        return userRepository.findByType(2,3,pageable);
    }

    @Override
    public Page<UsersEntity> findAllOwners(Pageable pageable)
    {
        return userRepository.findByType(1,3,pageable);
    }
/*
    public List<RenterEntity> getRenters(){
        return renterRepository.findAll();
    }

    public List<OwnerEntity> getOwners(){
        return (List<OwnerEntity>) ownerRepository.findAll();
    }

   */


    @Override
    public void approveOwner(OwnerEntity ownerEntity)
    {
        ownerEntity.setApproval(1);
        ownerRepository.save(ownerEntity);
    }

    @Override
    public String getType(UsersEntity user)
    {
        int type=user.getType();
        if(type==1)
            return ("Renter");
        else if(type==2)
            return( "Owner");
        else if(type==3)
            return ("Owner and Renter");
        else
            return( "Admin");
    }

    @Override
    public void uploadPhoto(UsersEntity useron,String photopath)
    {
        useron.setPhoto(photopath);
        userRepository.save(useron);
    }

    @Override
    public ArrayList<UsersEntity> findAll(){ return userRepository.findAll();}

    @Override
    public ArrayList<UsersEntity> findAllOwners()
    {
        return userRepository.findAllByType(1,3);
    }

    @Override
    public ArrayList<UsersEntity> findAllRenters()
    {
        return userRepository.findAllByType(2,3);
    }
}
