package fragments;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.menu.menus.R;

import android.R.string;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.SlidingDrawer;
import android.widget.TextView;

public class FragmentoTwitter extends Fragment {

	// Lista de Tweets
	private List<Status> lista;
	// Vista principal
	private ViewGroup rootView;
	// Centinela para finalizacion de Thread
	private boolean continuar = true;

	// Thread de carga de tweets
	Thread fmTicker = new Thread() {

		// Metodo lanzado al ejecutar Thread
		public void run() {

			QueryResult result = null;
			// Parametros de configuracion de Twitter
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
					.setOAuthConsumerKey("FHTHDSnaOI4vJpB74QJg")
					.setOAuthConsumerSecret(
							"0tO74Eb1GrTi2A60XJkWvZ3oaxdYevZ1npYL1jB8lmI")
					.setOAuthAccessToken(
							"1255151029-7PHnxhCh9efC6wTOsEULGde5Rg99cdBXYUBTrlH")
					.setOAuthAccessTokenSecret(
							"EEGdDQ1dyIelfgo071UQW3cZfYTBTrkFltSto6084");

			// Crea la variable Twitter con la configuracion anterior
			Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			// Parametro de la consulta
			Query query = new Query("#NBA");

			try {

				// Consulta 
				result = twitter.search(query);
				// Obtiene lista de resultados
				lista = result.getTweets();

				int i = 0;
				
				// Mientras este cargado sigue ejecutando el Thread
				// Al eliminar la vista finalizara el Thread 
				while (continuar) {

					// A partir del 3º tweet espera 5s para cargar 
					if (i >= 3) {

						try {

							sleep(5000);

						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					//
					final int j = i;
					
					// Para ejecutar en el Thread de interfaz de usuario 
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {

							// Actualiza USUARIO y MENSAJE
							setMensaje(j);
							Log.i("TWEET " + j, lista.get(j).getText());
						}

					});

					i++;
					
					// Muestra 15 mensajes
					if (i == 14) {

						i = 0;

					}

				}

			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		// Al crear la vista carga el layout
		rootView = (ViewGroup) inflater.inflate(R.layout.vista_fragment_twitter,
				container, false);

		// Lanza el Thread de carga y actualizacion de tweets
		fmTicker.start();

		return rootView;

	}

	// Carga un tweet en uno de los 3 espacios reservados mediante un numero de
	// tweet
	public void setMensaje(int i) {

		// Selecciona la vista de mensaje mediante el modulo 3
		// (muestra 3 mensajes simultaneos 0,1,2)

		// Construye el nombre de id de la vista, para el USUARIO
		String nombreId = "usuarioTwitter" + i % 3;
		int id = getResources().getIdentifier(nombreId, "id",
				getActivity().getPackageName());

		// Obtiene vista y le asigna el nombre de usuario
		TextView user = (TextView) rootView.findViewById(id);
		user.setText(lista.get(i).getUser().getName());

		// --------------------------------------------------------------------------------

		// Construye el nombre de id de la vista, para el MENSAJE
		nombreId = "mensajeTwitter" + i % 3;
		id = getResources().getIdentifier(nombreId, "id",
				getActivity().getPackageName());

		// Obtiene vista y le asigna el nombre de usuario
		TextView msg = (TextView) rootView.findViewById(id);
		msg.setText(lista.get(i).getText());

	}

	// Al destruir esta vista finaliza el Thread de actualizacion de tweets
	// con el centinela continuar
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		continuar = false;
	}

}
