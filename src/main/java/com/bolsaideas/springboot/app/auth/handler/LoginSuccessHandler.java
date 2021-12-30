package com.bolsaideas.springboot.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

/*MANEJA CUANDO HAY EXITO EN EL LOGIN*/
@Component //contenedor en contexto de spring
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		SessionFlashMapManager flashMannager = new SessionFlashMapManager(); 
		FlashMap flashMap = new FlashMap(); 
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		
		flashMap.put("success", "Hola "+auth.getName()+", Has iniciado sesión con éxito");
		
		flashMannager.saveOutputFlashMap(flashMap, request, response);
		if(authentication!=null) {
			logger.info("EL usuario "+authentication.getName()+" Ha inciiado sesión con éxito");
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
	
}
