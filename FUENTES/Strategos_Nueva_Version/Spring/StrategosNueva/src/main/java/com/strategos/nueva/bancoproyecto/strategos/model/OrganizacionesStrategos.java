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
	
	@Column(nullable=false)
	private Long padreId;
	
	@Size(max=50)
	@Column(nullable=false)
	private String nombre;
	
	@Size(max=15)
	@Column(nullable=true)
	private String rif;
	
	@Size(max=150)
	@Column(nullable=true)
	private String direccion;
	
	@Size(max=50)
	@Column(nullable=true)
	private String telefono;
	
	@Size(max=50)
	@Column(nullable=true)
	private String fax;
	
	@Column(nullable=true)
	private Byte mesCierre;
	
	@Column(nullable=true)
	private Date creado;
	
	@Column(nullable=true)
	private Date modificado;
	
	@Column(nullable=true)
	private Long creadoId;
	
	@Column(nullable=true)
	private Long modificadoId;
	
	@Column(nullable=true)
	private Integer alertaMinMax;
	
	@Column(nullable=true)
	private Integer alertaMetaN1;
	
	@Column(nullable=true)
	private Integer alertaMetaN2;
	
	@Column(nullable=true)
	private Integer alertaIniciativaZv;
	
	@Column(nullable=true)
	private Integer alertaIniciativaZa;
	
	@Column(nullable=true)
	private String enlaceParcial;
	
	@Column(nullable=true)
	private String subclase;
	
	@Column(nullable=true)
	private Byte visible;
	
	@Column(nullable=true)
	private Byte readOnly;
			
	public Long getOrganizacionId() {
		return organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}

	public Long getPadreId() {
		return padreId;
	}

	public void setPadreId(Long padreId) {
		this.padreId = padreId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRif() {
		return rif;
	}

	public void setRif(String rif) {
		this.rif = rif;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Byte getMesCierre() {
		return mesCierre;
	}

	public void setMesCierre(Byte mesCierre) {
		this.mesCierre = mesCierre;
	}

	public Date getCreado() {
		return creado;
	}

	public void setCreado(Date creado) {
		this.creado = creado;
	}

	public Date getModificado() {
		return modificado;
	}

	public void setModificado(Date modificado) {
		this.modificado = modificado;
	}

	public Long getCreadoId() {
		return creadoId;
	}

	public void setCreadoId(Long creadoId) {
		this.creadoId = creadoId;
	}

	public Long getModificadoId() {
		return modificadoId;
	}

	public void setModificadoId(Long modificadoId) {
		this.modificadoId = modificadoId;
	}

	public Integer getAlertaMinMax() {
		return alertaMinMax;
	}

	public void setAlertaMinMax(Integer alertaMinMax) {
		this.alertaMinMax = alertaMinMax;
	}

	public Integer getAlertaMetaN1() {
		return alertaMetaN1;
	}

	public void setAlertaMetaN1(Integer alertaMetaN1) {
		this.alertaMetaN1 = alertaMetaN1;
	}

	public Integer getAlertaMetaN2() {
		return alertaMetaN2;
	}

	public void setAlertaMetaN2(Integer alertaMetaN2) {
		this.alertaMetaN2 = alertaMetaN2;
	}

	public Integer getAlertaIniciativaZv() {
		return alertaIniciativaZv;
	}

	public void setAlertaIniciativaZv(Integer alertaIniciativaZv) {
		this.alertaIniciativaZv = alertaIniciativaZv;
	}

	public Integer getAlertaIniciativaZa() {
		return alertaIniciativaZa;
	}

	public void setAlertaIniciativaZa(Integer alertaIniciativaZa) {
		this.alertaIniciativaZa = alertaIniciativaZa;
	}

	public String getEnlaceParcial() {
		return enlaceParcial;
	}

	public void setEnlaceParcial(String enlaceParcial) {
		this.enlaceParcial = enlaceParcial;
	}

	public String getSubclase() {
		return subclase;
	}

	public void setSubclase(String subclase) {
		this.subclase = subclase;
	}

	public Byte getVisible() {
		return visible;
	}

	public void setVisible(Byte visible) {
		this.visible = visible;
	}

	public Byte getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Byte readOnly) {
		this.readOnly = readOnly;
	}

	private static final long serialVersionUID = 1L;
			
}
