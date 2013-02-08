package com.example.cartadigital_aux_002;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

//Sentencia para la base de datos MYSQL
//create database prueba;
//use prueba;
//create table persona (id smallint auto_increment, nombre varchar(60), personaclientesnacimiento date, primary key(id)); 

public class MainActivity extends Activity {

    private static final String LOGTAG = "AUX";
    //private static final String url = "jdbc:mysql://10.0.2.2/gestion_db";
    private static final String url = "jdbc:mysql://192.168.1.50:3306/gestion_db";
    
    private static final String user = "root";
    private static final String pass = "gestion";

    public void onCreate(Bundle savedInstanceState) {
    	Log.d(LOGTAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //testDB();
        PruebaMySQL();
    }

    
    public boolean onCreateOptionsMenu(Menu menu) {
    	Log.d(LOGTAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void PruebaMySQL() 
    {
    	TextView tv = (TextView)this.findViewById(R.id.text_view);
    	String resultado_muestra = "";
        // Se mete todo en un try por los posibles errores de MySQL
        try
        {
            // Se registra el Driver de MySQL
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
        	// DriverManager.registerDriver(new org.);
            
            // Se obtiene una conexión con la base de datos. Hay que
            // cambiar el usuario "root" y la clave "la_clave" por las
            // adecuadas a la base de datos que estemos usando.
            Connection conexion = DriverManager.getConnection (
                "jdbc:mysql://10.0.2.2/prueba","root", "gestion");
            
            // Se crea un Statement, para realizar la consulta
            Statement s = conexion.createStatement();
            
            // Se realiza la consulta. Los resultados se guardan en el 
            // ResultSet rs
            ResultSet rs = s.executeQuery ("select * from persona");
            
            // Se recorre el ResultSet, mostrando por pantalla los resultados.
            while (rs.next())
            {
            	Log.d(LOGTAG, rs.getInt ("Id") + " " + rs.getString (2)+ 
                        " " + rs.getDate(3));

            	resultado_muestra += (rs.getInt ("Id") + " " + rs.getString (2)+ 
                        " " + rs.getDate(3));
            	resultado_muestra +="\n";
            }
        	tv.setText(resultado_muestra);
            // Se cierra la conexión con la base de datos.
            conexion.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            tv.setText(e.toString());
        }
    }
    
}
