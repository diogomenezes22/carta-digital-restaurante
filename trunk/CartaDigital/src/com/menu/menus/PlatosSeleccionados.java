package com.menu.menus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/*
 * Clase de gestion de platos seleccionados
 */

public class PlatosSeleccionados implements Serializable {

	// Estructura de almacenamiento de los platos seleccionados
	private Map<String, Map<String, PrecioUndsImporte>> platosSeleccionados;

	
	// Constructor que iniciliza la estructura
	public PlatosSeleccionados() {

		platosSeleccionados = new LinkedHashMap<String, Map<String, PrecioUndsImporte>>();

	}

	// Getter de la estructura de platos seleccionados
	public Map<String, Map<String, PrecioUndsImporte>> getPlatos() {
		return platosSeleccionados;
	}

	// Setter de la estructura de platos seleccionados
	public void setPlatos(Map<String, Map<String, PrecioUndsImporte>> platos) {
		this.platosSeleccionados = platos;
	}

	// Setter de categoria
	public void setCategoria(String categoria) {

		platosSeleccionados.put(categoria, new HashMap<String, PrecioUndsImporte>());

	}

	// Obtiene las categorias de la estructura
	public Set<String> categorias() {

		return platosSeleccionados.keySet();

	}

	// Obtiene los nombre de platos de la categoria pasada como parametro
	public Set<String> nombrePlatos(String categoria) {

		return platosSeleccionados.get(categoria).keySet();

	}

	// Obtiene el precio del plato con su nombre y categoria como parametros
	public Double precioPlato(String categoria, String nombre) {

		return platosSeleccionados.get(categoria).get(nombre).getPrecio();

	}

	// Obtiene las unidades del plato con su nombre y categoria como parametros
	public Integer unidadesPlato(String categoria, String nombre) {

		return platosSeleccionados.get(categoria).get(nombre).getUnds();

	}

	// Obtiene el importe del plato con su nombre y categoria como parametros
	public Double importePlato(String categoria, String nombre) {

		return platosSeleccionados.get(categoria).get(nombre).getImporte();

	}

	// Setter de plato. PrecioUndsImporte solo se usa si el plato es nuevo en la estructura 
	public void setPlato(String categoria, String nombrePlato,
			PrecioUndsImporte precUndsImp) {

		if (estaPlato(categoria, nombrePlato)) {

			// Si el plato esta en la estructura se aumentan las unidades
			platosSeleccionados.get(categoria).get(nombrePlato).setUnds(
							platosSeleccionados.get(categoria).get(nombrePlato)
									.getUnds() + 1);

		} else {

			// Si no esta, introduce el plato
			platosSeleccionados.get(categoria).put(nombrePlato, precUndsImp);

		}

	}

	// Elimina un plato con su nombre y categoria como parametros
	public void eliminarPlato(String categoria, String nombrePlato) {

		platosSeleccionados.get(categoria).remove(nombrePlato);

	}

	// Devuelve true si el plato con su nombre y categoria esta en la estructura
	public boolean estaPlato(String categoria, String nombrePlato) {

		return platosSeleccionados.get(categoria).containsKey(nombrePlato);

	}

}
