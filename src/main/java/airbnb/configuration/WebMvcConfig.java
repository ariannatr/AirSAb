package airbnb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	/**Define Url Pages **/
	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		registry.addViewController("/").setViewName("/index");
		registry.addViewController("/index").setViewName("/index");
		registry.addViewController("/admin").setViewName("/admin");
		registry.addViewController("/login").setViewName("/login");
		registry.addViewController("/register").setViewName("/register");
		registry.addViewController("/users").setViewName("/users");
		registry.addViewController("/profile").setViewName("/profile");
		registry.addViewController("/renters").setViewName("/users");
		registry.addViewController("/owners").setViewName("/users");
		registry.addViewController("/accept").setViewName("/users");
		registry.addViewController("/apartment_reg").setViewName("/apartment_reg");
		registry.addViewController("/apartment").setViewName("/apartment");
		registry.addViewController("/apartment/**").setViewName("/apartment");
		registry.addViewController("/apartment_update").setViewName("/apartment");
		registry.addViewController("/aparts").setViewName("/aparts");
		registry.addViewController("/searchAparts").setViewName("/searchAparts");
		registry.addViewController("/messages").setViewName("/messages");
		registry.addViewController("/apartslist").setViewName("/apartslist");
		registry.addViewController("/reservationslist").setViewName("/reservationslist");
	}
}