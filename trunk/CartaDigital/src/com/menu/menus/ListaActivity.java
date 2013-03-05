package com.menu.menus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import android.support.v13.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListaActivity extends Activity {

	private static final String accionSoap = "ServicioWeb/titulo";
	private static final String Metodo = "titulo";
	private static final String namespace = "ServicioWeb";
	private static final String url = "http://192.168.1.35:8080/WebService1.asmx";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_lista);

		// AsyncCallWS task = new AsyncCallWS();
		// task.execute();

	}

	public String titulo(int idCategoria) {

		String accionSoap = "ServicioWeb/titulo";
		String Metodo = "titulo";
		String categoria = null;
		try {

			// Modelo el request
			SoapObject request = new SoapObject(namespace, Metodo);
			request.addProperty("categoria", idCategoria);

			// Modelo el Sobre
			SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			sobre.dotNet = true;
			sobre.setOutputSoapObject(request);

			// Modelo el transporte
			HttpTransportSE transporte = new HttpTransportSE(url);

			// Llamada
			transporte.call(accionSoap, sobre);

			// Resultado
			SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();

			categoria = resultado.toString();

		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			Log.e("alberto", e.getMessage());

		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("alberto", e.getMessage());
		}

		return categoria;

	}

	public void insertarTitulo(final String categoria, ViewGroup vg) {

		String s = "titulo" + categoria;
		int id = vg.getContext().getResources()
				.getIdentifier(s, "id", vg.getContext().getPackageName());
		// Log.e("//*", id + "");

		final TextView text = (TextView) vg.findViewById(id);

		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {

				text.setText(categoria);

			}
		});

	}

	public List<Map<String, String>> lista(String categoria) {

		// Namespace del web service y metodo a consultar
		String accionSoap = "ServicioWeb/lista";
		String Metodo = "lista";
		
		// Lista de maps que contendran los valores mostrados en cada fila
					// de la lista
		List<Map<String, String>> mylist = new ArrayList<Map<String, String>>();

		try {

			// Modelo de la petici�n al Web Service
			SoapObject request = new SoapObject(namespace, Metodo);
			// Par�metro pasado al m�todo del Web Service
			request.addProperty("categoria", categoria);

			// Modelo el Sobre
			SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			sobre.dotNet = true;
			sobre.setOutputSoapObject(request);

			// Modelo el transporte
			HttpTransportSE transporte = new HttpTransportSE(url);

			// Llamada al Web Service
			transporte.call(accionSoap, sobre);

			// Resultado de la funcion del WebService
			SoapObject data = (SoapObject) sobre.getResponse();

			// Lista de maps que contendran los valores mostrados en cada fila
			// de la lista

			for (int i = 0; i < data.getPropertyCount(); i++) {

				SoapObject sdd = (SoapObject) data.getProperty(i);

				HashMap<String, String> map = new HashMap<String, String>();
				for (int j = 0; j < sdd.getPropertyCount(); j++) {

					if (j == 0) {
						map.put("Nombre", sdd.getProperty(j).toString());
					} else {
						map.put("Precio", sdd.getProperty(j).toString()
								+ "\u20AC");
					}
				}

				mylist.add(map);
			}
			

		} catch (XmlPullParserException e) {

			Log.e("alberto", e.getMessage());

		} catch (SoapFault e) {

			Log.e("alberto", e.getMessage());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Log.e("alberto", e.toString());
		}

		return mylist;
	}

	public void insertaLista(String categoria, List<Map<String, String>> lista,
			ViewGroup vg) {

		// Construir el id de la vista de lista con el idCategoria pasada como
		// paramentro
		String s = "lista" + categoria;
		// Con idCategoria=1 , se carga en la listView1, los datos de
		// categoria=1
		// int id = getResources().getIdentifier(s, "id", getPackageName());
		int id = vg.getContext().getResources()
				.getIdentifier(s, "id", vg.getContext().getPackageName());

		// Obtenemos la vista de la lista apropiada
		final ListView list = (ListView) vg.findViewById(id);

		final SimpleAdapter mSchedule = new SimpleAdapter(vg.getContext(),
				lista, R.layout.fila, new String[] { "Nombre", "Precio" },
				new int[] { R.id.column1, R.id.column2 });

		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {

				list.setAdapter(mSchedule);

			}
		});

	}

	private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

		private String TAG = "Vik";

		@Override
		protected Void doInBackground(Void... params) {
			Log.i(TAG, "doInBackground");

			// titulo(1);
			// lista(1);

			// titulo(2);
			// lista(2);

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lista, menu);
		return true;
	}

}
