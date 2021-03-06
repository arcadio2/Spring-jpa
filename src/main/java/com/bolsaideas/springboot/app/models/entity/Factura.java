package com.bolsaideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity //clase de persistencia
@Table(name="facturas") //table facturas
public class Factura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//autoincrementable
	private long id;
	
	@NotEmpty
	private String descripcion;
	
	private String observacion;
	
	@Temporal(TemporalType.DATE)//se guardan las fechas
	@Column(name = "create_at")
	private Date createAt;
	
	//carga perezosa, se espera para hacer consultas. Evita que traiga todo de una vez
	@ManyToOne(fetch = FetchType.LAZY) //muchas facturas a un cliente, un cliente solo una factura
	@JsonBackReference //esta ya no se muestra en el JSON, se omite
 	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //una factura tiene muchos items, por cascada se agregan los items
	//ya que es unidireccional
	@JoinColumn(name = "factura_id")  //llave foranea para facutra_items, esto ya que no es en ambos sentidos como en mappedby
	private List<ItemFactura> items; 
	

	
	
	public Factura() {
		items = new ArrayList<ItemFactura>();
	}

	@PrePersist //para que se ejecute antes de crear un registro
	public void prePersist() {
		createAt = new Date();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	//cuando se serializa el xml no llama este m??todo
	@XmlTransient
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	/*SE AGREGAN EN CASCADA*/
	public void addItemFactura(ItemFactura item) {
		this.items.add(item);
	}
	
	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	
	public Double getTotal() {
		Double total = 0.0; 
		int size = items.size(); 
		for (int i = 0; i < size; i++) {
			total+= this.items.get(i).calcularImporte(); 
		}
		return total; 
	}
	
	
	




	private static final long serialVersionUID = 1L;
}
