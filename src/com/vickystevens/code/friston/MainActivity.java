package com.vickystevens.code.friston;

//import com.vickystevens.code.friston.EditPreferences;



import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{
	
	

	private Button btn=null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
		btn=(Button)findViewById(R.id.btnChooseMap);
		btn.setOnClickListener(this);
	}
    
    
    public void onClick(View view){
    	btn.setText(new Date().toString());
    	
    	//start ShowMap Activity
    }
	
	@Override
	public void onResume() {
		super.onResume();

	}
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}

}

//j	getLocationManager()
//	getLocationListener()
//	onChange({
//		updateLocation()
	
