package com.vickystevens.code.friston;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by IntelliJ IDEA.
 * User: vicks
 * Date: 17/04/12
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */
public class Help extends Activity {
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