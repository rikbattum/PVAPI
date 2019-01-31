package nl.paardenvriendjes.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@EnableAutoConfiguration
@EnableTransactionManagement
@PropertySources({
	  @PropertySource("classpath:application.properties"),
	  @PropertySource(value = { "classpath:application.properties" })
	})

public class Application  {

	
	public static void main(String[] args) throws Exception {
	   SpringApplication.run(Application.class, args);
	} 
	   
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(Application.class);
//    }

 
}

