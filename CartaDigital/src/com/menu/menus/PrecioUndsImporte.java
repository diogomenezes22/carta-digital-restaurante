package com.menu.menus;


// Clase que sera objeto del map de la estructura de platos seleccionados 
public class PrecioUndsImporte {

	
	private Double precio;
	private Integer unds;
	private Double importe;
	
	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
}
