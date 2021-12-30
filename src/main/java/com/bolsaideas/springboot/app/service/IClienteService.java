package com.bolsaideas.springboot.app.service;


import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.models.entity.Factura;
import com.bolsaideas.springboot.app.models.entity.ItemFactura;
import com.bolsaideas.springboot.app.models.entity.Producto;

public interface IClienteService {
	public List<Cliente> findAll();
	public Page<Cliente> findAll(Pageable pageable);
	public void save(Cliente cliente); 
	public Cliente findOne(Long id); 
	public void delete(Long id); 
	public List<Producto> findByNombre(String term); 
	public List<Producto> findByNombre2(String term);
	public void saveFactura(Factura factura);
	
	public Producto findProductoById(Long id);
	
	public void saveItemFactura(ItemFactura item);
	
	public Factura findFacturaById(Long id);
	
	public boolean deleteFactura(Long id); 
	
	public Factura fetchFacturaById(Long id); 
	
	public Cliente fetchClienteById(Long id); 
	
} 
