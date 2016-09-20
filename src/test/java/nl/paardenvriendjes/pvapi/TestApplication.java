package nl.paardenvriendjes.pvapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "nl")
@EnableAutoConfiguration
@EnableTransactionManagement
@PropertySources({
	  @PropertySource("classpath:testapplication.properties"),
	  @PropertySource("classpath:auth0.properties"),
	  @PropertySource(value = { "classpath:testapplication.properties" })
	})


public class TestApplication {

	public static void main(String[] args) {
				
		SpringApplication.run(TestApplication.class, args);
	}
		
}
