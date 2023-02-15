package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.persistence.UniqueConstraint;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

@Entity
@Table(name="bp_usuarios")
public class Usuario implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long usuarioId;
	
	@Size(max=250)
	@Column(nullable=true)
	private String fullName;
	
	@Size(max=50)
	@Column(nullable=true)
	private String username;
	
	@Column(nullable=true)
	private Boolean isAdmin;
		
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date timeStamp;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date creado;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date modificado;	
	
	@Column(nullable=true)
	private Integer estatus;
	
	@Column(nullable=true)
	private Boolean habilitado;
	
	@Size(max=500)
	@Column(nullable=true)
	private String pwd;
	
	@Size(max=200)
	@Column(nullable=true)
	private String pass;
	
	@Column(nullable=true)
	private Byte tipo;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="bp_usuarios_roles", joinColumns= @JoinColumn(name="usuario_id"),
	inverseJoinColumns=@JoinColumn(name="rol_id"),
	uniqueConstraints= {@UniqueConstraint(columnNames= {"usuario_id", "rol_id"})})
	@JsonIgnoreProperties(value ={ "hibernateLazyInitializer", "handler", "usuarios" }, allowSetters = true)
	private List<Roles> roles;

	
	public void addRol(Roles rol){
		
        if(this.roles == null){
            this.roles = new ArrayList<>();
        }
        
        this.roles.add(rol);
    }
	
	public void deleteRol() {
		
		
	}


		
	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
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

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Byte getTipo() {
		return tipo;
	}

	public void setTipo(Byte tipo) {
		this.tipo = tipo;
	}



	private static final long serialVersionUID = 1L;
	
}
