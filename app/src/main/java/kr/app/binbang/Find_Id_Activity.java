package kr.app.binbang;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
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


public class Find_Id_Activity extends FontActivity {
    private ImageButton mBack;
    private EditText mEdit_name;
    private EditText mEdit_phone;
    private Button mBtn_ok;
    private String mUser_id;
    private String mResult;
    private ProgressDialog pd;
    private Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_find_id);
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

        mEdit_name = (EditText) findViewById(R.id.edit_name);
        mEdit_phone = (EditText) findViewById(R.id.edit_phone);
        mBtn_ok = (Button) findViewById(R.id.btn_ok);
        mBtn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mEdit_name.getText().toString().length() > 0 && mEdit_phone.getText().toString().length() > 0){
                    new FindIdTask().execute();
                }else {
                    Toast.makeText(con,"빈칸을 채워주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public InputStream getInputStreamFromUrl(String url) {
        InputStream contentStream = null;
        // 실제 전송하는 부분
        ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
        post.add(new BasicNameValuePair("name", mEdit_name.getText().toString()));

        String formattingNumber = PhoneNumberUtils.formatNumber(mEdit_phone.getText().toString());
        post.add(new BasicNameValuePair("phone", formattingNumber));
        Log.d("phone", formattingNumber);

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
    public void	getFindFromJSON(){
        String url = "http://dlawogus1.cafe24.com/app/find_id.php";
        try{
            String line = getStringFromUrl(url);
            // 원격에서 읽어온 데이터로 JSON 객체 생성
            Log.d("json_login", line);
            JSONObject object = new JSONObject(line);
            mResult = object.getString("result");
            mUser_id = object.getString("user_id");
            Log.d("json_login", line);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    private class FindIdTask extends AsyncTask<String, Void, Void> {
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
            getFindFromJSON();
            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pd.dismiss();
            AlertDialog alert = new AlertDialog.Builder(con)
                    .setTitle("아이디 찾기")
                    .setMessage(mUser_id)
                    .setPositiveButton("확인",
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            }).create();

            AlertDialog alert_1 = new AlertDialog.Builder(con)
                    .setTitle("일치하는 아이디가 없습니다")
                    .setMessage("입력정보를 다시한번 확인해주세요")
                    .setPositiveButton("확인",
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();

            if( mResult.equals("1") ){
                alert.show();
            }else if( mResult.equals("2")){
                alert_1.show();
            }else{
                Toast.makeText(con,"인터넷넷을 확인해주세요",Toast.LENGTH_SHORT).show();
           }
        }
    }

}
