package fragments;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.menu.menus.ListaActivity;
import com.menu.menus.R;

public class FragmentoEntrantes extends Fragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.vista_entrantes,
				container, false);
		ListaActivity la = new ListaActivity();
		
		// Titulo
		la.insertarTitulo("Entrantes", rootView);
				
		// Lista
		List<Map<String, String>> lista=la.lista("Entrantes");
		la.insertaLista("Entrantes", lista, rootView);
		
        return rootView;
    }
	
	
	
}