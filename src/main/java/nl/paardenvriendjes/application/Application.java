package nl.paardenvriendjes.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "nl.paardenvriendjes")
@EnableAutoConfiguration 
//(exclude = {org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
@EnableTransactionManagement
@EnableWebMvc

public class Application {

	public static void main(String[] args) {
				
		SpringApplication.run(Application.class, args);
		
	}
}
