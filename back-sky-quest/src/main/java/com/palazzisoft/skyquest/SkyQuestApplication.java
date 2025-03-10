package com.palazzisoft.skyquest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class SkyQuestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkyQuestApplication.class, args);
	}

}
