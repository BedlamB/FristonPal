package com.vickystevens.code.friston;



import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

// TODO: Auto-generated Javadoc
/**
 * The Class RouteItemizedOverlay.
 * 
 * @author Vicky Stevens
 * @version 1.0 Build 9000 15th April 2012.
 */
public class RouteItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    /** The my overlays. */
    private ArrayList<OverlayItem> myOverlays ;

    /**
     * Instantiates a new route itemized overlay.
     *
     * @param defaultMarker the default marker
     */
    public RouteItemizedOverlay(Drawable defaultMarker) {
        super(boundCenterBottom(defaultMarker));
        myOverlays = new ArrayList<OverlayItem>();
        populate();
    }

    /**
     * Adds the overlay.
     *
     * @param overlay the overlay
     */
    public void addOverlay(OverlayItem overlay){
        myOverlays.add(overlay);
      //  populate();
    }

    /* (non-Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#createItem(int)
     */
    @Override
    protected OverlayItem createItem(int i) {
        return myOverlays.get(i);
    }

    // Removes overlay item i
    /**
     * Removes the item.
     *
     * @param i the i
     */
    public void removeItem(int i){
        myOverlays.remove(i);
    //    populate();
    }
    
    /* (non-Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#draw(android.graphics.Canvas, com.google.android.maps.MapView, boolean)
     */
    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow){
        if(!shadow)
        {
            super.draw(canvas, mapView, shadow);
        }
            
    }

    // Handle tap events on overlay icons
//    @Override
//    protected boolean onTap(int i){
//
//        /*	In this case we will just put a transient Toast message on the screen indicating that we have
//    captured the relevant information about the overlay item.  In a more serious application one
//    could replace the Toast with display of a customized view with the title, snippet text, and additional
//    features like an embedded image, video, or sound, or links to additional information. (The lat and
//    lon variables return the coordinates of the icon that was clicked, which could be used for custom
//    positioning of a display view.)*/
//
//        GeoPoint  gpoint = myOverlays.get(i).getPoint();
//        double lat = gpoint.getLatitudeE6()/1e6;
//        double lon = gpoint.getLongitudeE6()/1e6;
//        String toast = "Title: "+myOverlays.get(i).getTitle();
//        toast += "\nText: "+myOverlays.get(i).getSnippet();
//        toast += 	"\nSymbol coordinates: Lat = "+lat+" Lon = "+lon+" (microdegrees)";
//        Toast.makeText(MapOverlayDemo.context, toast, Toast.LENGTH_LONG).show();
//        return(true);
//    }

    // Returns present number of items in list
    /* (non-Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#size()
     */
    @Override
    public int size() {
        return myOverlays.size();
    }
}

