package com.menu.menus;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import fragments.FragmentoComanda;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StackAdapter extends ArrayAdapter<View> {
	 
    private List<View> items;
	private Context ctx;
	
	
	// Constructor del adapter
    public StackAdapter(Context context, int textViewResourceId,
            List<View> items) {
    	super(context, textViewResourceId, items); 	
        this.items = items;
        this.ctx = context;    
        
    }

    // Obtiene la vista de una determinada posicion pasada como parametro 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;	
		
		if(v==null){
			
			v = items.get(position);
	
		}
		
		Log.w("POSITION",position+"");
		
		return v;
		
	}

}