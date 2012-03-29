package com.vickystevens.code.friston;

import java.util.List;
import android.util.Log;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import android.graphics.drawable.Drawable;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ShowTheMap extends MapActivity {

    private static double lat;
    private static double lon;
    private int latE6;
    private int lonE6;
    private MapController mapControl;
    private GeoPoint gp;
    private MapView mapView;
    private Button overlayButton, accessButton;

    private List<Overlay> mapOverlays;
    private Drawable drawable1, drawable2;
    private RouteItemizedOverlay itemizedOverlay1, itemizedOverlay2;
    private boolean foodIsDisplayed = false;

    private Button routeButton;

    // Set up the array of GeoPoints defining the route
    int numberRoutePoints;
    GeoPoint routePoints [];   // Dimension will be set in class RouteLoader below
    int routeGrade [];               // Index for slope of route from point i to point i+1
    RouteSegmentOverlay route;   // This will hold the route segments
    boolean routeIsDisplayed = false;

    // Define an array containing the food overlay items

    private OverlayItem [] foodItem = {
            new OverlayItem( new GeoPoint(35952967,-83929158), "Food Title 1", "Food snippet 1"),
            new OverlayItem( new GeoPoint(35953000,-83928000), "Food Title 2", "Food snippet 2"),
            new OverlayItem( new GeoPoint(35955000,-83929158), "Food Title 3", "Food snippet 3")
    };

    // Define an array containing the  access overlay items

    private OverlayItem [] accessItem = {
            new OverlayItem( new GeoPoint(35953700,-83926158), "Access Title 1", "Access snippet 1"),
            new OverlayItem( new GeoPoint(35954000,-83928200), "Access Title 2", "Access snippet 2"),
            new OverlayItem( new GeoPoint(35955000,-83927558), "Access Title 3", "Access snippet 3"),
            new OverlayItem( new GeoPoint(35954000,-83927158), "Access Title 4", "Access snippet 4")
    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // Suppress title bar for more space
        setContentView(R.layout.showmap);

        // Add map controller with zoom controls
        mapView = (MapView) findViewById(R.id.map);
        mapView.setSatellite(false);
        mapView.setTraffic(false);
        mapView.setBuiltInZoomControls(true);   // Set android:clickable=true in main.xml
        int maxZoom = mapView.getMaxZoomLevel();
        int initZoom = maxZoom-2;
        mapControl = mapView.getController();
        mapControl.setZoom(initZoom);
        // Convert lat/long in degrees into integers in microdegrees
        latE6 =  (int) (lat*1e6);
        lonE6 = (int) (lon*1e6);
        gp = new GeoPoint(latE6, lonE6);
        mapControl.animateTo(gp);
        numberRoutePoints = 5;

        // Button to control route overlay
        routeButton = (Button)findViewById(R.id.doRoute);
        routeButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                setOverlay1();
                loadRouteData();
                overlayRoute();
                mapControl.animateTo(new GeoPoint(35952967, -83929158));
//                overlayRoute();
//                if(! routeIsDisplayed){
//                    routeIsDisplayed = true;
//                    loadRouteData();
//                    overlayRoute();
//                } else {
//                    if(route != null) route.setRouteView(false);
//                    route=null; // Prevent multiple route instances if key toggled rapidly
//                    routeIsDisplayed = false;
//                    mapView.postInvalidate();
//                }
            }
        });









    }

    public void setOverlay1(){
        int foodLength = foodItem.length;
        // Create itemizedOverlay2 if it doesn't exist and display all three items
        if(! foodIsDisplayed){
            mapOverlays = mapView.getOverlays();
            drawable1 = this.getResources().getDrawable(R.drawable.eject);
            itemizedOverlay1 = new RouteItemizedOverlay(drawable1);
            // Display all three items at once
            for(int i=0; i<foodLength; i++){
                itemizedOverlay1.addOverlay(foodItem[i]);
            }
            mapOverlays.add(itemizedOverlay1);
            foodIsDisplayed = !foodIsDisplayed;
            // Remove each item successively with button clicks
        } else {
            itemizedOverlay1.removeItem(itemizedOverlay1.size()-1);
            if((itemizedOverlay1.size() < 1))  foodIsDisplayed = false;
        }
        // Added symbols will be displayed when map is redrawn so force redraw now
        mapView.postInvalidate();
    }


    // Method to read route data from server as XML

    public void loadRouteData(){

        routePoints = new GeoPoint[5];
        routeGrade = new int[5];
        routePoints[0] = new GeoPoint(35952967, -83929158);
        routeGrade[0] = 1;
        routePoints[1] = new GeoPoint(35954021, -83930341);
        routeGrade[1] = 1;
        routePoints[2] = new GeoPoint(35954951, -83929075);
        routeGrade[2] = 1;
        routePoints[3] = new GeoPoint(35955203, -83928973);
        routeGrade[3] = 2;
        routePoints[4] = new GeoPoint(35955212, -83928855);
        routeGrade[4] = 2;

//        try {
//            String url = "http://eagle.phys.utk.edu/reubendb/UTRoute.php";
//            String data = "?lat1=35952967&lon1=-83929158&lat2=35956567&lon2=-83925450";
//            //RouteLoader RL = new RouteLoader();
//            //RL.execute(new URL(url+data));
//            new RouteLoader().execute(new URL(url+data));
//        } catch (MalformedURLException e) {
//            Log.i("NETWORK", "Failed to generate valid URL");
//        }
    }



    // Method to insert latitude and longitude in degrees
    public static void putLatLong(double latitude, double longitude){
        lat = latitude;
        lon =longitude;
    }

    // This sets the s key on the phone to toggle between satellite and map view
    // and the t key to toggle between traffic and no traffic view (traffic view
    // relevant only in urban areas where it is reported).

    public boolean onKeyDown(int keyCode, KeyEvent e){
        if(keyCode == KeyEvent.KEYCODE_S){
            mapView.setSatellite(!mapView.isSatellite());
            return true;
        } else if(keyCode == KeyEvent.KEYCODE_T){
            mapView.setTraffic(!mapView.isTraffic());
            mapControl.animateTo(gp);  // To ensure change displays immediately
        }
        return(super.onKeyDown(keyCode, e));
    }


    // Overlay a route.  This method is only executed after loadRouteData() completes
    // on background thread.

    public void overlayRoute() {
      //  if(route != null) return;  // To prevent multiple route instances if key toggled rapidly
        // Set up the overlay controller
        route = new RouteSegmentOverlay(routePoints, routeGrade); // My class defining route overlay  THIS IS THE KEY!!  Make two arrays here and pass them
        mapOverlays = mapView.getOverlays();
        mapOverlays.add(route);

        // Added symbols will be displayed when map is redrawn so force redraw now
        mapView.postInvalidate();
    }

    // Required method since class extends MapActivity
    @Override
    protected boolean isRouteDisplayed() {
        return false;  // Don't display a route
    }
}

