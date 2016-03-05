package kr.app.binbang;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MapActivity extends Activity {
    //private GoogleMap map;
    public  static String lat;
    public  static String lng;
    private final int FRAGMENT_ONE = 1;
    private final int FRAGMENT_TWO = 2;
    private int index = 1;
    public FragmentManager fragmentManager;
    private ImageButton map_btn_change;
    private ImageButton mBtn_menu;
    private ImageButton mBtn_search;
    private Context con;
    WebView webView;
    WebView webView1;
    //MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_map);
        View top = (View)findViewById(R.id.top_m);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            top.setVisibility(View.GONE);
        }
        con = this;

        ImageView logo = (ImageView)findViewById(R.id.backtomain);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //액션바 메뉴 버튼
        mBtn_menu = (ImageButton) findViewById(R.id.main_menu);
        mBtn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mDialog_con = con;
                if (!MainActivity.quick.is_open) {
                    MainActivity.quick.is_open = true;
                    MainActivity.quick.show(v);
                } else {
                    MainActivity.quick.is_open = false;
                    MainActivity.quick.dismiss();
                }
            }
        });

        //액션바 매물 검색
        mBtn_search = (ImageButton)findViewById(R.id.main_search);
        mBtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.UserID.equals("")) {
                    Intent intent = new Intent(MapActivity.this, SearchActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MapActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        Intent intent = getIntent();
        lat = intent.getExtras().getString("lat");
        lng = intent.getExtras().getString("lng");
        index = intent.getExtras().getInt("index");
/*        MapPoint POINT = MapPoint.mapPointWithGeoCoord(Float.parseFloat(lat), Float.parseFloat(lng));

        mapView = new MapView(this);
        mapView.setDaumMapApiKey("58e704627964db7174c1a5725d44aa57");

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // 중심점 변경 + 줌 레벨 변경
        mapView.setMapCenterPointAndZoomLevel(POINT, 0, true);

        // 줌 인
        mapView.zoomIn(true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(lat + " " + lng);
        marker.setTag(0);
        marker.setMapPoint(POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);*/

        String MAP_URL1 = "http://dlawogus1.cafe24.com/map.php?lat="+lat+"&lng="+lng;
        webView1 = (WebView) findViewById(R.id.webView1);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.setVerticalScrollBarEnabled(false);
        webView1.setHorizontalScrollBarEnabled(false);
        webView1.loadUrl(MAP_URL1);
        webView1.setWebViewClient(new WebViewClient());

        String MAP_URL = "http://dlawogus1.cafe24.com/loadview.php?lat="+lat+"&lng="+lng;
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.loadUrl(MAP_URL);
        webView.setWebViewClient(new WebViewClient());


        //mapView.setVisibility(View.GONE);
        //webView.setVisibility(View.GONE);

        map_btn_change = (ImageButton)findViewById(R.id.map_btn_change);
        if(index==1){
            //selectItem(FRAGMENT_ONE);
            map_btn_change.setBackgroundResource(R.drawable.roadview_btn);
            //mapView.setVisibility(View.VISIBLE);
            webView1.setVisibility(View.VISIBLE);
            webView.setVisibility(View.INVISIBLE);
        }
        else {
            //selectItem(FRAGMENT_TWO);
            map_btn_change.setBackgroundResource(R.drawable.map_btn);
            //mapView.setVisibility(View.GONE);
            webView1.setVisibility(View.INVISIBLE);
            webView.setVisibility(View.VISIBLE);
        }

        map_btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (index == 1) {
                //selectItem(FRAGMENT_TWO);
                index = 2;
                map_btn_change.setBackgroundResource(R.drawable.map_btn);
                //mapView.setVisibility(View.GONE);
                webView1.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.VISIBLE);
            } else {
                //selectItem(FRAGMENT_ONE);
                index = 1;
                map_btn_change.setBackgroundResource(R.drawable.roadview_btn);
                //mapView.setVisibility(View.VISIBLE);
                webView1.setVisibility(View.VISIBLE);
                webView.setVisibility(View.INVISIBLE);
            }
            }
        });
/*        mapView.setVisibility(View.GONE);


        Intent in = new Intent(Intent.ACTION_VIEW);
        in.addCategory(Intent.CATEGORY_DEFAULT);
        in.addCategory(Intent.CATEGORY_BROWSABLE);
        in.setData(Uri.parse("daummaps://roadView?p=37.537229,127.005515"));
        startActivity(in);*/

        //String url ="daummaps://roadView?p=37.537229,127.005515";
        //Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        //startActivity(in);
/*        ImageView logo = (ImageView)findViewById(R.id.backtomain);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //액션바 메뉴 버튼
        mBtn_menu = (ImageButton) findViewById(R.id.main_menu);
        mBtn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mDialog_con = con;
                if (!MainActivity.quick.is_open) {
                    MainActivity.quick.is_open = true;
                    MainActivity.quick.show(v);
                } else {
                    MainActivity.quick.is_open = false;
                    MainActivity.quick.dismiss();
                }
            }
        });

        //액션바 매물 검색
        mBtn_search = (ImageButton)findViewById(R.id.main_search);
        mBtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.UserID.equals("")) {
                    Intent intent = new Intent(MapActivity.this, SearchActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MapActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        Intent intent = getIntent();
        lat = intent.getExtras().getString("lat");
        lng = intent.getExtras().getString("lng");
        index = intent.getExtras().getInt("index");

        MapFragment_map.v = null;
        MapFragment_roadview.v = null;

        map_btn_change = (ImageButton)findViewById(R.id.map_btn_change);
        map_btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==1) {
                    selectItem(FRAGMENT_TWO);
                    index = 2;
                    map_btn_change.setBackgroundResource(R.drawable.map_btn);
                }else {
                    selectItem(FRAGMENT_ONE);
                    index = 1;
                    map_btn_change.setBackgroundResource(R.drawable.roadview_btn);
                }
            }
        });

        if(index==1){
            selectItem(FRAGMENT_ONE);
            map_btn_change.setBackgroundResource(R.drawable.roadview_btn);
        }
        else {
            selectItem(FRAGMENT_TWO);
            map_btn_change.setBackgroundResource(R.drawable.map_btn);
        }*/
    }

/*
    public void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment=null;
        if( position == FRAGMENT_ONE ) {
            fragment = new MapFragment_map();
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.map_fragment_place, fragment).commit();
        }else if( position == FRAGMENT_TWO ){
            fragment = new MapFragment_roadview();
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.map_fragment_place, fragment).commit();
        }
    }
*/


}
