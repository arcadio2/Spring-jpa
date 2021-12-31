package com.bolsaideas.springboot.app;

import java.nio.file.Paths;
import java.rmi.registry.Registry;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

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
	/*Se anota el metodo como statico*/
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("es","ES")); //se guarda en la sesion
		return localeResolver;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor; 
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
	
}

