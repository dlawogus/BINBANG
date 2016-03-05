package kr.app.binbang;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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


public class JoinMemberActivity extends FontActivity {
    private ProgressDialog pd;
    private Context con;
    private ImageButton mBack;
    private EditText edit_join_ceo;
    private Button btn_join_ceo_check;
    private EditText edit_join_id;
    private Button btn_join_id_check;
    private EditText edit_join_name;
    private EditText edit_join_pw;
    private EditText edit_join_repw;
    private EditText edit_join_title;
    private EditText edit_join_phone;
    private EditText edit_join_call;
    private EditText edit_join_email;
    private Button btn_join_cancel;
    private Button btn_join_ok;

    private String mJoinResult = "";
    private String mIsCeoResult = "";
    private String mIsUserResult = "";
    private boolean is_Idcheck = false;
    private boolean is_Ceocheck = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_join_member);
        View top = (View)findViewById(R.id.top_m);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            top.setVisibility(View.GONE);
        }

        con = this;
        mBack = (ImageButton)findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit_join_ceo = (EditText)findViewById(R.id.edit_join_ceo);
        btn_join_ceo_check = (Button)findViewById(R.id.btn_join_ceo_check);
        btn_join_ceo_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getIsCeoTask().execute();
            }
        });

        edit_join_id = (EditText)findViewById(R.id.edit_join_id);
        btn_join_id_check = (Button)findViewById(R.id.btn_join_id_check);
        btn_join_id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getIsUserTask().execute();
            }
        });
        edit_join_name = (EditText)findViewById(R.id.edit_join_name);
        edit_join_pw = (EditText)findViewById(R.id.edit_join_pw);
        edit_join_repw = (EditText)findViewById(R.id.edit_join_repw);
        edit_join_title = (EditText)findViewById(R.id.edit_join_title);
        edit_join_phone = (EditText)findViewById(R.id.edit_join_phone);
        edit_join_call = (EditText)findViewById(R.id.edit_join_call);
        edit_join_email = (EditText)findViewById(R.id.edit_join_email);

        btn_join_ok = (Button)findViewById(R.id.btn_join_ok);
        btn_join_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( is_Ceocheck && is_Idcheck)
                    if( !edit_join_id.getText().equals("") && !edit_join_name.getText().equals("") && !edit_join_pw.getText().equals("")
                            && !edit_join_repw.getText().equals("") && !edit_join_title.getText().equals("") && !edit_join_phone.getText().equals(""))
                        new JoinTask().execute();
                    else
                        Toast.makeText(con,"빈칸을 채워주세요",Toast.LENGTH_SHORT).show();
                else if( !is_Ceocheck ){
                    Toast.makeText(con,"대표님 아이디체크를 해주세요",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(con,"아이디 중복확인을 해주세요",Toast.LENGTH_SHORT).show();
            }
        });
        btn_join_cancel = (Button)findViewById(R.id.btn_join_cancel);
        btn_join_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public InputStream getInputStreamFromUrl(String url) {
        InputStream contentStream = null;
        // 실제 전송하는 부분
        ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
        post.add(new BasicNameValuePair("user_id", edit_join_id.getText().toString()));
        post.add(new BasicNameValuePair("user_pw", edit_join_pw.getText().toString()));
        post.add(new BasicNameValuePair("user_name", edit_join_name.getText().toString()));
        post.add(new BasicNameValuePair("company_title", edit_join_title.getText().toString()));
        post.add(new BasicNameValuePair("ceo_id", edit_join_ceo.getText().toString()));
        post.add(new BasicNameValuePair("phone_number", edit_join_phone.getText().toString()));
        post.add(new BasicNameValuePair("call_number", edit_join_call.getText().toString()));
        post.add(new BasicNameValuePair("email", edit_join_email.getText().toString()));
        post.add(new BasicNameValuePair("token", MainActivity.token.toString() ));

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
    public void	getJoinFromJSON(){
        String url = "http://dlawogus1.cafe24.com/app/join_employ.php";
        try{
            String line = getStringFromUrl(url);
            // 원격에서 읽어온 데이터로 JSON 객체 생성
            Log.d("json_join", line);
            JSONObject object = new JSONObject(line);
            mJoinResult = object.getString("result");

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    private class JoinTask extends AsyncTask<String, Void, Void> {
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
            getJoinFromJSON();
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pd.dismiss();
            if( mJoinResult.equals("1") ) {//성공
                Toast.makeText(con,"회원가입하였습니다",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(con,"입력정보를 다시 확인해주세요",Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void	getIsCeoFromJSON(){
        String url = MainActivity.RESTFUL_URL + "is_exCeo.php?ceo_id="+edit_join_ceo.getText().toString();
        try{
            String line = MainActivity.getStringFromUrl(url);
            // 원격에서 읽어온 데이터로 JSON 객체 생성
            JSONObject object = new JSONObject(line);
            mIsCeoResult = object.getString("result");
            Log.d("json_r", line);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    private class getIsCeoTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute(){}
        @Override
        protected Void doInBackground(String... strs) {
            getIsCeoFromJSON();
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if( mIsCeoResult.equals("1") ){
                Toast.makeText(con, "확인되었습니다", Toast.LENGTH_SHORT).show();
                is_Ceocheck = true;
            }else{
                Toast.makeText(con,"존재하지 않는 아이디입니다",Toast.LENGTH_SHORT).show();
                edit_join_ceo.setText("");
                is_Ceocheck = false;
            }
        }
    }
    public void	getIsUserFromJSON(){
        String url = MainActivity.RESTFUL_URL + "is_exUser.php?user_id="+edit_join_id.getText().toString();
        try{
            String line = MainActivity.getStringFromUrl(url);
            // 원격에서 읽어온 데이터로 JSON 객체 생성
            JSONObject object = new JSONObject(line);
            mIsUserResult = object.getString("result");
            Log.d("json_r", line);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    private class getIsUserTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute(){}
        @Override
        protected Void doInBackground(String... strs) {
            getIsUserFromJSON();
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if( mIsUserResult.equals("1") ){
                Toast.makeText(con,"이용가능한 아이디입니다",Toast.LENGTH_SHORT).show();
                is_Idcheck = true;
            }else{
                Toast.makeText(con,"이용이 불가능한 아이디입니다.",Toast.LENGTH_SHORT).show();
                edit_join_id.setText("");
                is_Idcheck = false;
            }
        }
    }

}
