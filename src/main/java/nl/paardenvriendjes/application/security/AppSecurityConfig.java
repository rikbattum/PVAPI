package nl.paardenvriendjes.application.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;

@Configuration
@EnableWebSecurity(debug = true)
// enable pre post method security
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)

public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     *  Our API Configuration - for Profile CRUD operations
     *
     *  Here we choose not to bother using the `auth0.securedRoute` property configuration
     *  and instead ensure any unlisted endpoint in our config is secured by default
     */    
	
	    @Value(value = "${auth0.apiAudience}")
	    private String apiAudience;
	    @Value(value = "${auth0.issuer}")
	    private String issuer;
	
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http)
                .authorizeRequests()
                // most specific rules must come - order is important (see Spring Security docs)
                .antMatchers("/welcome").permitAll()
                .antMatchers("/authenticatedwelcome").fullyAuthenticated()
                .antMatchers("/authenticateduserrole").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/comments/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/members/friend/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/members/signup").permitAll()
                .antMatchers("/members/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/messages/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/likes/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/paspoorts/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/horses/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/events/**").hasAnyAuthority("USER", "ADMIN")     
                .anyRequest().authenticated();
        
         http.csrf().disable();
         http.cors().disable();
    }
    
    
    
    
}


