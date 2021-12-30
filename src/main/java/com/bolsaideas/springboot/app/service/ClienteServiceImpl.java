package com.bolsaideas.springboot.app.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import com.bolsaideas.springboot.app.models.dao.IClienteDao;
import com.bolsaideas.springboot.app.models.dao.IFacturaDao;
import com.bolsaideas.springboot.app.models.dao.IProductoDao;
import com.bolsaideas.springboot.app.models.dao.IitemFacturaDao;
import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.models.entity.Factura;
import com.bolsaideas.springboot.app.models.entity.ItemFactura;
import com.bolsaideas.springboot.app.models.entity.Producto;

@Service("clienteDaoFachada") //clase service, fachada. punto de acceso a distintos DAO
public class ClienteServiceImpl  implements IClienteService{
	/*Por cada metodo en DAO, debe haber uno en service*/
	/*no usamos anotation transactional  */
	
	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IProductoDao productoDao; 

	@Autowired
	private IFacturaDao facturaDao;  
	
	@Autowired
	private IitemFacturaDao itemDao; 
	
	
	@Override
	@Transactional(readOnly=true)
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		// TODO Auto-generated method stub
		clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findOne(Long id) {
		// TODO Auto-generated method stub
		return clienteDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente fetchClienteById(Long id) {
		// TODO Auto-generated method stub
		return clienteDao.fetchClienteByIdWithFacturas(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		clienteDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Cliente> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return clienteDao.findAll(pageable);
	}

	@Override
	public List<Producto> findByNombre(String term) {
		
		return productoDao.buscarPorNombre(term);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre2(String term) {
		// TODO Auto-generated method stub
		return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		// TODO Auto-generated method stub
		facturaDao.save(factura);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		// TODO Auto-generated method stub
		return productoDao.findById(id).orElse(null);
	}

	@Override
	public void saveItemFactura(ItemFactura item) {
		// TODO Auto-generated method stub
		itemDao.save(item);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		// TODO Auto-generated method stub
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public boolean deleteFactura(Long id) {
		// TODO Auto-generated method stub
		facturaDao.deleteById(id);
		return true;
	}

	@Override
	@Transactional
	public Factura fetchFacturaById(Long id) {
		// TODO Auto-generated method stub
		return facturaDao.fetchByIdWithClienteWhithItemFacturaWithProducto(id);
	}







	
}
