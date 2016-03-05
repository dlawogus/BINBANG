package kr.app.binbang;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions*/
;

/**
 * Created by imjaehyun on 15. 9. 24..
 */
public class MapFragment_map extends Fragment {
    //private GoogleMap map;
    private String lat;
    private String lng;
    static View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            if (v == null) {
                v = inflater.inflate(R.layout.map_fr_map
                        , container, false);
            }
        }catch (InflateException e){}

        lat = MapActivity.lat;
        lng = MapActivity.lng;

        Log.d("latlng_1", lat + " " + lng);

/*        LatLng latlng = new LatLng(Float.parseFloat(lat),Float.parseFloat(lng));
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        Marker seoul = map.addMarker(new MarkerOptions().position(latlng));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17));

        map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);*/

        return v;
    }

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
