package com.vickystevens.code.friston;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by IntelliJ IDEA.
 * User: vicks
 * Date: 03/02/12
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
public class PreferencesActivity extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}