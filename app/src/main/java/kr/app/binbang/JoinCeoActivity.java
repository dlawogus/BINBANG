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


public class JoinCeoActivity extends FontActivity {
    private ProgressDialog pd;
    private Context con;
    private ImageButton mBack;
    private Button btn_join_id_check;   //중복확인
    private EditText edit_join_company; //중개사무소 이름
    private EditText edit_join_name;    //대표이름
    private EditText edit_join_id;      //아이디
    private EditText edit_join_pw;      //비밀번호
    private EditText edit_join_repw;    //비밀번호 확인
    private EditText edit_join_com_1;   //사업자등록번호
    private EditText edit_join_com_2;
    private EditText edit_join_com_3;
    private EditText edit_join_com_reg; //중개업 등록번호
    private EditText edit_join_juso_gu; //동
    private EditText edit_join_juso_dong;
    private EditText edit_join_juso_sangse;
    private EditText edit_join_phone;
    private EditText edit_join_fax;
    private EditText edit_join_email;
/*    private EditText edit_join_juso_pay;    //납부자명
    private EditText edit_join_juso_pay_title; //납부자 직함
    private EditText edit_join_juso_pay_phone; //납부자 휴대폰 번호
    private EditText edit_join_juso_pay_email; //납부자 이메일*/
    private Button btn_join_cancel;
    private Button btn_join_ok;

    private String mJoinResult = "";
    private String mIsUserResult = "";
    private boolean is_Idcheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_join_ceo);
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

        edit_join_company = (EditText)findViewById(R.id.edit_join_company);
        edit_join_name = (EditText)findViewById(R.id.edit_join_name);
        edit_join_id = (EditText)findViewById(R.id.edit_join_id);
        btn_join_id_check = (Button)findViewById(R.id.btn_join_id_check);
        btn_join_id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getIsUserTask().execute();
            }
        });
        edit_join_pw = (EditText)findViewById(R.id.edit_join_pw);
        edit_join_repw = (EditText)findViewById(R.id.edit_join_repw);
        edit_join_com_1 = (EditText)findViewById(R.id.edit_join_com_1);
        edit_join_com_2 = (EditText)findViewById(R.id.edit_join_com_2);
        edit_join_com_3 = (EditText)findViewById(R.id.edit_join_com_3);
        edit_join_com_reg = (EditText)findViewById(R.id.edit_join_com_reg);
        edit_join_juso_gu = (EditText)findViewById(R.id.edit_join_juso_gu);
        edit_join_juso_dong = (EditText)findViewById(R.id.edit_join_juso_dong);
        edit_join_juso_sangse = (EditText)findViewById(R.id.edit_join_juso_sangse);
        edit_join_phone = (EditText)findViewById(R.id.edit_join_phone);
        edit_join_fax = (EditText)findViewById(R.id.edit_join_fax);
        edit_join_email = (EditText)findViewById(R.id.edit_join_email);
/*        edit_join_juso_pay = (EditText)findViewById(R.id.edit_join_juso_pay);
        edit_join_juso_pay_title = (EditText)findViewById(R.id.edit_join_juso_pay_title);
        edit_join_juso_pay_phone = (EditText)findViewById(R.id.edit_join_juso_pay_phone);
        edit_join_juso_pay_email = (EditText)findViewById(R.id.edit_join_juso_pay_email);*/

        btn_join_ok = (Button)findViewById(R.id.btn_join_ok);
        btn_join_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String com_reg = edit_join_com_1.getText()+"-"+edit_join_com_2.getText()+"-"+edit_join_com_3.getText();
                if( is_Idcheck ) {
                    if( !edit_join_pw.getText().equals("") && !edit_join_id.getText().equals("") &&
                            ( !com_reg.equals("") || !edit_join_com_reg.getText().equals("") ) &&  !edit_join_juso_gu.getText().equals("")
                            && !edit_join_juso_dong.getText().equals("") && !edit_join_juso_sangse.equals("") && !edit_join_phone.getText().equals("") )
                        new JoinTask().execute();
                    else
                        Toast.makeText(con,"빈칸을 채워주세요",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(con,"아이디 중복 확인을 해주세요",Toast.LENGTH_SHORT).show();
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
        post.add(new BasicNameValuePair("company_name", edit_join_company.getText().toString()));
        post.add(new BasicNameValuePair("phone_number", edit_join_phone.getText().toString()));
        post.add(new BasicNameValuePair("fax_number", edit_join_fax.getText().toString()));
        post.add(new BasicNameValuePair("email", edit_join_email.getText().toString()));
        post.add(new BasicNameValuePair("juso",  edit_join_juso_gu.getText().toString()+" "+edit_join_juso_dong.getText().toString()+" "+edit_join_juso_sangse.getText().toString() ));
        post.add(new BasicNameValuePair("company_reg_num", edit_join_com_1.getText().toString()+"-"+edit_join_com_2.getText().toString()+"-"+edit_join_com_3.getText().toString() ));
        post.add(new BasicNameValuePair("relay_reg_num", edit_join_com_reg.getText().toString()));
/*        post.add(new BasicNameValuePair("pay_name", edit_join_juso_pay.getText().toString() ));
        post.add(new BasicNameValuePair("pay_title", edit_join_juso_pay_title.getText().toString() ));
        post.add(new BasicNameValuePair("pay_phone", edit_join_juso_pay_phone.getText().toString() ));
        post.add(new BasicNameValuePair("pay_email", edit_join_juso_pay_email.getText().toString() ));*/
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
        String url = "http://dlawogus1.cafe24.com/app/join_ceo.php";
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

