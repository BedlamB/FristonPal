package com.vickystevens.code.friston;



import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class JSONHandler.
 * 
 * Takes a filename, reads JSON file into
 * a string then parses JSON into
 * a JSON array
 * 
 * @author Vicky Stevens
 * @version 1.0 Build 9000 15th April 2012.
 */
public class JSONHandler{
	
    /** The data. */
    private List<MyGeoPoint> data;
    
    /** The filename. */
    private String filename;
    

    
    /** The context. */
    private Context context;

    /**
     * Instantiates a new jSON handler.
     *
     * @param context the context
     * @param filename the filename
     * @throws JSONException the jSON exception
     */
    public JSONHandler(Context context, String filename) throws JSONException {

        this.context = context;
        this.filename = filename;
            parseJSON();
     }

    /**
     * Parses the JSON into a JSONARRAY
     *
     * @return the JSON array
     * @throws JSONException the JSON exception
     */
    public JSONArray parseJSON() throws JSONException {
        /** The JSON text. */
        String JSONText;
        JSONText = readFile();
        try {
            JSONObject jObject = new JSONObject(JSONText);
            JSONArray resultsArray = jObject.getJSONArray("results");
            return resultsArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Read file from Assets.
     *
     */
    private String readFile() {
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
            return new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }



	

}