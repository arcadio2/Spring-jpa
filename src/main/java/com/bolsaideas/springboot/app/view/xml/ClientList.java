package com.bolsaideas.springboot.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bolsaideas.springboot.app.models.entity.Cliente;

@XmlRootElement(name="clientes")
public class ClientList {

	@XmlElement(name="cliente")
	public List<Cliente> clietes;

	
	public ClientList() {
		
	}

	public ClientList(List<Cliente> clietes) {
		super();
		this.clietes = clietes;
	}

	public List<Cliente> getClietes() {
		return clietes;
	}

	
	
}
