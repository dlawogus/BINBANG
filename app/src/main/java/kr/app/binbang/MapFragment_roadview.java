package kr.app.binbang;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;*/

/**
 * Created by imjaehyun on 15. 9. 24..
 */
public class MapFragment_roadview extends Fragment {

    private String lat;
    private String lng;
    static View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            if (v == null) {
                v = inflater.inflate(R.layout.map_fr_road_view, container, false);
            }
        }catch (InflateException e){}
        //Log.d("map_v", v.toString() );

        lat = MapActivity.lat;
        lng = MapActivity.lng;

        Log.d("latlng_2", lat + " " + lng);


/*        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);*/
        return v;
    }

/*    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
        panorama.setPosition(new LatLng(Float.parseFloat(lat), Float.parseFloat(lng)),100);
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(v!=null){
            ViewGroup parent = (ViewGroup)v.getParent();
            if(parent!=null){
                parent.removeView(v);
            }
        }
    }
}
