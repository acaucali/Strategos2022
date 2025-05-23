package com.strategos.nueva.bancoproyectos.model.util;

import java.util.ArrayList;
import java.util.List;

public class Arbol {
	
	private String text;
	private Integer id; 
	
	private Double valor;
	private Byte alerta;
	private Boolean isPadre;
	
	private List<Arbol> items = new ArrayList();
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Arbol> getItems() {
		return items;
	}
			
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public void setItems(List<Arbol> items) {
		this.items = items;
	}
	public Byte getAlerta() {
		return alerta;
	}
	public void setAlerta(Byte alerta) {
		this.alerta = alerta;
	}
	public Boolean getIsPadre() {
		return isPadre;
	}
	public void setIsPadre(Boolean isPadre) {
		this.isPadre = isPadre;
	}
	
}
