package com.vickystevens.code.friston;
// idea from stack overflow http://stackoverflow.com/questions/753793/how-can-i-use-a-custom-bitmap-for-the-you-are-here-point-in-a-mylocationoverlay
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.location.Location;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomMyLocationOverlay.
 *
 * class extending MyLocationOverlay.
 * Overrides drawlocation to use a custom icon and animates
 * icon as accelerometer changes position
 *
 * @author Vicky Stevens
 * @version 1.0 Build 9000 15th April 2012.
 */
public class CustomMyLocationOverlay extends MyLocationOverlay {
    
    /** The m context. */
    private Context context;
    
    /** The m map view. */
    private MapView map;


    /**
     * Instantiates a new my custom location overlay.
     *
     * @param context the context
     * @param map the map view
     */
    public CustomMyLocationOverlay(Context context, MapView map) {
        super(context, map);
        map = map;
        this.context = context;
    }

    /* (non-Javadoc)
     * @see com.google.android.maps.MyLocationOverlay#drawMyLocation(android.graphics.Canvas, com.google.android.maps.MapView, android.location.Location, com.google.android.maps.GeoPoint, long)
     */
    @Override
    protected void drawMyLocation(Canvas canvas, MapView map, Location lastFix, GeoPoint myLocation, long when) {
        // translate the GeoPoint to screen pixels
        Point screenPts = map.getProjection().toPixels(myLocation, null);

        // create a rotated copy of the marker
        Bitmap arrowBitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.helmet);
        Matrix matrix = new Matrix();
        matrix.postRotate(this.getOrientation());
        Bitmap rotatedBmp = Bitmap.createBitmap(
            arrowBitmap,
            0, 0,
            arrowBitmap.getWidth(),
            arrowBitmap.getHeight(),
            matrix,
            true
        );
        // add the rotated marker to the canvas
        canvas.drawBitmap(
            rotatedBmp,
            screenPts.x - (rotatedBmp.getWidth()  / 2),
            screenPts.y - (rotatedBmp.getHeight() / 2),
            null
        );
		map.postInvalidate();

    }
    
    /* (non-Javadoc)
     * @see com.google.android.maps.MyLocationOverlay#onLocationChanged(android.location.Location)
     */
    @Override
    public synchronized void onLocationChanged(Location location){
        super.onLocationChanged(location);
/*        GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));   // can use this to keep focus
        MapController mapController = mMapView.getController();                                                         // on user icon as phone changes location
        mapController.animateTo(point);*/
    }



}