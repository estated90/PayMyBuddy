package com.application.paymybuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author nicolas
 *
 */
@SpringBootApplication
@EnableSwagger2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationPayMyBuddyApplication {

	/**
	 * @param args spring
	 * 
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApplicationPayMyBuddyApplication.class, args);
	}

}
