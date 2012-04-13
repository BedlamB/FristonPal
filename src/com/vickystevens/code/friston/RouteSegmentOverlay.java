// adapted from http://eagle.phys.utk.edu/guidry/android/mapOverlayDemo.html 

package com.vickystevens.code.friston;
    
    import android.graphics.Canvas;
    import android.graphics.Paint;
    import android.graphics.Point;
    
    import com.google.android.maps.GeoPoint;
    import com.google.android.maps.MapView;
    import com.google.android.maps.Overlay;

    import java.util.ArrayList;

public class RouteSegmentOverlay extends Overlay {
        private GeoPoint locpoint;
        private Paint paint;
        private ArrayList<GeoPoint> routePoints;
 //       private GeoPoint routePoints [];
        private boolean routeIsActive;
        private Point pold, pnew, pp;
        private int numberRoutePoints;
        
        // Constructor permitting the route array to be passed as an argument.
        public RouteSegmentOverlay(ArrayList<GeoPoint> routePoints) {
                this.routePoints = routePoints;
                  numberRoutePoints  = routePoints.size();
                routeIsActive = true;
                // If first time, set initial location to start of route
                locpoint = routePoints.get(0);
                pold = new Point(0, 0);
                pnew = new Point(0,0);
                pp = new Point(0,0);
                paint = new Paint();
        }


        
        // Method to turn route display on and off
        public void setRouteView(boolean routeIsActive){
                this.routeIsActive = routeIsActive;
        }
    
        @Override
        public void draw(Canvas canvas, MapView mapview, boolean shadow) {
            if (!shadow){
                super.draw(canvas, mapview, shadow);
            }
            if(! routeIsActive) return;
            

            mapview.getProjection().toPixels(locpoint, pp);       // Converts GeoPoint to screen pixels
            
            int xoff = 0;
            int yoff = 0;
            int oldx = pp.x;
            int oldy = pp.y;
            int newx = oldx + xoff;
            int newy = oldy + yoff;
            
            paint.setAntiAlias(true);
    
            // Draw route segment by segment, setting color and width of segment according to the slope
            // information returned from the server for the route.
            
            for(int i=0; i<numberRoutePoints-1; i++){
               paint.setARGB(100, 44,133,110);
               paint.setStrokeWidth(6);

                // Find endpoints of this segment in pixels
                mapview.getProjection().toPixels(routePoints.get(i), pold);
                oldx = pold.x;
                oldy = pold.y;
                mapview.getProjection().toPixels(routePoints.get(i+1), pnew);
                newx = pnew.x;
                newy = pnew.y;
                
                // Draw the segment
                canvas.drawLine(oldx, oldy, newx, newy, paint);
            }      
        }
    }

