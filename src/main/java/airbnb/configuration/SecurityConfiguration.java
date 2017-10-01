package airbnb.configuration;


import airbnb.authentication.MyDBAythenticationService;
import airbnb.model.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@ComponentScan
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MyDBAythenticationService myDBAythenticationService;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myDBAythenticationService).passwordEncoder(bCryptPasswordEncoder);
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests()
                .antMatchers("/").permitAll()
				.antMatchers("/index").permitAll()
				.antMatchers("/register").anonymous()
				.antMatchers("/usernameCheck").anonymous()
                .antMatchers("/login").anonymous()
				.antMatchers("/users").hasRole("0")
                .antMatchers("/error").permitAll()
				.antMatchers("/searchAparts").permitAll()
                .antMatchers("/search","/search2").permitAll()
                .antMatchers("/profile").hasAnyRole("0","1","2","3")
//				.antMatchers("/admin").permitAll()
				.antMatchers("/renters").hasRole("0")
				.antMatchers("/apartment_reg").permitAll()
				.antMatchers("/apartment","/apartment/**").permitAll()
				.antMatchers("/apartment_update").hasAnyRole("1","3")
				.antMatchers("/owners").hasRole("0")
				.antMatchers("/apartslist").hasRole("0")
				.antMatchers("/messages").hasAnyRole("1","2","3")
				.antMatchers("/reserves").hasAnyRole("1","2","3")
				.antMatchers("/accept","/accept/**").hasRole("0")
				.antMatchers("/downloadusers","/downloadusers/**").hasRole("0")
				.antMatchers("/downloadapartments").hasRole("0")
				.antMatchers("/aparts").hasAnyRole("1","3")
				.antMatchers("/reservationslist").hasRole("0")
				.antMatchers("/resbyrenter").hasRole("0")
				.antMatchers("/downloadreservations","/downloadreservations/**").hasRole("0")
				.antMatchers("/critbyrenter").hasRole("0")
				.antMatchers("/critforowner").hasRole("0")
				.antMatchers("/downloadcriticsR","/downloadcriticsR/**").hasRole("0")
				.antMatchers("/downloadcriticsO","/downloadcriticsO/**").hasRole("0")
				.anyRequest().authenticated().and().anonymous().and()
                .csrf().disable()
                .formLogin()
				.loginPage("/index")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?success=false")
                .defaultSuccessUrl("/index")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/index").and().exceptionHandling();
				//.accessDeniedPage("/error");
        http.sessionManagement()//now added
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(2)
                .expiredUrl("/index");
        http.sessionManagement()
                .invalidSessionUrl("/index")
                .sessionFixation().migrateSession();
	}

    @Bean//lately added
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

  /*  @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }*/
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	       .antMatchers("/fragments/**","/resources/**","/static/**", "/css/**", "/js/**", "/images/**","/scripts/**","/vendor/**");
		web.ignoring().antMatchers("/the_js_path/**");
		web.ignoring().antMatchers("/image/**","/jquery/**");
//		web.ignoring().antMatchers("/apartment/**");
	}
}