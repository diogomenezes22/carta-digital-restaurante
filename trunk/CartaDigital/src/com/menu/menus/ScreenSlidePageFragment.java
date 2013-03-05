/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.menu.menus;

import java.util.HashMap;

import fragments.FragmentoComandas;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.net.NetworkInfo.DetailedState;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy
 * title indicating the page number, along with some dummy text.
 * 
 * <p>
 * This class is used by the {@link CardFlipActivity} and
 * {@link ScreenSlideActivity} samples.
 * </p>
 */
public class ScreenSlidePageFragment extends Fragment {
	/**
	 * The argument key for the page number this fragment represents.
	 */
	public static final String ARG_PAGE = "page";

	/**
	 * The fragment's page number, which is set to the argument value for
	 * {@link #ARG_PAGE}.
	 */
	private int mPageNumber;

	private HashMap<String, String> curGroupMap1;

	private ViewGroup rootView;

	
//	private static boolean anteriorVertical=true;
	
	public HashMap<String, String> getCurGroupMap1() {
		return curGroupMap1;
	}

	public void setCurGroupMap1(HashMap<String, String> curGroupMap1) {
		this.curGroupMap1 = curGroupMap1;
	}

	/**
	 * Factory method for this fragment class. Constructs a new fragment for the
	 * given page number.
	 */
	public static ScreenSlidePageFragment create(int pageNumber) {
		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}


	public ScreenSlidePageFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	//	if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
	
		
			if (mPageNumber == 0) {

				rootView = (ViewGroup) inflater.inflate(R.layout.vista_bebidas_vinos,
						container, false);		
				
			}

			if (mPageNumber == 1) {
				
				rootView = (ViewGroup) inflater.inflate(R.layout.vista_entrantes_com_tradicional,
						container, false);
				
			}

			if (mPageNumber == 2) {
				
				rootView = (ViewGroup) inflater.inflate(R.layout.vista_pescados_carnes,
						container, false);
			
			}

			if (mPageNumber == 3) {
											
				rootView = (ViewGroup) inflater.inflate(R.layout.vista_comanda_ops,
						container, false);	
				
			}
			
		//	anteriorVertical=false;
			
	/*	}else{
			
			
			if (mPageNumber == 0) {

				rootView = (ViewGroup) inflater.inflate(R.layout.vista_vertical_bebidas,
						container, false);	
				
			}

			if (mPageNumber == 1) {
				
				rootView = (ViewGroup) inflater.inflate(R.layout.vista_vertical__vinos,
						container, false);
			
			}

			if (mPageNumber == 2) {
				
				rootView = (ViewGroup) inflater.inflate(R.layout.vista_vertical_entrantes,
						container, false);
				
			}

			if (mPageNumber == 3) {
				
				
				rootView = (ViewGroup) inflater.inflate(R.layout.vista_vertical_com_tradicional,
						container, false);
						
			}
			
			if (mPageNumber == 4) {
				
				
				rootView = (ViewGroup) inflater.inflate(R.layout.vista_vertical_pescados,
						container, false);
						
			}
			
			if (mPageNumber == 5) {
			
		
				rootView = (ViewGroup) inflater.inflate(R.layout.vista_vertical_carnes,
						container, false);
						
			}
			
			if (mPageNumber == 6) {
				
				
				rootView = (ViewGroup) inflater.inflate(R.layout.vista_vertical_comanda,
						container, false);
						
			}
			
			anteriorVertical=true;
			
		}*/
				
		return rootView;
	}

	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	/**
	 * Returns the page number represented by this fragment object.
	 */
	public int getPageNumber() {
		return mPageNumber;
	}

}
