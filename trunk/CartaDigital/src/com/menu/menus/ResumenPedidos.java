package com.menu.menus;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.StackView;

public class ResumenPedidos extends Activity {

	private StackAdapter adapt;
	private StackView stV;


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("OncreateResumenPedidos");
		setContentView(R.layout.vista_horizontal_resumen_pedidos);

		// Obtiene la vista del StackView
		stV = (StackView) findViewById(R.id.stackView);

		if (ScreenSlideActivity.getComandasTomadas() == null) {

			// Obtener vista de comanda recien marchada
			View vistaComanda = ScreenSlideActivity.getVistaComanda();
			vistaComanda.setMinimumHeight(700);

			// Lista de vista de comandas
			List<View> l = new ArrayList<View>();
			l.add(vistaComanda);

			// Establece la lista de vistas de comandas
			ScreenSlideActivity.setComandasTomadas(l);

			// Crear el adapter del StackView y asignarlo
			adapt = new StackAdapter(this, R.layout.vista_fragment_comanda, l);
			stV.setAdapter(adapt);

			// Carga el panel opciones con con los campos actuales vista de
			// comanda
			cargaOpciones(stV.getCurrentView());

		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("onStopResPed");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		System.out.println("restartResumenPed");

		// Obtiene la vista del StackView
		stV = (StackView) findViewById(R.id.stackView);

		// Obtener vista de comanda recien marchada
		View v = ScreenSlideActivity.getVistaComanda();
		v.setMinimumHeight(700);

		// Obtiene las vistas de todas las comandas tomadas anteriormente
		List<View> l = ScreenSlideActivity.getComandasTomadas();

		// Añade la vista de comanda recien marchada a las anteriores
		l.add(v);

		// Establece la lista de vistas de comandas
		ScreenSlideActivity.setComandasTomadas(l);

		// Crear el adapter del StackView y asignarlo
		adapt = new StackAdapter(this, R.layout.vista_fragment_comanda, l);
		stV.setAdapter(adapt);

		// Carga el panel opciones con con los campos actuales vista de comanda
		cargaOpciones(stV.getCurrentView());

		/*
		 * Evento para controlar el cambio de los campos del panel opciones con
		 * los datos de la comanda actual al ir cambiando por el StackView
		 */
		stV.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// MotionEvent.

				float y = 0;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					y = event.getY();

				} else

				if ((event.getAction() == MotionEvent.ACTION_UP)) {

					System.out.println("Evento soltar");
					// System.out.println(event.getHistorySize());
					// float
					// inicialY=event.getHistoricalY(event.getHistorySize()-5);
					float finalY = event.getY();

					float dif = finalY - y;
					System.out.println(dif + " "
							+ stV.getPositionForView(stV.getCurrentView()));

					if (dif >= 10) {

						// stV.setSelection(stV.getPositionForView(stV.getCurrentView()));
						cargaOpciones(stV.getChildAt(1));
						System.out.println("adelante");

					} else if (dif <= -10) {
						// stV.showPrevious();
						cargaOpciones(stV.getChildAt(3));

						// if(stV.getPositionForView(stV.getCurrentView())!=stV.getCount()){

						// stV.setSelection(stV.getPositionForView(stV.getCurrentView())-1);
						// cargaOpciones(stV.getCurrentView());
						System.out.println("atras");

						// }

					}

				}

				return false;

			}
		});

	}

	// Muestra en los campos de opciones los datos de la comanda actual en el
	// StackView
	public void cargaOpciones(View vistaComanda) {

		// Obtiene la vista del panel opciones
		View vistaOpciones = findViewById(R.id.Opciones);

		// Obtiene los datos de la comanda actual
		TextView fechaObtenida = (TextView) vistaComanda
				.findViewById(R.id.fModificacion);
		TextView horaObtenida = (TextView) vistaComanda.findViewById(R.id.hora);
		TextView nPlatosObtenido = (TextView) vistaComanda
				.findViewById(R.id.nPlatos);
		TextView importeObtenido = (TextView) vistaComanda
				.findViewById(R.id.importe);

		// Obtiene las vistas de los campos del panel opciones
		TextView fecha = (TextView) vistaOpciones
				.findViewById(R.id.fechaOpciones);
		TextView hora = (TextView) vistaOpciones
				.findViewById(R.id.horaOpciones);
		TextView nPlatos = (TextView) vistaOpciones
				.findViewById(R.id.nPlatosOpciones);
		TextView importe = (TextView) vistaOpciones
				.findViewById(R.id.importeOpciones);

		// Muestra los datos en los campos opciones
		fecha.setText(fechaObtenida.getText());
		hora.setText(horaObtenida.getText());
		nPlatos.setText(nPlatosObtenido.getText());
		importe.setText(importeObtenido.getText());

	}

	// Metodo lanzado al pulsar en seguir añadiendo comandas
	public void anadirNuevaComanda(View v) {

		Intent intent = new Intent(this, ScreenSlideActivity.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		// super.finish();
		System.out.println("Finish");
	}

}
