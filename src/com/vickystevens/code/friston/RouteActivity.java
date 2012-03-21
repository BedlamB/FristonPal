package com.vickystevens.code.friston;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

public class RouteActivity extends Activity {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routeshow);
        if(!isNetworkReachable()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Network Connection");
            builder.setMessage("The Network is not available");
            builder.setPositiveButton("OK", null);
            builder.create().show();
        }
    }


    public boolean isNetworkReachable(){
        ConnectivityManager manager = (ConnectivityManager)this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo current = manager.getActiveNetworkInfo();
        if(current==null){
            return false;
        }
        return (current.getState() == NetworkInfo.State.CONNECTED);
    }

	

}