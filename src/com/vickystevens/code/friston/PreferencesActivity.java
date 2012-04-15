package com.vickystevens.code.friston;

import android.os.Bundle;
import android.preference.PreferenceActivity;

// TODO: Auto-generated Javadoc
/**
 * The Class PreferencesActivity.
 * 
 * @author Vicky Stevens
 * @version 1.0 Build 9000 15th April 2012.
 */
public class PreferencesActivity extends PreferenceActivity {
    
    /* (non-Javadoc)
     * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}