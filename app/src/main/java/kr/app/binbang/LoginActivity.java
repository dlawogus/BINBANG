package kr.app.binbang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class LoginActivity extends FontActivity {
    private EditText mEdit_Id;
    private EditText mEdit_Pw;
    private Button mBtn_Ok;
    private Button mBtn_find_id;
    private Button mBtn_find_pw;
    private Button mBtn_join;
    private String mLoginResult = "";
    private ProgressDialog pd;
    private Context con;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.activity_login);

        con = this;
        token = MainActivity.token;

        mEdit_Id = (EditText)findViewById(R.id.login_edit_id);
        mEdit_Pw = (EditText)findViewById(R.id.login_edit_pw);

        mBtn_Ok = (Button)findViewById(R.id.login_btn_ok);
        mBtn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginTask().execute();
            }
        });


        //아이디 찾기
        mBtn_find_id = (Button)findViewById(R.id.login_find_id);
        mBtn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Find_Id_Activity.class);
                startActivity(intent);
            }
        });

        //비번 찾기
        mBtn_find_pw = (Button)findViewById(R.id.login_find_pw);
        mBtn_find_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Find_Pw_Activity.class);
                startActivity(intent);
            }
        });

        //회원가입
        mBtn_join = (Button)findViewById(R.id.login_join);
        mBtn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
    }


    public InputStream getInputStreamFromUrl(String url) {
        InputStream contentStream = null;
        // 실제 전송하는 부분
        ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
        post.add(new BasicNameValuePair("user_id", mEdit_Id.getText().toString()));
        post.add(new BasicNameValuePair("user_pw", mEdit_Pw.getText().toString()));
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
    public String getStringFromUrl(String url) throws UnsupportedEncodingException {
        BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url), "UTF-8"));
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
            String line = getStringFromUrl(url);
            // 원격에서 읽어온 데이터로 JSON 객체 생성
            Log.d("json_login", line);
            JSONObject object = new JSONObject(line);
            mLoginResult = object.getString("result");
            Log.d("json_login", line);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    private class LoginTask extends AsyncTask<String, Void, Void> {
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
            getLoginFromJSON();
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pd.dismiss();
            if( mLoginResult.equals("1") ){//성공
                MainActivity.UserID = mEdit_Id.getText().toString();
                MainActivity.UserPW = mEdit_Pw.getText().toString();
                //MainFragment_1.mBang_array.clear();
                MainActivity.quick.changActionItem("로그아웃", 0);
                finish();
            }else if( mLoginResult.equals("2")) {//비밀번호 틀림
                Toast.makeText(LoginActivity.this,"비밀번호가 정확하지 않습니다",Toast.LENGTH_SHORT).show();
            }else if( mLoginResult.equals("3")){//존재하지 않는 아이디
                Toast.makeText(LoginActivity.this,"존재하지 않은 아이디입니다",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LoginActivity.this,"등록되지 않은 기기입니다",Toast.LENGTH_SHORT).show();
            }
            savePreferences();
        }
    }

    // 값 저장하기
    private void savePreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        //editor.putBoolean("appFirstStart", MainActivity.appFirstStart);
        editor.putString("user_id", MainActivity.UserID);
        editor.putString("user_pw", MainActivity.UserPW);
        editor.commit();
    }

}

