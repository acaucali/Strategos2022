package com.visiongc.framework.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class AfwLic implements Serializable {

	static final long serialVersionUID = 0L;
	private Long licId;
	private String corporacion;
	private String serial;
	private String licenciamiento;
	
	public AfwLic()
	{		
	}

	public String getCorporacion() {
		return corporacion;
	}

	public void setCorporacion(String corporacion) {
		this.corporacion = corporacion;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getLicenciamiento() {
		return licenciamiento;
	}

	public void setLicenciamiento(String licenciamiento) {
		this.licenciamiento = licenciamiento;
	}

	public Long getLicId() {
		return licId;
	}

	public void setLicId(Long licId) {
	    this.licId = licId;
	}


	public String toString() {
		return (new ToStringBuilder(this)).append("licId", getLicId()).toString();
	}

	public int compareTo(Object o) {
		AfwLic or = (AfwLic) o;
		return getLicId().compareTo(or.getLicId());
	}

}
