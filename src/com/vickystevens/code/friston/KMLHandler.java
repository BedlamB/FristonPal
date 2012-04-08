package com.vickystevens.code.friston;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import com.google.android.maps.GeoPoint;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class KMLHandler {
    private XmlResourceParser xpp;
    private GeoPoint[] route;
   // private ArrayList<Location> locations = new ArrayList<Location>();
    private Context context;

    public KMLHandler(Context context){
        this.context = context;
        route = new GeoPoint[4];


    }

    public GeoPoint[] getParsedItems() throws IOException, XmlPullParserException {
        try {
            getAllXML();
        } catch (XmlPullParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return route;
    }

    public void getAllXML() throws XmlPullParserException, IOException {
        Resources res = context.getResources();
        xpp = res.getXml(R.xml.purple);
        int eventType = xpp.getEventType();
        int cnt = 0;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals("coordinates")){


                    // convert to doubles

                    String s = xpp.nextText();
                    String[] tokens = s.split(",");
                    String lat = tokens[1];
                    String lon = tokens[0];
                    double d = Double.valueOf(lat.trim()).doubleValue();
                    double d2 = Double.valueOf(lon.trim()).doubleValue();
                    GeoPoint g = getPoint(d, d2);  //longitude first in KML
                    route[cnt] = g;
                    cnt++;

                }
            }
            eventType = xpp.next();
        }


    }

    private GeoPoint getPoint(double lat, double lon) {
        return(new GeoPoint((int)(lat*1e6), (int)(lon*1e6)));
    }
}
