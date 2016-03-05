package kr.app.binbang;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.app.adapter.BangViewAdapter;
import kr.app.customalert.CustomDialog;
import kr.app.datainfo.AdDataInfo;
import kr.app.datainfo.BangDataInfo;
import kr.app.dbhandle.DbAdapter;


public class BangViewActivity extends FontActivity {
    private ViewPager pager;
    private BangViewAdapter viewAdapter;
    private Thread thread = null;
    private Handler handler = null;
    //private int p=0;	//페이지번호
    //private int v=0;	//화면 전환 뱡향
    private ArrayList<AdDataInfo> mBangView_array;
    private BangViewAdapter mBangViewAdapter;

    //데이터
    private String _id  = "";
    private String building_name = "";
    private String building_hosu = "";
    private String si ="";
    private String gu = "";
    private String dong = "";
    private String sangse_juso = "";
    private String deposit ="";
    private String deposit_possible ="";
    private String monthly_rental="";
    private String base_price="";
    private String empty="";
    private String price_type="";
    private String manage_price="";
    private String is_manage_internet="";
    private String is_manage_sudo="";
    private String is_manage_yusun="";
    private String bang_type="";
    private String boiler="";
    private String call_who="";
    private String call="";
    private String lat="";
    private String lng="";
    private String is_park="";
    private String possible_date="";
    private String building_password="";
    private String is_elevator="";
    private String bang_option="";
    private String comment="";
    private String list_img_url="";
    private ArrayList<String> img_url;

    private ImageButton mBtn_menu;
    private ImageButton mBtn_search;
    private boolean mIs_Menu = false;
    private String mBang_id;
    private String mResult = "";

    private TextView building_name_txt;
    private TextView building_hosu_txt;
    private TextView dong_txt;
    private TextView juso_txt;
    private TextView call_txt;
    private TextView price_txt;
    private TextView is_deposit_txt;
    private TextView password_txt;
    private TextView bangtype_txt;
    private TextView boiler_txt;
    private TextView is_elevator_txt;
    private TextView is_park_txt;
    private TextView empty_txt;
    private TextView option_txt;
    private TextView coments_txt;
    private TextView manage_price_txt;
    private ImageView is_internet_img;
    private ImageView is_sudo_img;
    private ImageView is_yusun_img;
    private ImageView img_empty;

    private ImageButton call_btn;
    private ImageView view_map;
    private ImageButton view_map_btn;
    private ImageButton view_map_roadview;
    private EditText view_edit_memo;
    private boolean is_edit_in = false;

    private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();
    private CustomDialog alert;
    private Context con;
    private DbAdapter dbadapter;

    public BangDataInfo item = new BangDataInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_bang_view);
        View top = (View)findViewById(R.id.top);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            top.setVisibility(View.GONE);
        }

        Intent intent = getIntent();
        mBang_id = intent.getExtras().getString("bang_id");
        item.set_id(mBang_id);

        con = this;
        dbadapter = new DbAdapter(con);

        ImageView logo = (ImageView)findViewById(R.id.backtomain);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BangViewActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        //액션바 메뉴 버튼
        mBtn_menu = (ImageButton) findViewById(R.id.view_menu);
        mBtn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mDialog_con = con;
                if( !MainActivity.quick.is_open ) {
                    MainActivity.quick.is_open = true;
                    MainActivity.quick.show(v);
                }else{
                    MainActivity.quick.is_open = false;
                    MainActivity.quick.dismiss();
                }
            }
        });

        //액션바 매물 검색
        mBtn_search = (ImageButton)findViewById(R.id.view_search);
        mBtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !MainActivity.UserID.equals("") ) {
                    Intent intent = new Intent(BangViewActivity.this, SearchActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(BangViewActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        pager = (ViewPager)findViewById(R.id.pager);
        //pager.setCurrentItem(getArguments().getInt(Constants.Extra.IMAGE_POSITION, 0));

        img_url = new ArrayList<String>();

        building_name_txt = (TextView)findViewById(R.id.view_building_name_text);
        building_hosu_txt = (TextView)findViewById(R.id.view_building_hosu_text);
        dong_txt = (TextView)findViewById(R.id.view_dong_text);
        juso_txt = (TextView)findViewById(R.id.view_juso_text);
        call_txt = (TextView)findViewById(R.id.view_call_text);
        price_txt = (TextView)findViewById(R.id.price_text);
        is_deposit_txt = (TextView)findViewById(R.id.is_deposit_text);
        password_txt = (TextView)findViewById(R.id.password_text);
        bangtype_txt = (TextView)findViewById(R.id.bangtype_text);
        boiler_txt = (TextView)findViewById(R.id.boiler_text);
        is_elevator_txt = (TextView)findViewById(R.id.is_elevator_text);
        is_park_txt = (TextView)findViewById(R.id.is_park_text);
        empty_txt = (TextView)findViewById(R.id.empty_text);
        option_txt = (TextView)findViewById(R.id.option_text);
        coments_txt = (TextView)findViewById(R.id.coments_text);
        manage_price_txt = (TextView)findViewById(R.id.manage_price_text);
        is_sudo_img = (ImageView)findViewById(R.id.is_sudo_img);
        is_yusun_img = (ImageView)findViewById(R.id.is_yusun_img);
        is_internet_img = (ImageView)findViewById(R.id.is_internet_img);
        img_empty = (ImageView)findViewById(R.id.img_empty);

        //전화
        call_btn = (ImageButton)findViewById(R.id.view_call_btn);
        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert = new CustomDialog(con,call,
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                alert.dismiss();
                            }
                        },
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + call));
                                con.startActivity(intent);
                                alert.dismiss();
                            }
                        }
                );
                alert.show();
            }
        });

        view_map = (ImageView)findViewById(R.id.view_map);
        //지도보기
        view_map_btn = (ImageButton)findViewById(R.id.view_map_btn);
        view_map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con,MapActivity.class);
                intent.putExtra("index", 1);
                intent.putExtra("lat",lat);
                intent.putExtra("lng", lng);
                if( lat.equals("") || lng.equals(""))
                    Toast.makeText(con,"위치데이터가 없습니다.",Toast.LENGTH_SHORT).show();
                else
                    startActivity(intent);
            }
        });

        //로드뷰
        view_map_roadview = (ImageButton)findViewById(R.id.view_map_roadview);
        view_map_roadview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con,MapActivity.class);
                intent.putExtra("index",2);
                intent.putExtra("lat",lat);
                intent.putExtra("lng", lng);
                if( lat.equals("") || lng.equals(""))
                    Toast.makeText(con,"위치데이터가 없습니다.",Toast.LENGTH_SHORT).show();
                else
                    startActivity(intent);
            }
        });

        view_edit_memo = (EditText)findViewById(R.id.view_edit_memo);
        //글자 입력시 마다 호출
        view_edit_memo.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String contents = s.toString(); //입력받은 글자 저장

                dbadapter.remove_memo(_id);
                dbadapter.insert_memo(_id, contents);

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}
        });

        if( !isNetworkStat(BangViewActivity.this) ){
            finish();
        }else {
            new getBangTask().execute();
        }
    }

    public void	getBangFromJSON(){
        String url = MainActivity.RESTFUL_URL + "bang_view.php?bang_id=" + mBang_id;
        try{
            String line = MainActivity.getStringFromUrl(url);

            // 원격에서 읽어온 데이터로 JSON 객체 생성
            JSONObject object = new JSONObject(line);
            mResult = object.getString("result");

            JSONObject object_1 = new JSONObject(object.getString("bang"));
                _id = object_1.getString("_id");
                building_name = object_1.getString("building_name");
                building_hosu = object_1.getString("building_hosu");
                si = object_1.getString("si");
                gu = object_1.getString("gu");
                dong = object_1.getString("dong");
                sangse_juso = object_1.getString("sangse_juso");
                deposit = object_1.getString("deposit");
                deposit_possible = object_1.getString("deposit_possible");
                monthly_rental = object_1.getString("monthly_rental");
                base_price = object_1.getString("base_price");
                empty = object_1.getString("empty");
                price_type = object_1.getString("price_type");
                manage_price = object_1.getString("manage_price");
                is_manage_internet = object_1.getString("is_manage_internet");
                is_manage_sudo = object_1.getString("is_manage_sudo");
                is_manage_yusun = object_1.getString("is_manage_yusun");
                bang_type = object_1.getString("bang_type");
                boiler = object_1.getString("boiler");
                call_who = object_1.getString("call_who");
                call = object_1.getString("call");
                lat = object_1.getString("lat");
                lng = object_1.getString("lng");
                is_park = object_1.getString("is_park");
                possible_date = object_1.getString("possible_date");
                building_password = object_1.getString("building_password");
                is_elevator = object_1.getString("is_elevator");
                bang_option = object_1.getString("bang_option");
                comment = object_1.getString("comment");
                list_img_url = object_1.getString("list_img_url");
                JSONArray arrayUrl = new JSONArray(object_1.getString("view_img_url"));
                img_url = new ArrayList<String>();
                for( int j = 0; j< arrayUrl.length(); j++) {
                    JSONObject inarray = arrayUrl.getJSONObject(j);
                    img_url.add( inarray.getString("url") );
                }
            item.set_id(mBang_id);
            if( mResult.equals("0"))
                item.setIs_available("0");
            else
                item.setIs_available("1");
            item.setBuilding_name(building_name);
            item.setBuilding_hosu(building_hosu);
            item.setDong(dong);
            item.setSangse_juso(sangse_juso);
            item.setDeposit(deposit);
            item.setMonthly_rental(monthly_rental);
            item.setManage_price(manage_price);
            item.setBang_type(bang_type);
            item.setPrice_type(price_type);
            item.setEmpty(empty);
            item.setCall(call);
            item.setLat(lat);
            item.setLng(lng);
            item.setImg_url(list_img_url);

            Log.d("json_view", line);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private class getBangTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute(){}
        @Override
        protected Void doInBackground(String... strs) {
            getBangFromJSON();
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (mResult.equals("1")){
                dbadapter.update_recently(item);
                dbadapter.update_favorite(item);

                viewAdapter = new BangViewAdapter(BangViewActivity.this, img_url);

                if (img_url.size() == 0) {
                    pager.setVisibility(View.GONE);
                    img_empty.setVisibility(View.VISIBLE);
                } else {
                    pager.setAdapter(viewAdapter);
                }

                building_name_txt.setText(building_name);
                building_hosu_txt.setText(building_hosu);
                dong_txt.setText(dong);
                juso_txt.setText(sangse_juso);
                call_txt.setText(call_who+" "+call);
                //price_txt.setText();
                if (monthly_rental.equals("0")) {
                    price_txt.setText(deposit);
                } else {
                    price_txt.setText(deposit + "/" + monthly_rental);
                }
                if (deposit_possible.equals("1"))
                    is_deposit_txt.setText("가능");
                else
                    is_deposit_txt.setText("불가능");
                password_txt.setText(building_password);
                bangtype_txt.setText(bang_type);
                boiler_txt.setText(boiler);
                if (is_elevator.equals("1"))
                    is_elevator_txt.setText("있음");
                else
                    is_elevator_txt.setText("없음");
                if (is_park.equals("1"))
                    is_park_txt.setText("가능");
                else
                    is_park_txt.setText("불가능");

                empty_txt.setText(empty + " " + possible_date);
                option_txt.setText(bang_option);
                coments_txt.setText(comment);
                manage_price_txt.setText(manage_price);
                //인터넷
                if (is_manage_internet.equals("1"))
                    is_internet_img.setImageResource(R.mipmap.view_check_on);
                else
                    is_internet_img.setImageResource(R.mipmap.view_check_off);
                //수도
                if (is_manage_sudo.equals("1"))
                    is_sudo_img.setImageResource(R.mipmap.view_check_on);
                else
                    is_sudo_img.setImageResource(R.mipmap.view_check_off);

                //유선
                if (is_manage_yusun.equals("1"))
                    is_yusun_img.setImageResource(R.mipmap.view_check_on);
                else
                    is_yusun_img.setImageResource(R.mipmap.view_check_off);

                if (!lat.equals("") && !lng.equals("")) {
                    String url = "http://maps.googleapis.com/maps/api/staticmap?"
                            + "center=" + lat + "," + lng + "&markers=color:blue%7Clabel:" + sangse_juso + "%7C" + lat + "," + lng + ""
                            + "&zoom=17&size=500x450&markers=color:blue&maptype=roadmap&sensor=false";
                    ImageLoader.getInstance().displayImage(url, view_map, MainActivity.options, animateFirstListener);
                }

                int querycount;
                Cursor mCount = MainActivity.mdb.rawQuery("SELECT count(*) from memo WHERE bang_id=" + _id, null);
                mCount.moveToFirst();
                querycount = mCount.getInt(0);
                if (querycount > 0) {
                    Cursor qresult = MainActivity.mdb.rawQuery("SELECT * from memo WHERE bang_id=" + _id, null);
                    qresult.moveToFirst();
                    String memo = qresult.getString(2);
                    view_edit_memo.setText(memo);
                }
            }else if(mResult.equals("2")){
                Toast.makeText(con,"이미 삭제된 매물입니다.",Toast.LENGTH_SHORT).show();
                dbadapter.remove_recently_find(item);
                dbadapter.remove_favorite(item);
                Log.d("db t","m");
                finish();
            }else{
                Toast.makeText(con,"이용할 수 없습니다",Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        MainActivity.quick.is_open = false;
        MainActivity.quick.dismiss();
    }

    public boolean isNetworkStat( Context context ) {
        ConnectivityManager manager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo lte_4g = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
        boolean blte_4g = false;
        if(lte_4g != null)
            blte_4g = lte_4g.isConnected();
        if( mobile != null ) {
            if (mobile.isConnected() || wifi.isConnected() || blte_4g)
                return true;
        } else {
            if ( wifi.isConnected() || blte_4g )
                return true;
        }

        //Intent intent = new Intent(context, NetworkAlram.class);
        //context.startActivity(intent);
        //Toast.makeText(context, "인터넷 연결을 확인해주세요", Toast.LENGTH_LONG).show();
        mWeatherRunnableHandler.sendEmptyMessage(0);

        return false;
    }
    public Handler mWeatherRunnableHandler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==0){
                Toast.makeText(BangViewActivity.this, "인터넷 연결을 확인해주세요", Toast.LENGTH_LONG).show();
            }
        }
    };
}
