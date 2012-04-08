package com.vickystevens.code.friston;



import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class JSONHandler{
	
    private List<MyGeoPoint> data;
    private String filename;
    private String JSONText;
    private Context context;

    public JSONHandler(Context context, String filename) throws JSONException {
        this.context = context;
        this.filename = filename;
            parseJSON();
     }

    public JSONArray parseJSON() throws JSONException {
        readFile();
        try {
            JSONObject jObject = new JSONObject(JSONText);
            JSONArray resultsArray = jObject.getJSONArray("results");
            return resultsArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    private void readFile() {
        AssetManager am = context.getAssets();
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
            e.printStackTrace();
        }

    }



	

}