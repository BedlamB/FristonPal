package com.vickystevens.code.friston;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by IntelliJ IDEA.
 * User: vicks
 * Date: 03/02/12
 * Time: 21:34
 * To change this template use File | Settings | File Templates.
 */
public class AboutActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        try {
            Button scanner = (Button)findViewById(R.id.launch_ar);
            scanner.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_VIEW);
                    i.setDataAndType(Uri.parse("http://ws.geonames.org/findNearbyWikipediaJSON"), "application/mixare-json");
                    startActivity(i);
                }

            });


        } catch (ActivityNotFoundException anfe) {
            Log.e("onCreate", "Scanner Not Found", anfe);
        }

    }







}