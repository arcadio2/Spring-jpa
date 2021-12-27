package com.bolsaideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsaideas.springboot.app.service.IUploadFileService;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner{

	@Autowired
	IUploadFileService uploadFileService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*Borra y vuelve a crear*/
		uploadFileService.deleteAll();
		uploadFileService.init();
	}

}
