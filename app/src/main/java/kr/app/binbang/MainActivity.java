package kr.app.binbang;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import kr.app.adapter.AdPagerAdapter;
import kr.app.customalert.CustomDialog;
import kr.app.datainfo.AdDataInfo;
import kr.app.datainfo.NoticeDataInfo;
import kr.app.dbhandle.DbHelper;
import kr.app.mywindow.ActionItem;
import kr.app.mywindow.QuickAction;


public class MainActivity extends FontActivity {
    private final String ROOT_DIR = "/data/data/kr.app.binbang/databases/";
    public static final String RESTFUL_URL = "http://dlawogus1.cafe24.com/app/";
    public static SQLiteDatabase mdb;
    public static DbHelper mDbHelper;

    public final int FRAGMENT_ONE = 1;
    public final int FRAGMENT_TWO = 2;
    public static boolean Fragment_two_exe = false;
    public int mIndex = FRAGMENT_ONE;

    //로그인 관련
    public static boolean appFirstStart;
    public static String UserID;
    public static String UserPW;
    public static String token;
    private String mLoginResult = "";
    private String mLogoutResult;

    private ImageButton mBtn_menu;
    private ImageButton mBtn_search;
    private ImageButton mTab_1_img;
    private ImageButton mTab_2_img;
    private LinearLayout mTab_1;
    private LinearLayout mTab_2;
    public static QuickAction quick;
    private boolean mIs_Menu = false;

    public static FragmentManager fragmentManager;
    public static DisplayImageOptions options;
    public static HttpClient httpclient;
    private long backKeyPressedTime = 0;
    private Toast toast;

    //광고 관련
    private ViewPager pager;
    private Thread thread = null;
    private Handler handler = null;
    private int p=0;	//페이지번호
    private int v=0;	//화면 전환 뱡향
    private ArrayList<AdDataInfo> mAd_array;
    private AdPagerAdapter adPagerAdapter;

    //공지사항
    public static ArrayList<NoticeDataInfo> mNotice_array = new ArrayList<NoticeDataInfo>();
    public static boolean is_notice_set = false;

    private CustomDialog alert;
    public static Context mDialog_con;
    public static String mActivityName;

    //폰트
    public static Typeface mTypeface;


    @Override
    public void finish(){
        super.finish();
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_main);
        View top = (View)findViewById(R.id.top_m);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            top.setVisibility(View.GONE);
        }
        getPreferences();

        //디비오픈
        mDbHelper = new DbHelper(this);
        mdb = mDbHelper.getWritableDatabase();

        TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        token = telManager.getLine1Number();

        mTypeface = Typeface.createFromAsset(getAssets(), "font.ttf.mp3");
        httpclient  = new DefaultHttpClient();
        //setDB();
        mDialog_con = this;
        mActivityName = "Main";

        //test
        //token = "test";

        //도움말 불러오기
/*        if( appFirstStart == true ){
            //Intent intent = new Intent(this, HelpFirst.class);
            //startActivity(intent);
            appFirstStart = false;
            savePreferences();
        }*/

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(2 * 1024 * 1024) // 2 Mb
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(config);

        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.list_default)          //Error image
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(0))
                .build();

        mAd_array = new ArrayList<AdDataInfo>();

        pager = (ViewPager)findViewById(R.id.pager);
        handler = new Handler(){
            public void handleMessage(android.os.Message msg) {
                if(p==0 && v==0){
                    pager.setCurrentItem(1);
                    p++;
                }else if(p==1 && v==0){
                    pager.setCurrentItem(2);
                    p++;
                }else if(p==2 && v==0) {
                    pager.setCurrentItem(3);
                    p++;
                }else if(p==3 && v==0) {
                    pager.setCurrentItem(4);
                    p++;
                }else if(p==4){
                    pager.setCurrentItem(3);
                    p--;
                    v=1;
                }else if(p==3 && v==1){
                    pager.setCurrentItem(2);
                    p--;
                }else if(p==2 && v==1){
                    pager.setCurrentItem(1);
                    p--;
                }else if(p==1 && v==1){
                    pager.setCurrentItem(0);
                    p = 0;
                    v = 0;
                }
            }
        };
        thread = new Thread(){
            //run은 jvm이 쓰레드를 채택하면, 해당 쓰레드의 run메서드를 수행한다.
            public void run() {
                super.run();
                while(true){
                    try {
                        Thread.sleep(3000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

        ActionItem itm1 = new ActionItem(1, "로그인", null);
        //ActionItem itm2 = new ActionItem(2, "서비스신청", null);
        ActionItem itm3 = new ActionItem(2, "고객센터", null);
        ActionItem itm4 = new ActionItem(3, "중계수수료 계산기", null);
        quick = new QuickAction(this, QuickAction.VERTICAL);
        quick.addActionItem(itm1);
        //quick.addActionItem(itm2);
        quick.addActionItem(itm3);
        quick.addActionItem(itm4);
        quick.setOnActionItemClickListener(quick_Clicked);

        if( !UserID.equals("") ){
            quick.changActionItem("로그아웃",0);
        }

        //액션바 메뉴 버튼
        mBtn_menu = (ImageButton) findViewById(R.id.main_menu);
        mBtn_menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !quick.is_open ) {
                    quick.is_open = true;
                    Log.d("dddd", Boolean.toString(quick.is_open));
                    quick.show(v);
                }else{
                    quick.is_open = false;
                    Log.d("dddd", Boolean.toString(quick.is_open));
                    quick.dismiss();
                }
            }
        });

        //액션바 매물 검색
        mBtn_search = (ImageButton)findViewById(R.id.main_search);
        mBtn_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !UserID.equals("") ) {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        //탭 버튼
        mTab_1_img = (ImageButton)findViewById(R.id.main_tab_1_img);
        mTab_2_img = (ImageButton)findViewById(R.id.main_tab_2_img);
        mTab_1 = (LinearLayout)findViewById(R.id.main_tab_1);
        mTab_2 = (LinearLayout)findViewById(R.id.main_tab_2);
        mTab_1_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(FRAGMENT_ONE);
                mIndex = FRAGMENT_ONE;
                mTab_1_img.setImageResource(R.mipmap.tab1_on);
                mTab_2_img.setImageResource(R.mipmap.tab2_off);
            }
        });
        mTab_2_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(FRAGMENT_TWO);
                mIndex = FRAGMENT_TWO;
                mTab_1_img.setImageResource(R.mipmap.tab1_off);
                mTab_2_img.setImageResource(R.mipmap.tab2_on);
            }
        });
        mTab_1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(FRAGMENT_ONE);
                mIndex = FRAGMENT_ONE;
                mTab_1_img.setImageResource(R.mipmap.tab1_on);
                mTab_2_img.setImageResource(R.mipmap.tab2_off);
            }
        });
        mTab_2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(FRAGMENT_TWO);
                mIndex = FRAGMENT_TWO;
                mTab_1_img.setImageResource(R.mipmap.tab1_off);
                mTab_2_img.setImageResource(R.mipmap.tab2_on);
            }
        });

        selectItem(FRAGMENT_ONE);
        if( !UserID.equals("")  ){
            new AutoLoginTask().execute();
        }
        new getInfoTask().execute();

    }

    private QuickAction.OnActionItemClickListener quick_Clicked = new QuickAction.OnActionItemClickListener() {
        @Override
        public void onItemClick(QuickAction source, int pos, int actionId) {
            quick.is_open = false;
            Log.d("dddd", Boolean.toString(quick.is_open));

            if (actionId == 1) {            //로그인
                if( UserID.equals("") ){
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    new LogoutTask().execute();
                }
            }/*else if(actionId == 2 ) {       //서비스 신청
                if( !mActivityName.equals("ServiceReq")){
                    if (UserID.equals("")) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, ServiceReq.class);
                        startActivity(intent);
                    }
                }
            }*/else if(actionId == 2){        //고객센터
                alert = new CustomDialog(mDialog_con,"051-611-1594",
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                alert.dismiss();
                            }
                        },
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: 051-611-1594"));
                                startActivity(intent);
                                alert.dismiss();
                            }
                        }
                );
                alert.show();
            }else{                          //중계수수료 계산기
                if( !mActivityName.equals("Calculator") ){
                    if( UserID.equals("") ){
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(MainActivity.this,Calculator.class);
                        startActivity(intent);
                    }
                }
            }
        }
    };

    @Override
    public void onResume(){
        super.onResume();
/*        if( mIndex == FRAGMENT_ONE)
            selectItem(FRAGMENT_ONE);
        else
            selectItem(FRAGMENT_TWO);*/
        if( mIndex == FRAGMENT_TWO)
            selectItem(FRAGMENT_TWO);

        mDialog_con = this;
        mActivityName = "Main";

        if( UserID.equals("") )
            quick.changActionItem("로그인",0);
        else
            quick.changActionItem("로그아웃",0);
    }

    public void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment=null;

        if( position == FRAGMENT_ONE ) {
            fragment = new MainFragment_1();
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_fragment_place, fragment).commit();
        }else if( position == FRAGMENT_TWO ){
            fragment = new MainFragment_2();
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_fragment_place, fragment).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause(){
        super.onPause();
        quick.is_open = false;
        quick.dismiss();
    }

    // 값 저장하기
    private void savePreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("appFirstStart", appFirstStart);
        editor.putString("user_id", UserID);
        editor.putString("user_pw", UserPW);
        editor.commit();
    }
    // 값 불러오기
    private void getPreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        appFirstStart = pref.getBoolean("appFirstStart",true);
        UserID = pref.getString("user_id", "");
        UserPW = pref.getString("user_pw", "");
        token = pref.getString("token", "");
    }
    // 디비 불러오기
    public void setDB() {
        File folder = new File(ROOT_DIR);
        if(folder.exists()) {
        }
        else {
            folder.mkdirs();
            //Toast.makeText(this, "폴더생성", Toast.LENGTH_LONG).show();
        }
        AssetManager assetManager = getResources().getAssets();
        File outfile = new File(ROOT_DIR+"binbangDB.db"); //--폰에 위치할 경로
        InputStream is = null;
        FileOutputStream fo = null;
        long filesize = 0;
        try {
            // --asset 폴더 및 복사할 DB 지정
            is = assetManager.open("binbangDB.db", AssetManager.ACCESS_BUFFER);
            filesize = is.available(); //--사이즈 검증

            // 파일이 없거나 패키지 폴더에 설치된 DB파일이 포함된 DB파일 보다 크기가 같지않을 경우 DB파일을 덮어 쓴다.
            if (outfile.length() <= 0) {
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            }else{
                //디버깅용//////////////////
                Toast.makeText(this, "db있음", Toast.LENGTH_SHORT).show();
                //db.execSQL("DROP TABLE IF EXISTS"+DB_TABLE1 );
                //outfile.delete();
                //setDB();
            }
        }catch (IOException e) {
            Toast.makeText(this, "db이동실패", Toast.LENGTH_LONG).show();
        }
    }
    //이미지 로더
    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    public static boolean isNetworkStat( Context context ) {
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
        Toast.makeText(context, "인터넷 연결을 확인해주세요", Toast.LENGTH_LONG).show();

        return false;
    }
    public static InputStream getInputStreamFromUrl(String url) {
        InputStream contentStream = null;
        try {
            HttpParams params = httpclient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);
            HttpResponse response = httpclient.execute(new HttpGet(url));
            contentStream = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentStream;
    } // getInputStreamFromUrl
    public static String getStringFromUrl(String url) throws UnsupportedEncodingException {
        // 입력스트림을 "UTF-8" 를 사용해서 읽은 후, 라인 단위로 데이터를 읽을 수 있는 BufferedReader 를 생성한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url), "UTF-8"));
        // 읽은 데이터를 저장한 StringBuffer 를 생성한다.
        StringBuffer sb = new StringBuffer();
        try {
            // 라인 단위로 읽은 데이터를 임시 저장한 문자열 변수 line
            String line = null;
            // 라인 단위로 데이터를 읽어서 StringBuffer 에 저장한다.
            while ((line = br.readLine()) != null) { sb.append(line); }
        } catch (Exception e) { e.printStackTrace(); }
        return sb.toString();
    }
    public void	getAdFromJSON(){
        String url = "http://dlawogus1.cafe24.com/app/ad_list.php";
        try{
            String line = getStringFromUrl(url);

            // 원격에서 읽어온 데이터로 JSON 객체 생성
            JSONObject object = new JSONObject(line);
            JSONArray objectArray = new JSONArray(object.getString("ad"));
            for( int i = 0; i< objectArray.length(); i++){
                JSONObject insideObject = objectArray.getJSONObject(i);
                AdDataInfo datainfo = new AdDataInfo();
                datainfo.set_id(insideObject.getString("_id"));
                datainfo.setUrl(insideObject.getString("url"));
                datainfo.setCall(insideObject.getString("call"));
                datainfo.setDate(insideObject.getString("date"));
                mAd_array.add(datainfo);
            }
            Log.d("json_ad", line);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void	getNoticeFromJSON(){
        String url = "http://dlawogus1.cafe24.com/app/notice_list.php";
        try{
            String line = getStringFromUrl(url);

            // 원격에서 읽어온 데이터로 JSON 객체 생성
            JSONObject object = new JSONObject(line);
            JSONArray objectArray = new JSONArray(object.getString("notice"));
            for( int i = 0; i< objectArray.length(); i++){
                JSONObject insideObject = objectArray.getJSONObject(i);
                NoticeDataInfo datainfo = new NoticeDataInfo();
                datainfo.set_id(insideObject.getString("_id"));
                datainfo.setUrl(insideObject.getString("url"));
                datainfo.setTitle(insideObject.getString("title"));
                datainfo.setDate(insideObject.getString("date"));
                mNotice_array.add(datainfo);
            }
            Log.d("json_notice", line);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    private class getInfoTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute(){}
        @Override
        protected Void doInBackground(String... strs) {
            getAdFromJSON();
            getNoticeFromJSON();
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adPagerAdapter = new AdPagerAdapter(MainActivity.this, mAd_array);
            pager.setAdapter(adPagerAdapter);
            if( mNotice_array.size() > 0) {
                MainFragment_1.mNotice1.setText(mNotice_array.get(0).getTitle());
                MainFragment_1.mNotice2.setText(mNotice_array.get(1).getTitle());
                MainFragment_1.mNotice3.setText(mNotice_array.get(2).getTitle());
                MainFragment_1.mNotice4.setText(mNotice_array.get(3).getTitle());
                MainFragment_1.mNotice5.setText(mNotice_array.get(4).getTitle());
            }
            is_notice_set = true;
        }
    }
    public InputStream getPostInputStreamFromUrl(String url) {
        InputStream contentStream = null;
        // 실제 전송하는 부분
        ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
        post.add(new BasicNameValuePair("user_id", UserID));
        post.add(new BasicNameValuePair("user_pw", UserPW));
        post.add(new BasicNameValuePair("token", token));
        HttpParams params = MainActivity.httpclient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 5000);

        // Post객체 생성
        HttpPost httpPost = new HttpPost(url);
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse response = MainActivity.httpclient.execute(httpPost);
            contentStream = response.getEntity().getContent();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentStream;
    }
    public String getPostStringFromUrl(String url) throws UnsupportedEncodingException {
        BufferedReader br = new BufferedReader(new InputStreamReader(getPostInputStreamFromUrl(url), "UTF-8"));
        StringBuffer sb = new StringBuffer();
        try {
            String line = null;
            while ((line = br.readLine()) != null) { sb.append(line); }
        } catch (Exception e) { e.printStackTrace(); }
        return sb.toString();
    }
    public void	getLoginFromJSON(){
        String url = "http://dlawogus1.cafe24.com/app/login_check.php";
        try{
            String line = getPostStringFromUrl(url);
            // 원격에서 읽어온 데이터로 JSON 객체 생성
            Log.d("json_login", line);
            JSONObject object = new JSONObject(line);
            mLoginResult = object.getString("result");
            Log.d("json_login", line);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    private class AutoLoginTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute(){}
        @Override
        protected Void doInBackground(String... strs) {
            getLoginFromJSON();
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if( mLoginResult.equals("1") ){//성공
                quick.changActionItem("로그아웃",0);
                Toast.makeText(MainActivity.this,"로그인되었습니다",Toast.LENGTH_SHORT).show();
            }else{
                quick.changActionItem("로그인",0);
            }
            savePreferences();
        }
    }
    public void	getLogoutFromJSON(){
        String url = "http://dlawogus1.cafe24.com/app/logout.php";
        try{
            String line = getStringFromUrl(url);
            // 원격에서 읽어온 데이터로 JSON 객체 생성
            Log.d("json_logout", line);
            JSONObject object = new JSONObject(line);
            mLogoutResult = object.getString("result");
            Log.d("json_logout", line);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    private class LogoutTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute(){}
        @Override
        protected Void doInBackground(String... strs) {
            getLogoutFromJSON();
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if( mLogoutResult.equals("1") ){//성공
                UserID = "";
                UserPW = "";
                quick.changActionItem("로그인",0);
                savePreferences();
                Toast.makeText(MainActivity.this,"로그아웃 하였습니다.",Toast.LENGTH_SHORT).show();
            }else if( mLogoutResult.equals("2")) {

            }else{                         //존재하지 않는 아이디

            }
        }
    }
    //뒤로가기 버튼
    public void onBackPressed() {
        if( MainActivity.quick.is_open ){
            MainActivity.quick.is_open = false;
            MainActivity.quick.dismiss();
        }else{
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                toast = Toast.makeText(this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                //앱 완전 종료
                if( appFirstStart == true ){
                    appFirstStart = false;
                }
                savePreferences();
                android.os.Process.killProcess(android.os.Process.myPid());
                toast.cancel();
            }
        }
    }

}
