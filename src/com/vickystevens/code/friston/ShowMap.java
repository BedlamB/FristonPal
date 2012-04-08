package com.vickystevens.code.friston;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.content.res.AssetManager;
import com.google.android.maps.*;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.vickystevens.code.friston.JSONHandler;
import org.xmlpull.v1.XmlPullParserException;


public class ShowMap extends MapActivity
{
    private MapView map;
    long start;
    long stop;
    private MyCustomLocationOverlay me;
    private MyLocationOverlay me2;
    private RouteSegmentOverlay route;
    private Overlay uphill, downhill, jumps, pirate, carparks, purple, pubs;
    private MapController mapController;
    private String theText;



    private Button uphillButton, downhillButton, jumpsButton, pirateButton, carparksButton, purpleButton, pubsButton,routeButton;
    private boolean PUBS_SHOWN, CARPARKS_SHOWN, PURPLE_SHOWN, UPHILL_SHOWN, DOWNHILL_SHOWN, JUMPS_SHOWN, PIRATE_SHOWN;

    
    private ArrayList<GeoPoint> routePoints;
    private int[] routeGrade;
    
    public enum OverlayType
    {
        UPHILL,
        DOWNHILL,
        JUMPS,
        PIRATE,
        CARPARKS,
        PURPLE,
        PUBS
    }

    

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmap);
        map=(MapView)findViewById(R.id.map);
        mapController = map.getController();
		map.getController().setZoom(17);
		map.setSatellite(true);
	
        Touchy t = new Touchy();
        List<Overlay> overlayList = map.getOverlays();
        overlayList.add(t);
		
		// set up the drawables
        Drawable uphillMarker=getResources().getDrawable(R.drawable.uphill);
        Drawable downhillMarker=getResources().getDrawable(R.drawable.downhill);
        Drawable jumpsMarker=getResources().getDrawable(R.drawable.warning);
        Drawable pirateMarker=getResources().getDrawable(R.drawable.pirate);
        Drawable carparkMarker=getResources().getDrawable(R.drawable.carpark);
        Drawable purpleMarker=getResources().getDrawable(R.drawable.purple);
		Drawable pubsMarker=getResources().getDrawable(R.drawable.pubs);


        purpleMarker.setBounds(0, 0, purpleMarker.getIntrinsicWidth(),
                purpleMarker.getIntrinsicHeight());
		carparkMarker.setBounds(0, 0, carparkMarker.getIntrinsicWidth(),
				carparkMarker.getIntrinsicHeight());
        pubsMarker.setBounds(0, 0, pubsMarker.getIntrinsicWidth(),
                pubsMarker.getIntrinsicHeight());

        //populate purple
        ArrayList<MyGeoPoint> purpleOverlays = new ArrayList<MyGeoPoint>();
        JSONArray purpleArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "purple");
                    purpleArray = handler.parseJSON();

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // can get these from file later
        for (int i = 0; i < purpleArray.length(); i++) {
            try {

                Double lat = purpleArray.getJSONObject(i).getDouble("lat");
                Double lon = purpleArray.getJSONObject(i).getDouble("lng");
                String title = purpleArray.getJSONObject(i).getString("title");
                String snippet =purpleArray.getJSONObject(i).getString("snippet");
                purpleOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }




        //populate pubs
        ArrayList<MyGeoPoint> pubOverlays = new ArrayList<MyGeoPoint>();
        JSONArray pubsArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "pubs");
            pubsArray = handler.parseJSON();

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // can get these from file later
        for (int i = 0; i < pubsArray.length(); i++) {
            try {

                Double lat = pubsArray.getJSONObject(i).getDouble("lat");
                Double lon = pubsArray.getJSONObject(i).getDouble("lng");
                String title = pubsArray.getJSONObject(i).getString("title");
                String snippet = pubsArray.getJSONObject(i).getString("snippet");
                pubOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        //populate carparks
        ArrayList<MyGeoPoint> carparkOverlays = new ArrayList<MyGeoPoint>();
        JSONArray carparkArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "carparks");
            carparkArray = handler.parseJSON();

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // can get these from file later
        for (int i = 0; i < pubsArray.length(); i++) {
            try {

                Double lat = carparkArray.getJSONObject(i).getDouble("lat");
                Double lon = carparkArray.getJSONObject(i).getDouble("lng");
                String title = carparkArray.getJSONObject(i).getString("title");
                String snippet = carparkArray.getJSONObject(i).getString("snippet");
                carparkOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        //populate uphills
        ArrayList<MyGeoPoint> uphillOverlays = new ArrayList<MyGeoPoint>();
        JSONArray uphillArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "uphill");
            uphillArray = handler.parseJSON();

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // can get these from file later
        for (int i = 0; i < uphillArray.length(); i++) {
            try {

                Double lat = uphillArray.getJSONObject(i).getDouble("lat");
                Double lon = uphillArray.getJSONObject(i).getDouble("lng");
                String title = uphillArray.getJSONObject(i).getString("title");
                String snippet = uphillArray.getJSONObject(i).getString("snippet");
                uphillOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        //populate downhills
        ArrayList<MyGeoPoint> downhillOverlays = new ArrayList<MyGeoPoint>();
        JSONArray downhillArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "downhill");
            downhillArray = handler.parseJSON();

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // can get these from file later
        for (int i = 0; i < downhillArray.length(); i++) {
            try {

                Double lat = downhillArray.getJSONObject(i).getDouble("lat");
                Double lon = downhillArray.getJSONObject(i).getDouble("lng");
                String title = downhillArray.getJSONObject(i).getString("title");
                String snippet = downhillArray.getJSONObject(i).getString("snippet");
                downhillOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        //populate jumps
        ArrayList<MyGeoPoint> jumpsOverlays = new ArrayList<MyGeoPoint>();
        JSONArray jumpsArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "jumps");
            jumpsArray = handler.parseJSON();

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // can get these from file later
        for (int i = 0; i < jumpsArray.length(); i++) {
            try {

                Double lat = jumpsArray.getJSONObject(i).getDouble("lat");
                Double lon = jumpsArray.getJSONObject(i).getDouble("lng");
                String title = jumpsArray.getJSONObject(i).getString("title");
                String snippet = jumpsArray.getJSONObject(i).getString("snippet");
                jumpsOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        //populate pirate
        ArrayList<MyGeoPoint> pirateOverlays = new ArrayList<MyGeoPoint>();
        JSONArray pirateArray = null;
        try {
            JSONHandler handler = new JSONHandler(this, "pirate");
            pirateArray = handler.parseJSON();

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // can get these from file later
        for (int i = 0; i < pirateArray.length(); i++) {
            try {

                Double lat = pirateArray.getJSONObject(i).getDouble("lat");
                Double lon = pirateArray.getJSONObject(i).getDouble("lng");
                String title = pirateArray.getJSONObject(i).getString("title");
                String snippet = pirateArray.getJSONObject(i).getString("snippet");
                pirateOverlays.add(new MyGeoPoint(lat, lon, title, snippet));

            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }





        purple  = (new MyItemizedOverlay(purpleMarker, purpleOverlays));
        carparks = (new MyItemizedOverlay(carparkMarker, carparkOverlays));        
        pubs = (new MyItemizedOverlay(pubsMarker, pubOverlays));
        uphill =(new MyItemizedOverlay(uphillMarker, uphillOverlays));
        downhill = (new MyItemizedOverlay(downhillMarker, downhillOverlays));
        jumps = (new MyItemizedOverlay(jumpsMarker, jumpsOverlays));
        pirate = (new MyItemizedOverlay(pirateMarker, pirateOverlays));
        


        
        //add overlays
        overlayList.add(purple);
        overlayList.add(carparks);
        overlayList.add(pubs);
        overlayList.add(uphill);
        overlayList.add(downhill);
        overlayList.add(jumps);
        overlayList.add(pirate);

        //add overlay
   //     map.getOverlays().add(purple);
        PURPLE_SHOWN = true;
        CARPARKS_SHOWN = true;
        PUBS_SHOWN = true;
        UPHILL_SHOWN = true;
        DOWNHILL_SHOWN = true;
        JUMPS_SHOWN = true;
        PIRATE_SHOWN = true;



        
        
        // Button to add overlays
        pubsButton = (Button)findViewById(R.id.pubsOverlay);
        pubsButton.setOnClickListener(new OnClickListener(){      
            public void onClick(View v) {	
            	toggleOverlay(map, pubs, OverlayType.PUBS);
            }
        });
        
        // Button to add overlays
        carparksButton = (Button)findViewById(R.id.carparksOverlay);
        carparksButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {	
            	toggleOverlay(map, carparks, OverlayType.CARPARKS);
            }
        });
        
        // Button to add overlays
        purpleButton = (Button)findViewById(R.id.purpleOverlay);
        purpleButton.setOnClickListener(new OnClickListener(){      
            public void onClick(View v) {	
            	toggleOverlay(map, purple, OverlayType.PURPLE);
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

        loadRouteData();
        overlayRoute();
        mapController.animateTo(getPoint(50.7768613938, 0.1555597410));


     
       
		me=new MyCustomLocationOverlay(this, map);
    	map.getOverlays().add(me);
        me.enableMyLocation();
        // sets middle of map to be mylocationoverlay

//        me.runOnFirstFix(new Runnable() {
//            @Override
//            public void run() {
//                mapController.animateTo(me.getMyLocation());
//            }
//        });

        // Set up the array of GeoPoints defining the route
        int numberRoutePoints = 5;
        GeoPoint routePoints [];   // Dimension will be set in class RouteLoader below
        int routeGrade [];               // Index for slope of route from point i to point i+1
        RouteSegmentOverlay route;   // This will hold the route segments
        boolean routeIsDisplayed = false;
     	map.postInvalidate();
    }



    
	private void toggleOverlay(MapView map, Overlay o, OverlayType type) {

		switch (type) {
		case PUBS:
			if (PUBS_SHOWN) {
				map.getOverlays().remove(o);
				PUBS_SHOWN = !PUBS_SHOWN;
				map.postInvalidate();
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
		case PURPLE:
			if (PURPLE_SHOWN) {
				map.getOverlays().remove(o);
				PURPLE_SHOWN = !PURPLE_SHOWN;
				map.postInvalidate();
				break;
			} else {
				map.getOverlays().add(o);
				PURPLE_SHOWN = !PURPLE_SHOWN;
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

    public JSONArray readJSON(String type) throws JSONException {
        read(type);
        try {
            JSONObject jObject = new JSONObject(theText);
            JSONArray resultsArray = jObject.getJSONArray("results");
            return resultsArray;
          } catch (JSONException e) {
            Toast.makeText(this, "JSON exception", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "wtf?", Toast.LENGTH_SHORT).show();
        return null;

    }

    private void read(String type) {
        //   FileInputStream fis = null;
        Scanner scanner = null;
        AssetManager am = this.getAssets();
        InputStream fis;

        // To load text file
        InputStream input;
        try {
            input = am.open(type);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            theText = new String(buffer);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            Toast.makeText(this, "IOException", Toast.LENGTH_SHORT).show();
        }
        //    line1.setText(theText);

    }

    // Overlay a route.  This method is only executed after loadRouteData() completes
    // on background thread.

    public void overlayRoute() {
        //  if(route != null) return;  // To prevent multiple route instances if key toggled rapidly
        // Set up the overlay controller
        route = new RouteSegmentOverlay(routePoints); // My class defining route overlay  THIS IS THE KEY!!  Make two arrays here and pass them
        map.getOverlays().add(route);

        // Added symbols will be displayed when map is redrawn so force redraw now
        map.postInvalidate();
    }

    private GeoPoint getPoint(double lat, double lon) {
        return(new GeoPoint((int)(lat*1e6), (int)(lon*1e6)));
    }

 

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
   
   
    public boolean isRouteDisplayed(){
    	return false;
    }



    public void onPause(){
    	super.onPause();
    	me.disableMyLocation();
    }
    
    public void onResume(){
    	super.onResume();
    	me.enableCompass();
		me.enableMyLocation();
    }


    class Touchy extends Overlay {
    	public boolean onTouchEvent(MotionEvent e, MapView v){
    		if (e.getAction() == MotionEvent.ACTION_DOWN){
    			start = e.getEventTime();
    		}
    		if (e.getAction() == MotionEvent.ACTION_UP){
    			stop = e.getEventTime();
    		}
    		if (stop - start > 1500){
    			AlertDialog alert = new AlertDialog.Builder(ShowMap.this).create();
    			alert.setTitle("Pick an Option");
    			alert.setMessage("I told you to pick and option");
    			alert.setButton("Place a pin", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
    			alert.setButton2("Get Address", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
				
					}
				});
    			alert.setButton3("Option 3", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
				

					}
				});
    			alert.show();
    			return true;

    		}
    		return false;
    	}

    }
    
    


// extends ItemIized overlay
	private class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> items=new ArrayList<OverlayItem>();
        private Drawable marker=null;

	//	public MyItemizedOverlay(Drawable marker, ArrayList<MyGeoPoint> points) {
    public MyItemizedOverlay(Drawable marker, ArrayList<MyGeoPoint> points) {
            super(boundCenterBottom(marker));
			this.marker=marker;
            for(MyGeoPoint point: points){
                this.items.add(new OverlayItem(getPoint(point.getLat(), point.getLon()), point.getTitle(), point.getSnippet()));
               }
            populate();

        }




    @Override
		protected OverlayItem createItem(int i) {
			return(items.get(i));
		}



        @Override
		protected boolean onTap(int i) {
			Toast.makeText(ShowMap.this,
						items.get(i).getSnippet(),
							Toast.LENGTH_SHORT).show();
			return(true);
		}
		
		@Override
		public int size() {
			return(items.size());
		}
	}
}
	