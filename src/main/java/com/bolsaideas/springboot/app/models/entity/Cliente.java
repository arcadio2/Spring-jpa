package com.bolsaideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;



@Entity // es una entidad JPA
@Table(name = "clientes") // si es diferente a a la clase
public class Cliente implements Serializable { // es recomendado para convertir en bytes

	@Id // indica que es la llave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) // como se genera la llave, si es autoincrement, etc
	private Long id;
	/* Los demas se mapean al campo que se llame exactamente igual */
	// @Column(name = "nombre") // esto por si se llama diferente
	@NotEmpty
	@Size(min = 4)
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	@Email
	private String email;

	@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE) // en que formato se guarda en la bd
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;

	private static final long serialVersionUID = 1L;

	/*@PrePersist //para que se ejecute antes de crear un registro
	public void prePersist() {
		createAt = new Date();
	}*/
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		
		return super.toString();
	}

	
}
