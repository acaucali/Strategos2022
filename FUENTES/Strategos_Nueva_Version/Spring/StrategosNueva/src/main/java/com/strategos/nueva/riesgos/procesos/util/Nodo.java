package com.strategos.nueva.riesgos.procesos.util;

import java.util.ArrayList;
import java.util.List;

import com.strategos.nueva.riesgos.procesos.model.Procesos;



public class Nodo<T> {
	private Procesos data = null;
	private List<Nodo<T>> children = new ArrayList();
	private Integer parent = null;
	
	public Nodo(Procesos data) {
		this.data = data;
	}
	
	public Nodo<T> addChild(Nodo<T> child){
		child.setParent(this.getData().getProcesosId().intValue());
		this.children.add(child);
		return child;
	}
	
	public void addChildren(ArrayList<Nodo<T>> children) {
		children.forEach(each -> each.setParent(this.getData().getProcesosId().intValue()));
		this.children.addAll(children);
	}

	public Procesos getData() {
		return data;
	}

	public void setData(Procesos data) {
		this.data = data;
	}

	public List<Nodo<T>> getChildren() {
		return children;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}
	
	
}
