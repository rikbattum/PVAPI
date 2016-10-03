package nl.paardenvriendjes.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.cache.annotation.EnableCaching;

@Configuration
@ComponentScan(basePackages = "nl.paardenvriendjes")
@EnableCaching
@EnableAutoConfiguration
@EnableTransactionManagement
@PropertySources({
	  @PropertySource("classpath:application.properties"),
	  @PropertySource("classpath:auth0.properties"),
	  @PropertySource(value = { "classpath:application.properties" })
	})


public class Application {

	public static void main(String[] args) {
				
		SpringApplication.run(Application.class, args);
	}
	
	
}

