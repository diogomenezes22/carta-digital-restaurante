package com.menu.menus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.viewpagerindicator.PageIndicator;

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
	private static final String url = "http://10.0.2.2:51511/WebService1.asmx";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista);
		
		AsyncCallWS task = new AsyncCallWS();
		task.execute();
		
	}

	public void titulo(int idCategoria) {

		String accionSoap = "ServicioWeb/titulo";
		String Metodo = "titulo";
		
		String s="textView"+idCategoria;
		int id=getResources().getIdentifier(s, "id", getPackageName());
		Log.e("//*", id+"");
		
		try {

			final TextView text = (TextView) findViewById(id);
			final String valores;

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

			valores = resultado.toString();

			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {

					text.setText(valores);

				}
			});

		} catch (XmlPullParserException e) {

			Log.e("alberto", e.getMessage());

		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Log.e("alberto", e.toString());
		}

	}

	public void manejadorPedido(View v){
		
		LinearLayout fila=(LinearLayout)v.getParent();
		TextView nombre=(TextView)fila.getChildAt(0);
		Toast t=Toast.makeText(v.getContext(), nombre.getText(), Toast.LENGTH_SHORT);
		t.show();
		
	}

	
	public void lista(int idCategoria) {

		String accionSoap = "ServicioWeb/lista";
		String Metodo = "lista";
		String s="listView"+idCategoria;

		int id=getResources().getIdentifier(s, "id", getPackageName());
		Log.e("//*", id+"");
		try {
			final ListView list = (ListView) findViewById(id);

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
			SoapObject data = (SoapObject) sobre.getResponse();
			final String[][] valores = new String[data.getPropertyCount()][];
			for (int i = 0; i < data.getPropertyCount(); i++) {
				
				SoapObject sdd=(SoapObject)data.getProperty(i);

				valores[i]=new String[sdd.getPropertyCount()];
				
				for (int j = 0; j < sdd.getPropertyCount(); j++) {
					
					valores[i][j] = sdd.getProperty(j).toString();
				
				}
			
			}

			List<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
			
			for(int i=0;i<valores.length;i++)
			{
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("Nombre", valores[i][0]);
				map.put("Precio", valores[i][1]+"\u20AC");
				mylist.add(map);
				
			}

			final SimpleAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.fila,
			            new String[] {"Nombre", "Precio"}, new int[] { R.id.column1, R.id.column2});
			

			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					list.setAdapter(mSchedule);		
						
				}
			});

		} catch (XmlPullParserException e) {

			Log.e("alberto", e.getMessage());

		} catch (SoapFault e) {

			Log.e("alberto", e.getMessage());
		}
		// try {

		catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Log.e("alberto", e.toString());
		}
	

	}

	private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

		private String TAG = "Vik";

		@Override
		protected Void doInBackground(Void... params) {
			Log.i(TAG, "doInBackground");
			
			titulo(1);
			lista(1);
			
			titulo(2);
			lista(2);
				
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
