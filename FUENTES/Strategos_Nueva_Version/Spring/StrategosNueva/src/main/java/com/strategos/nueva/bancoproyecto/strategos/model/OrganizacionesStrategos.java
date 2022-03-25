package com.strategos.nueva.bancoproyecto.strategos.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="organizacion")
public class OrganizacionesStrategos implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long organizacionId;
	
	private Long padreId;
	
	@Size(max=50)
	@Column(nullable=true)
	private String nombre;
	
	@Size(max=50)
	@Column(nullable=true)
	private String rif;
	
	@Size(max=50)
	@Column(nullable=true)
	private String direccion;
	
	@Size(max=50)
	@Column(nullable=true)
	private String telefono;
	
	@Size(max=50)
	@Column(nullable=true)
	private String fax;
	
	private Integer mesCierre;
	
	private Date creado;
	
	private Date modificado;
	
	private Long creadoId;
	
	private Long modificadoId;
	
	private Integer alertaMinMax;
	
	private Integer alertaMetaN1;
	
	private Integer alertaMetaN2;
	
	private Integer alertaIniciativaZv;
	
	private Integer alertaIniciativaZa;
	
	@Size(max=50)
	@Column(nullable=true)
	private String enlaceParcial;
	
	@Size(max=50)
	@Column(nullable=true)
	private String subclase;
	
	private Byte visible;
	
	private Byte readOnly;
	
	
	private static final long serialVersionUID = 1L;
			
}
