package nl.paardenvriendjes.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "nl.paardenvriendjes")
@EnableCaching
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class}
)
@EnableTransactionManagement
@PropertySources({
	  @PropertySource("classpath:application.properties"),
	  @PropertySource("classpath:auth0.properties"),
	  @PropertySource(value = { "classpath:application.properties" })
	})

public class Application  {

	public static void main(String[] args) throws Exception {
	   System.setProperty("server.servlet.context-path", "/paardenvriendjes");
	   SpringApplication.run(Application.class, args);
	}
}

