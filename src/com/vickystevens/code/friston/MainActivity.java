package com.vickystevens.code.friston;

import com.vickystevens.code.friston.EditPreferences;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final int EDIT_ID = Menu.FIRST+2;
	
	private TextView checkbox=null;
	private TextView ringtone=null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		checkbox=(TextView)findViewById(R.id.checkbox);
		ringtone=(TextView)findViewById(R.id.ringtone);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		SharedPreferences prefs=PreferenceManager
				.getDefaultSharedPreferences(this);
		
		checkbox.setText(new Boolean(prefs
					.getBoolean("checkbox", false))
					.toString());
		ringtone.setText(prefs.getString("ringtone", "<unset>"));
	}
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, EDIT_ID, Menu.NONE, "Edit Prefs")
				.setIcon(R.drawable.misc)
				.setAlphabeticShortcut('e');

		return(super.onCreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case EDIT_ID:
				startActivity(new Intent(this, EditPreferences.class));
				return(true);
		}

		return(super.onOptionsItemSelected(item));
	}
}

//j	getLocationManager()
//	getLocationListener()
//	onChange({
//		updateLocation()
	
