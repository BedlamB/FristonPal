package com.vickystevens.code.friston;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;


// main activity class to implement FristonPal
/**
 * The Class MainActivity.
 * Main activity of app.  Starts main.xml layout
 * and handles users choice. Starts relevant technologies
 *
 * @author Vicky Stevens
 * @version 1.0 Build 9000 15th April 2012.
 */
public class MainActivity extends Activity implements View.OnClickListener {
	
	/** The map btn. */
	private Button mapBtn=null;
	
	/** The wiki btn. */
	private Button wikiBtn=null;
	
	/** The qr btn. */
	private Button qrBtn=null;
	
	/** The rt btn. */
	private Button rtBtn=null;
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        
        // gets buttons and sets up click-handlers for them
        View mapButton = findViewById(R.id.btnChooseMap);
        mapButton.setOnClickListener(this);
        View arButton = findViewById(R.id.btnChooseWiki);
        arButton.setOnClickListener(this);
        View qrButton = findViewById(R.id.btnChooseQr);
        qrButton.setOnClickListener(this);
        View exButton = findViewById(R.id.exit_button);
        exButton.setOnClickListener(this);

    }
    
    

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
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
            // launches mixare with Intent and reads JSON from web
            case R.id.btnChooseWiki:
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse("http://www.fristonpal.info/mixare.php"), "application/mixare-json");
                startActivity(i);
                break;
            case R.id.exit_button:
                  finish();
         }
    }



    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.xml.mainmenu, menu);
        return true;
    }


	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();

	}

    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
	public void onPause() {
		super.onPause();

    }


}  
    


