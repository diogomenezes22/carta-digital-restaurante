package com.menu.menus;

import java.io.Serializable;


// Clase que sera objeto del map de la estructura de platos seleccionados 
public class PrecioUndsImporte implements Serializable{

	
	private Double precio;
	private Integer unds;
	private Double importe;
	
	
	// Constructor de la clase
	public PrecioUndsImporte(Double precio, Integer unds, Double importe){
		
		this.precio=precio;
		this.unds=unds;
		this.importe=importe;
		
	}	
	
	public Double getPrecio() {
		return precio;
	}
		
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	public Integer getUnds() {
		return unds;
	}
	
	public void setUnds(Integer unds) {
		this.unds = unds;
		this.importe=unds*precio;
	}
	
	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	@Override
	public String toString() {
		return "PrecioUndsImporte [precio=" + precio + ", unds=" + unds
				+ ", importe=" + importe + "]";
	}
	
	
	
	
}
