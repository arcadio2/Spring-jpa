package com.bolsaideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsaideas.springboot.app.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long>{
	
	/*LAS CONSULTAS DE AQUI SON PERSONALIZADAS*/
	@Query("select p from Producto p where p.nombre like %?1%")//cuando el nombre sea termino, por eso el signo
	public List<Producto> buscarPorNombre(String term); 
	
	
	public List<Producto> findByNombreLikeIgnoreCase(String term); //consulta basada en el nombre del metodo
	
}
