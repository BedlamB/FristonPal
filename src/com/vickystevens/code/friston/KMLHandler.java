package com.vickystevens.code.friston;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class KMLHandler.
 *
 * A class that takes KML and
 * parses it, returning an Arraylist of Geopoints
 *
 * @author Vicky Stevens
 * @version 1.0 Build 9000 15th April 2012.
 */
public class KMLHandler extends AsyncTask <Void, Void, ArrayList<GeoPoint>>{
    
    /** The xpp. */
    private XmlResourceParser xpp;
    private ArrayList<GeoPoint> r = new ArrayList<GeoPoint>();
    /** The route. */
  
   // private ArrayList<Location> locations = new ArrayList<Location>();
    /** The context. */
   private Context context;

    /**
     * Instantiates a new kML handler.
     *
     * @param context the context
     */
    public KMLHandler(Context context){
        this.context = context;

    }

    /**
     * Gets the parsed items.
     *
     * @return the parsed items
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws XmlPullParserException the xml pull parser exception
     */

    /**
     * Returns a Geopoint in Int number of microdegrees.
     *
     * @param lat the lat
     * @param lon the lon
     * @return the point
     */
    private GeoPoint getPoint(double lat, double lon) {
        return(new GeoPoint((int)(lat*1e6), (int)(lon*1e6)));
    }


    @Override
    protected ArrayList<GeoPoint> doInBackground(Void... voids) {
        /**
         * Uses XML pullparser to get co-ordinate tags.
         *
         * @return the all xml
         * @throws XmlPullParserException the xml pull parser exception
         * @throws IOException Signals that an I/O exception has occurred.
         */
        ArrayList<GeoPoint> theRoute = new ArrayList<GeoPoint>();
        Resources res = context.getResources();
        xpp = res.getXml(R.xml.path);
        int eventType = 0;
        try {
            eventType = xpp.getEventType();
        } catch (XmlPullParserException e) {
            //To change body of catch statement use File | Settings | File Templates.
        }

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals("coordinates")){
                    // convert to doubles
                    String s = null;
                    try {
                        s = xpp.nextText();
                    } catch (XmlPullParserException e) {
                        //To change body of catch statement use File | Settings | File Templates.
                    } catch (IOException e) {
                        //To change body of catch statement use File | Settings | File Templates.
                    }
                    String[] pairs = s.trim().split(" ");
                    String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude lngLat[1]=latitude lngLat[2]=height
                    for (int i = 1; i < pairs.length; i++) // the last one would be crash
                    {
                        lngLat = pairs[i].split(",");
                        String lat = lngLat[1];
                        String lon = lngLat[0];
                        double d = Double.valueOf(lat.trim()).doubleValue();
                        double d2 = Double.valueOf(lon.trim()).doubleValue();
                        GeoPoint g = getPoint(d, d2);  //longitude first in KML
                        theRoute.add(g);

                    }

                }
            }
            try {
                eventType = xpp.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return theRoute;
    }
}

