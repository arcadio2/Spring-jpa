package com.bolsaideas.springboot.app;

import java.nio.file.Paths;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	
	/* Registra directorio estatico uploads
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		//Mapeamos nuestras imgs a una ruta 
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString(); //un poco mas dinamico
		log.info("INFOOO: "+resourcePath);
		
		registry.addResourceHandler("/uploads/**")//ruta url Este es aparte del estatic que ya existe
		.addResourceLocations(resourcePath);//directorio. Se configura como estatico para qie se pieda ver em ña vista
		//D:/Temp/uploads "file:/D:/Temp/uploads/"
		// file:///D:/Users/Asus/Documents/CURSOS/Spring_0-experto/spring-boot-data-jpa/uploads/
	}*/

	//maneja el error 403
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error/403"); 
	}
}

