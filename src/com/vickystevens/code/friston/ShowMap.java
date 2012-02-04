/* Copyright (c) 2008-2009 -- CommonsWare, LLC

	 Licensed under the Apache License, Version 2.0 (the "License");
	 you may not use this file except in compliance with the License.
	 You may obtain a copy of the License at

		 http://www.apache.org/licenses/LICENSE-2.0

	 Unless required by applicable law or agreed to in writing, software
	 distributed under the License is distributed on an "AS IS" BASIS,
	 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	 See the License for the specific language governing permissions and
	 limitations under the License.
*/
	 
package com.vickystevens.code.friston;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;
import java.util.ArrayList;
import java.util.List;

public class ShowMap extends MapActivity {
	private MapView map=null;
	private MyLocationOverlay me=null;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapshow);
		
		map=(MapView)findViewById(R.id.map);
		


		// going to want to get users current location here and set centre on that
		//	instead
		map.getController().setCenter(getPoint(50.84827,-0.12070));
		map.getController().setZoom(17);
		map.setBuiltInZoomControls(true);
		map.setSatellite(!map.isSatellite());
		
		Drawable marker=getResources().getDrawable(R.drawable.marker);
		
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
														marker.getIntrinsicHeight());
		
		map.getOverlays().add(new SitesOverlay(marker));
		
		me=new MyLocationOverlay(this, map);
		map.getOverlays().add(me);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		me.enableCompass();
	}		
	
	@Override
	public void onPause() {
		super.onPause();
		
		me.disableCompass();
	}		
	
 	@Override
	protected boolean isRouteDisplayed() {
		return(false);
	}
	
 	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_S) {
			map.setSatellite(!map.isSatellite());
			return(true);
		}
		else if (keyCode == KeyEvent.KEYCODE_Z) {
			map.displayZoomControls(true);
			return(true);
		}
		
		return(super.onKeyDown(keyCode, event));
	}

	private GeoPoint getPoint(double lat, double lon) {
		return(new GeoPoint((int)(lat*1000000.0),
													(int)(lon*1000000.0)));
	}
		
	private class SitesOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> items=new ArrayList<OverlayItem>();
		private List<OverlayItem> pubs = new ArrayList<OverlayItem>();
		private Drawable pubMarker=getResources().getDrawable(R.drawable.cw);
		private Drawable marker=null;
		
		public SitesOverlay(Drawable marker) {
			super(marker);
			this.marker=marker;
		

			items.add(new OverlayItem(getPoint(50.842941, -0.131312),
																"BR", "Central Brighton"));
				
			items.add(new OverlayItem(getPoint(50.819583, -0.136420),
																"Pier", "Brighton Pier"));
			this.marker = pubMarker;
			pubs.add(new OverlayItem(getPoint(50.824174,-0.135785),
					"Brighton University",
					"Home of Jazz at Lincoln Center"));
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