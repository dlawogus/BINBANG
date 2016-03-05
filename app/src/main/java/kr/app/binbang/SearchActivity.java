package kr.app.binbang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.app.adapter.BangSearchApt;
import kr.app.datainfo.BangDataInfo;
import kr.app.dbhandle.DbAdapter;


public class SearchActivity extends FontActivity {
    private ProgressDialog pd;
    private Context con;
    private ImageButton mBtn_menu;
    private EditText mEdit_search;
    private Button mBtn_ok;
    private Button mBtn_more;
    private ListView searchlist;
    private ArrayList<BangDataInfo> mArray;
    public static BangSearchApt mAdapter;
    private int page = 1;
    private ImageButton order_gunmul;
    private ImageButton order_price;
    private ImageButton order_juso;
    private LinearLayout lay_empty_txt;
    private View header;
    private View footer;
    private DbAdapter dbadapter;
    private String orderby = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_search);
        View top = (View)findViewById(R.id.top_m);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            top.setVisibility(View.GONE);
        }
        con = this;
        //액션바 메뉴 버튼
        ImageView logo = (ImageView)findViewById(R.id.backtomain);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mBtn_menu = (ImageButton) findViewById(R.id.main_menu);
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

        mEdit_search = (EditText)findViewById(R.id.search_edit);

        mBtn_ok = (Button)findViewById(R.id.search_ok);
        mBtn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArray.clear();
                page = 1;
                new BangListTask().execute();
            }
        });

        mArray = new ArrayList<BangDataInfo>();
        searchlist = (ListView) findViewById(R.id.search_listview);
        mAdapter = new BangSearchApt(SearchActivity.this, mArray );

        lay_empty_txt = (LinearLayout) findViewById(R.id.lay_empty_txt);

        dbadapter = new DbAdapter(this);

        searchlist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int pos = position - 1;
                if (!MainActivity.UserID.equals("")) {
                    int count = 0;
                    for (int i = 0; i < MainFragment_1.mBang_array_recently.size(); i++) {
                        if (mArray.get(pos).get_id().equals(MainFragment_1.mBang_array_recently.get(i).get_id())) {
                            count++;
                            break;
                        }
                    }
                    if (count == 0) {
                        dbadapter.insert_recently_find(mArray.get(pos));
                        MainFragment_1.mBang_array_recently.add(0, mArray.get(pos));
                        MainFragment_1.mListAdapter_recently.notifyDataSetChanged();
                    } else {
                        for (int j = 0; j < MainFragment_1.mBang_array_recently.size(); j++) {
                            if (MainFragment_1.mBang_array_recently.get(j).get_id().equals(mArray.get(pos).get_id())) {
                                MainFragment_1.mBang_array_recently.remove(j);
                                MainFragment_1.mBang_array_recently.add(0, mArray.get(pos));
                                MainFragment_1.mListAdapter_recently.notifyDataSetChanged();
                                dbadapter.remove_recently_find(mArray.get(pos));
                                dbadapter.insert_recently_find(mArray.get(pos));
                                break;
                            }
                        }
                    }
                    Intent intent = new Intent(con, BangViewActivity.class);
                    intent.putExtra("bang_id", mArray.get(pos).get_id());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(con, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        footer = this.getLayoutInflater().inflate(R.layout.search_footer, null, false);
        header = this.getLayoutInflater().inflate(R.layout.search_header, null, false);
        searchlist.addFooterView(footer, null, false);
        searchlist.addHeaderView(header, null, false);
        searchlist.setAdapter(mAdapter);

        order_gunmul = (ImageButton) header.findViewById(R.id.order_gunmul);
        order_price = (ImageButton) header.findViewById(R.id.order_price);
        order_juso = (ImageButton) header.findViewById(R.id.order_juso);
        order_gunmul.setImageResource(R.mipmap.od_gunmul_on);
        order_price.setImageResource(R.mipmap.od_price_off);
        order_juso.setImageResource(R.mipmap.od_juso_off);

        order_gunmul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Collections.sort(mArray, MainFragment_1.compareGunmul);
                //mAdapter.notifyDataSetChanged();
                if( !orderby.equals("1") ){
                    mArray.clear();
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
                //Collections.sort(mArray, MainFragment_1.comparePrice);
                //mAdapter.notifyDataSetChanged();
                if( !orderby.equals("2") ){
                    mArray.clear();
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
                //Collections.sort(mArray, MainFragment_1.compareJuso);
                //mAdapter.notifyDataSetChanged();
                if( !orderby.equals("3") ){
                    mArray.clear();
                    page = 1;
                    new BangListTask().execute();
                }
                orderby = "3";
                order_gunmul.setImageResource(R.mipmap.od_gunmul_off);
                order_price.setImageResource(R.mipmap.od_price_off);
                order_juso.setImageResource(R.mipmap.od_juso_on);
            }
        });

        footer.setVisibility(View.INVISIBLE);
        mBtn_more = (Button)findViewById(R.id.btn_more);
        mBtn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                new BangListTask().execute();
            }
        });
    }

    public void	getBangInfoFromJSON(){
        String url = "http://dlawogus1.cafe24.com/app/search_bang_list.php?query="+mEdit_search.getText().toString()+"&page="+Integer.toString(page)+"&orderby="+orderby;
        try{
            String line = MainActivity.getStringFromUrl(url);
            Log.d("json받은값", line);
            // 원격에서 읽어온 데이터로 JSON 객체 생성
            JSONObject object = new JSONObject(line);
            JSONArray objectArray = new JSONArray(object.getString("bang"));
            for( int i = 0; i< objectArray.length(); i++){
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
                Cursor qCount = MainActivity.mdb.rawQuery("SELECT count(*) from favorite WHERE bang_id="+bang_id , null);
                qCount.moveToFirst();
                cnt = qCount.getInt(0);
                if( cnt == 1 ) datainfo.setIs_favorite("1");
                else datainfo.setIs_favorite("0");

                cnt = 0;
                for(int j=0; j<mArray.size(); j++) {
                    if ( mArray.get(j).equals(datainfo) ){
                        cnt++;
                        break;
                    }
                }
                if( cnt == 0 ) mArray.add(datainfo);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public class BangListTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute(){
            pd = new ProgressDialog(con);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            pd.setContentView(R.layout.custom_progress);
        }
        @Override
        protected Void doInBackground(String... strs) {
            getBangInfoFromJSON();
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mAdapter.notifyDataSetChanged();
            if( mArray.size() == 0){
                header.setVisibility(View.GONE);
                footer.setVisibility(View.GONE);
                searchlist.setVisibility(View.GONE);
                lay_empty_txt.setVisibility(View.VISIBLE);
                Toast.makeText(con,"검색결과가 존재하지 않습니다",Toast.LENGTH_SHORT).show();
            }else if( mArray.size() > 0 && mArray.size() < 10 ){
                header.setVisibility(View.VISIBLE);
                footer.setVisibility(View.GONE);
                searchlist.setVisibility(View.VISIBLE);
                lay_empty_txt.setVisibility(View.GONE);
            }else if( mArray.size() >= 10 ){
                header.setVisibility(View.VISIBLE);
                footer.setVisibility(View.VISIBLE);
                searchlist.setVisibility(View.VISIBLE);
                lay_empty_txt.setVisibility(View.GONE);
            }
            pd.dismiss();
        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    } // JsonLoa

    //뒤로가기 버튼
    @Override
    public void onBackPressed() {
        MainActivity.quick.is_open = false;
        MainActivity.quick.dismiss();
        finish();
    }
}
