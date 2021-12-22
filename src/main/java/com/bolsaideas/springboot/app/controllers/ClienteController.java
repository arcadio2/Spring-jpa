package com.bolsaideas.springboot.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsaideas.springboot.app.controllers.util.paginator.PageRender;
import com.bolsaideas.springboot.app.models.dao.IClienteDao;
import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.service.IClienteService;

@Controller
@SessionAttributes("cliente") //se guarda en sesion el objeto cliente
public class ClienteController {
	
	@Autowired //busca componente que implemente la inferfaz, busca un bean con esa interfaz
	@Qualifier("clienteDaoFachada") //seleccionamos el bean concreto
	private IClienteService clienteDao;

	//@RequestMapping(value = "/listar",method =RequestMethod.GET)
	@GetMapping({"/","/listar"})
	public String listar(@RequestParam(name="page", defaultValue = "0") int page, Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 5); /* 5 registros por pagina */
		Page<Cliente> clientes = clienteDao.findAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/listar",clientes); /*Bista paginable*/
		model.addAttribute("page",pageRender);
		model.addAttribute("titulo","Listado de clientes");
		model.addAttribute("clientes",clientes);
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
	public String crear2(@Valid Cliente cliente, BindingResult result, RedirectAttributes flash, Model model, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo","Formulario de cliente");
			return "form";
		}
		String mensaje = (cliente.getId()!=null)? "Cliente editado con exito" : "Cliente creado con éxito";
		clienteDao.save(cliente); //viene con los datos del form
		status.setComplete(); //elimina el objeto cliente de la sesion, asi no usamos hidden
		flash.addFlashAttribute("success",mensaje);
		return "redirect:listar";
	}
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash ) {
		Cliente cliente = null; 
		if(id>0) {
			cliente = clienteDao.findOne(id); 
			if(cliente == null) {
				flash.addFlashAttribute("error","El ID no existe en la base de datos"); 
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error","El ID no puede ser 0"); 
			return "redirect:/listar";
		}
		model.addAttribute("cliente",cliente);
		model.addAttribute("titulo","Editar cliente");
		return "form";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {

		if(id>0) {
			clienteDao.delete(id);
			flash.addFlashAttribute("success","Cliente eliminado con éxito");
		}
		return "redirect:/listar"; 
	}
	
}
