package com.bolsaideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsaideas.springboot.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{ //clase entity, tipo del ID

	/*CON INNER JOIN NOS REGRESA LA UNION, Y SI NO TIENE FACTURAS NOS REGRESA VACÍO*/
	/*LO SOLUCIONAMOS CON LEFT JOIN, que nos da los clientes aunque no haya facturas (left outer join) */
	@Query("select c from Cliente c left join fetch c.facturas  where c.id=?1")
	public Cliente fetchClienteByIdWithFacturas(Long id); 
	
	
}
