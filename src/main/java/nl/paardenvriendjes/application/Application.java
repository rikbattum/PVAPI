package nl.paardenvriendjes.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@ComponentScan(basePackages = "nl.paardenvriendjes")
@EnableAutoConfiguration 
@EnableTransactionManagement
@PropertySources({
	  @PropertySource("classpath:application.properties"),
	  @PropertySource("classpath:auth0.properties")
	})


public class Application {

	public static void main(String[] args) {
				
		SpringApplication.run(Application.class, args);
	
		
	}
}
