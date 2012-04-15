package com.vickystevens.code.friston;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
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
 *
 * @author Vicky Stevens
 * @version 1.0 Build 9000 15th April 2012.
 */
public class KMLHandler {
    
    /** The xpp. */
    private XmlResourceParser xpp;
    
    /** The route. */
    private ArrayList<GeoPoint> route;
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
        route = new ArrayList<GeoPoint>();


    }

    /**
     * Gets the parsed items.
     *
     * @return the parsed items
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws XmlPullParserException the xml pull parser exception
     */
    public ArrayList<GeoPoint> getParsedItems() throws IOException, XmlPullParserException {
        try {
            getAllXML();
        } catch (XmlPullParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return route;
    }

    /**
     * Gets the all xml.
     *
     * @return the all xml
     * @throws XmlPullParserException the xml pull parser exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void getAllXML() throws XmlPullParserException, IOException {
        Resources res = context.getResources();
        xpp = res.getXml(R.xml.path);
        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals("coordinates")){


                    // convert to doubles

                    String s = xpp.nextText();
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
                        route.add(g);

                    }


                }
            }
            eventType = xpp.next();
        }


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
}
