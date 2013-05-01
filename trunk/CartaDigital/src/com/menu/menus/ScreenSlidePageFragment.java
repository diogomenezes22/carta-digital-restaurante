
package com.menu.menus;

import java.util.HashMap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fragments.FragmentoComanda;
import fragments.FragmentoTwitter;


public class ScreenSlidePageFragment extends Fragment {

	
	// Nombre del argumento numero de pagina
	public static final String ARG_PAGE = "page";
	// Numero de pagina
	private int mPageNumber;
	private HashMap<String, String> curGroupMap1;
	// Ultima vista de pagina cargada
	private ViewGroup rootView;
	// Vista de panel de Opciones
	private static ViewGroup optsView;
	
	
	// Getter para la vista del panel Opciones
	public static ViewGroup getOptsView() {
		return optsView;
	}
	
	// Getter para el nombre de arg de nº de pagina
	public static String getArgPage() {
		return ARG_PAGE;
	}
	
	public HashMap<String, String> getCurGroupMap1() {
		return curGroupMap1;
	}
	
	public void setCurGroupMap1(HashMap<String, String> curGroupMap1) {
		this.curGroupMap1 = curGroupMap1;
	}

	/*
	 * Factory method for this fragment class. Constructs a new fragment for the
	 * given page number.
	 */
	public static ScreenSlidePageFragment create(int pageNumber) {
		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public ScreenSlidePageFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);

	}

	// Carga de paginas
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Carga de pagina 0
		if (mPageNumber == 0) {

			rootView = (ViewGroup) inflater.inflate(
					R.layout.vista_horizontal_bebidas_vinos, container, false);

		}
		
		// Carga de pagina 1
		if (mPageNumber == 1) {

			rootView = (ViewGroup) inflater.inflate(
					R.layout.vista_horizontal_entrantes_com_tradicional, container, false);

		}
		
		// Carga de pagina 2
		if (mPageNumber == 2) {

			rootView = (ViewGroup) inflater.inflate(
					R.layout.vista_horizontal_pescados_carnes, container, false);

		}

		// Carga de pagina 3
		if (mPageNumber == 3) {

			rootView = (ViewGroup) inflater.inflate(R.layout.vista_horizontal_comanda_ops,
					container, false);
			
			// Guarda de panel Opciones
			optsView = rootView;
			
			// Crea nuevo FragmentoComandas
			FragmentoComanda fComanda = new FragmentoComanda();

			// Añade el fragmento de manera dinamica para evitar el duplicado de
			// tag en la vuelta a cargar
			FragmentTransaction fragmentTransaction = getFragmentManager()
					.beginTransaction();
			fragmentTransaction.add(R.id.layoutFragment, fComanda,
					"Comanda");
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();

			
			FragmentoTwitter fragmentTwitter = new FragmentoTwitter();

			// Añade el fragmento de manera dinamica para evitar el duplicado de
			// tag en la vuelta a cargar. necesario para finalizar Thread de tweets
			// al destruir pagina 3. (ScreenSlideActivity)
			FragmentTransaction fragmentTrans = getFragmentManager()
					.beginTransaction();
			fragmentTrans.add(R.id.Opciones, fragmentTwitter,
					"Opciones");
			fragmentTrans.addToBackStack(null);
			fragmentTrans.commit();

		}

		return rootView;
		
	}


	/*
	 *Devuelve el nº de pagina 
	 */
	public int getPageNumber() {
		return mPageNumber;
	}

}
