package com.bolsaideas.springboot.app.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.models.entity.Factura;
import com.bolsaideas.springboot.app.models.entity.ItemFactura;
import com.bolsaideas.springboot.app.models.entity.Producto;
import com.bolsaideas.springboot.app.service.IClienteService;

@Controller
@RequestMapping("/factura") //URL BASE
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClienteService clienteService; 
	
	@GetMapping("/form/{cliente_id}")//recibe el id de cliente
	public String crear(@PathVariable(value = "cliente_id") Long cliente_id, Model model, RedirectAttributes flash) {
		
		Cliente cliente  = clienteService.findOne(cliente_id); 
		if(cliente==null) {
			flash.addAttribute("error","No existe el cliente");
			return "redirect:/listar"; 
		}
		Factura factura = new Factura(); 
		factura.setCliente(cliente);
		model.addAttribute("factura",factura);
		model.addAttribute("cliente",cliente);
		model.addAttribute("titulo","Crear factura"); 
		return "factura/form";
	}
	
	/*@PostMapping("/factura/form")
	public String guardarFactura() {
		return "redirect:/listar";
	}*/
	@GetMapping(value="/cargar-productos/{term}",produces = {"application/xml","application/json"})//term es pathVariable
	//response body suprime el cargar una vista de thymeleaf, la transgorma en json
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		
		List<Producto>  productos=clienteService.findByNombre2(term);//me está regresando nulo
		
		/*Producto producto = new Producto();
		producto.setNombre("aaaa");
		producto.setId( Long.parseLong("12") ); 
		producto.setPrecio(123.0);
		producto.setCreateAt(null);
		
		productos.add(producto);
		System.out.println("Porductos :"+productos);*/
		return  productos; 
	}
	
	@PostMapping(value = "/form")
	public String guardar(@Valid Factura factura,BindingResult result, //añade validaciones y result comprueba error
			@RequestParam(name="item_id[]",required = false) Long[] itemId, Model model,
			@RequestParam(name="cantidad[]",required = false) Integer[] cantidad,
			RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo","Crear factura"); 
			model.addAttribute("cliente",factura.getCliente());
			return "factura/form";
		}
		if(itemId==null || itemId.length==0) {
			model.addAttribute("titulo","Crear factura"); 
			model.addAttribute("cliente",factura.getCliente());
			model.addAttribute("error","Error: la factura no puede no tener lineas"); 
			return "factura/form";
		}
		for(int i=0;i<itemId.length;i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]); 
			linea.setProducto(producto); 
			factura.addItemFactura(linea);
			System.out.println("ID: "+itemId[i].toString() +" Cantidad: "+cantidad[i].toString());
		}
		/*Se crean los items automaticamente pq esta relacionado, se guardan todos los items agregados en factura*/
		clienteService.saveFactura(factura); 
		status.setComplete();
		flash.addAttribute("success","Factura creada con éxito"); 
		return "redirect:/ver/"+factura.getCliente().getId();
	}
	
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {
		Factura factura = clienteService.findFacturaById(id);
		if(factura==null) {
			flash.addAttribute("error","La factura no existe en la Base de datos");
			return "redirect:/listar";
		}
		model.addAttribute("factura",factura); 
		model.addAttribute("titulo","Factura :".concat(factura.getDescripcion())); 
		return "factura/ver"; 
	}
	
	
}
