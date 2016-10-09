package nl.paardenvriendjes.application;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.auth0.spring.security.api.Auth0SecurityConfig;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)

public class AppSecurityConfig extends Auth0SecurityConfig {


    /**
     * Provides Auth0 API access
     */
    @Bean
    public Auth0Client auth0Client() {
        return new Auth0Client(clientId, issuer);
    }

    /**
     *  Our API Configuration - for Profile CRUD operations
     *
     *  Here we choose not to bother using the `auth0.securedRoute` property configuration
     *  and instead ensure any unlisted endpoint in our config is secured by default
     */
    @Override
    protected void authorizeRequests(final HttpSecurity http) throws Exception {
        // include some Spring Boot Actuator endpoints to check metrics
        // add others or remove as you choose, this is just a sample config to illustrate
        // most specific rules must come - order is important (see Spring Security docs)
        http.authorizeRequests()
        
                .antMatchers(HttpMethod.GET, "/welcome").permitAll()
                .antMatchers(HttpMethod.GET, "/adminsafewelcome").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/usersafewelcome").hasAnyAuthority("USER")
                .antMatchers("/comments/**").authenticated()
                .antMatchers("/members/friend/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/members/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/messages/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/likes/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/paspoorts/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/horses/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/events/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest().authenticated();
        
        http.csrf().disable();
        http.cors().disable();
        
    }    
}


