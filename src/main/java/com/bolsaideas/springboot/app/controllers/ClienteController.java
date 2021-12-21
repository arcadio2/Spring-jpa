package com.bolsaideas.springboot.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolsaideas.springboot.app.models.dao.IClienteDao;
import com.bolsaideas.springboot.app.models.entity.Cliente;

@Controller
public class ClienteController {
	
	@Autowired //busca componente que implemente la inferfaz, busca un bean con esa interfaz
	@Qualifier("clienteDaoJPA") //seleccionamos el bean concreto
	private IClienteDao clienteDao;
	
	//@RequestMapping(value = "/listar",method =RequestMethod.GET)
	@GetMapping({"/","/listar"})
	public String listar(Model model) {
		
		model.addAttribute("titulo","Listado de clientes");
		model.addAttribute("clientes",clienteDao.findAll());
		System.out.println(clienteDao.findAll());
		return "listar"; 
	}
	
	@GetMapping("/form")
	public String crear(Model model) {
		model.addAttribute("titulo","Formulario de cliente");
		Cliente cliente = new Cliente();
		model.addAttribute("cliente",cliente); 
		return "form";
	}
	
	@PostMapping("/form")
	public String crear2(@Valid Cliente cliente, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("titulo","Formulario de cliente");
			return "form";
		}
		System.out.println(cliente.getCreateAt());
		clienteDao.save(cliente); //viene con los datos del form
		return "redirect:listar";
	}
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Model model ) {
		Cliente cliente = null; 
		if(id>0) {
			cliente = clienteDao.findOne(id); 
		}else {
			return "redirect:listar";
		}
		model.addAttribute("cliente",cliente);
		model.addAttribute("titulo","Editar cliente");
		return "form";
	}
	
	
}
