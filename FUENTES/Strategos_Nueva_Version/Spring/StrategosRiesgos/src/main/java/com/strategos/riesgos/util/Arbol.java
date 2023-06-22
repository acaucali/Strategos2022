package com.strategos.riesgos.util;

import java.util.ArrayList;
import java.util.List;



public class Arbol {
	private String text;
	private Integer id; 
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
	public void setItems(List<Arbol> items) {
		this.items = items;
	}
		
	
}
