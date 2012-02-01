package com.vickystevens.code.friston;

import android.app.Activity;

import android.os.Bundle;

import com.google.android.maps.MapActivity;


public class TestMap extends MapActivity {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapshow);
    }
    
 	@Override
	protected boolean isRouteDisplayed() {
		return(false);
	}   
	

}