package com.vickystevens.code.friston;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 *
 * User: vicky stevens
 * Date: 17/03/12
 *
 * Simple Activity to show help page
 *
 */
public class HelpActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        View mapButton = findViewById(R.id.help_ok);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}