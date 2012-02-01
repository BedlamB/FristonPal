package com.vickystevens.code.friston;

//import com.vickystevens.code.friston.EditPreferences;



import java.util.Date;

import android.app.Activity;
import android.content.Intent;
//import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
//import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


// main activity class to implement FristonPal
public class MainActivity extends Activity {
	
	private Button mapBtn=null;
	private Button wikiBtn=null;
	private Button qrBtn=null;
	private Button rtBtn=null;
	private Button locBtn=null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // set up button listeners
        mapBtn=(Button)findViewById(R.id.btnChooseMap);
        mapBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            	Intent mapIntent = new Intent(MainActivity.this, ShowMap.class);
            	startActivity(mapIntent);
            }
        });

        
        // set up button listeners
        locBtn=(Button)findViewById(R.id.btnGetLoc);
        locBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
 //           	Log.d("usr clicked loc");
            	Intent mapIntent = new Intent(MainActivity.this, GetLocation.class);
            	startActivity(mapIntent);
            }
        });        
    
    	// wiki button listener
        wikiBtn=(Button)findViewById(R.id.btnChooseWiki);
    	wikiBtn.setOnClickListener(new View.OnClickListener() {

    		public void onClick(View v) {
    			Intent wikiIntent = new Intent(MainActivity.this, ArActivity.class);
    			startActivity(wikiIntent);  			
   			
    			//wikiBtn.setText(new Date().toString());
    		}
    	});

    	
    	// qr button listener
        qrBtn=(Button)findViewById(R.id.btnChooseQr);
    	qrBtn.setOnClickListener(new View.OnClickListener() {

    		public void onClick(View v) {
    			Intent qrIntent = new Intent(MainActivity.this, QrActivity.class);
    			startActivity(qrIntent);  			
   			
    			//qrBtn.setText(new Date().toString());
    		}
    	});
    	
    
    	// rt button listener
        rtBtn=(Button)findViewById(R.id.btnChooseRt);
    	rtBtn.setOnClickListener(new View.OnClickListener() {

    		public void onClick(View v) {
    			Intent rtIntent = new Intent(MainActivity.this, RouteActivity.class);
    			startActivity(rtIntent);  			
   			
    			//rtBtn.setText(new Date().toString());
    		}
    	});
    
    } 
		
        /*mapBtn.setOnClickListener(this);
		
         wikiBtn=(Button)findViewById(R.id.btnChooseWiki);
		wikiBtn.setOnClickListener(this);
		
		qrBtn=(Button)findViewById(R.id.btnChooseQr);
		qrBtn.setOnClickListener(this);
		
		rtBtn=(Button)findViewById(R.id.btnChooseRt);
		rtBtn.setOnClickListener(this);
		
	}
    
    
   // public void onClick(View view){
   // 	mapBtn.setText(new Date().toString());
   // 	wikiBtn.setText(new Date().toString());
   // 	qrBtn.setText(new Date().toString());
   // 	rtBtn.setText(new Date().toString());
    	//start relevant activites
   // } **/
	
	@Override
	public void onResume() {
		super.onResume();

	}
	
	public void onPause() {
		super.onPause();

	}

//    protected void onStart();
    
//    protected void onRestart();

 //   protected void onResume();

 //   protected void onPause();

 //   protected void onStop();

 //   protected void onDestroy();

      
}  
    





//j	getLocationManager()
//	getLocationListener()
//	onChange({
//		updateLocation()
	
