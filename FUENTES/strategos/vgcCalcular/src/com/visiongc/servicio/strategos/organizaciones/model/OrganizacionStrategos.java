package com.visiongc.servicio.strategos.organizaciones.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import com.visiongc.framework.model.Usuario;

public class OrganizacionStrategos implements Serializable
{
  static final long serialVersionUID = 0L;
  
  	private String rif;
  	private String telefono;
  	private String fax;
  	private Byte mesCierre;
  	private String enlaceParcial;
  	private Byte porcentajeZonaAmarillaMinMaxIndicadores;
  	private Byte porcentajeZonaVerdeMetaIndicadores;
  	private Byte porcentajeZonaAmarillaMetaIndicadores;
  	private Byte porcentajeZonaVerdeIniciativas;
  	private Byte porcentajeZonaAmarillaIniciativas;
  	private Boolean visible;
  	private Boolean soloLectura;
  	private List<OrganizacionStrategos> hijos;
  	private Set<?> memos;
  	private OrganizacionStrategos padre;
  	private Boolean hijosCargados;
  	private Set<?> clases;
  	private Usuario usuarioCreado;
  	private Usuario usuarioModificado;
  	private String rutaCompleta;
  	private Long organizacionId;
  	private Long padreId;
  	private String nombre;
  	private String direccion;
	
  	/** nullable persistent field */
	private Date creado;

	/** nullable persistent field */
	private Date modificado;

	/** nullable persistent field */
	private Long creadoId;

	/** nullable persistent field */
	private Long modificadoId;

  public OrganizacionStrategos(Long organizacionId, Long padreId, String nombre, String direccion, Date creado, Date modificado, Long creadoId, Long modificadoId, String rif, String telefono, String fax, Byte mesCierre, Byte porcentajeZonaAmarillaMinMaxIndicadores, Byte porcentajeZonaVerdeMetaIndicadores, Byte porcentajeZonaAmarillaMetaIndicadores, Byte porcentajeZonaVerdeIniciativas, Byte porcentajeZonaAmarillaIniciativas)
  {
    this.organizacionId = organizacionId;
    this.padreId = padreId;
    this.nombre = nombre;
    this.direccion = direccion;
	this.creado = creado;
	this.modificado = modificado;
	this.creadoId = creadoId;
	this.modificadoId = modificadoId;

    this.rif = rif;
    this.telefono = telefono;
    this.fax = fax;
    this.mesCierre = mesCierre;
    this.porcentajeZonaAmarillaMinMaxIndicadores = porcentajeZonaAmarillaMinMaxIndicadores;
    this.porcentajeZonaVerdeMetaIndicadores = porcentajeZonaVerdeMetaIndicadores;
    this.porcentajeZonaAmarillaMetaIndicadores = porcentajeZonaAmarillaMetaIndicadores;
    this.porcentajeZonaVerdeIniciativas = porcentajeZonaVerdeIniciativas;
    this.porcentajeZonaAmarillaIniciativas = porcentajeZonaAmarillaIniciativas;
  }

  public OrganizacionStrategos()
  {
  }

  public String getFax()
  {
    return this.fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public List<OrganizacionStrategos> getHijos() {
    return this.hijos;
  }

  public void setHijos(List<OrganizacionStrategos> hijos) {
    this.hijos = hijos;
  }

  public Byte getMesCierre() {
    return this.mesCierre;
  }

  public void setMesCierre(Byte mesCierre) {
    this.mesCierre = mesCierre;
  }

  public String getEnlaceParcial() {
    return this.enlaceParcial;
  }

  public void setEnlaceParcial(String enlaceParcial) {
    this.enlaceParcial = enlaceParcial;
  }

  public OrganizacionStrategos getPadre() {
    return this.padre;
  }

  public void setPadre(OrganizacionStrategos padre) {
    this.padre = padre;
  }

  public Byte getPorcentajeZonaAmarillaIniciativas() {
    return this.porcentajeZonaAmarillaIniciativas;
  }

  public void setPorcentajeZonaAmarillaIniciativas(Byte porcentajeZonaAmarillaIniciativas) {
    this.porcentajeZonaAmarillaIniciativas = porcentajeZonaAmarillaIniciativas;
  }

  public Byte getPorcentajeZonaAmarillaMetaIndicadores() {
    return this.porcentajeZonaAmarillaMetaIndicadores;
  }

  public void setPorcentajeZonaAmarillaMetaIndicadores(Byte porcentajeZonaAmarillaMetaIndicadores) {
    this.porcentajeZonaAmarillaMetaIndicadores = porcentajeZonaAmarillaMetaIndicadores;
  }

  public Byte getPorcentajeZonaAmarillaMinMaxIndicadores() {
    return this.porcentajeZonaAmarillaMinMaxIndicadores;
  }

  public void setPorcentajeZonaAmarillaMinMaxIndicadores(Byte porcentajeZonaAmarillaMinMaxIndicadores) {
    this.porcentajeZonaAmarillaMinMaxIndicadores = porcentajeZonaAmarillaMinMaxIndicadores;
  }

  public Byte getPorcentajeZonaVerdeIniciativas() {
    return this.porcentajeZonaVerdeIniciativas;
  }

  public void setPorcentajeZonaVerdeIniciativas(Byte porcentajeZonaVerdeIniciativas) {
    this.porcentajeZonaVerdeIniciativas = porcentajeZonaVerdeIniciativas;
  }

  public Byte getPorcentajeZonaVerdeMetaIndicadores() {
    return this.porcentajeZonaVerdeMetaIndicadores;
  }

  public void setPorcentajeZonaVerdeMetaIndicadores(Byte porcentajeZonaVerdeMetaIndicadores) {
    this.porcentajeZonaVerdeMetaIndicadores = porcentajeZonaVerdeMetaIndicadores;
  }

  public String getRif() {
    return this.rif;
  }

  public void setRif(String rif) {
    this.rif = rif;
  }

  public String getTelefono() {
    return this.telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public Boolean getVisible() {
    return this.visible;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }

  public Boolean getSoloLectura() {
    return this.soloLectura;
  }

  public void setSoloLectura(Boolean soloLectura) {
    this.soloLectura = soloLectura;
  }

  public Boolean getHijosCargados() {
    return this.hijosCargados;
  }

  public void setHijosCargados(Boolean hijosCargados) {
    this.hijosCargados = hijosCargados;
  }

  public Set<?> getMemos() {
    return this.memos;
  }

  public void setMemos(Set<?> memos) {
    this.memos = memos;
  }

  public Set<?> getClases() {
    return this.clases;
  }

  public void setClases(Set<?> clases) {
    this.clases = clases;
  }

  public Usuario getUsuarioCreado() {
    return this.usuarioCreado;
  }

  public void setUsuarioCreado(Usuario usuarioCreado) {
    this.usuarioCreado = usuarioCreado;
  }

  public Usuario getUsuarioModificado() {
    return this.usuarioModificado;
  }

  public void setUsuarioModificado(Usuario usuarioModificado) {
    this.usuarioModificado = usuarioModificado;
  }

  public Collection<?> getNodoArbolHijos() {
    return this.hijos;
  }

  public boolean getNodoArbolHijosCargados() {
    if (this.hijosCargados == null) {
      this.hijosCargados = new Boolean(false);
    }
    return this.hijosCargados.booleanValue();
  }

  public String getNodoArbolNombreCampoId() {
    return "organizacionId";
  }

  public String getNodoArbolNombreCampoNombre() {
    return "nombre";
  }

  public String getNodoArbolNombreCampoPadreId() {
    return "padreId";
  }

  public String getNodoArbolNombreImagen() {
    return "Organizacion";
  }

  public void setNodoArbolHijosCargados(boolean cargados) {
    this.hijosCargados = new Boolean(cargados);
  }

  public String getRutaCompleta()
  {
    return this.rutaCompleta;
  }

  public void setRutaCompleta(String rutaCompleta) {
    this.rutaCompleta = rutaCompleta;
  }
  
	public Long getOrganizacionId() {
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}

	public Long getPadreId() {
		return this.padreId;
	}

	public void setPadreId(Long padreId) {
		this.padreId = padreId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getCreado() {
		return this.creado;
	}

	public void setCreado(Date creado) {
		this.creado = creado;
	}

	public Date getModificado() {
		return this.modificado;
	}

	public void setModificado(Date modificado) {
		this.modificado = modificado;
	}

	public Long getCreadoId() {
		return this.creadoId;
	}

	public void setCreadoId(Long creadoId) {
		this.creadoId = creadoId;
	}

	public Long getModificadoId() {
		return this.modificadoId;
	}

	public void setModificadoId(Long modificadoId) {
		this.modificadoId = modificadoId;
	}
}