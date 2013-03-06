package fragments;

import java.util.Map;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.menu.menus.PrecioUndsImporte;
import com.menu.menus.R;


public class FragmentoComandas extends Fragment {

	private ViewGroup rootView;
	private static Map<String, PrecioUndsImporte> platos;

	
	public static Map<String, PrecioUndsImporte> getPlatos() {
		return platos;
	}

	
	public static void setPlatos(Map<String, PrecioUndsImporte> platos) {
		FragmentoComandas.platos = platos;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		rootView = (ViewGroup) inflater.inflate(R.layout.comanda, container,
				false);

		muestraPlatos();
		return rootView;
		
	}

	public void muestraPlatos() {

		if (platos != null) {

			for (String concepto : platos.keySet()) {

				String precio = platos.get(concepto).getPrecio().toString()
						+ "\u20AC";
				String unds = platos.get(concepto).getUnds().toString();
				String importe = platos.get(concepto).getImporte().toString()
						+ "\u20AC";
				setFila(concepto, precio, unds, importe);
				Log.i("plato", concepto + " " + precio);

			}
			Log.i("separador", "------------------------------");
		}
	}

	public void limpiaPlatos() {

		if (platos != null) {

			for (String concepto : platos.keySet()) {

				String precio = platos.get(concepto).getPrecio().toString();
				limpiaFila(concepto, precio);
				Log.i("limpiando", concepto + " " + precio);

			}
			
			Log.i("separador", "------------------------------");
		}

	}

	
	public void limpiaFila(CharSequence concepto, CharSequence precio) {

		TableLayout tl = (TableLayout) rootView.findViewById(R.id.tabla);

		while (tl.getChildAt(2) != null) {
			
			tl.removeViewAt(2);		
		
		}

	}

	
	public void setFila(CharSequence concepto, CharSequence precio,
			CharSequence unidades, CharSequence importe) {

		TableLayout tl = (TableLayout) rootView.findViewById(R.id.tabla);
		View clonado = LayoutInflater.from(getActivity()).inflate(
				R.layout.comanda, null);
		
		TableLayout tablaClonada = (TableLayout) clonado
				.findViewById(R.id.tabla);
		
		TableRow filaClonada = (TableRow) clonado.findViewById(R.id.tableRow2);
		ImageView separadorClonado = (ImageView) clonado
				.findViewById(R.id.ImageView01);
		
		tablaClonada.removeView(filaClonada);
		tablaClonada.removeView(separadorClonado);
		
		TextView unds = (TextView) filaClonada.findViewById(R.id.textView2cant);
		unds.setText(unidades);

		TextView concept = (TextView) filaClonada
				.findViewById(R.id.textView2concepto);
		concept.setText(concepto);

		TextView prec = (TextView) filaClonada
				.findViewById(R.id.textView2precio);
		prec.setText(precio.toString().replace(".", ","));

		TextView imp = (TextView) filaClonada.findViewById(R.id.textView2total);
		imp.setText(importe.toString().replace(".", ","));

		filaClonada.setVisibility(View.VISIBLE);
		separadorClonado.setVisibility(View.VISIBLE);

		tl.addView(filaClonada);
		tl.addView(separadorClonado);

	}

	
	public void setConcepto(CharSequence con) {

		TextView tv = (TextView) rootView.findViewById(R.id.textView2concepto);
		tv.setText(con);
	
	}

	
	public void setPrecio(CharSequence prec) {

		TextView tv = (TextView) rootView.findViewById(R.id.textView2precio);
		tv.setText(prec);

	}

}
