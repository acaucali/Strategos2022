package com.strategos.nueva.bancoproyectos.model.util;

import java.util.ArrayList;
import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.PerspectivaStrategos;

public class Nodo<T> {
	private PerspectivaStrategos data = null;
	private List<Nodo<T>> children = new ArrayList();
	private Integer parent = null;
	
	public Nodo(PerspectivaStrategos data) {
		this.data = data;
	}
	
	public Nodo<T> addChild(Nodo<T> child){
		child.setParent(this.getData().getPerspectivaId().intValue());
		this.children.add(child);
		return child;
	}
	
	public void addChildren(ArrayList<Nodo<T>> children) {
		children.forEach(each -> each.setParent(this.getData().getPerspectivaId().intValue()));
		this.children.addAll(children);
	}

	public PerspectivaStrategos getData() {
		return data;
	}

	public void setData(PerspectivaStrategos data) {
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
