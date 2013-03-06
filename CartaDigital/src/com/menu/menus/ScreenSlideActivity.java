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

import java.util.HashMap;
import java.util.Map;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.viewpagerindicator.TitlePageIndicator;
import fragments.FragmentoComandas;

/**
 * Demonstrates a "screen-slide" animation using a {@link ViewPager}. Because
 * {@link ViewPager} automatically plays such an animation when calling
 * {@link ViewPager#setCurrentItem(int)}, there isn't any animation-specific
 * code in this sample.
 * 
 * <p>
 * This sample shows a "next" button that advances the user to the next step in
 * a wizard, animating the current screen out (to the left) and the next screen
 * in (from the right). The reverse animation is played when the user presses
 * the "previous" button.
 * </p>
 * 
 * @see ScreenSlidePageFragment
 */
public class ScreenSlideActivity extends FragmentActivity {
	/**
	 * The number of pages (wizard steps) to show in this demo.
	 */
	private static final int NUM_PAGES = 4;

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;
	
	// Estructura de almacenamiento de platos seleccionados
	private Map<String, PrecioUndsImporte> m;
	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private FragmentPagerAdapter mPagerAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		// Instanciacion de estructura
		m = new HashMap<String, PrecioUndsImporte>();
		
		// Carga de vista principal (TitlePager y ViewPager)
		setContentView(R.layout.activity_screen_slide);
		
		// Recoge ViewPager de la vista 
		mPager = (ViewPager) findViewById(R.id.pager);
		
		// Crea adapter personalizado 
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		
		// Establecer el adapter en el pager
		mPager.setAdapter(mPagerAdapter);
		
		// Recoge el TitlePageIndicator de la vista y lo incluye en el Pager
		TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.titles);
		titleIndicator.setViewPager(mPager);

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

		menu.findItem(R.id.action_previous).setEnabled(
				mPager.getCurrentItem() > 0);

		// Add either a "next" or "finish" button to the action bar, depending
		// on which page
		// is currently selected.
		MenuItem item = menu
				.add(Menu.NONE,
						R.id.action_next,
						Menu.NONE,
						(mPager.getCurrentItem() == mPagerAdapter.getCount() - 1) ? R.string.action_finish
								: R.string.action_next);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// Navigate "up" the demo structure to the launchpad activity.
			// See http://developer.android.com/design/patterns/navigation.html
			// for more.
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
			return true;

		case R.id.action_previous:
			// Go to the previous step in the wizard. If there is no previous
			// step,
			// setCurrentItem will do nothing.
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			return true;

		case R.id.action_next:
			// Advance to the next step in the wizard. If there is no next step,
			// setCurrentItem
			// will do nothing.
			mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	
	// Procedimiento manejador al pulsar pedir un plato (Recoge la vista del boton causante)
	public void manejadorPedido(View v) {

		// Obtiene el padre del boton, la fila del plato
		LinearLayout fila = (LinearLayout) v.getParent();
		
		// Obtiene datos del plato mediante su fila
		TextView nombre = (TextView) fila.getChildAt(0);
		TextView precio = (TextView) fila.getChildAt(2);
		
		// Toast que muestra la info 
		Toast t = Toast.makeText(v.getContext(), nombre.getText(), 1000);
		t.show();

		// Consulta si el nombre del plato esta en la estructura
		if (!m.containsKey(nombre.getText())) {

			// Si no esta, prepara los datos y los mete en la estructura
			// Elimina el simbolo del euro en precio e importe y pone la cantidad a 1
			PrecioUndsImporte precUndsImp = new PrecioUndsImporte(
					Double.valueOf(precio.getText().toString()
							.replace("\u20AC", " ").replace(",", ".")), 1,
					Double.valueOf(precio.getText().toString()
							.replace("\u20AC", " ").replace(",", ".")));
			
			m.put(nombre.getText().toString(), precUndsImp);

		} else {

			// Si esta incrementa las unidades
			m.get(nombre.getText().toString()).setUnds(
					m.get(nombre.getText().toString()).getUnds() + 1);

		}

		/* 
		 * Si la pagina actual es la 2ª ya ha cargado la 3ª (pagina de comanda), la 
		 * tiene en memoria y procede a recoger su vista y añadir el plato y mostrar los almacenados.   
		 * Este bucle se complementa con la carga y muestra de los platos cuando se carga 
		 * la 3ª (pagina de comanda) pagina cuya pulsacion en la pagina 2 no recoge.
		 * */
		if (mPager.getCurrentItem() == 2) {

			FragmentoComandas f = (FragmentoComandas) getSupportFragmentManager()
					.findFragmentByTag("Comanda");
			f.limpiaPlatos();
			FragmentoComandas.setPlatos(m);
			f.muestraPlatos();

		} else {
			
			// Si no es la pagina 2, establece la estructura de datos en el FragmentoComandas 
			FragmentoComandas.setPlatos(m);

		}

	}

	/**
	 * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment}
	 * objects, in sequence.
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

		
		@Override
		public int getCount() {
			return NUM_PAGES;
		}

	}
}
