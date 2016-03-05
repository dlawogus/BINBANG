package kr.app.binbang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import kr.app.adapter.BangMainSearchApt;
import kr.app.datainfo.BangDataInfo;
import kr.app.dbhandle.DbAdapter;


public class MainSearchActivity extends FontActivity {
    private ProgressDialog pd;
    private Context con;
    private ImageButton mBack;
    private ListView mListView;
    private View footer;
    private View header;
    private Button mBtn_more;
    private LinearLayout lay_empty_txt;
    public static BangMainSearchApt mListAdapter;
    private ArrayList<BangDataInfo> mBang_array;
    private String mGu = "";
    private String mDong = "";
    private String mType = "";
    private String mPricetype = "";
    private String mDeposit_start = "";
    private String mDeposit_end = "";
    private String mRental_start = "";
    private String mRental_end = "";
    private String mBase_start = "";
    private String mBase_end = "";
    private String option1 = "";
    private String option2 = "";
    private int page = 1;
    private DbAdapter dbadapter;
    private ImageButton order_gunmul;
    private ImageButton order_price;
    private ImageButton order_juso;
    private String orderby = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_search_main);
        View top = (View)findViewById(R.id.top_m);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            top.setVisibility(View.GONE);
        }

        con = this;
        ImageView logo = (ImageView)findViewById(R.id.backtomain);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainSearchActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        dbadapter = new DbAdapter(this);

        footer = this.getLayoutInflater().inflate(R.layout.search_footer, null, false);
        header = this.getLayoutInflater().inflate(R.layout.search_header, null, false);
        mBang_array = new ArrayList<BangDataInfo>();
        mListView = (ListView)findViewById(R.id.search_main_listView);
        mListAdapter = new BangMainSearchApt(MainSearchActivity.this, mBang_array);
        mListView.addFooterView(footer, null, false);
        mListView.addHeaderView(header, null, false);
        mListView.setAdapter(mListAdapter);
        mListView.setVisibility(View.GONE);

        mBtn_more = (Button) footer.findViewById(R.id.btn_more);
        mBtn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                new BangListTask().execute();
            }
        });


        Intent intent = getIntent();
        mGu = intent.getExtras().getString("gu");
        mDong = intent.getExtras().getString("dong");
        mType = intent.getExtras().getString("type");
        mPricetype = intent.getExtras().getString("pricetype");
        mDeposit_start = intent.getExtras().getString("deposit_start");
        mDeposit_end = intent.getExtras().getString("deposit_end");
        mRental_start = intent.getExtras().getString("rental_start");
        mRental_end = intent.getExtras().getString("rental_end");
        mBase_start = intent.getExtras().getString("base_start");
        mBase_end = intent.getExtras().getString("base_end");
        option1 = intent.getExtras().getString("option1");
        option2 = intent.getExtras().getString("option2");

        //Log.d("json_search", line);
        mBack = (ImageButton)findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        order_gunmul = (ImageButton) header.findViewById(R.id.order_gunmul);
        order_price = (ImageButton) header.findViewById(R.id.order_price);
        order_juso = (ImageButton) header.findViewById(R.id.order_juso);
        order_gunmul.setImageResource(R.mipmap.od_gunmul_on);
        order_price.setImageResource(R.mipmap.od_price_off);
        order_juso.setImageResource(R.mipmap.od_juso_off);

        order_gunmul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Collections.sort(mBang_array, MainFragment_1.compareGunmul);
                //mListAdapter.notifyDataSetChanged();
                if( !orderby.equals("1") ){
                    mBang_array.clear();
                    page = 1;
                    new BangListTask().execute();
                }
                orderby = "1";
                order_gunmul.setImageResource(R.mipmap.od_gunmul_on);
                order_price.setImageResource(R.mipmap.od_price_off);
                order_juso.setImageResource(R.mipmap.od_juso_off);
            }
        });
        order_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Collections.sort(mBang_array, MainFragment_1.comparePrice);
                //mListAdapter.notifyDataSetChanged();
                if( !orderby.equals("2") ){
                    mBang_array.clear();
                    page = 1;
                    new BangListTask().execute();
                }
                orderby = "2";
                order_gunmul.setImageResource(R.mipmap.od_gunmul_off);
                order_price.setImageResource(R.mipmap.od_price_on);
                order_juso.setImageResource(R.mipmap.od_juso_off);
            }
        });
        order_juso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Collections.sort(mBang_array, MainFragment_1.compareJuso);
                //mListAdapter.notifyDataSetChanged();
                if( !orderby.equals("3") ){
                    mBang_array.clear();
                    page = 1;
                    new BangListTask().execute();
                }
                orderby = "3";
                order_gunmul.setImageResource(R.mipmap.od_gunmul_off);
                order_price.setImageResource(R.mipmap.od_price_off);
                order_juso.setImageResource(R.mipmap.od_juso_on);
            }
        });

        //아이템 클릭 리스너
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int pos = position - 1;
                if (!MainActivity.UserID.equals("")) {
                    int count = 0;
                    for (int i = 0; i < MainFragment_1.mBang_array_recently.size(); i++) {
                        if (mBang_array.get(pos).get_id().equals(MainFragment_1.mBang_array_recently.get(i).get_id())) {
                            count++;
                            break;
                        }
                    }
                    if (count == 0) {
                        dbadapter.insert_recently_find(mBang_array.get(pos));
                        MainFragment_1.mBang_array_recently.add(0, mBang_array.get(pos));
                        MainFragment_1.mListAdapter_recently.notifyDataSetChanged();
                    } else {
                        for (int j = 0; j < MainFragment_1.mBang_array_recently.size(); j++) {
                            if (MainFragment_1.mBang_array_recently.get(j).get_id().equals(mBang_array.get(pos).get_id())) {
                                MainFragment_1.mBang_array_recently.remove(j);
                                MainFragment_1.mBang_array_recently.add(0, mBang_array.get(pos));
                                MainFragment_1.mListAdapter_recently.notifyDataSetChanged();
                                dbadapter.remove_recently_find(mBang_array.get(pos));
                                dbadapter.insert_recently_find(mBang_array.get(pos));
                                break;
                            }
                        }
                    }
                    Intent intent = new Intent(con, BangViewActivity.class);
                    intent.putExtra("bang_id", mBang_array.get(pos).get_id());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(con, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


        //쓰레드 동작
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new BangListTask().execute();
            }
        }, 200);
    }

    public InputStream getInputStreamFromUrl(String url) {
        InputStream contentStream = null;
        // 실제 전송하는 부분
        ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
        if( !mGu.equals("") )
            post.add(new BasicNameValuePair("gu", mGu));
        if( !mDong.equals("") )
            post.add(new BasicNameValuePair("dong", mDong));
        if( !mType.equals("") )
            post.add(new BasicNameValuePair("bang_type", mType));
        if( !mPricetype.equals("") )
            post.add(new BasicNameValuePair("price_type", mPricetype));
        if( !mDeposit_start.equals("") )
            post.add(new BasicNameValuePair("deposit_start", mDeposit_start));
        if( !mDeposit_end.equals("") )
            post.add(new BasicNameValuePair("deposit_end", mDeposit_end));
        if( !mRental_start.equals("") )
            post.add(new BasicNameValuePair("monthly_rental_start", mRental_start));
        if( !mRental_end.equals("") )
            post.add(new BasicNameValuePair("monthly_rental_end", mRental_end));
        if( !mBase_start.equals("") )
            post.add(new BasicNameValuePair("base_price_start", mBase_start));
        if( !mBase_end.equals("") )
            post.add(new BasicNameValuePair("base_price_end", mBase_end));
        if( !option1.equals("") )
            post.add(new BasicNameValuePair("bang_type_1", option1));
        if( !option2.equals("") )
            post.add(new BasicNameValuePair("is_elevator", option2));
        if( !orderby.equals("") )
            post.add(new BasicNameValuePair("orderby", orderby));

        post.add(new BasicNameValuePair("page", Integer.toString(page)) );

        HttpParams params = MainActivity.httpclient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 3000);
        HttpConnectionParams.setSoTimeout(params, 3000);

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
    public String getStringFromUrl(String url) throws UnsupportedEncodingException {
        BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url), "UTF-8"));
        StringBuffer sb = new StringBuffer();
        try {
            String line = null;
            while ((line = br.readLine()) != null) { sb.append(line); }
        } catch (Exception e) { e.printStackTrace(); }
        return sb.toString();
    }
    public void	getBangFromJSON(){
        String url = "http://dlawogus1.cafe24.com/app/bang_list.php";
        try{
            String line = getStringFromUrl(url);
            // 원격에서 읽어온 데이터로 JSON 객체 생성
            JSONObject object = new JSONObject(line);
            JSONArray objectArray = new JSONArray(object.getString("bang"));
            for( int i = 0; i< objectArray.length(); i++) {
                JSONObject insideObject = objectArray.getJSONObject(i);
                BangDataInfo datainfo = new BangDataInfo();
                String bang_id = insideObject.getString("_id");
                datainfo.set_id(bang_id);
                datainfo.setIs_available(insideObject.getString("is_available"));
                datainfo.setBuilding_name(insideObject.getString("building_name"));
                datainfo.setBuilding_hosu(insideObject.getString("building_hosu"));
                datainfo.setDong(insideObject.getString("dong"));
                datainfo.setSangse_juso(insideObject.getString("sangse_juso"));
                datainfo.setDeposit(insideObject.getString("deposit"));
                datainfo.setMonthly_rental(insideObject.getString("monthly_rental"));
                datainfo.setEmpty(insideObject.getString("empty"));
                datainfo.setPrice_type(insideObject.getString("price_type"));
                datainfo.setManage_price(insideObject.getString("manage_price"));
                datainfo.setBang_type(insideObject.getString("bang_type"));
                datainfo.setCall(insideObject.getString("call"));
                datainfo.setLat(insideObject.getString("lat"));
                datainfo.setLng(insideObject.getString("lng"));
                datainfo.setImg_url(insideObject.getString("img_url"));
                int cnt;
                Cursor qCount = MainActivity.mdb.rawQuery("SELECT count(*) from favorite WHERE bang_id=" + bang_id, null);
                qCount.moveToFirst();
                cnt = qCount.getInt(0);
                if (cnt == 1) datainfo.setIs_favorite("1");
                else datainfo.setIs_favorite("0");
                cnt = 0;
                for (int j = 0; j < mBang_array.size(); j++) {
                    if (mBang_array.get(j).equals(datainfo)) {
                        cnt++;
                        break;
                    }
                }
                if (cnt == 0) mBang_array.add(datainfo);
            }
            Log.d("json_search", line);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    private class BangListTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute(){
            pd = new ProgressDialog(con);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            pd.setContentView(R.layout.custom_progress);
            Log.d("json_search", "test");
        }
        @Override
        protected Void doInBackground(String... strs) {
            getBangFromJSON();
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.

        @Override
        protected void onPostExecute(Void result) {
            //super.onPostExecute(result);

            pd.dismiss();

            mBtn_more = (Button) footer.findViewById(R.id.btn_more);
            lay_empty_txt = (LinearLayout) findViewById(R.id.lay_empty_txt);
            if( mBang_array.size() == 0){
                mListView.setVisibility(View.GONE);
                lay_empty_txt.setVisibility(View.VISIBLE);
                Toast.makeText(con, "검색결과가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
            }else if( mBang_array.size() < 10) {
                mListView.setVisibility(View.VISIBLE);
                mBtn_more.setVisibility(View.GONE);
                lay_empty_txt.setVisibility(View.GONE);
            }else {
                mListView.setVisibility(View.VISIBLE);
                mBtn_more.setVisibility(View.VISIBLE);
                lay_empty_txt.setVisibility(View.GONE);
            }
            mListAdapter.notifyDataSetChanged();


        }
    }


}
