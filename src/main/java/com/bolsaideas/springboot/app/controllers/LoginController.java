package com.bolsaideas.springboot.app.controllers;

import java.security.Principal;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required = false) String error,
			@RequestParam(value="logout", required = false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
		//System.out.println("XDDDDDDD "+principal.toString());
		//si es distinto es pq ya tiene sesion
		if(principal != null) {
			flash.addFlashAttribute("info","Ya has iniciado sesión anteriormente");
			return "redirect:/";
		}
		if(error !=null ) {
			model.addAttribute("error", "Error en el login, nombre de usuario o contraseña inválida");
		}
		if(logout !=null ) {
			model.addAttribute("success", "Has cerrado sesión!");
		}
		model.addAttribute("titulo","Sign In");
		return "login"; 
	}
	
	@GetMapping("/logout")
	public String logout(Model model, Principal principal, RedirectAttributes flash) {
		principal = null;
		return "redirect:/login";
	}
	
}
