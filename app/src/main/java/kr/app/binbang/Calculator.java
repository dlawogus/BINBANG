package kr.app.binbang;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Calculator extends FontActivity {
    private ImageButton mBtn_menu;
    private ImageButton mBtn_search;
    private Button mBtn_cal_ok;
    private TextView cal_1_txt;
    private TextView cal_2_txt;
    private TextView cal_3_txt;
    private TextView text_info;
    private Context con;
    private Spinner spinner_1;
    private Spinner spinner_2;
    private EditText edit_price_1;
    private EditText edit_price_2;
    // 값 셋팅시, StackOverFlow를 막기 위해서, 바뀐 변수를 저장해준다.
    private String result_1="";
    private String result_2="";
    private String spinner1_item;
    private String spinner2_item;

    private LinearLayout lay_rental;
    private TextView cal_deposit_txt;
    private TextView cal_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_calculator);
        View top = (View)findViewById(R.id.top_m);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            top.setVisibility(View.GONE);
        }
        con = this;
        MainActivity.mActivityName = "Calculator";


        ImageView logo = (ImageView)findViewById(R.id.backtomain);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calculator.this,MainActivity.class);
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
                    Intent intent = new Intent(Calculator.this, SearchActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Calculator.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        lay_rental = (LinearLayout)findViewById(R.id.lay_rental);
        cal_deposit_txt = (TextView)findViewById(R.id.cal_deposit_txt);
        cal_info = (TextView)findViewById(R.id.cal_info);
        cal_1_txt = (TextView)findViewById(R.id.cal_1_txt);     //중개보수
        cal_2_txt = (TextView)findViewById(R.id.cal_2_txt);     //상한요율
        cal_3_txt = (TextView)findViewById(R.id.cal_3_txt);     //거래금액
        text_info = (TextView)findViewById(R.id.text_info);

        spinner_1 = (Spinner)findViewById(R.id.spinner);
        ArrayList mlist = new ArrayList<String>();
        mlist.add("주택");
        mlist.add("주거용 오피스텔");
        mlist.add("주택 외 부동산");
        ArrayAdapter<String> mSpinerAdapter =
                new ArrayAdapter<String>(Calculator.this, android.R.layout.simple_spinner_item, mlist);
        mSpinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_1.setPrompt("주택");
        spinner_1.setAdapter(mSpinerAdapter);

        spinner_2 = (Spinner)findViewById(R.id.spinner_1);
        ArrayList mlist_1 = new ArrayList<String>();
        mlist_1.add("매매/교환");
        mlist_1.add("전세 임대차");
        mlist_1.add("월세 임대차");
        ArrayAdapter<String> mSpinerAdapter_1 =
                new ArrayAdapter<String>(Calculator.this, android.R.layout.simple_spinner_item, mlist_1);
        mSpinerAdapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_2.setPrompt("매매/교환");
        spinner_2.setAdapter(mSpinerAdapter_1);

        spinner_1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner1_item = (String) spinner_1.getSelectedItem();
                cal_1_txt.setText("0");
                cal_2_txt.setText("0");
                cal_3_txt.setText("0");
                if( spinner1_item.equals("주택") )
                    text_info.setText("아파트,원룸,주거형 오피스텔,단독다가구 주택 등");
                else if( spinner1_item.equals("주거용 오피스텔") )
                    text_info.setText("부엌·화장실 등의 시설을 갖춘 전용면적 85㎡ 이하 오피스텔");
                else
                    text_info.setText("오피스텔(주거용 제외), 상가, 토지 등");
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
        spinner_2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner2_item = (String) spinner_2.getSelectedItem();
                if( spinner2_item.equals("월세 임대차") ) {
                    lay_rental.setVisibility(View.VISIBLE);
                    cal_info.setVisibility(View.VISIBLE);
                }else{
                    lay_rental.setVisibility(View.GONE);
                    cal_info.setVisibility(View.GONE);
                }

                if( spinner2_item.equals("매매/교환") )
                    cal_deposit_txt.setText("거래금액");
                else
                    cal_deposit_txt.setText("보증금");

                cal_1_txt.setText("0");
                cal_2_txt.setText("0");
                cal_3_txt.setText("0");
            }
           @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });


        //보증금
        edit_price_1 = (EditText)findViewById(R.id.edit_price_1);
        edit_price_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(result_1)) {
                    result_1 = makeStringComma( s.toString().replaceAll(",", ""));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    edit_price_1.setText(result_1);    // 결과 텍스트 셋팅.
                    edit_price_1.setSelection(result_1.length());     // 커서를 제일 끝으로 보냄.
                }

            }
        });

        //월세액
        edit_price_2 = (EditText)findViewById(R.id.edit_price_2);
        edit_price_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(result_2)) {
                    result_2 = makeStringComma(s.toString().replaceAll(",", ""));    // 에딧텍스트의 값을 변환하여, result에 저장.
                    edit_price_2.setText(result_2);    // 결과 텍스트 셋팅.
                    edit_price_2.setSelection(result_2.length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });

        cal_1_txt = (TextView)findViewById(R.id.cal_1_txt);     //중개보수
        cal_2_txt = (TextView)findViewById(R.id.cal_2_txt);     //상한요율
        cal_3_txt = (TextView)findViewById(R.id.cal_3_txt);     //거래금액

        mBtn_cal_ok = (Button)findViewById(R.id.btn_cal_ok);
        mBtn_cal_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //키보드 숨기기
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_price_1.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(edit_price_2.getWindowToken(), 0);

                String p1 = edit_price_1.getText().toString();  //보증금
                String p2 = edit_price_2.getText().toString();  //월세액
                if( edit_price_1.getText().toString().equals("") )
                    p1 = "0";
                if( edit_price_2.getText().toString().equals("") )
                    p2 = "0";
                String s1 = p1.replaceAll(",", "");    //보증금
                String s2 = p2.replaceAll(",", "");    //월세액
                long deposit = Long.parseLong(s1);
                long rental = Long.parseLong(s2);
                if( spinner1_item.equals("주택") ) {
                    if (spinner2_item.equals("매매/교환")) {
                        cal_info.setVisibility(View.GONE);
                        if (deposit < 50000000) {
                            double i = deposit * 0.006;
                            long t;
                            i = Math.round(i);
                            if (i > 250000) i = 250000;
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)));
                            cal_2_txt.setText("0.6");
                            cal_3_txt.setText(makeStringComma(s1));
                        } else if (deposit >= 50000000 && deposit < 200000000) {
                            double i = deposit * 0.005;
                            long t;
                            i = Math.round(i);
                            if (i > 800000) i = 800000;
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)));
                            cal_2_txt.setText("0.5");
                            cal_3_txt.setText(makeStringComma(s1));
                        } else if (deposit >= 200000000 && deposit < 600000000) {
                            double i = deposit * 0.004;
                            long t;
                            i = Math.round(i);
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)));
                            cal_2_txt.setText("0.4");
                            cal_3_txt.setText(makeStringComma(s1));
                        } else if (deposit >= 600000000 && deposit < 900000000) {
                            double i = deposit * 0.005;
                            long t;
                            i = Math.round(i);
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)));
                            cal_2_txt.setText("0.5");
                            cal_3_txt.setText(makeStringComma(s1));
                        } else {
                            double i = deposit * 0.009;
                            long t;
                            i = Math.round(i);
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)));
                            cal_2_txt.setText("0.9");
                            cal_3_txt.setText(makeStringComma(s1));
                        }
                    }else if( spinner2_item.equals("전세 임대차") ) {
                        cal_info.setVisibility(View.GONE);
                        if( deposit < 50000000 ){
                            double i = deposit * 0.005;
                            long t;
                            i = Math.round(i);
                            if (i > 200000) i = 200000;
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)));
                            cal_2_txt.setText("0.5");
                            cal_3_txt.setText(makeStringComma(s1));
                        }else if( deposit >= 50000000 && deposit < 100000000 ){
                            double i = deposit * 0.004;
                            long t;
                            i = Math.round(i);
                            if (i > 300000) i = 300000;
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)));
                            cal_2_txt.setText("0.4");
                            cal_3_txt.setText(makeStringComma(s1));
                        }else if( deposit >= 100000000 && deposit < 300000000 ){
                            double i = deposit * 0.003;
                            long t;
                            i = Math.round(i);
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)));
                            cal_2_txt.setText("0.3");
                            cal_3_txt.setText(makeStringComma(s1));
                        }else if( deposit >= 300000000 && deposit < 600000000 ){
                            double i = deposit * 0.004;
                            long t;
                            i = Math.round(i);
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)));
                            cal_2_txt.setText("0.4");
                            cal_3_txt.setText(makeStringComma(s1));
                        }else{
                            double i = deposit * 0.008;
                            long t;
                            i = Math.round(i);
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)));
                            cal_2_txt.setText("0.8");
                            cal_3_txt.setText(makeStringComma(s1));
                        }
                    }else{//월세 임대차
                        cal_info.setVisibility(View.VISIBLE);
                        long p = deposit + (rental*100);
                        if( p < 50000000 ){
                            p = deposit + (rental*70);
                            cal_info.setText("보증금 + (월세액x70)");
                        }else cal_info.setText("보증금 + (월세액x100)");

                        if( p < 50000000 ){
                            double i = p * 0.005;
                            long t;
                            i = Math.round(i);
                            if (i > 200000) i = 200000;
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)) );
                            cal_2_txt.setText("0.5");
                            cal_3_txt.setText(makeStringComma(Long.toString(p)) );
                        }else if( p>= 50000000 && p < 100000000 ){
                            double i = p * 0.004;
                            long t;
                            i = Math.round(i);
                            if (i > 300000) i = 300000;
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)) );
                            cal_2_txt.setText("0.4");
                            cal_3_txt.setText(makeStringComma(Long.toString(p)) );
                        }else if( p >= 100000000 && p < 300000000 ){
                            double i = p * 0.003;
                            long t;
                            i = Math.round(i);
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)) );
                            cal_2_txt.setText("0.3");
                            cal_3_txt.setText(makeStringComma(Long.toString(p)) );
                        }else if( p >= 300000000 && p < 600000000 ){
                            double i = p * 0.004;
                            long t;
                            i = Math.round(i);
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)) );
                            cal_2_txt.setText("0.4");
                            cal_3_txt.setText(makeStringComma(Long.toString(p)) );
                        }else{
                            double i = p * 0.008;
                            long t;
                            i = Math.round(i);
                            t = (long) i;
                            cal_1_txt.setText(makeStringComma(Long.toString(t)) );
                            cal_2_txt.setText("0.8");
                            cal_3_txt.setText(makeStringComma(Long.toString(p)) );
                        }
                    }
                }else if( spinner1_item.equals("주거용 오피스텔") ){
                    if( spinner2_item.equals("매매/교환")){
                        cal_info.setVisibility(View.GONE);
                        double i = deposit * 0.005;
                        long t;
                        i = Math.round(i);
                        t = (long) i;
                        cal_1_txt.setText(makeStringComma( Long.toString(t)) );
                        cal_2_txt.setText("0.5");
                        cal_3_txt.setText(makeStringComma(s1));
                    }else if( spinner2_item.equals("전세 임대차") ){
                        cal_info.setVisibility(View.GONE);
                        double i = deposit * 0.004;
                        long t;
                        i = Math.round(i);
                        t = (long) i;
                        cal_1_txt.setText(makeStringComma(Long.toString(t)) );
                        cal_2_txt.setText("0.4");
                        cal_3_txt.setText(makeStringComma(s1));
                    }else{
                        cal_info.setVisibility(View.VISIBLE);
                        long p = deposit + (rental*100);
                        if( p < 50000000 ){
                            p = deposit + (rental*70);
                            cal_info.setText("보증금 + (월세액x70)");
                        }else cal_info.setText("보증금 + (월세액x100)");

                        double i = p * 0.004;
                        long t;
                        i = Math.round(i);
                        t = (long) i;
                        cal_1_txt.setText(makeStringComma(Long.toString(t)) );
                        cal_2_txt.setText("0.4");
                        cal_3_txt.setText(makeStringComma(Long.toString(p)));
                    }
                }else{
                    if( spinner2_item.equals("매매/교환")){
                        cal_info.setVisibility(View.GONE);
                        double i = deposit * 0.009;
                        long t;
                        i = Math.round(i);
                        t = (long) i;
                        cal_1_txt.setText(makeStringComma(Long.toString(t)) );
                        cal_2_txt.setText("0.9");
                        cal_3_txt.setText(makeStringComma(s1));
                    }else if( spinner2_item.equals("전세 임대차") ){
                        cal_info.setVisibility(View.GONE);
                        double i = deposit * 0.009;
                        long t;
                        i = Math.round(i);
                        t = (long) i;
                        cal_1_txt.setText(makeStringComma(Long.toString(t)) );
                        cal_2_txt.setText("0.9");
                        cal_3_txt.setText(makeStringComma(s1));
                    }else{
                        cal_info.setVisibility(View.VISIBLE);
                        long p = deposit + (rental*100);
                        if( p < 50000000 ){
                            p = deposit + (rental*70);
                            cal_info.setText("보증금 + (월세액x70)");
                        }else cal_info.setText("보증금 + (월세액x100)");
                        double i = p * 0.009;
                        long t;
                        i = Math.round(i);
                        t = (long) i;
                        cal_1_txt.setText(makeStringComma(Long.toString(t)) );
                        cal_2_txt.setText("0.9");
                        cal_3_txt.setText(makeStringComma(Long.toString(p)));
                    }
                }
/*                String p1 = edit_price_1.getText().toString();
                String p2 = edit_price_2.getText().toString();
                if( edit_price_1.getText().toString().equals("") )
                    p1 = "0";
                if( edit_price_2.getText().toString().equals("") )
                    p2 = "0";
                String s1 = p1.replaceAll(",", "");
                String s2 = p2.replaceAll(",", "");

                long r =  Long.parseLong(s1) + (Long.parseLong(s2)*70);
                cal_3_txt.setText(makeStringComma(Long.toString(r)));*/
            }
        });
    }

    public String makeStringComma(String str) {
        if (str.length() == 0)
            return "";
        long value = Long.parseLong(str);
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(value);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        MainActivity.quick.is_open = false;
        MainActivity.quick.dismiss();
    }

}
