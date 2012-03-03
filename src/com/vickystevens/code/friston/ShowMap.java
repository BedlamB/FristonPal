package com.vickystevens.code.friston;


import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;




import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class ShowMap extends MapActivity
{
    private MapView map;
    long start;
    long stop;
    private MyCustomLocationOverlay me;

    

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmap);
        map=(MapView)findViewById(R.id.map);
	//	map.getController().setZoom(17);
		map.setSatellite(true);
		
		
		// set up the drawables
		Drawable marker=getResources().getDrawable(R.drawable.marker);
		Drawable marker2=getResources().getDrawable(R.drawable.marker2);		
		
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
														marker.getIntrinsicHeight());
        marker2.setBounds(0, 0, marker.getIntrinsicWidth(),
                marker.getIntrinsicHeight());

        // populate brap arraylist
        ArrayList<MyGeoPoint> pubOverlays = new ArrayList<MyGeoPoint>();
           // can get these from file later
        pubOverlays.add(new MyGeoPoint(50.842941, -0.141312, "BR", "The End Of The Rainbow"));
        pubOverlays.add(new MyGeoPoint(50.819583, -0.136420, "PR", "Brighton Pier"));


        ArrayList<MyGeoPoint> trailOverlays = new ArrayList<MyGeoPoint>();
         // can get these from file later
        trailOverlays.add(new MyGeoPoint(50.842911, -0.131312, "MD", "Where Dreams Are Made"));
        trailOverlays.add(new MyGeoPoint(50.829200, -0.146230, "MD", "Xanadu"));

        Overlay o = (new Brap(marker, trailOverlays));
        map.getOverlays().add(o);
        Overlay p = (new Brap(marker2, pubOverlays));
        map.getOverlays().add(p);


        Touchy t = new Touchy();
        List<Overlay> overlayList = map.getOverlays();
        overlayList.add(t);


		me=new MyCustomLocationOverlay(this, map);
	/*	me.runOnFirstFix(new Runnable(){
			public void run(){
				map.getController().animateTo(me.getMyLocation());
			}
		});*/
    

		map.getOverlays().add(me);
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
						// TODO Auto-generated method stub

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







	private class Brap extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> items=new ArrayList<OverlayItem>();
        private Drawable marker=null;

		public Brap(Drawable marker, ArrayList<MyGeoPoint> points) {

            super(marker);
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

        public void draw(Canvas canvas, MapView mapv, boolean shadow){
            super.draw(canvas, mapv, shadow);

//            Paint mPaint = new Paint();
//            mPaint.setDither(true);
//            mPaint.setColor(Color.RED);
//            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//            mPaint.setStrokeJoin(Paint.Join.ROUND);
//            mPaint.setStrokeCap(Paint.Cap.ROUND);
//            mPaint.setStrokeWidth(4);
//
//            GeoPoint gP1 = getPoint(50.842911, -0.131312);
//            GeoPoint gP2 = getPoint(50.842941, -0.141312);
//            GeoPoint gP3 = getPoint(50.829200, -0.146230);
//            GeoPoint gP4 = getPoint(50.819583, -0.136420);
//
//            Point p1 = new Point();
//            Point p2 = new Point();
//            Point p3 = new Point();
//            Point p4 = new Point();
//
//
//            Path path = new Path();
//
//            Projection projection = mapv.getProjection();
//            projection.toPixels(gP1, p1);
//            projection.toPixels(gP2, p2);
//            projection.toPixels(gP3, p3);
//            projection.toPixels(gP4, p4);
//
//            path.moveTo(p2.x, p2.y);
//            path.lineTo(p1.x,p1.y);
//            path.moveTo(p3.x, p3.y);
//            path.lineTo(p2.x, p2.y);
//            path.moveTo(p4.x, p4.y);
//            path.lineTo(p3.x, p3.y);
//            path.moveTo(p1.x, p1.y);
//            path.lineTo(p4.x, p4.y);
//
//            canvas.drawPath(path, mPaint);
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
	