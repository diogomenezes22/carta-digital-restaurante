package com.menu.menus;

import java.io.IOException;
import java.text.DecimalFormat;
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
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class ListaActivity extends Activity{

	// Namespace + nombre de metodo 
	private String accionSoap;
	private String metodo;
	// Nombre del espacio del Web Service
	private static final String namespace = "ServicioWeb";
	// Direccion del Web Sevice
	private static final String url = "http://192.168.1.34:8080/WebService1.asmx";

	
	public String titulo(int idCategoria) {
		
		// Namespace + nombre de metodo 
		accionSoap = "ServicioWeb/titulo";
		metodo = "titulo";
		String categoria = null;
		try {

			// Modelo el request
			SoapObject request = new SoapObject(namespace, metodo);
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

	
	// Muestra el titulo en la vista pasada como parametro 
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

	
	//Lista de Maps para incluirlo en el adapter para mostrarlo con el metodo insertaLista
	public List<Map<String, String>> lista(String categoria) {

		// Namespace del web service y metodo a consultar
		accionSoap = "ServicioWeb/lista";
		metodo = "lista";	
		
		// Lista de maps que contendran los valores mostrados en cada fila
					// de la lista
		List<Map<String, String>> mylist = new ArrayList<Map<String, String>>();

		try {

			// Modelo de la petición al Web Service
			SoapObject request = new SoapObject(namespace, metodo);
			// Parámetro pasado al método del Web Service
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
				
				//Recoge los datos de BD y los mete como parametros en pares en un map
				for (int j = 0; j < sdd.getPropertyCount(); j++) {

					if (j == 0) {
						
						map.put("Nombre", sdd.getProperty(j).toString());
						
					} else {
						
						// Crea el formato de dos decimales para aplicarlo al mostrar el precio
						DecimalFormat dosdec=new DecimalFormat();
						dosdec.setMinimumFractionDigits(2);
						
						// Obtiene el dato de la bd (##,##) reemplaza la , por . convierte el dato a Double 
						// y formatea el numero para mostrar siempre 2 decimales
						String precioFormateado=dosdec.format(Double.valueOf(sdd.getProperty(j).toString().replace(",", "."))) +"\u20AC";						
						map.put("Precio", precioFormateado);
						
					}
					
				}
				
				map.put("Categoria", categoria);
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

	
	// Muestra la lista obtenida obtenida en el metodo anterior en la vista pasada
	public void insertaLista(String categoria, List<Map<String, String>> lista,
			ViewGroup vista) {

		// Construir el id de la vista de lista con el idCategoria pasada como
		// paramentro
		String s = "lista" + categoria;
	
		
		// int id = getResources().getIdentifier(s, "id", getPackageName());
		int id = vista.getContext().getResources()
				.getIdentifier(s, "id", vista.getContext().getPackageName());

		// Obtiene la vista de la lista apropiada
		final ListView vistaLista = (ListView) vista.findViewById(id);

		// Crea adapter enlazando vistas con sus campos 
		final SimpleAdapter mSchedule = new SimpleAdapter(vista.getContext(),
				lista, R.layout.fila_carta, new String[] { "Nombre", "Precio", "Categoria" },
				new int[] { R.id.column1, R.id.column2, R.id.Categoria });

		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {

				vistaLista.setAdapter(mSchedule);

			}
		});

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lista, menu);
		return true;
	}

}
