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
    private Overlay uphill, downhill, jumps, pirate, carparks, purple, pubs;
    private MapController mapController;
    private Button uphillButton, downhillButton, jumpsButton, pirateButton, carparksButton, purpleButton, pubsButton,routeButton;
    private boolean PUBS_SHOWN, CARPARKS_SHOWN, PURPLE_SHOWN, UPHILL_SHOWN, DOWNHILL_SHOWN, JUMPS_SHOWN, PIRATE_SHOWN;

    
    private GeoPoint[] routePoints;
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


        ArrayList<MyGeoPoint> purpleOverlays = new ArrayList<MyGeoPoint>();
        // can get these from file later
        purpleOverlays.add(new MyGeoPoint(50.852911, -0.161312, "purple", "Where Dreams Are Made"));
        purpleOverlays.add(new MyGeoPoint(50.879200, -0.186230, "purple", "Xanadu"));



        ArrayList<MyGeoPoint> carparkOverlays = new ArrayList<MyGeoPoint>();
        // can get these from file later
        carparkOverlays.add(new MyGeoPoint(50.842911, -0.131312, "car", "Where Dreams Are Made"));
        carparkOverlays.add(new MyGeoPoint(50.829200, -0.146230, "car", "Xanadu"));        

        // populate arraylist
        ArrayList<MyGeoPoint> pubOverlays = new ArrayList<MyGeoPoint>();
           // can get these from file later
        pubOverlays.add(new MyGeoPoint(50.842941, -0.141312, "pub", "The End Of The Rainbow"));
        pubOverlays.add(new MyGeoPoint(50.819583, -0.136420, "pub", "Brighton Pier"));





        
        ArrayList<MyGeoPoint> uphillOverlays = new ArrayList<MyGeoPoint>();
        uphillOverlays.add(new MyGeoPoint(50.853811, -0.171323, "uphill", "its steep here"));
        uphillOverlays.add(new MyGeoPoint(50.82734, -0.168301, "uphill", "its steep here"));
        
        ArrayList<MyGeoPoint> downhillOverlays = new ArrayList<MyGeoPoint>();
        downhillOverlays.add(new MyGeoPoint(50.852780, -0.159480, "downhill", "whee!"));
        downhillOverlays.add(new MyGeoPoint(50.882095, -0.160392, "downhill", "Whee!"));
        
        ArrayList<MyGeoPoint> jumpsOverlays = new ArrayList<MyGeoPoint>();
        jumpsOverlays.add(new MyGeoPoint(50.839573, -0.148493, "jump", "hup!"));
        jumpsOverlays.add(new MyGeoPoint(50.882920, -0.129482, "jump", "hup!"));
        
        ArrayList<MyGeoPoint> pirateOverlays = new ArrayList<MyGeoPoint>();
        pirateOverlays.add(new MyGeoPoint(50.894829, -0.182920, "pirate", "ARRRH"));
        pirateOverlays.add(new MyGeoPoint(50.884930, -0.184028, "pirate", "ARRRRR"));




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
   //     map.getOverlays().add(pubs);
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
        routeGrade[3] = 1;
        routePoints[4] = new GeoPoint(50852911, -01613120);
        routeGrade[4] = 1;
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
	