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
    private MyLocationOverlay me;
    

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmap);
        map=(MapView)findViewById(R.id.map);
		map.getController().setCenter(getPoint(50.84827,-0.12070));
		map.getController().setZoom(17);
		map.setBuiltInZoomControls(true);
		map.setSatellite(!map.isSatellite());
		
        Touchy t = new Touchy();
        List<Overlay> overlayList = map.getOverlays();
        overlayList.add(t);
	
	
		Drawable marker=getResources().getDrawable(R.drawable.marker);
		Drawable marker2=getResources().getDrawable(R.drawable.marker2);		
		
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
														marker.getIntrinsicHeight());
		
		map.getOverlays().add(new SitesOverlay(marker));
		map.getOverlays().add(new Brap(marker2));
		
		me=new MyLocationOverlay(this, map);
		map.getOverlays().add(me);
     }
    
    public boolean isRouteDisplayed(){
    	return false;
    }
    
    class MyOverlay extends Overlay{
    	
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
		
	private class SitesOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> items=new ArrayList<OverlayItem>();

		private Drawable marker=null;
		
		public SitesOverlay(Drawable marker) {
			super(marker);
			this.marker= marker;
			this.items = items;
			// @TODO implement iterating over passed list of markers and extracting info
//			for (int i = 0; i <= items.size(); i++){
//				this.items.add(new OverlayItem(getPoint(items)))
//			}
			this.items.add(new OverlayItem(getPoint(50.842941, -0.131312),
        														"BR", "Central Brighton"));
			this.items.add(new OverlayItem(getPoint(50.819583, -0.136420),
																"Pier", "Brighton Pier"));

			populate();
		}
		

		@Override
		protected OverlayItem createItem(int i) {
			return(items.get(i));
		}
		
	    public void draw(Canvas canvas, MapView mapv, boolean shadow){
	        super.draw(canvas, mapv, shadow);

	        Paint mPaint = new Paint();
	        mPaint.setDither(true);
	        mPaint.setColor(Color.RED);
	        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	        mPaint.setStrokeJoin(Paint.Join.ROUND);
	        mPaint.setStrokeCap(Paint.Cap.ROUND);
	        mPaint.setStrokeWidth(2);

	        GeoPoint gP1 = getPoint(50.842941, -0.131312);
	        GeoPoint gP2 = getPoint(50.819583, -0.136420);

	        Point p1 = new Point();
	        Point p2 = new Point();

	        Path path = new Path();

	        Projection projection = mapv.getProjection();
	        projection.toPixels(gP1, p1);
	        projection.toPixels(gP2, p2);

	        path.moveTo(p2.x, p2.y);
	        path.lineTo(p1.x,p1.y);

	        canvas.drawPath(path, mPaint);
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

	
	private class Brap extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> items=new ArrayList<OverlayItem>();

		private Drawable marker=null;
		
		public Brap(Drawable marker) {
			super(marker);
			this.marker=marker;
			items.add(new OverlayItem(getPoint(51.842941, -0.741312),
        														"BR", "Central Brighton"));
			items.add(new OverlayItem(getPoint(49.819583, -0.326420),
																"Pier", "Brighton Pier"));

			populate();
		}
		

		@Override
		protected OverlayItem createItem(int i) {
			return(items.get(i));
		}
		
		@Override
		public void draw(Canvas canvas, MapView mapView,
											boolean shadow) {
			super.draw(canvas, mapView, shadow);
			
			boundCenterBottom(marker);
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
	