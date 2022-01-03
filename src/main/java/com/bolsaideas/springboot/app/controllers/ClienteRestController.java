package com.bolsaideas.springboot.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.service.IClienteService;

/*Es controller + Response BODY*/
@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {
	
	@Autowired //busca componente que implemente la inferfaz, busca un bean con esa interfaz
	@Qualifier("clienteDaoFachada") //seleccionamos el bean concreto
	private IClienteService clienteService;
	
	/*Respuesta REST API*///si queremos xml y json debemos retornar la clase wraper
	@GetMapping("/listar")
	public List<Cliente> listar() {
		return clienteService.findAll();
		
	}
}
