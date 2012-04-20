package com.vickystevens.code.friston;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;


// main activity class to implement FristonPal
/**
 * The Class MainActivity.
 * Main activity of app.  Starts mainpage.xml layout
 * and handles users choice. Starts relevant technologies
 *
 * @author Vicky Stevens
 * @version 1.0 Build 9000 15th April 2012.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    /** The map btn. */
    private Button mapBtn=null;

    /** The help btn. */
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
        setContentView(R.layout.mainpage);


        // gets buttons and sets up click-handlers for them
        View mapButton = findViewById(R.id.btnChooseMap);
        mapButton.setOnClickListener(this);
        View arButton = findViewById(R.id.btnChooseAR);
        arButton.setOnClickListener(this);
        View qrButton = findViewById(R.id.btnChooseQr);
        qrButton.setOnClickListener(this);
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     *
     *  Handles which option is selected the user
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                 Intent i = new Intent(this, HelpActivity.class);
                 startActivity(i);
                 return true;
            case R.id.about:
                Toast.makeText(this, "FristonPal is an app giving information about the cycling trails in the Friston Forest", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("FristonPal needs GPS.  Enable?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    



    protected  void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 0 && resultCode == 0){
            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if(provider != null){
                Toast.makeText(this, "Please enable GPS to use FristonPal", Toast.LENGTH_LONG);
            }
        }
        if(requestCode == 0 && resultCode == 1){
            if (requestCode == 0) {
                if (resultCode == RESULT_OK) {
                    String contents = intent.getStringExtra("SCAN_RESULT");
                    String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                    // Handle successful scan
                } else if (resultCode == RESULT_CANCELED) {
                    // Handle cancel
                }
            }
        }
    }



    /* (non-Javadoc)
    * @see android.view.View.OnClickListener#onClick(android.view.View)
    */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChooseMap:
                Intent mapIntent = new Intent(this, MapMainView.class);
                startActivity(mapIntent);
                break;
            case R.id.btnChooseQr:
                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.initiateScan();
                break;
                // launches mixare with Intent and reads JSON from web
            case R.id.btnChooseAR:
                String intentToCheck = "com.google.ACTION_VIEW"; //can be any other intent
                final PackageManager packageManager = getPackageManager();
                Intent i = new Intent(intentToCheck);
                List list = packageManager.queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY);
                final boolean isAvailable = list.size() > 0;

                if (!isAvailable){

                    buildAlertMessageNoAR();
                    break;

                }
                else{
                    i = new Intent();
                    i.setAction(Intent.ACTION_VIEW);
                    i.setDataAndType(Uri.parse("http://www.fristonpal.info/mixare.php"), "application/mixare-json");
                    startActivity(i);
                    break;
                }
        }
    }


    private void buildAlertMessageNoAR() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("FristonPal needs the Mixare API.  Install?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=org.mixare&hl=en"));
                        startActivity(marketIntent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
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
    
