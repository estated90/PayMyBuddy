package com.paymybuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author nicolas
 *
 */
@SpringBootApplication
@EnableSwagger2
public class ApplicationPayMyBuddyApplication {

	/**
	 * @param args spring
	 * 
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApplicationPayMyBuddyApplication.class, args);
	}

}
