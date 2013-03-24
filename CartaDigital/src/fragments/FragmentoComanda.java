package fragments;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import twitter4j.Twitter;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.menu.menus.PrecioUndsImporte;
import com.menu.menus.R;
import com.menu.menus.ScreenSlidePageFragment;

public class FragmentoComanda extends Fragment {

	private ViewGroup rootView;
	private static Map<String, Map<String, PrecioUndsImporte>> platos;

	
	public static Map<String, Map<String, PrecioUndsImporte>> getPlatos() {
		return platos;
	}

	
	public static void setPlatos(Map<String, Map<String, PrecioUndsImporte>> platos) {
		FragmentoComanda.platos = platos;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// Permite acceso a entrada salida en Thread
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Inflate the layout for this fragment
		rootView = (ViewGroup) inflater.inflate(R.layout.vista_fragment_comanda, container,
				false);
		
		// Muestra los platos seleccionados
		muestraPlatos();
		
		return rootView;

	}

		
	public void muestraPlatos() {

		double total = 0;

		if (platos != null) {

			for (String categoria : platos.keySet()) {

				for (String concepto : platos.get(categoria).keySet()) {

					String precio = platos.get(categoria).get(concepto)
							.getPrecio().toString();

					String unds = platos.get(categoria).get(concepto).getUnds()
							.toString();

					String importe = platos.get(categoria).get(concepto)
							.getImporte().toString();
					total = total+Double.valueOf(importe);
					setFila(concepto, precio, unds, importe);

				}

			}
			
			setImporteTotal(total);
			
		}
	}

	
	private void setImporteTotal(Double importe) {
		// TODO Auto-generated method stub

		// Formatea siempre con 2 decimales el importe
		DecimalFormat formato=new DecimalFormat();
		formato.setMinimumFractionDigits(2);
		
		String importeFormato = formato.format(importe);
		
		// Obtener vista de la pagina 3 (comanda y ops) 
		TextView importeTotal = (TextView) ScreenSlidePageFragment.getOptsView()
				.findViewById(R.id.importeTotal);
		
		// Establece importe
		importeTotal.setText(importeFormato + "\u20AC");

	}

	
	public void setFila(CharSequence concepto, CharSequence precio,
			CharSequence unidades, CharSequence importe) {

		// Tabla original
		TableLayout tl = (TableLayout) rootView.findViewById(R.id.tabla);

		// Creamos nueva vista clonada para obtener elementos
		View clonado = LayoutInflater.from(getActivity()).inflate(
				R.layout.vista_fragment_comanda, null);

		// Recoge tabla clonada
		TableLayout tablaClonada = (TableLayout) clonado
				.findViewById(R.id.tabla);

		// Obtiene de la tabla clonada la fila y el separador
		TableRow filaClonada = (TableRow) clonado.findViewById(R.id.tableRow2);
		ImageView separadorClonado = (ImageView) clonado
				.findViewById(R.id.ImageView2);

		// Elimina los elementos clonados de la vista clonada para no
		// duplicarlos al añadirlos a la vista original
		tablaClonada.removeView(filaClonada);
		tablaClonada.removeView(separadorClonado);

		// Obtiene la vista de cant de la fila clonada y establece su nuevo
		// valor
		TextView unds = (TextView) filaClonada.findViewById(R.id.textView2cant);
		unds.setText(unidades);

		// Obtiene la vista de concepto de la fila clonada y establece su nuevo valor
		TextView concept = (TextView) filaClonada
				.findViewById(R.id.textView2concepto);
		concept.setText(concepto);

		// Crea el formato de dos decimales para aplicarlo al mostrar el precio
		DecimalFormat dosdec = new DecimalFormat();
		dosdec.setMinimumFractionDigits(2);

		// El formato recibido es (##.##) y formatea para mostrar siempre 2
		// decimales y reemplazar el . por , (formatea en formato de la region) añade el simbolo del euro
		String precioFormato = dosdec.format(Double.valueOf(precio.toString()));
		TextView prec = (TextView) filaClonada.findViewById(R.id.textView2precio);
		prec.setText(precioFormato + "\u20AC");

		/// El formato recibido es (##.##) y formatea para mostrar siempre 2
		// decimales y reemplazar el . por , (formatea en formato de la region) añade el simbolo del euroro
		String importeFormato = dosdec
				.format(Double.valueOf(importe.toString()));
		TextView imp = (TextView) filaClonada.findViewById(R.id.textView2total);
		imp.setText(importeFormato.toString() + "\u20AC");

		filaClonada.setVisibility(View.VISIBLE);
		separadorClonado.setVisibility(View.VISIBLE);

		tl.addView(filaClonada);
		tl.addView(separadorClonado);

	}

	
	public void limpiaPlatos() {

		TableLayout tl = (TableLayout) rootView.findViewById(R.id.tabla);

		while (tl.getChildAt(2) != null) {

			tl.removeViewAt(2);

		}

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
