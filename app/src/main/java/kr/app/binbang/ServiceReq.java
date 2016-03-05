package kr.app.binbang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;


public class ServiceReq extends kr.app.binbang.FontActivity {
    private ImageButton mBtn_menu;
    private ImageButton mBtn_search;
    private Context con;

    private ImageButton req_check_1;
    private ImageButton req_check_2;
    private Button req_ok;

    private String period = "";
    private String mResult = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_service_req);
        View top = (View)findViewById(R.id.top_m);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            top.setVisibility(View.GONE);
        }
        con = this;
        kr.app.binbang.MainActivity.mActivityName = "ServiceReq";

        ImageView logo = (ImageView)findViewById(R.id.backtomain);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceReq.this, kr.app.binbang.MainActivity.class);
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
                kr.app.binbang.MainActivity.mDialog_con = con;
                if( !kr.app.binbang.MainActivity.quick.is_open ) {
                    kr.app.binbang.MainActivity.quick.is_open = true;
                    kr.app.binbang.MainActivity.quick.show(v);
                }else{
                    kr.app.binbang.MainActivity.quick.is_open = false;
                    kr.app.binbang.MainActivity.quick.dismiss();
                }
            }
        });

        //액션바 매물 검색
        mBtn_search = (ImageButton)findViewById(R.id.main_search);
        mBtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!kr.app.binbang.MainActivity.UserID.equals("")) {
                    Intent intent = new Intent(ServiceReq.this, kr.app.binbang.SearchActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ServiceReq.this, kr.app.binbang.LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        req_check_1 = (ImageButton)findViewById(R.id.req_check_1);
        req_check_2 = (ImageButton)findViewById(R.id.req_check_2);
        req_check_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                req_check_1.setImageResource(R.mipmap.view_check_on);
                req_check_2.setImageResource(R.mipmap.view_check_off);
                period = "1";
            }
        });
        req_check_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                req_check_1.setImageResource(R.mipmap.view_check_off);
                req_check_2.setImageResource(R.mipmap.view_check_on);
                period = "3";
            }
        });

        req_ok = (Button)findViewById(R.id.req_ok);
        req_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( period.equals("") ){
                    Toast.makeText(con,"서비스 이용기간을 선택하여 주십시오",Toast.LENGTH_SHORT).show();
                }else{
                    new getReqTask().execute();
                }
            }
        });

    }

    public void	getReqFromJSON(){
        String url = kr.app.binbang.MainActivity.RESTFUL_URL + "service_req.php?period="+period;
        try{
            String line = kr.app.binbang.MainActivity.getStringFromUrl(url);
            // 원격에서 읽어온 데이터로 JSON 객체 생성
            JSONObject object = new JSONObject(line);
            mResult = object.getString("result");
            Log.d("json_r", line);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    private class getReqTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute(){}
        @Override
        protected Void doInBackground(String... strs) {
            getReqFromJSON();
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if( mResult.equals("1") ) {
                //Toast.makeText(con, "관리자 사용승인후 사용가능합니다", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(con)
                        .setTitle("서비스 신청")
                        .setMessage("관리자 사용승인후 사용가능합니다")
                        .setPositiveButton("확인",
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        dialog.dismiss();
                                    }
                                }).create().show();
            }else if( mResult.equals("2") ){
                new AlertDialog.Builder(con)
                        .setTitle("이미 신청하였습니다")
                        .setMessage("관리자 승인을 기다려주세요")
                        .setPositiveButton("확인",
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        dialog.dismiss();
                                    }
                                }).create().show();
            }
            else{
                Toast.makeText(con, "에러! 잠시 후 다시 시도해보세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        kr.app.binbang.MainActivity.quick.is_open = false;
        kr.app.binbang.MainActivity.quick.dismiss();
    }

}
