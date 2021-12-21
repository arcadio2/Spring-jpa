package com.bolsaideas.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsaideas.springboot.app.models.entity.Cliente;

@Repository("clienteDaoJPA") //componente de persistencia, traduce las excepciones que pueden ocurrir
public class ClienteDaoImpl implements IClienteDao {
	
	@PersistenceContext //JPA
	private EntityManager em; //maneja la clase de entidades, el ciclo de vida y la persistencia (op a la bases a nivel de objeto)
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true) //es solo de consulta de lectura
	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList();
	}

	@Transactional
	@Override
	public void save(Cliente cliente) {	
		em.persist(cliente);
	}

	@Override
	public Cliente findOne(Long id) {
		return em.find(Cliente.class, id); // entrega por ID
	}

}
