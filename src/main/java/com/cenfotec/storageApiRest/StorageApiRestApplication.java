package com.cenfotec.storageApiRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cenfotec.storageApiRest.azure")
@ComponentScan(basePackages = "com.cenfotec.storageApiRest.web")
public class StorageApiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageApiRestApplication.class, args);
	}

}
