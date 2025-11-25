package com.globalshopper.GlobalShopper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class GlobalShopperApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalShopperApplication.class, args);
	}

}
