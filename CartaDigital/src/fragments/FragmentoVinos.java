package fragments;

import java.util.List;
import java.util.Map;

import com.menu.menus.ListaActivity;
import com.menu.menus.R;
import com.menu.menus.R.layout;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentoVinos extends Fragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.vista_vinos,
				container, false);
		ListaActivity la = new ListaActivity();
			
		// Titulo
		la.insertarTitulo("Vinos", rootView);
				
		// Lista
		List<Map<String, String>> lista=la.lista("Vinos");
		la.insertaLista("Vinos", lista, rootView);
		
        return rootView;
    }
	
	
	
}
