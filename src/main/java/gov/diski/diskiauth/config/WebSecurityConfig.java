package gov.diski.diskiauth.config;

import gov.diski.diskiauth.domain.User;
import gov.diski.diskiauth.service.JdbcUserDetailsService;
import gov.diski.diskiauth.util.Oauth2LogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ShaPasswordEncoder passwordEncoder;

    @Autowired
    private Oauth2LogoutHandler logoutHandler;

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new JdbcUserDetailsService();
    }


//    @Autowired
//    public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
//    	// @formatter:off
//		auth.inMemoryAuthentication()
//				.withUser("john").password("123").roles("USER")
//				.and()
//				.withUser("tom").password("111").roles("ADMIN");
//		// @formatter:on
//    }

    @Override
	@Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setSaltSource(new SaltSource() {
            @Override
            public Object getSalt(UserDetails userDetails) {
                User user = (User) userDetails;
                return user.getSalt();
            }
        });

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
		http
				.authorizeRequests()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated()
				.and().formLogin().permitAll();
		// @formatter:on
    }

}