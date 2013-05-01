/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.menu.menus;


import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.viewpagerindicator.TitlePageIndicator;
import fragments.FragmentoComanda;
import fragments.FragmentoTwitter;


public class ScreenSlideActivity extends FragmentActivity {
	
	
	// Numero de paginas a mostrar
	private static final int NUM_PAGES = 4;	
	// El ViewPager 
	private ViewPager mPager;		
	// Estructura de almacenamiento de platos seleccionados Map<Categoria, Map<NombrePlato,PrecioUndsImporte>>
	private PlatosSeleccionados platosSeleccionados;
	// El pager adapter, proporciona las paginas al viewpager.
	private FragmentPagerAdapter mPagerAdapter;
	// Vista de la comanda actual
	private static View vistaComanda;
	// Lista con las vistas de las diferentes comandas a mostrar como StackView en ResumenPedidos
	private static List<View> comandasTomadas;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		// Instanciacion de estructura
		platosSeleccionados=new PlatosSeleccionados();
		// Introducir categorias existentes
		platosSeleccionados.setCategoria("Bebidas");
		platosSeleccionados.setCategoria("Vinos");
		platosSeleccionados.setCategoria("Entrantes");
		platosSeleccionados.setCategoria("Tradicional");
		platosSeleccionados.setCategoria("Pescado");
		platosSeleccionados.setCategoria("Carne");
	
		// Carga de vista principal (TitlePager y ViewPager)
		setContentView(R.layout.contenedor_pager_principal);
		
		// Recoge ViewPager de la vista 
		mPager = (ViewPager) findViewById(R.id.pager);
		
		// Crea adapter personalizado 
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		
		// Establecer el adapter en el pager
		mPager.setAdapter(mPagerAdapter);
		
		// Recoge el TitlePageIndicator de la vista y lo incluye en el Pager
		TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.titles);
		titleIndicator.setViewPager(mPager);
		System.out.println("onCreateSlide");
		
	}


	/*
	 * Metodo lanzado al volver a pedir de la carta y retomar la instancia anterior
	 * No se crea una nueva instancia
	 */
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();		
		System.out.println("onRestartScreenSlide");
		
		mPager.setCurrentItem(0);
		
		platosSeleccionados=new PlatosSeleccionados();
		platosSeleccionados.setCategoria("Bebidas");
		platosSeleccionados.setCategoria("Vinos");
		platosSeleccionados.setCategoria("Entrantes");
		platosSeleccionados.setCategoria("Tradicional");
		platosSeleccionados.setCategoria("Pescado");
		platosSeleccionados.setCategoria("Carne");
		
		FragmentoComanda.setPlatosSeleccionados(platosSeleccionados);
		
	}


	//Metodo lanzado al marchar comanda
	public void marchaComanda(View v){
		
		// Elimina el fragmento twitter para finalizar el Thread
		FragmentoTwitter fTwitter= (FragmentoTwitter) getSupportFragmentManager().findFragmentByTag("Opciones");
		fTwitter.terminar();	
		
		// Elimina el fragmento twitter para finalizar el Thread
		FragmentoComanda f = (FragmentoComanda) getSupportFragmentManager()
				.findFragmentByTag("Comanda");			

		// Pasar como parametro a la nueva activity los platos 
		setVistaComanda(f.getRootView());
		
		// Inicia nueva Activity de resumen de pedidos
		Intent intent=new Intent(this, ResumenPedidos.class);
		startActivity(intent);
		
	}


	// Metodo manejador al pulsar pedir un plato (Recoge la vista del boton causante)
	public void manejadorPedido(View v) {

		// Obtiene el padre del boton, la fila del plato
		LinearLayout fila = (LinearLayout) v.getParent();
		
		// Obtiene datos del plato mediante su fila y posicion
		TextView nombre = (TextView) fila.getChildAt(0);
		TextView categoria = (TextView) fila.getChildAt(1);
		TextView precio = (TextView) fila.getChildAt(3);
		
		// Toast que muestra la info 
		//Toast t = Toast.makeText(v.getContext(), nombre.getText(), 100);
		//t.show();

		// Nuevo objeto para alamacenar el precio, unidades e importe del plato
		PrecioUndsImporte precUndsImp = new PrecioUndsImporte(
				Double.valueOf(precio.getText().toString()
						// Intercambio de . por , para poder convertirlo a Double
						.replace("\u20AC", " ").replace(",", "*").replace(".", ",").replace("*", ".")), 1,
				Double.valueOf(precio.getText().toString()
						// Intercambio de . por , para poder convertirlo a Double
						.replace("\u20AC", " ").replace(",", "*").replace(".", ",").replace("*", ".")));
		
		// Añade plato nuevo o suma 1 a la cantidad
		platosSeleccionados.setPlato(categoria.getText().toString(), nombre.getText().toString(), precUndsImp);
		
		// Establece la estructura de datos en el FragmentoComandas 
		FragmentoComanda.setPlatosSeleccionados(platosSeleccionados);

		/* 
		 * Si la pagina actual es la 2ª ya ha cargado la 3ª (pagina de comanda), la 
		 * tiene en memoria y procede a recoger su vista y añadir el plato y mostrar los almacenados.   
		 * Este bucle se complementa con la carga y muestra de los platos cuando se carga 
		 * la 3ª (pagina de comanda), pagina cuya pulsacion en la pagina 2 no recoge.
		 * */
		if (mPager.getCurrentItem() == 2) {

			// Recogida de fragment Comanda mostrado
			FragmentoComanda f = (FragmentoComanda) getSupportFragmentManager()
					.findFragmentByTag("Comanda");
			
			if(platosSeleccionados.estaPlato(categoria.getText().toString(), nombre.getText().toString())){
				
					/* 
					 * Si esta el plato en la estructura limpiamos y mostramos toda la 
					 * estructura. 
					 */
					f.limpiaPlatos();
					// Al estar el plato en la estructura no es necesario el ultimo parametro
					platosSeleccionados.setPlato(categoria.getText().toString(), nombre.getText().toString(), null);
					f.setPlatosSeleccionados(platosSeleccionados);
					f.muestraPlatos();
				
			}else{
	
				// Añadir la fila con el nuevo plato(mas eficiente que volver a mostrar todo)
				f.setFila(nombre.getText(), precUndsImp.getPrecio().toString(), precUndsImp.getUnds().toString(), precUndsImp.getImporte().toString(), categoria.getText());
			
			}
			
			f.setImporteTotal();
			
		} 

	}

	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("ScreenSlideActivityStop");
	}


	public void eliminaPlato(View v){
		
		// Obtiene el padre del boton, la fila del plato
		LinearLayout fila = (LinearLayout) v.getParent();
			
		// Obtiene datos del plato mediante su fila y posicion
		TextView nombrePlato = (TextView) fila.getChildAt(1);
		TextView categoria = (TextView) fila.getChildAt(5);
		
		// Recoge el fragmento Comanda que esta siendo mostrado
		FragmentoComanda f = (FragmentoComanda) getSupportFragmentManager()
				.findFragmentByTag("Comanda");
		
		// Eliminar plato seleccionado y establece la nueva estructura sin el plato
		platosSeleccionados.eliminarPlato(categoria.getText().toString(), nombrePlato.getText().toString());
		f.setPlatosSeleccionados(platosSeleccionados);			
		
		// Limpia platos anteriores y muestra la nueva lista de platos
		f.limpiaPlatos();	
		f.muestraPlatos();
		
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();

	}

	/*
	 * Getter y setter  de la vista de la ultima comanda 
	 */	
	public static View getVistaComanda() {
		((ViewManager)vistaComanda.getParent()).removeView(vistaComanda);
		return vistaComanda;
	}
	
	public static void setVistaComanda(View vistaComanda) {
		ScreenSlideActivity.vistaComanda = vistaComanda;
	}

	
	/*
	 * Getter y setter  de la lista de vistas de comandas(cada elemento del StackView) 
	 */	
	public static List<View> getComandasTomadas() {
		return comandasTomadas;
	}
	
	public static void setComandasTomadas(List<View> cmdsTomadas) {
		comandasTomadas = cmdsTomadas;
	}



	/*
	 * PagerApdapter que muestra 4 paginas 
	 */
	private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

		
		private String[] titles = { "Bebidas y Vino",
				"Entrantes y Comida tradicional", "Pescado y Carne", "Comanda" };

		
		public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		
		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		
		@Override
		public Fragment getItem(int position) {
			return ScreenSlidePageFragment.create(position);

		}
		
		// Metodo lanzado al eliminar una pagina
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			
			// Si la pagina eliminada de memoria es la 3(Comanda y twitter) elimina el
			// fragmento twitter para finalizar el Thread de actualizacion
			if(position==3){
				
				FragmentoTwitter fTwitter= (FragmentoTwitter) getSupportFragmentManager().findFragmentByTag("Opciones");
				fTwitter.onDestroyView();				
				
			}
			
			super.destroyItem(container, position, object);
		
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}

	}
}
