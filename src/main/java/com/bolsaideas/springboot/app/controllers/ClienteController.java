package com.bolsaideas.springboot.app.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsaideas.springboot.app.controllers.util.paginator.PageRender;
import com.bolsaideas.springboot.app.models.dao.IClienteDao;
import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.service.IClienteService;
import com.bolsaideas.springboot.app.service.IUploadFileService;

@Controller
@SessionAttributes("cliente") //se guarda en sesion el objeto cliente
public class ClienteController {
	
	@Autowired //busca componente que implemente la inferfaz, busca un bean con esa interfaz
	@Qualifier("clienteDaoFachada") //seleccionamos el bean concreto
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadFile; 
	
	
	//@RequestMapping(value = "/listar",method =RequestMethod.GET)
	@GetMapping({"/","/listar"})
	public String listar(@RequestParam(name="page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request) {
		if(authentication!=null) {
			model.addAttribute("username",authentication.getName());
		}
		if(hasRole("ROLE_ADMIN")) {
			model.addAttribute("isAdmin","Hola administrador"); 
		}
		//VALIDAR EL ROLE
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
		if(securityContext.isUserInRole("ADMIN")) {
			model.addAttribute("isAdmin","Hola administrador 2"); 
		}else if(securityContext.isUserInRole("ADMIN")) {
			model.addAttribute("isAdmin","Hola usuario 2"); 
		}
		//forma rapida
		//request.isUserInRole("ROLE_ADMIN");
		
		Pageable pageRequest = PageRequest.of(page, 4); /* 5 registros por pagina */
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/listar",clientes); /*Bista paginable*/
		model.addAttribute("page",pageRender);
		model.addAttribute("titulo","Listado de clientes");
		model.addAttribute("clientes",clientes);
		//System.out.println(clienteService.findAll());
		return "listar"; 
	}
	
	/*Sube nuestra imagen y se pasa cp,p árametro el archivo, esto lo hace en el input type file*/
	@Secured({"ROLE_USER","ROLE_ADMIN"})	
	@GetMapping("/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename){
		
		Resource recurso = null; 
		try {
			 recurso = uploadFile.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*AÑADE A LA CABECERA*/
		return ResponseEntity.ok().
				header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.getFilename()+"\"")
				.body(recurso);
	} 
	
	@PreAuthorize("hasRole('ROLE_USER')")
	//@Secured("ROLE_USER")	
	@GetMapping("ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {
		Cliente cliente = clienteService.fetchClienteById(id);
		if(cliente==null) {
			flash.addFlashAttribute("error","El cliente no existe en la BD");
			return "redirect:/listar";
		}
		model.addAttribute("cliente",cliente);
		model.addAttribute("titulo","Detalle cliente: "+cliente.getNombre());
		return "ver";
	}
	
	@Secured("ROLE_ADMIN")	
	@GetMapping("/form")
	public String crear(Model model) {
		model.addAttribute("titulo","Formulario de cliente");
		Cliente cliente = new Cliente();
		model.addAttribute("cliente",cliente); 
		return "form";
	}
	
	@Secured("ROLE_ADMIN")	
	@PostMapping("/form")
	public String crear2(@Valid Cliente cliente, BindingResult result, RedirectAttributes flash, Model model, 
						@RequestParam("file") MultipartFile foto, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo","Formulario de cliente");
			return "form";
		}
		if(!foto.isEmpty()) {
			System.out.println("ORIGINAL : "+foto.getOriginalFilename());
			//Path directorioRecursos = Paths.get("src//main//resources//static/uploads");
			//String roothPath = directorioRecursos.toFile().getAbsolutePath();
			//String roothPath = "D://Temp/uploads"; /*Se guardan las imagenes en un recurso externo*/
			
			//Para validar si ya tiene foto y pasar a eliminar
			if(cliente.getId()!=null && cliente.getId()>0 && cliente.getFoto()!=null && cliente.getFoto().length()>0) {
				if(uploadFile.delete(cliente.getFoto())) {
					flash.addFlashAttribute("info","Foto "+cliente.getFoto() + " Eliminada con exito");
				}
			}
			String uniqueFilename=null;
			try {
				uniqueFilename = uploadFile.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flash.addFlashAttribute("info","Ha subido correctamente :"+foto.getOriginalFilename());
			cliente.setFoto(uniqueFilename); //ya con el nombre unico
		}
		
		String mensaje = (cliente.getId()!=null)? "Cliente editado con exito" : "Cliente creado con éxito";
		clienteService.save(cliente); //viene con los datos del form
		status.setComplete(); //elimina el objeto cliente de la sesion, asi no usamos hidden
		flash.addFlashAttribute("success",mensaje);
		
		return "redirect:listar";
	}
	@Secured("ROLE_ADMIN")	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash ) {
		Cliente cliente = null; 
		if(id>0) {
			cliente = clienteService.findOne(id); 
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
	
	@Secured("ROLE_ADMIN")	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {

		if(id>0) {
			Cliente  cliente = clienteService.findOne(id); 
			
			clienteService.delete(id);
			flash.addFlashAttribute("success","Cliente eliminado con éxito");
			
			if(uploadFile.delete(cliente.getFoto())) {
				flash.addFlashAttribute("info","Foto "+cliente.getFoto() + " Eliminada con exito");
			}
		}
		return "redirect:/listar"; 
	}
	
	private boolean hasRole(String role) {
		boolean has = false; 
		SecurityContext context = SecurityContextHolder.getContext(); 
		if(context==null) {
			return false; 
		}
		Authentication auth= context.getAuthentication(); 
		if(auth==null) {
			return false; 
		}
		//cualquier tipo queherede o implemente la interfa<
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities(); 
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		/*for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())) {
				return true; 
			}
		}
		
		return false; */
	}
	
}
