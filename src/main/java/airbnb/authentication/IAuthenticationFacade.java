package airbnb.authentication;

import org.springframework.security.core.Authentication;

/**
 * Created by Arianna on 24/8/2017.
 */
public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
