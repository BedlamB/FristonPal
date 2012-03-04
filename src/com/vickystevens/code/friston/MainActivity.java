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
public class MainActivity extends Activity implements View.OnClickListener {
	
	private Button mapBtn=null;
	private Button wikiBtn=null;
	private Button qrBtn=null;
	private Button rtBtn=null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // click-handlers for buttons
        View mapButton = findViewById(R.id.btnChooseMap);
        mapButton.setOnClickListener(this);
        View arButton = findViewById(R.id.btnChooseWiki);
        arButton.setOnClickListener(this);
        View qrButton = findViewById(R.id.btnChooseQr);
        qrButton.setOnClickListener(this);
        View rtButton = findViewById(R.id.btnChooseRt);
        rtButton.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChooseMap:
                Intent mapIntent = new Intent(this, ShowMap.class);
                startActivity(mapIntent);
                break;
            case R.id.btnChooseQr:
                Intent qrIntent = new Intent(this, QrActivity.class);
                startActivity(qrIntent);
                break;
            case R.id.btnChooseRt:
                Intent rtIntent = new Intent(this, RouteActivity.class);
                startActivity(rtIntent);
                break;
        }
    }



	
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
	
