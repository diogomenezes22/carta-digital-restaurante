package com.menu.menus;


import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class ColumnasAdapter extends PagerAdapter {

    private static int NUM_COLUMNAS = 2;
    private Context cxt;
	
    
	public ColumnasAdapter(Context baseContext) {
		// TODO Auto-generated constructor stub
		cxt=baseContext;
		
	}

	@Override
	public int getCount() {
		return NUM_COLUMNAS;
	}

	@Override
	public Object instantiateItem(View collection, int position) {
	
		LinearLayout v = (LinearLayout) LayoutInflater.from(cxt).inflate(
				R.layout.activity_lista, null);

		if (position == 0) {
			v = (LinearLayout) LayoutInflater.from(cxt).inflate(
					R.layout.activity_lista, null);
		} else if (position == 1) {
			v = (LinearLayout) LayoutInflater.from(cxt).inflate(
					R.layout.activity_service, null);
		} //else {
		//	v = (LinearLayout) LayoutInflater.from(cxt).inflate(
		//			R.layout.columna3, null);
		//}

		((ViewPager) collection).addView(v, 0);

		return v;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((LinearLayout) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((LinearLayout) object);
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

}
