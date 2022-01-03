package com.bolsaideas.springboot.app.view.json;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.bolsaideas.springboot.app.models.entity.Cliente;

@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView{

	@Override
	protected Object filterModel(Map<String, Object> model) {
		
		model.remove("titulo"); 
		model.remove("page"); 
		
		@SuppressWarnings("unchecked")
		List<Cliente> clientes = ((Page<Cliente>) model.get("clientes")).getContent();
		
		model.remove("clientes"); 
		model.put("clientes", clientes);
		
		return super.filterModel(model);
	}

	
	
}
