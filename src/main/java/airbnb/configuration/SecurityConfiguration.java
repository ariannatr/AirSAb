package airbnb.configuration;


import airbnb.authentication.MyDBAythenticationService;
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
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
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
                .antMatchers("/users").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/search","/search2").permitAll()
                .antMatchers("/profile").hasAnyRole("0","1","2","3")
				.antMatchers("/admin").permitAll()
				.antMatchers("/renters").permitAll()
				.antMatchers("/apartment_reg").permitAll()
				.antMatchers("/apartment").permitAll()
				.antMatchers("/apartment_update").permitAll()
				.antMatchers("/owners").permitAll()
				.antMatchers("/accept","/accept/**").permitAll()
				.antMatchers("/aparts").hasAnyRole("1","3")
				.anyRequest()//be admin
				.authenticated().and().csrf().disable()
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
	}
}