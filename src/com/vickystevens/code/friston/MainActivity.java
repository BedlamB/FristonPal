package com.vickystevens.code.friston;

//import com.vickystevens.code.friston.EditPreferences;



import android.app.Activity;
import android.content.Intent;
//import android.content.Intent;
//import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
//import android.preference.PreferenceManager;
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
        View exButton = findViewById(R.id.exit_button);
        exButton.setOnClickListener(this);
        

    }
    
    

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChooseMap:
                Intent mapIntent = new Intent(this, ShowMap.class);
                startActivity(mapIntent);
                break;
            case R.id.btnChooseQr:
                Intent qrIntent = new Intent(this, QrWebView.class);
                startActivity(qrIntent);
                break;
            case R.id.btnChooseRt:
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=50.773901,0.145079&daddr=50.758887,0.205702&dirflg=w&mra=ltm&t=h&z=13"));
                startActivity(intent);
            case R.id.btnChooseWiki:
                Intent arIntent = new Intent(this, ArActivity.class);
                startActivity(arIntent);
                break;
            case R.id.exit_button:
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse("http://www.fristonpal.info/mixare.php"), "application/mixare-json");
                startActivity(i);
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
	
