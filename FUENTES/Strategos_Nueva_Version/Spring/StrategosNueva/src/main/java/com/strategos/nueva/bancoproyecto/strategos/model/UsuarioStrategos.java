package com.strategos.nueva.bancoproyecto.strategos.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name="afw_usuario")
public class UsuarioStrategos implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long usuarioId;
	
	@Size(max=50)
	@Column(nullable=true)
	private String fullName;
	
	@Size(max=50)
	@Column(nullable=true, name="u_id")
	private String user;
	
	@Column(nullable=true)
	private Boolean isAdmin;
	
	@Column(nullable=true)
	private Boolean isConnected;
	
	@Column(nullable=true)
	private Boolean isSystem;
	
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
	private Long creadoId;
	
	@Column(nullable=true)
	private Long modificadoId;
	
	@Size(max=50)
	@Column(nullable=true)
	private String instancia;
	
	@Column(nullable=true)
	private Integer estatus;
	
	@Column(nullable=true, name="bloqueado")
	private Boolean bloqueado;
	
	@Size(max=100)
	@Column(nullable=true)
	private String pwd;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true, name="ultima_modif_pwd")
	private Date ultimaModifPwd;
	
	@Column(nullable=true, name="deshabilitado")
	private Boolean deshabilitado;
	
	@Column(nullable=true, name="forzar_cambiar_pwd")
	private Boolean forzarCambiarpwd;
	
		
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
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(Boolean isConnected) {
		this.isConnected = isConnected;
	}

	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
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

	public String getInstancia() {
		return instancia;
	}

	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Date getUltimaModifPwd() {
		return ultimaModifPwd;
	}

	public void setUltimaModifPwd(Date ultimaModifPwd) {
		this.ultimaModifPwd = ultimaModifPwd;
	}

	public Boolean getDeshabilitado() {
		return deshabilitado;
	}

	public void setDeshabilitado(Boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}

	public Boolean getForzarCambiarpwd() {
		return forzarCambiarpwd;
	}

	public void setForzarCambiarpwd(Boolean forzarCambiarpwd) {
		this.forzarCambiarpwd = forzarCambiarpwd;
	}



	private static final long serialVersionUID = 1L;
}
