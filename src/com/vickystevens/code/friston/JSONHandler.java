package com.vickystevens.code.friston;


import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class JSONHandler extends Activity{
	
    private List<MyGeoPoint> data;
    private String filename;
    private String JSONText;

    public void JSONHandler(String filename){
        this.filename = filename;
        try {
            parseJSON();
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public JSONArray parseJSON() throws JSONException {
        readFile();
        try {
            JSONObject jObject = new JSONObject(JSONText);
            JSONArray resultsArray = jObject.getJSONArray("results");
            return resultsArray;
        } catch (JSONException e) {
            Toast.makeText(this, "JSON exception", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "wtf?", Toast.LENGTH_SHORT).show();
        return null;

    }

    private void readFile() {
        //   FileInputStream fis = null;
        Scanner scanner = null;
        AssetManager am = this.getAssets();
        InputStream fis;

        // To load text file
        InputStream input;
        try {
            input = am.open(filename);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            JSONText = new String(buffer);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            Toast.makeText(this, "IOException", Toast.LENGTH_SHORT).show();
        }
        //    line1.setText(theText);

    }



	

}