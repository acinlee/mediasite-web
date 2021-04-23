package com.cudo.mediabusiness.mediasite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MediaSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaSiteApplication.class, args);
	}

}
