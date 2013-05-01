package fragments;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.menu.menus.PlatosSeleccionados;
import com.menu.menus.PrecioUndsImporte;
import com.menu.menus.R;
import com.menu.menus.ScreenSlidePageFragment;

public class FragmentoComanda extends Fragment {

	// Vista mostrada por el Fragment
	private ViewGroup rootView;
	// Estructura con los platos seleccionados para ser mostrados
	private static PlatosSeleccionados platosSeleccionados;

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Permite acceso a entrada salida en Thread
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Inflate the layout for this fragment
		rootView = (ViewGroup) inflater.inflate(
				R.layout.vista_fragment_comanda, container, false);

		// Muestra los platos seleccionados
		muestraPlatos();

		return rootView;

	}

	// Recorre la estructura de platos seleccionados y los muestra
	public void muestraPlatos() {

		double total = 0;

		if (platosSeleccionados != null) {

			for (String categoria : platosSeleccionados.categorias()) {

				for (String nombrePlato : platosSeleccionados
						.nombrePlatos(categoria)) {

					String precio = platosSeleccionados.precioPlato(categoria,
							nombrePlato).toString();
					String unds = platosSeleccionados.unidadesPlato(categoria,
							nombrePlato).toString();
					String importe = platosSeleccionados.importePlato(
							categoria, nombrePlato).toString();
					total = total + Double.valueOf(importe);
					setFila(nombrePlato, precio, unds, importe, categoria);

				}

			}
			
			// Mostrar los valores del panel Opciones
			setImporteTotal(total);
			setFecha();
			setHora();
			
		}
		
	}


	public void setFila(CharSequence concepto, CharSequence precio,
			CharSequence unidades, CharSequence importe, CharSequence categoria) {

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

		// Obtiene la vista de concepto de la fila clonada y establece su nuevo
		// valor
		TextView concept = (TextView) filaClonada
				.findViewById(R.id.textView2concepto);
		concept.setText(concepto);

		// Obtiene la vista de categoria de la fila clonada y establece su nuevo
		// valor
		TextView cat = (TextView) filaClonada
				.findViewById(R.id.textView2categoria);
		cat.setText(categoria);

		// Crea el formato de dos decimales para aplicarlo al mostrar el precio
		DecimalFormat dosdec = new DecimalFormat();
		dosdec.setMinimumFractionDigits(2);

		// El formato recibido es (##.##) y formatea para mostrar siempre 2
		// decimales y reemplazar el . por , (formatea en formato de la region)
		// añade el simbolo del euro
		String precioFormato = dosdec.format(Double.valueOf(precio.toString()));
		TextView prec = (TextView) filaClonada
				.findViewById(R.id.textView2precio);
		prec.setText(precioFormato + "\u20AC");

		// / El formato recibido es (##.##) y formatea para mostrar siempre 2
		// decimales y reemplazar el . por , (formatea en formato de la region)
		// añade el simbolo del euroro
		String importeFormato = dosdec
				.format(Double.valueOf(importe.toString()));
		TextView imp = (TextView) filaClonada.findViewById(R.id.textView2total);
		imp.setText(importeFormato.toString() + "\u20AC");

		filaClonada.setVisibility(View.VISIBLE);
		separadorClonado.setVisibility(View.VISIBLE);

		tl.addView(filaClonada);
		tl.addView(separadorClonado);

	}

	// Limpia todos los platos de la comanda
	public void limpiaPlatos() {

		TableLayout tl = (TableLayout) rootView.findViewById(R.id.tabla);

		while (tl.getChildAt(2) != null) {

			tl.removeViewAt(2);

		}

	}

	// Muestra la fecha en el panel Opciones
	public void setFecha() {

		TextView tv = (TextView) rootView.findViewById(R.id.fModificacion);
		Date fecha = new Date();
		DateFormat formatoFecha = DateFormat.getDateInstance(DateFormat.LONG);
		String formateado = formatoFecha.format(fecha);
		tv.setText(formateado);

	}

	// Muestra la hora en el panel Opciones
	public void setHora() {

		TextView tv = (TextView) rootView.findViewById(R.id.hora);
		Date hora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss");
		String formateado = formateador.format(hora);
		tv.setText(formateado);

	}

	// Muestra el nº de platos en el panel Opciones
	public void setNPlatos() {

		TextView tv = (TextView) rootView.findViewById(R.id.nPlatos);
		Date fecha = new Date();
		DateFormat formatoFecha = DateFormat.getDateInstance(DateFormat.FULL);
		String formateado = formatoFecha.format(fecha);
		tv.setText(formateado);

	}

	// Getter la vista principal de FragmentoComanda 
	public ViewGroup getRootView() {
		return rootView;
	}

	// Setter la vista principal de FragmentoComanda 
	public void setRootView(ViewGroup rootView) {
		this.rootView = rootView;
	}

	// Setter los platos seleccionados a mostrar 
	public static void setPlatosSeleccionados(
			PlatosSeleccionados platosSeleccionados) {
		FragmentoComanda.platosSeleccionados = platosSeleccionados;
	}
	
	// Getter los platos seleccionados a mostrar 
	public static PlatosSeleccionados getPlatosSeleccionados() {
		return platosSeleccionados;
	}
	
	// Muestra el importe total pasado como parametro en el panel Opciones 
	public void setImporteTotal(Double importe) {
		// TODO Auto-generated method stub

		// Formatea siempre con 2 decimales el importe
		DecimalFormat formato = new DecimalFormat();
		formato.setMinimumFractionDigits(2);

		String importeFormato = formato.format(importe);

		// Obtener vista de la pagina 3 (comanda y ops)
		TextView importeTotal = (TextView) ScreenSlidePageFragment
				.getOptsView().findViewById(R.id.importeTotal);

		// Establece importe
		importeTotal.setText(importeFormato + "\u20AC");

		TextView tv = (TextView) rootView.findViewById(R.id.importe);
		tv.setText(importeFormato + "\u20AC");

	}
	
	// Muestra el importe total de la comanda en el panel opciones
	public void setImporteTotal() {
		
		double total = 0;
		/*
		 * Iteraciones sobre los platos obteniendo los importes 
		 */
		if (platosSeleccionados != null) {

			for (String categoria : platosSeleccionados.categorias()) {

				for (String nombrePlato : platosSeleccionados
						.nombrePlatos(categoria)) {

					String importe = platosSeleccionados.importePlato(
							categoria, nombrePlato).toString();
					total = total + Double.valueOf(importe);
					
				}

			}
		
		}

		setImporteTotal(total);
		
	}


}
