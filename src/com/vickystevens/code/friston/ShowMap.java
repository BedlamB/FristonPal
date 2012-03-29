package com.vickystevens.code.friston;


import java.util.ArrayList;
import java.util.List;

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


public class ShowMap extends MapActivity
{
    private MapView map;
    long start;
    long stop;
    private MyCustomLocationOverlay me;
    private MyLocationOverlay me2;
    private RouteSegmentOverlay route;
    private Overlay trails, pubs, purple;
    private MapController mapController;
    private Button pubsButton, trailsButton, purpleButton, routeButton;
    private boolean PUBS_SHOWN, TRAILS_SHOWN, PURPLE_SHOWN;

    
    private GeoPoint[] routePoints;
    private int[] routeGrade;
    
    public enum OverlayType
    {
        PUBS,
        TRAILS,
        DOWNHILL,
        PURPLE,
        JUMPS
      
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
		Drawable pubsMarker=getResources().getDrawable(R.drawable.pubsmarker);
		Drawable trailsMarker=getResources().getDrawable(R.drawable.trailsmarker);	
		Drawable purpleMarker=getResources().getDrawable(R.drawable.purplemarker);
//		Drawable downhillMarker=getResources().getDrawable(R.drawable.downhillMarker);		
//		Drawable jumpsMarker=getResources().getDrawable(R.drawable.jumpsMarker);	
		
		pubsMarker.setBounds(0, 0, pubsMarker.getIntrinsicWidth(),
				pubsMarker.getIntrinsicHeight());
        trailsMarker.setBounds(0, 0, trailsMarker.getIntrinsicWidth(),
                trailsMarker.getIntrinsicHeight());
        purpleMarker.setBounds(0, 0, trailsMarker.getIntrinsicWidth(),
                purpleMarker.getIntrinsicHeight());

        // populate arraylist
        ArrayList<MyGeoPoint> pubOverlays = new ArrayList<MyGeoPoint>();
           // can get these from file later
        pubOverlays.add(new MyGeoPoint(50.842941, -0.141312, "pub", "The End Of The Rainbow"));
        pubOverlays.add(new MyGeoPoint(50.819583, -0.136420, "pub", "Brighton Pier"));




        ArrayList<MyGeoPoint> trailOverlays = new ArrayList<MyGeoPoint>();
         // can get these from file later
        trailOverlays.add(new MyGeoPoint(50.842911, -0.131312, "trail", "Where Dreams Are Made"));
        trailOverlays.add(new MyGeoPoint(50.829200, -0.146230, "trail", "Xanadu"));
        
        ArrayList<MyGeoPoint> purpleOverlays = new ArrayList<MyGeoPoint>();
        // can get these from file later
        purpleOverlays.add(new MyGeoPoint(50.852911, -0.161312, "purple", "Where Dreams Are Made"));
        purpleOverlays.add(new MyGeoPoint(50.879200, -0.186230, "purple", "Xanadu"));

        pubs = (new MyItemizedOverlay(pubsMarker, pubOverlays));
        trails = (new MyItemizedOverlay(trailsMarker, trailOverlays));
        purple  = (new MyItemizedOverlay(purpleMarker, purpleOverlays));
        //add overlays
        overlayList.add(trails);
        overlayList.add(purple);
        overlayList.add(pubs);

        //add overlay
   //     map.getOverlays().add(pubs);
        PUBS_SHOWN = true;
        TRAILS_SHOWN = true;
        PURPLE_SHOWN = true;



        
        
        // Button to add overlays
        pubsButton = (Button)findViewById(R.id.pubsOverlay);
        pubsButton.setOnClickListener(new OnClickListener(){      
            public void onClick(View v) {	
            	toggleOverlay(map, pubs, OverlayType.PUBS);
            }
        });
        
        // Button to add overlays
        trailsButton = (Button)findViewById(R.id.trailsOverlay);
        trailsButton.setOnClickListener(new OnClickListener(){      
            public void onClick(View v) {	
            	toggleOverlay(map, trails, OverlayType.TRAILS);
            }
        });
        
        // Button to add overlays
        purpleButton = (Button)findViewById(R.id.purpleOverlay);
        purpleButton.setOnClickListener(new OnClickListener(){      
            public void onClick(View v) {	
            	toggleOverlay(map, purple, OverlayType.PURPLE);
            }
        });

        // Button to control route overlay
        routeButton = (Button)findViewById(R.id.doRoute);
        routeButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                loadRouteData();
                overlayRoute();
                mapController.animateTo(new GeoPoint(50842941, -01413120));
            }
        });

     
     
		me=new MyCustomLocationOverlay(this, map);
    	map.getOverlays().add(me);
        me.enableMyLocation();
        // sets middle of map to be mylocationoverlay

        me.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                mapController.animateTo(me.getMyLocation());
            }
        });

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

		case TRAILS:
			if (TRAILS_SHOWN) {
				map.getOverlays().remove(o);
				TRAILS_SHOWN = !TRAILS_SHOWN;
				map.postInvalidate();
				break;
			} else {
				map.getOverlays().add(o);
				TRAILS_SHOWN = !TRAILS_SHOWN;
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
		case DOWNHILL:
			break;
		case JUMPS:
			break;
		}
	}

    // Overlay a route.  This method is only executed after loadRouteData() completes
    // on background thread.

    public void overlayRoute() {
        //  if(route != null) return;  // To prevent multiple route instances if key toggled rapidly
        // Set up the overlay controller
        route = new RouteSegmentOverlay(routePoints, routeGrade); // My class defining route overlay  THIS IS THE KEY!!  Make two arrays here and pass them
        map.getOverlays().add(route);

        // Added symbols will be displayed when map is redrawn so force redraw now
        map.postInvalidate();
    }

    public void loadRouteData(){

        routePoints = new GeoPoint[5];
        routeGrade = new int[5];
        routePoints[0] = new GeoPoint(50842941, -01413120);
        routeGrade[0] = 1;
        routePoints[1] = new GeoPoint(50842850, -01364200);
        routeGrade[1] = 1;
        routePoints[2] = new GeoPoint(50842911, -01313120);
        routeGrade[2] = 1;
        routePoints[3] = new GeoPoint(50829200, -01462300);
        routeGrade[3] = 2;
        routePoints[4] = new GeoPoint(50852911, -01613120);
        routeGrade[4] = 2;
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
    
    
    
	private GeoPoint getPoint(double lat, double lon) {
		return(new GeoPoint((int)(lat*1000000.0),
													(int)(lon*1000000.0)));
	}




// extends ItemIzed overlay
	private class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> items=new ArrayList<OverlayItem>();
        private Drawable marker=null;

	//	public MyItemizedOverlay(Drawable marker, ArrayList<MyGeoPoint> points) {
    public MyItemizedOverlay(Drawable marker, ArrayList<MyGeoPoint> points) {
            super(boundCenterBottom(marker));
			this.marker=marker;
            for(MyGeoPoint point: points){
                this.items.add(new OverlayItem(getPoint(point.getLat(), point.getLon()), point.getTag(), point.getMsg()));
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
	