package com.example.cartadigital_aux_002;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

//import org.postgresql.Driver;
//CONEXION POR POSTGREST !!! 
//La base de datos es igual que ela de CartaDigital

public class MainActivity extends Activity {

    private static final String LOGTAG = "AUX";


    public void onCreate(Bundle savedInstanceState) {
    	Log.d(LOGTAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //****
        consultar();
        
        Log.d(LOGTAG, "------------------4");
        //****
    }

    
    public boolean onCreateOptionsMenu(Menu menu) {
    	Log.d(LOGTAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void consultar() {
    	
    	TextView tv = (TextView)this.findViewById(R.id.text_view);
    	String resultado_muestra = "";
    	
        String cc = "jdbc:postgresql://10.0.2.2/Menu?" +
            "user=postgres&password=hola";
       try 
       {
           Class.forName("org.postgresql.Driver");
           Connection conexion = DriverManager.getConnection(cc);
           Statement comando = conexion.createStatement();
           
           String sql = "SELECT nombre, precio FROM plato;";
           
           ResultSet resultado = comando.executeQuery(sql);
           
           while(resultado.next()) 
           {
               String n = resultado.getString("nombre");
               double a = resultado.getDouble("precio");
               
               resultado_muestra +=n + " " + a +"\n";
           }
           
           resultado.close();
           comando.close();
           conexion.close();
       } 
       catch(Exception e) 
       {
    	   resultado_muestra +="Estamos en un error !";
    	   resultado_muestra +=e.getMessage();
           
      }
       
       tv.setText(resultado_muestra);
       
   }

    
    

    
    
}
