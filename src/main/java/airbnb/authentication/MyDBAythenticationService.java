package airbnb.authentication;

import airbnb.model.UsersEntity;
import airbnb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arianna on 24/8/2017.
**/
@Service
public class MyDBAythenticationService implements UserDetailsService {
    @Autowired
    private UsersService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        // [USER,ADMIN,..]
        Integer roles= user.getType();

        List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
        if(roles!= -1)  {
                            // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roles);
                grantList.add(authority);
                System.out.println("Has role"+roles);

        }

        UserDetails userDetails = (UserDetails) new User(user.getUsername(),
                user.getPassword(),grantList);

        return userDetails;
    }
}
