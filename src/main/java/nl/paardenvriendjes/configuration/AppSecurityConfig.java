package nl.paardenvriendjes.configuration;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER)
// enable pre post method security
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Our API Configuration - for Profile CRUD operations
     * <p>
     * Here we choose not to bother using the `auth0.securedRoute` property
     * configuration and instead ensure any unlisted endpoint in our config is
     * secured by default
     */

    protected void authorizeRequests(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // most specific rules must come - order is important (see
                // Spring Security docs)
                .antMatchers("/welcome").permitAll()
                .antMatchers(HttpMethod.GET, "/authenticateduserrole").hasAnyRole("USER", "ADMIN")
                .antMatchers("/comments/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/members/friend/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/members/signup").permitAll()
                .antMatchers("/members/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/messages/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/likes/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/paspoorts/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/horses/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/events/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/authenticatedwelcome").fullyAuthenticated();

    }
}
