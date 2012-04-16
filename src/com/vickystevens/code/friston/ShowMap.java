package com.vickystevens.code.friston;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import com.google.android.maps.*;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;


// TODO: Auto-generated Javadoc
/**
 * The Class ShowMap.
 * main controller of app.  Handles displaying the map, and
 * the pois.
 * 
 * @author Vicky Stevens
 * @version 1.0 Build 9000 15th April 2012.
 */
public class ShowMap extends MapActivity
{
    
    /** The map. */
    private MapView map;
    
    /** The start. */
    long start;
    
    /** The stop. */
    long stop;
    
    /** The me. */
    private MyCustomLocationOverlay me;
  //  private MyLocationOverlay me2;
    /** The route. */
  private RouteSegmentOverlay route;
    
    /** The user. */
    private Overlay uphill, downhill, jumps, pirate, carparks, purple, pubs, user;
    
    /** The map controller. */
    private MapController mapController;
    

    /** The user overlays. */
    private ArrayList<MyGeoPoint> userOverlays = new ArrayList<MyGeoPoint>();
    
    /** The edit. */
    private boolean edit = false;



    /** The route button. */
    private Button uphillButton, downhillButton, jumpsButton, pirateButton, carparksButton, purpleButton, pubsButton;
    
    /** The PIRAT e_ shown. */
    private boolean PUBS_SHOWN, CARPARKS_SHOWN, PURPLE_SHOWN, UPHILL_SHOWN, DOWNHILL_SHOWN, JUMPS_SHOWN, PIRATE_SHOWN, USER_SHOWN;

    
    /** The route points. */
    private ArrayList<GeoPoint> routePoints;
//    private int[] routeGrade;
    
    /**
 * The Enum OverlayType.
     * Enum for keeping track of which overlays are shown
 */
public enum OverlayType
    {
        
		USER,
        UPHILL,        
        DOWNHILL,
        JUMPS,
        PIRATE,
        CARPARKS,
        PURPLE,
        PUBS
    }

    

    /* (non-Javadoc)
     * @see com.google.android.maps.MapActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmap);
        map=(MapView)findViewById(R.id.map);             // getting map
        mapController = map.getController();              // getting controller for map and
		map.getController().setZoom(17);                 // setting zoom and satellite options
		map.setSatellite(true);

        loadRouteData();             // loads main trail
        overlayRoute();              // draws the route in an overlay

        TouchOverlay t = new TouchOverlay();                        // creates Touch overlay which resonds to touch events
        List<Overlay> overlayList = map.getOverlays();
        overlayList.add(t);                                           // add it to overlay list
		
		// set up the drawables
        Drawable uphillMarker=getResources().getDrawable(R.drawable.uphill);
        Drawable downhillMarker=getResources().getDrawable(R.drawable.downhill);
        Drawable jumpsMarker=getResources().getDrawable(R.drawable.jumps);
        Drawable pirateMarker=getResources().getDrawable(R.drawable.pirate);
        Drawable carparkMarker=getResources().getDrawable(R.drawable.carpark);
        Drawable purpleMarker=getResources().getDrawable(R.drawable.purple);
		Drawable pubsMarker=getResources().getDrawable(R.drawable.pubs);
        Drawable userMarker=getResources().getDrawable(R.drawable.pin_blue);


        purpleMarker.setBounds(0, 0, purpleMarker.getIntrinsicWidth(),
                purpleMarker.getIntrinsicHeight());
		carparkMarker.setBounds(0, 0, carparkMarker.getIntrinsicWidth(),
				carparkMarker.getIntrinsicHeight());
        pubsMarker.setBounds(0, 0, pubsMarker.getIntrinsicWidth(),
                pubsMarker.getIntrinsicHeight());
        

        //populate purple overlaylist
        ArrayList<MyGeoPoint> purpleOverlays = new ArrayList<MyGeoPoint>();
        JSONArray purpleArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "purple", false);             // starts JSONHandler to get pois from file
                    purpleArray = handler.parseJSON();

        } catch (JSONException e) {
            Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
        }
        // reads creates geopoints based on data from json
        for (int i = 0; i < purpleArray.length(); i++) {
            try {

                Double lat = purpleArray.getJSONObject(i).getDouble("lat");
                Double lon = purpleArray.getJSONObject(i).getDouble("lng");
                String title = purpleArray.getJSONObject(i).getString("title");
                String snippet =purpleArray.getJSONObject(i).getString("snippet");
                purpleOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
            }
        }




        //populate pubs
        ArrayList<MyGeoPoint> pubOverlays = new ArrayList<MyGeoPoint>();
        JSONArray pubsArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "pubs", false);
            pubsArray = handler.parseJSON();

        } catch (JSONException e) {
            Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
        }

        for (int i = 0; i < pubsArray.length(); i++) {
            try {

                Double lat = pubsArray.getJSONObject(i).getDouble("lat");
                Double lon = pubsArray.getJSONObject(i).getDouble("lng");
                String title = pubsArray.getJSONObject(i).getString("title");
                String snippet = pubsArray.getJSONObject(i).getString("snippet");
                pubOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
            }
        }

        //populate carparks
        ArrayList<MyGeoPoint> carparkOverlays = new ArrayList<MyGeoPoint>();
        JSONArray carparkArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "carparks", false);
            carparkArray = handler.parseJSON();

        } catch (JSONException e) {
            Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
        }

        for (int i = 0; i < pubsArray.length(); i++) {
            try {

                Double lat = carparkArray.getJSONObject(i).getDouble("lat");
                Double lon = carparkArray.getJSONObject(i).getDouble("lng");
                String title = carparkArray.getJSONObject(i).getString("title");
                String snippet = carparkArray.getJSONObject(i).getString("snippet");
                carparkOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
            }
        }

        //populate uphills
        ArrayList<MyGeoPoint> uphillOverlays = new ArrayList<MyGeoPoint>();
        JSONArray uphillArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "uphill", false);
            uphillArray = handler.parseJSON();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < uphillArray.length(); i++) {
            try {

                Double lat = uphillArray.getJSONObject(i).getDouble("lat");
                Double lon = uphillArray.getJSONObject(i).getDouble("lng");
                String title = uphillArray.getJSONObject(i).getString("title");
                String snippet = uphillArray.getJSONObject(i).getString("snippet");
                uphillOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
            }
        }

        //populate downhills
        ArrayList<MyGeoPoint> downhillOverlays = new ArrayList<MyGeoPoint>();
        JSONArray downhillArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "downhill", false);
            downhillArray = handler.parseJSON();

        } catch (JSONException e) {
            Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
        }

        for (int i = 0; i < downhillArray.length(); i++) {
            try {

                Double lat = downhillArray.getJSONObject(i).getDouble("lat");
                Double lon = downhillArray.getJSONObject(i).getDouble("lng");
                String title = downhillArray.getJSONObject(i).getString("title");
                String snippet = downhillArray.getJSONObject(i).getString("snippet");
                downhillOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
            }
        }

        //populate jumps
        ArrayList<MyGeoPoint> jumpsOverlays = new ArrayList<MyGeoPoint>();
        JSONArray jumpsArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "jumps", false);
            jumpsArray = handler.parseJSON();

        } catch (JSONException e) {
            Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
        }

        for (int i = 0; i < jumpsArray.length(); i++) {
            try {

                Double lat = jumpsArray.getJSONObject(i).getDouble("lat");
                Double lon = jumpsArray.getJSONObject(i).getDouble("lng");
                String title = jumpsArray.getJSONObject(i).getString("title");
                String snippet = jumpsArray.getJSONObject(i).getString("snippet");
                jumpsOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
            }
        }

        //populate pirate
        ArrayList<MyGeoPoint> pirateOverlays = new ArrayList<MyGeoPoint>();
        JSONArray pirateArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "pirate", false);
            pirateArray = handler.parseJSON();

        } catch (JSONException e) {
            Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
        }

        for (int i = 0; i < pirateArray.length(); i++) {
            try {

                Double lat = pirateArray.getJSONObject(i).getDouble("lat");
                Double lon = pirateArray.getJSONObject(i).getDouble("lng");
                String title = pirateArray.getJSONObject(i).getString("title");
                String snippet = pirateArray.getJSONObject(i).getString("snippet");
                pirateOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
            }
        }


         // sets up an itemized overlay for each set of pois
        purple  = (new MyItemizedOverlay(purpleMarker, purpleOverlays));
        carparks = (new MyItemizedOverlay(carparkMarker, carparkOverlays));        
        pubs = (new MyItemizedOverlay(pubsMarker, pubOverlays));
        uphill =(new MyItemizedOverlay(uphillMarker, uphillOverlays));
        downhill = (new MyItemizedOverlay(downhillMarker, downhillOverlays));
        jumps = (new MyItemizedOverlay(jumpsMarker, jumpsOverlays));
        pirate = (new MyItemizedOverlay(pirateMarker, pirateOverlays));
        user = (new MyItemizedOverlay(userMarker, userOverlays));
        


        
        //add overlays to the map overlay list
        overlayList.add(purple);
        overlayList.add(carparks);
        overlayList.add(pubs);
        overlayList.add(uphill);
        overlayList.add(downhill);
        overlayList.add(jumps);
        overlayList.add(pirate);
        overlayList.add(user);



   //    keeping track of overlay displayed or not
        PURPLE_SHOWN = true;
        CARPARKS_SHOWN = true;
        PUBS_SHOWN = true;
        UPHILL_SHOWN = true;
        DOWNHILL_SHOWN = true;
        JUMPS_SHOWN = true;
        PIRATE_SHOWN = true;
        USER_SHOWN = true;



        
        
        // Button to add overlays
        pubsButton = (Button)findViewById(R.id.pubsOverlay);
        pubsButton.setOnClickListener(new OnClickListener(){      
            public void onClick(View v) {
                try {
                    writeJson();
                } catch (IOException e) {
                    Toast.makeText(v.getContext(), "User file non-existant", Toast.LENGTH_SHORT);
                }
                //toggleOverlay(map, pubs, OverlayType.PUBS);
            }
        });
        
        // Button to add overlays
        carparksButton = (Button)findViewById(R.id.carparksOverlay);
        carparksButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {


                try {
                    readJson();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }


                //toggleOverlay(map, carparks, OverlayType.CARPARKS);           //calls toggle overlay to switch between visible or not
            }
        });
        
        // Button to add overlays
        purpleButton = (Button)findViewById(R.id.purpleOverlay);
        purpleButton.setOnClickListener(new OnClickListener(){      
            public void onClick(View v) {	
            	toggleOverlay(map, user, OverlayType.USER);
            }
        });

        // Button to add overlays
        uphillButton = (Button)findViewById(R.id.uphillOverlay);
        uphillButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                toggleOverlay(map, uphill, OverlayType.UPHILL);
            }
        });

        // Button to add overlays
        downhillButton = (Button)findViewById(R.id.downhillOverlay);
        downhillButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                toggleOverlay(map, downhill, OverlayType.DOWNHILL);
            }
        });

        // Button to add overlays
        jumpsButton = (Button)findViewById(R.id.jumpsOverlay);
        jumpsButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                toggleOverlay(map, jumps, OverlayType.JUMPS);
            }
        });

        // Button to add overlays
        pirateButton = (Button)findViewById(R.id.pirateOverlay);
        pirateButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                toggleOverlay(map, pirate, OverlayType.PIRATE);
            }
        });


       
		me=new MyCustomLocationOverlay(this, map);    //creates a new custom MyLocationOverlay
        map.getOverlays().add(me);                     // and adds it to the map
        me.enableMyLocation();

//        // sets middle of map to be mylocationoverlay
//         me.runOnFirstFix(new Runnable() {
//            @Override
//            public void run() {
//                mapController.animateTo(me.getMyLocation());
//            }
//        });
        
        mapController.animateTo(getPoint(50.776155,0.152914));    // centres map on start of route. (change to user location when finished)
     	map.postInvalidate();       // call postInvalidate to redraw mapview with changes added
        
    }
    
    /**
     * Write the JSON file of user pois
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void writeJson() throws IOException {
        
        try{
            Toast.makeText(this, userOverlays.get(0).getTitle(),Toast.LENGTH_LONG).show();
        }catch (ArrayIndexOutOfBoundsException e){
            Toast.makeText(this, "Out of bounds", Toast.LENGTH_LONG);
        }
        JSONObject object = new JSONObject();
        for (int i = 0; i < userOverlays.size(); i++){

            try{
                object.put("title", userOverlays.get(i).getTitle());
                object.put("snippet", userOverlays.get(i).getSnippet());
                object.put("lat", userOverlays.get(i).getLat());
                object.put("lon", userOverlays.get(i).getLon());

            } catch(JSONException e){
                Toast.makeText(this, "crashed on object put", Toast.LENGTH_LONG).show();
            }    
        }
        

       FileOutputStream fos = null;
        try {
            fos = openFileOutput("test", Context.MODE_APPEND);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "file not found", Toast.LENGTH_LONG).show();
        }
        //     fos.write(userOverlays.get(0).getTitle().getBytes());
        try {
            fos.write(object.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Toast.makeText(this, "file written", Toast.LENGTH_LONG).show();
            fos.close();
    }


    
    public void readJson() throws IOException, JSONException {
        String JSONString = null;
        try {
            FileInputStream input = openFileInput("test");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            JSONString = new String(buffer);
            Toast.makeText(this, JSONString, Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e){
            Toast.makeText(this, "boo", Toast.LENGTH_LONG).show();
        }
         JSONArray userArray = new JSONArray(JSONString);
         Toast.makeText(this, " before parsing" + userArray.toString(), Toast.LENGTH_LONG);

        for (int i = 0; i < userArray.length(); i++) {
            try {

                Double lat = userArray.getJSONObject(i).getDouble("lat");
                Double lon = userArray.getJSONObject(i).getDouble("lng");
                String title = userArray.getJSONObject(i).getString("title");
                String snippet = userArray.getJSONObject(i).getString("snippet");
                userOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                Toast.makeText(this, "JSON Exception", Toast.LENGTH_SHORT);
            }
        }
        List<Overlay> overlayList = map.getOverlays();
        overlayList.add(user);
        map.postInvalidate();
    }


/* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {               // creates options menu based on
        MenuInflater inflater = getMenuInflater();                // mapmenu.xml
        inflater.inflate(R.xml.mapmenu, menu);
        return true;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     *
     *  Handles which option is selected my user
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addpin:
                addPin();                        // calls method allowing user to add a pin
                return true;
            case R.id.armenu:
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);          // starts a mixare view
                i.setDataAndType(Uri.parse("http://www.fristonpal.info/mixare.php"), "application/mixare-json");
                startActivity(i);
                return true;
            case R.id.togglemenu:
                toggle();                              // opens up a toggle menu allowing user to toggle the pois
                return true;
            case R.id.scanQRmenu:
                Intent qrIntent = new Intent(this, QrWebView.class);
                startActivity(qrIntent);
                return true;                                   // starts a ZXing QR reader activity
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * Adds the pin.
     * sets a flag which enables the touch overlay
     */
    public void addPin()
    {
        edit = true;
        Toast.makeText(this, "Long press to add", Toast.LENGTH_LONG).show();
    }

    /**
     * Shows the toggle options for the pois.
     */
    public void toggle()
    {
        Toast.makeText(this, "toggle pushed", Toast.LENGTH_SHORT).show();
    }


    /**
     * Toggles the overlays to be visible or not
     *
     * @param map the map
     * @param o the the Overlay
     * @param type the type
     */
    private void toggleOverlay(MapView map, Overlay o, OverlayType type) {

		switch (type) {
		case PUBS:
			if (PUBS_SHOWN) {
				map.getOverlays().remove(o);                   // removes overlay
				PUBS_SHOWN = !PUBS_SHOWN;                       // sets flag
				map.postInvalidate();                           // invalidate map to force redraw
				break;
			}

			else {
				map.getOverlays().add(o);
				PUBS_SHOWN = !PUBS_SHOWN;
				map.postInvalidate();
				break;
			}

		case CARPARKS:
			if (CARPARKS_SHOWN) {
				map.getOverlays().remove(o);
				CARPARKS_SHOWN= !CARPARKS_SHOWN;
				map.postInvalidate();
				break;
			} else {
				map.getOverlays().add(o);
				CARPARKS_SHOWN = !CARPARKS_SHOWN;
				map.postInvalidate();
				break;
			}
		case USER:
			if (USER_SHOWN) {
				map.getOverlays().remove(o);
				USER_SHOWN = !USER_SHOWN;
				map.postInvalidate();
				break;
			} else {
				map.getOverlays().add(o);
				USER_SHOWN = !USER_SHOWN;
				map.postInvalidate();
				break;
			}
		case UPHILL:
            if (UPHILL_SHOWN) {
                map.getOverlays().remove(o);
                UPHILL_SHOWN = !UPHILL_SHOWN;
                map.postInvalidate();
                break;
            } else {
                map.getOverlays().add(o);
                UPHILL_SHOWN = !UPHILL_SHOWN;
                map.postInvalidate();
                break;
            }
            case DOWNHILL:
                if (DOWNHILL_SHOWN) {
                    map.getOverlays().remove(o);
                    DOWNHILL_SHOWN = !DOWNHILL_SHOWN;
                    map.postInvalidate();
                    break;
                } else {
                    map.getOverlays().add(o);
                    DOWNHILL_SHOWN = !DOWNHILL_SHOWN;
                    map.postInvalidate();
                    break;
                }

            case JUMPS:
                if (JUMPS_SHOWN) {
                    map.getOverlays().remove(o);
                    JUMPS_SHOWN = !JUMPS_SHOWN;
                    map.postInvalidate();
                    break;
                } else {
                    map.getOverlays().add(o);
                    JUMPS_SHOWN = !JUMPS_SHOWN;
                    map.postInvalidate();
                    break;
                }
            case PIRATE:
                if (PIRATE_SHOWN) {
                    map.getOverlays().remove(o);
                    PIRATE_SHOWN = !PIRATE_SHOWN;
                    map.postInvalidate();
                    break;
                } else {
                    map.getOverlays().add(o);
                    PIRATE_SHOWN = !PIRATE_SHOWN;
                    map.postInvalidate();
                    break;
                }
        }
	}



    // Overlay a route.  This method is only executed after loadRouteData() completes
    // on background thread.

    /**
     * Overlay route.
     */
    public void overlayRoute() {
        //  if(route != null) return;  // To prevent multiple route instances if key toggled rapidly
        // Set up the overlay controller
        route = new RouteSegmentOverlay(routePoints); // My class defining route overlay  THIS IS THE KEY!!  Make two arrays here and pass them
        map.getOverlays().add(route);
        // Added symbols will be displayed when map is redrawn so force redraw now

    }

    /**
     * Gets the point.
     *
     * @param lat the lat
     * @param lon the lon
     * @return the point
     */
    private GeoPoint getPoint(double lat, double lon) {
        return(new GeoPoint((int)(lat*1e6), (int)(lon*1e6)));
    }

 

    /**
     * Loads route data via an instance of KMLHandler
     */
    public void loadRouteData(){
        routePoints = new ArrayList<GeoPoint>();
        KMLHandler handler = new KMLHandler(this);
        try {
            routePoints = handler.getParsedItems();
        } catch (IOException e) {
            Toast.makeText(this, "KML file does not exist", Toast.LENGTH_SHORT);
        } catch (XmlPullParserException e) {
            Toast.makeText(this, "Invalid XML", Toast.LENGTH_SHORT);
        }

    }
   
   
    /* (non-Javadoc)
     * @see com.google.android.maps.MapActivity#isRouteDisplayed()
     */
    public boolean isRouteDisplayed(){
    	return false;
    }



    /* (non-Javadoc)
     * @see com.google.android.maps.MapActivity#onPause()
     *
     * disables MyLocation on pause of application
     *
     */
    public void onPause(){
    	super.onPause();
    	me.disableMyLocation();
    }
    
    /* (non-Javadoc)
     * @see com.google.android.maps.MapActivity#onResume()
     *
     * re-enables mylocation on resume
     *
     */
    public void onResume(){
    	super.onResume();
    	me.enableCompass();
		me.enableMyLocation();
    }


    /**
     * The Class TouchOverlay.
     *
     * handles event when map touched.
     * counts time and responds on a long touch
     * gets location of touch and creates geopoint at that location
     * add geopoint to useroverlay and adds pin to map
     *
     */
    class TouchOverlay extends Overlay {
    	
	    /* (non-Javadoc)
	     * @see com.google.android.maps.Overlay#onTouchEvent(android.view.MotionEvent, com.google.android.maps.MapView)
	     */
	    public boolean onTouchEvent(MotionEvent event, MapView map){
    		if (event.getAction() == MotionEvent.ACTION_DOWN){
    			start = event.getEventTime();
    		}
    		if (event.getAction() == MotionEvent.ACTION_UP){
    			stop = event.getEventTime();
    		}
    		if (stop - start > 1500 && edit){
                GeoPoint p = map.getProjection().fromPixels(
                        (int) event.getX(),
                        (int) event.getY());
                Toast.makeText(getBaseContext(),
                        p.getLatitudeE6() / 1E6 + "," +
                                p.getLongitudeE6() /1E6 ,
                        Toast.LENGTH_SHORT).show();
                Log.d("tap event ", "tap called");

                final MapController mc = map.getController();
                mc.setZoom(16);
                Drawable drawable  =getResources().getDrawable(R.drawable.pin_yellow);
                userOverlays.add(new MyGeoPoint(p.getLatitudeE6() / 1E6, p.getLongitudeE6() / 1E6, "test", "test"));
                user  = (new MyItemizedOverlay(drawable, userOverlays));
              //  overlayList.add(purple);
                map.getOverlays().add(user);
                map.postInvalidate();
                edit = false;
                return true;

    		}
    		return false;
    	}

    }




// extends Itemiized overlay
	/**
 * The Class MyItemizedOverlay.
     * extends Itemized overlay, takes an Arraylist of Geopoints
     * and adds them to the maps overlay list
     * then populates list
     *
 */
private class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {
		
		/** The items. */
		private List<OverlayItem> items=new ArrayList<OverlayItem>();
        
        /** The marker. */
        private Drawable marker=null;


    /**
	 * Instantiates a new my itemized overlay.
	 *
	 * @param marker the marker
	 * @param points the points
	 */
	public MyItemizedOverlay(Drawable marker, ArrayList<MyGeoPoint> points) {
            super(boundCenterBottom(marker));
			this.marker=marker;
            for(MyGeoPoint point: points){
                this.items.add(new OverlayItem(getPoint(point.getLat(), point.getLon()), point.getTitle(), point.getSnippet()));   // iterates over list
               }                                                                                                                   // adding Overlay items to overlay list
            populate();

        }




    /* (non-Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#createItem(int)
     */
    @Override
		protected OverlayItem createItem(int i) {
			return(items.get(i));
		}







        /* (non-Javadoc)
         * @see com.google.android.maps.ItemizedOverlay#onTap(int)
         *
         * handles tap events on the OverlayItems (pois)
         * if flag not set, gets location of poi and creates builder dialog
         * allowing the user to map a route, get more details or cancel
         *
         */
        protected boolean onTap(int i) {
            if(!edit){                      // if in edit mode, we don't want to do this.

            OverlayItem itemClicked = items.get(i);                           //gets item clicked
            GeoPoint  point = itemClicked.getPoint();
            final Double lat = point.getLatitudeE6()/1e6;
            final Double lon = point.getLongitudeE6()/1e6;                     // gets location co-ords
            AlertDialog.Builder dialog = new AlertDialog.Builder(ShowMap.this);

            dialog.setTitle(itemClicked.getTitle());
            dialog.setMessage(itemClicked.getSnippet());
            dialog.setCancelable(true);
            dialog.setPositiveButton("Navigate Here", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    StringBuilder sb = new StringBuilder();                                             // build string to pass to google nav
                    sb.append("http://maps.google.com/maps?saddr=");
                    sb.append(Double.toString( me.getMyLocation().getLatitudeE6()/1e6));
                    sb.append(",");
                    sb.append(Double.toString( me.getMyLocation().getLongitudeE6()/1e6));
                    sb.append("&daddr=");
                    sb.append(Double.toString((lat)));
                    sb.append(",");
                    sb.append(Double.toString((lon)));
                    sb.append("&dirflg=b&mra=ltm&t=h&z=13");
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                          Uri.parse(sb.toString()));
                    startActivity(intent);                                                    // start google navigation Intent
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Log.i(this.getClass().getName(), "Selected No To Add Location");               // handles cancel
                    dialog.cancel();
                }
            });
            dialog.setNeutralButton("Details", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String url = "http://www.fristonpal.info";
                    final SpannableString s = new SpannableString(url);                                 // makes string a clickable url.
                    Linkify.addLinks(s, Linkify.ALL);

                    final AlertDialog d = new AlertDialog.Builder(ShowMap.this)
                            .setPositiveButton(android.R.string.cancel, null)
                            .setIcon(R.drawable.icon)
                            .setMessage(s)
                            .create();
                    d.show();

                    // Make the textview clickable. Must be called after show()
                    ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());     // on click, web Intent started
                }
            });
            dialog.show();
            return true;
        }
            return false;
        }



        /* (non-Javadoc)
         * @see com.google.android.maps.ItemizedOverlay#draw(android.graphics.Canvas, com.google.android.maps.MapView, boolean)
         */
        @Override public void draw(Canvas canvas, MapView mapView, boolean shadow){
            if(!shadow)
                {
                    super.draw(canvas, mapView, shadow);
                }
        }

		
		/* (non-Javadoc)
		 * @see com.google.android.maps.ItemizedOverlay#size()
		 */
		@Override
		public int size() {
			return(items.size());
		}
	}
}
	