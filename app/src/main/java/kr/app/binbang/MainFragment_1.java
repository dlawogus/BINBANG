package kr.app.binbang;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kr.app.adapter.BangListAdapter;
import kr.app.adapter.BangNewAdapter;
import kr.app.datainfo.BangDataInfo;


public class MainFragment_1 extends Fragment {
    private ProgressDialog pd;
    private Context con;

    //최근 올라온 매물
    public static ArrayList<BangDataInfo> mBang_array;
    public static BangNewAdapter mListAdapter;
    private ListView mBanglist;

    //최근 살펴본방
    public static ArrayList<BangDataInfo> mBang_array_recently;
    public static BangListAdapter mListAdapter_recently;
    private ListView mBanglist_recently;

    private TextView text_title;
    //공지사항
    public static TextView mNotice1;
    public static TextView mNotice2;
    public static TextView mNotice3;
    public static TextView mNotice4;
    public static TextView mNotice5;
    private LinearLayout mLayNotive1;
    private LinearLayout mLayNotive2;
    private LinearLayout mLayNotive3;
    private LinearLayout mLayNotive4;
    private LinearLayout mLayNotive5;
    //더보기
    private Button mMore_btn;
    private int page = 2;
    private int pageNum = 10;

    private LinearLayout mSelect_gu;
    private TextView mSelect_gu_text;
    private LinearLayout mSelect_dong;
    private TextView mSelect_dong_text;
    private LinearLayout mSelect_type;
    private TextView mSelect_type_text;
    private LinearLayout mSelect_pricetype;
    private TextView mSelect_pricetype_text;
    private RelativeLayout mSelect_plus;
    private TextView mSelect_plus_text;
    private RelativeLayout mSelect_ok;
    private ImageView mGu_indicator;
    private ImageView mDong_indicator;
    private ImageView mType_indicator;
    private ImageView mPricetype_indicator;

    private boolean is_lay_select_gu = false;
    private boolean is_lay_select_dong = false;
    private boolean is_lay_select_type = false;
    private boolean is_lay_select_pricetype = false;
    private boolean is_lay_select_plus = false;

    private String mGU = "";
    private int mGU_pos;
    private LinearLayout mLay_select_gu;
    private LinearLayout mLay_select_dong;
    private LinearLayout mLay_select_type;
    private LinearLayout mLay_select_pricetype;
    private LinearLayout mLay_select_plus;
    LinearLayout mLay_select_gu_1;
    LinearLayout mLay_select_gu_2;
    LinearLayout mLay_select_gu_3;
    LinearLayout mLay_select_gu_4;
    LinearLayout mLay_select_dong_1;
    LinearLayout mLay_select_dong_2;
    LinearLayout mLay_select_dong_3;
    LinearLayout mLay_select_dong_4;
    LinearLayout mLay_select_dong_5;
    LinearLayout mLay_select_dong_6;
    LinearLayout mLay_select_type_1;
    LinearLayout mLay_select_type_2;
    LinearLayout mLay_select_pricetype_1;

    private ArrayList<String> mDONG = new ArrayList<String>();
    private ArrayList<String> mTYPE = new ArrayList<String>();
    private String mPRICETYPE = "";
    private String deposit_start = "";
    private String deposit_end = "";
    private String rental_start = "";
    private String rental_end = "";
    private String base_start = "";
    private String base_end = "";
    private EditText edit_deposit_start;
    private EditText edit_deposit_end;
    private EditText edit_rental_start;
    private EditText edit_rental_end;
    private EditText edit_base_start;
    private EditText edit_base_end;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private boolean is_check_1 = false;
    private boolean is_check_2 = false;
    private boolean is_check_3 = false;
    private ImageButton order_gunmul;
    private ImageButton order_price;
    private ImageButton order_juso;

    private View footer;
    private View header;
    private DisplayMetrics dm;
    private int size;
    private int txt_size;

    String[] gu;
    String[][] dong;
    boolean[][] is_dong_select;
    String[] type;
    boolean[] is_type;
    String[] pricetype;
    boolean[] is_pricetype;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.main_fr_1, container, false);
        con = getActivity();

        //dp
        dm = getResources().getDisplayMetrics();
        size = Math.round(8 * dm.density);
        txt_size = Math.round(6 * dm.density);

        gu = getResources().getStringArray(R.array.gu);
        String[] dong1 = getResources().getStringArray(R.array.dong1);
        String[] dong2 = getResources().getStringArray(R.array.dong2);
        String[] dong3 = getResources().getStringArray(R.array.dong3);
        String[] dong4 = getResources().getStringArray(R.array.dong4);
        String[] dong5 = getResources().getStringArray(R.array.dong5);
        String[] dong6 = getResources().getStringArray(R.array.dong6);
        String[] dong7 = getResources().getStringArray(R.array.dong7);
        String[] dong8 = getResources().getStringArray(R.array.dong8);
        String[] dong9 = getResources().getStringArray(R.array.dong9);
        String[] dong10 = getResources().getStringArray(R.array.dong10);
        String[] dong11 = getResources().getStringArray(R.array.dong11);
        String[] dong12 = getResources().getStringArray(R.array.dong12);
        String[] dong13 = getResources().getStringArray(R.array.dong13);
        String[] dong14 = getResources().getStringArray(R.array.dong14);
        String[] dong15 = getResources().getStringArray(R.array.dong15);
        String[] dong16 = getResources().getStringArray(R.array.dong16);
        dong = new String[16][];
        dong[0] = dong1;
        dong[1] = dong2;
        dong[2] = dong3;
        dong[3] = dong4;
        dong[4] = dong5;
        dong[5] = dong6;
        dong[6] = dong7;
        dong[7] = dong8;
        dong[8] = dong9;
        dong[9] = dong10;
        dong[10] = dong11;
        dong[11] = dong12;
        dong[12] = dong13;
        dong[13] = dong14;
        dong[14] = dong15;
        dong[15] = dong16;
        is_dong_select = new boolean[16][];
        for (int i = 0; i < 16; i++)
            is_dong_select[i] = new boolean[dong[i].length];

        type = getResources().getStringArray(R.array.type);
        is_type = new boolean[6];
        pricetype = getResources().getStringArray(R.array.pricetype);
        is_pricetype = new boolean[2];

        mBang_array_recently = new ArrayList<BangDataInfo>();
        int querycount;
        Cursor mCount = MainActivity.mdb.rawQuery("SELECT _id from recently_find Order by _id desc Limit 5", null);
        querycount = mCount.getCount();

        Cursor result = MainActivity.mdb.rawQuery("SELECT * from recently_find Order by _id desc Limit 5", null);
        result.moveToFirst();
        int i = 0;

        Log.d("count", Integer.toString(querycount) );
        while (i < querycount ) {
            BangDataInfo item = new BangDataInfo();
            int _id = result.getInt(0);
            String bang_id = result.getString(1);
            item.set_id(bang_id);
            item.setIs_available(result.getString(2));
            item.setBuilding_name(result.getString(3));
            item.setBuilding_hosu(result.getString(4));
            item.setDong(result.getString(5));
            item.setSangse_juso(result.getString(6));
            item.setDeposit(result.getString(7));
            item.setMonthly_rental(result.getString(8));
            item.setEmpty(result.getString(9));
            item.setPrice_type(result.getString(10));
            item.setManage_price(result.getString(11));
            item.setBang_type(result.getString(12));
            item.setCall(result.getString(13));
            item.setLat(result.getString(14));
            item.setLng(result.getString(15));
            item.setImg_url(result.getString(16));
            int cnt;
            Cursor count = MainActivity.mdb.rawQuery("SELECT count(*) from favorite WHERE bang_id=" + bang_id, null);
            count.moveToFirst();
            cnt = count.getInt(0);
            if (cnt == 1) item.setIs_favorite("1");
            else item.setIs_favorite("0");
            mBang_array_recently.add(item);
            i++;
            result.moveToNext();
        }

        mBanglist_recently = (ListView) v.findViewById(R.id.listView_recently);
        mListAdapter_recently = new BangListAdapter(getActivity(), mBang_array_recently);
        mBang_array = new ArrayList<BangDataInfo>();
        mBanglist = (ListView) v.findViewById(R.id.listView);
        mListAdapter = new BangNewAdapter(getActivity(), mBang_array);


        if (MainActivity.appFirstStart) {
            mBanglist.setVisibility(View.VISIBLE);
            mBanglist_recently.setVisibility(View.GONE);
        } else {
            mBanglist.setVisibility(View.GONE);
            mBanglist_recently.setVisibility(View.VISIBLE);
        }

        footer = getActivity().getLayoutInflater().inflate(R.layout.list_footer, null, false);
        header = getActivity().getLayoutInflater().inflate(R.layout.list_header, null, false);

        /*
        lay_new = (LinearLayout) v.findViewById(R.id.lay_new);
        if( !MainActivity.appFirstStart ){
            lay_new.setVisibility(View.GONE);
            mBanglist.setVisibility(View.GONE);
        }*/
        //공지사항
        mNotice1 = (TextView) footer.findViewById(R.id.notice_1);
        mNotice2 = (TextView) footer.findViewById(R.id.notice_2);
        mNotice3 = (TextView) footer.findViewById(R.id.notice_3);
        mNotice4 = (TextView) footer.findViewById(R.id.notice_4);
        mNotice5 = (TextView) footer.findViewById(R.id.notice_5);
        mLayNotive1 = (LinearLayout) footer.findViewById(R.id.lay_notice_1);
        mLayNotive2 = (LinearLayout) footer.findViewById(R.id.lay_notice_2);
        mLayNotive3 = (LinearLayout) footer.findViewById(R.id.lay_notice_3);
        mLayNotive4 = (LinearLayout) footer.findViewById(R.id.lay_notice_4);
        mLayNotive5 = (LinearLayout) footer.findViewById(R.id.lay_notice_5);
        mMore_btn = (Button) footer.findViewById(R.id._more);
        if (MainActivity.is_notice_set && MainActivity.mNotice_array.size() > 0) {
            mNotice1.setText(MainActivity.mNotice_array.get(0).getTitle());
            mNotice2.setText(MainActivity.mNotice_array.get(1).getTitle());
            mNotice3.setText(MainActivity.mNotice_array.get(2).getTitle());
            mNotice4.setText(MainActivity.mNotice_array.get(3).getTitle());
            mNotice5.setText(MainActivity.mNotice_array.get(4).getTitle());
        }
        mLayNotive1.setOnClickListener(footerClick);
        mLayNotive2.setOnClickListener(footerClick);
        mLayNotive3.setOnClickListener(footerClick);
        mLayNotive4.setOnClickListener(footerClick);
        mLayNotive5.setOnClickListener(footerClick);
        mMore_btn.setOnClickListener(footerClick);
        mBanglist_recently.addHeaderView(header, null, false);
        mBanglist_recently.addFooterView(footer, null, false);
        mBanglist.addHeaderView(header, null, false);
        mBanglist.addFooterView(footer, null, false);
        mBanglist_recently.setAdapter(mListAdapter_recently);
        mBanglist.setAdapter(mListAdapter);

        text_title = (TextView) header.findViewById(R.id.text_title);
        if (MainActivity.appFirstStart) {
            text_title.setText("최신매물");
            mMore_btn.setVisibility(View.GONE);
        } else {
            text_title.setText("최근에 살펴본 방");
            mMore_btn.setVisibility(View.VISIBLE);
        }

        mLay_select_gu = (LinearLayout) header.findViewById(R.id.lay_select_gu);
        mLay_select_dong = (LinearLayout) header.findViewById(R.id.lay_select_dong);
        mLay_select_type = (LinearLayout) header.findViewById(R.id.lay_select_type);
        mLay_select_pricetype = (LinearLayout) header.findViewById(R.id.lay_select_pricetype);
        mLay_select_plus = (LinearLayout) header.findViewById(R.id.lay_select_plus);
        mGu_indicator = (ImageView) header.findViewById(R.id.gu_indicator);
        mDong_indicator = (ImageView) header.findViewById(R.id.dong_indicator);
        mType_indicator = (ImageView) header.findViewById(R.id.type_indicator);
        mPricetype_indicator = (ImageView) header.findViewById(R.id.pricetype_indicator);
        checkBox1 = (CheckBox) header.findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) header.findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) header.findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) header.findViewById(R.id.checkBox4);

        mSelect_dong = (LinearLayout) header.findViewById(R.id.select_dong);
        mSelect_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_lay_select_dong) {
                    mLay_select_gu.setVisibility(View.GONE);
                    is_lay_select_gu = false;
                    mLay_select_dong.setVisibility(View.VISIBLE);
                    is_lay_select_dong = true;
                    mLay_select_type.setVisibility(View.GONE);
                    is_lay_select_type = false;
                    mDong_indicator.setImageResource(R.mipmap.arrow_up);
                    mGu_indicator.setImageResource(R.mipmap.arrow_down);
                    mType_indicator.setImageResource(R.mipmap.arrow_down);
                } else {
                    mLay_select_gu.setVisibility(View.GONE);
                    is_lay_select_gu = false;
                    mLay_select_dong.setVisibility(View.GONE);
                    is_lay_select_dong = false;
                    mLay_select_type.setVisibility(View.GONE);
                    is_lay_select_type = false;
                    mDong_indicator.setImageResource(R.mipmap.arrow_down);
                    mGu_indicator.setImageResource(R.mipmap.arrow_down);
                    mType_indicator.setImageResource(R.mipmap.arrow_down);
                }
            }
        });

        mSelect_gu = (LinearLayout) header.findViewById(R.id.select_gu);
        mSelect_gu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_lay_select_gu) {
                    printGuLay();
                    mLay_select_gu.setVisibility(View.VISIBLE);
                    mLay_select_dong.setVisibility(View.GONE);
                    mLay_select_type.setVisibility(View.GONE);
                    is_lay_select_gu = true;
                    is_lay_select_type = false;
                    is_lay_select_dong = false;
                    mDong_indicator.setImageResource(R.mipmap.arrow_down);
                    mGu_indicator.setImageResource(R.mipmap.arrow_up);
                    mType_indicator.setImageResource(R.mipmap.arrow_down);
                } else {
                    mLay_select_gu.setVisibility(View.GONE);
                    mLay_select_dong.setVisibility(View.GONE);
                    mLay_select_type.setVisibility(View.GONE);
                    is_lay_select_gu = false;
                    is_lay_select_type = false;
                    is_lay_select_dong = false;
                    mDong_indicator.setImageResource(R.mipmap.arrow_down);
                    mGu_indicator.setImageResource(R.mipmap.arrow_down);
                    mType_indicator.setImageResource(R.mipmap.arrow_down);
                }
            }
        });

        mSelect_type = (LinearLayout) header.findViewById(R.id.select_type);
        mSelect_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_lay_select_type) {
                    printTypeLay();
                    mLay_select_gu.setVisibility(View.GONE);
                    mLay_select_dong.setVisibility(View.GONE);
                    mLay_select_type.setVisibility(View.VISIBLE);
                    is_lay_select_gu = false;
                    is_lay_select_dong = false;
                    is_lay_select_type = true;
                    mDong_indicator.setImageResource(R.mipmap.arrow_down);
                    mGu_indicator.setImageResource(R.mipmap.arrow_down);
                    mType_indicator.setImageResource(R.mipmap.arrow_up);
                } else {
                    mLay_select_gu.setVisibility(View.GONE);
                    mLay_select_dong.setVisibility(View.GONE);
                    mLay_select_type.setVisibility(View.GONE);
                    is_lay_select_gu = false;
                    is_lay_select_dong = false;
                    is_lay_select_type = false;
                    mDong_indicator.setImageResource(R.mipmap.arrow_down);
                    mGu_indicator.setImageResource(R.mipmap.arrow_down);
                    mType_indicator.setImageResource(R.mipmap.arrow_down);
                }
            }
        });

        // 전/월세
        mSelect_pricetype = (LinearLayout) header.findViewById(R.id.select_pricetype);
        mSelect_plus = (RelativeLayout) header.findViewById(R.id.select_plus);
        mSelect_plus_text = (TextView) header.findViewById(R.id.select_plus_text);

        mSelect_pricetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_lay_select_pricetype) {
                    printPriceTypeLay();
                    mLay_select_pricetype.setVisibility(View.VISIBLE);
                    mLay_select_plus.setVisibility(View.GONE);
                    is_lay_select_pricetype = true;
                    is_lay_select_plus = false;
                    mSelect_plus_text.setText("+");
                    mPricetype_indicator.setImageResource(R.mipmap.arrow_up);
                } else {
                    mLay_select_pricetype.setVisibility(View.GONE);
                    mLay_select_plus.setVisibility(View.GONE);
                    is_lay_select_pricetype = false;
                    is_lay_select_plus = false;
                    mSelect_plus_text.setText("+");
                    mPricetype_indicator.setImageResource(R.mipmap.arrow_down);
                }
            }
        });

        //플러스
        mSelect_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_lay_select_plus) {
                    mLay_select_pricetype.setVisibility(View.GONE);
                    mLay_select_plus.setVisibility(View.VISIBLE);
                    is_lay_select_pricetype = false;
                    is_lay_select_plus = true;
                    mSelect_plus_text.setText("-");
                    mPricetype_indicator.setImageResource(R.mipmap.arrow_down);
                } else {
                    mLay_select_pricetype.setVisibility(View.GONE);
                    mLay_select_plus.setVisibility(View.GONE);
                    is_lay_select_pricetype = false;
                    is_lay_select_plus = false;
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    mSelect_plus_text.setText("+");
                    mPricetype_indicator.setImageResource(R.mipmap.arrow_down);
                }
            }
        });

        mSelect_ok = (RelativeLayout) header.findViewById(R.id.select_ok);
        edit_deposit_start = (EditText) header.findViewById(R.id.edit_deposit_start);
        edit_deposit_end = (EditText) header.findViewById(R.id.edit_deposit_end);
        edit_rental_start = (EditText) header.findViewById(R.id.edit_rental_start);
        edit_rental_end = (EditText) header.findViewById(R.id.edit_rental_end);
        edit_base_start = (EditText) header.findViewById(R.id.edit_base_start);
        edit_base_end = (EditText) header.findViewById(R.id.edit_base_end);

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_check_1) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    is_check_1 = true;
                    is_check_2 = false;
                    is_check_3 = false;
                } else {
                    is_check_1 = false;
                    is_check_2 = false;
                    is_check_3 = false;
                }
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_check_2) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                    is_check_1 = false;
                    is_check_2 = true;
                    is_check_3 = false;
                } else {
                    is_check_1 = false;
                    is_check_2 = false;
                    is_check_3 = false;
                }
            }
        });

        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_check_3) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    is_check_1 = false;
                    is_check_2 = false;
                    is_check_3 = true;
                } else {
                    is_check_1 = false;
                    is_check_2 = false;
                    is_check_3 = false;
                }
            }
        });

        mSelect_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deposit_start = edit_deposit_start.getText().toString();
                deposit_end = edit_deposit_end.getText().toString();
                rental_start = edit_rental_start.getText().toString();
                rental_end = edit_rental_end.getText().toString();
                base_start = edit_base_start.getText().toString();
                base_end = edit_base_end.getText().toString();

                Intent intent = new Intent(con, MainSearchActivity.class);
                intent.putExtra("gu", mGU);
                String dong = "";
                for (int i = 0; i < mDONG.size(); i++) {
                    if (i == mDONG.size() - 1)
                        dong += mDONG.get(i).toString();
                    else
                        dong += mDONG.get(i).toString() + "|";
                }
                intent.putExtra("dong", dong);
                String type = "";
                for (int i = 0; i < mTYPE.size(); i++) {
                    if (i == mTYPE.size() - 1)
                        type += mTYPE.get(i).toString();
                    else
                        type += mTYPE.get(i).toString() + "|";
                }
                intent.putExtra("type", type);
                intent.putExtra("pricetype", mPRICETYPE);
                intent.putExtra("deposit_start", deposit_start);
                intent.putExtra("deposit_end", deposit_end);
                intent.putExtra("rental_start", rental_start);
                intent.putExtra("rental_end", rental_end);
                intent.putExtra("base_start", base_start);
                intent.putExtra("base_end", base_end);
                if (checkBox1.isChecked())
                    intent.putExtra("option1", "오픈형");
                else if (checkBox2.isChecked())
                    intent.putExtra("option1", "분리형");
                else if (checkBox3.isChecked())
                    intent.putExtra("option1", "복층형");
                else
                    intent.putExtra("option1", "");

                if (checkBox4.isChecked())
                    intent.putExtra("option2", "1");
                else
                    intent.putExtra("option2", "");

                if (!MainActivity.UserID.equals("")) {
                    startActivity(intent);
                } else {
                    Intent in = new Intent(getActivity(), LoginActivity.class);
                    startActivity(in);
                }
            }
        });


        order_gunmul = (ImageButton) header.findViewById(R.id.order_gunmul);
        order_price = (ImageButton) header.findViewById(R.id.order_price);
        order_juso = (ImageButton) header.findViewById(R.id.order_juso);
        order_gunmul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mBang_array_recently, compareGunmul);
                mListAdapter_recently.notifyDataSetChanged();
                order_gunmul.setImageResource(R.mipmap.od_gunmul_on);
                order_price.setImageResource(R.mipmap.od_price_off);
                order_juso.setImageResource(R.mipmap.od_juso_off);
            }
        });
        order_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mBang_array_recently, comparePrice);
                mListAdapter_recently.notifyDataSetChanged();
                order_gunmul.setImageResource(R.mipmap.od_gunmul_off);
                order_price.setImageResource(R.mipmap.od_price_on);
                order_juso.setImageResource(R.mipmap.od_juso_off);
            }
        });
        order_juso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mBang_array_recently, compareJuso);
                mListAdapter_recently.notifyDataSetChanged();
                order_gunmul.setImageResource(R.mipmap.od_gunmul_off);
                order_price.setImageResource(R.mipmap.od_price_off);
                order_juso.setImageResource(R.mipmap.od_juso_on);
            }
        });

        //아이템 클릭 리스너

/*        mBanglist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if( !MainActivity.UserID.equals("") ){
                    Intent intent = new Intent(getActivity(),BangViewActivity.class);
                    intent.putExtra("bang_id",mBang_array.get(position).get_id());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
            }
        });*/

        //아이템 클릭 리스너
        mBanglist_recently.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int pos = position - 1;
                if (!MainActivity.UserID.equals("")) {
                    Intent intent = new Intent(getActivity(), BangViewActivity.class);
                    intent.putExtra("bang_id", mBang_array_recently.get(pos).get_id());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
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

        return v;
    }

    public final static Comparator<BangDataInfo> compareGunmul = new Comparator<BangDataInfo>() {
        private final Collator collator = Collator.getInstance();

        @Override
        public int compare(BangDataInfo object1, BangDataInfo object2) {
            return collator.compare(object1.getBuilding_name(),object2.getBuilding_name());
        }
    };
    public final static Comparator<BangDataInfo> comparePrice = new Comparator<BangDataInfo>() {
        private final Collator collator = Collator.getInstance();

        @Override
        public int compare(BangDataInfo object1, BangDataInfo object2) {
            int t1 = Integer.parseInt(object1.getDeposit()) + Integer.parseInt(object1.getMonthly_rental()) * 100;
            if (t1 < 5000)
                t1 = Integer.parseInt(object1.getDeposit()) + Integer.parseInt(object1.getMonthly_rental()) * 70;

            int t2 = Integer.parseInt(object2.getDeposit()) + Integer.parseInt(object2.getMonthly_rental()) * 100;
            if (t2 < 5000)
                t2 = Integer.parseInt(object2.getDeposit()) + Integer.parseInt(object2.getMonthly_rental()) * 70;

            String tt1 = String.format("%07d", t1 );
            String tt2 = String.format("%07d", t2 );
            return collator.compare(tt1,tt2);
            //return collator.compare(Integer.parseInt(object1.getDeposit()), Integer.parseInt(object2.getDeposit()) );
        }
    };
    public final static Comparator<BangDataInfo> compareJuso = new Comparator<BangDataInfo>() {
        private final Collator collator = Collator.getInstance();

        @Override
        public int compare(BangDataInfo object1, BangDataInfo object2) {
            return collator.compare(object1.getDong(), object2.getDong());
        }
    };


    //푸터레이아웃 클릭 리스너
    Button.OnClickListener footerClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.lay_notice_1:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.mNotice_array.get(0).getUrl().toString()));
                    startActivity(intent);
                    break;
                case R.id.lay_notice_2:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.mNotice_array.get(1).getUrl().toString()));
                    startActivity(intent);
                    break;
                case R.id.lay_notice_3:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.mNotice_array.get(2).getUrl().toString()));
                    startActivity(intent);
                    break;
                case R.id.lay_notice_4:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.mNotice_array.get(3).getUrl().toString()));
                    startActivity(intent);
                    break;
                case R.id.lay_notice_5:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.mNotice_array.get(4).getUrl().toString()));
                    startActivity(intent);
                    break;
                case R.id._more:
                    Log.d("tic", "tic");
                    //int p = (page - 1) * pageNum;
                    int querycount;
                    Cursor mCount = MainActivity.mdb.rawQuery("SELECT _id from recently_find Order by _id desc Limit " + pageNum, null);
                    querycount = mCount.getCount();
                    Cursor result = MainActivity.mdb.rawQuery("SELECT * from recently_find Order by _id desc Limit " + pageNum, null);
                    result.moveToFirst();
                    int i = 0;
                    while (i < querycount) {
                        BangDataInfo item = new BangDataInfo();
                        int _id = result.getInt(0);
                        String bang_id = result.getString(1);
                        item.set_id(bang_id);
                        item.setIs_available(result.getString(2));
                        item.setBuilding_name(result.getString(3));
                        item.setBuilding_hosu(result.getString(4));
                        item.setDong(result.getString(5));
                        item.setSangse_juso(result.getString(6));
                        item.setDeposit(result.getString(7));
                        item.setMonthly_rental(result.getString(8));
                        item.setEmpty(result.getString(9));
                        item.setPrice_type(result.getString(10));
                        item.setManage_price(result.getString(11));
                        item.setBang_type(result.getString(12));
                        item.setCall(result.getString(13));
                        item.setLat(result.getString(14));
                        item.setLng(result.getString(15));
                        item.setImg_url(result.getString(16));
                        int cnt;
                        Cursor count = MainActivity.mdb.rawQuery("SELECT count(*) from favorite WHERE bang_id=" + bang_id, null);
                        count.moveToFirst();
                        cnt = count.getInt(0);
                        if (cnt == 1) item.setIs_favorite("1");
                        else item.setIs_favorite("0");
                        cnt = 0;
                        for (int j = 0; j < mBang_array_recently.size(); j++) {
                            if (item.get_id().equals(mBang_array_recently.get(j).get_id())) {
                                cnt++;
                                break;
                            }
                        }
                        if (cnt == 0)
                            mBang_array_recently.add(item);
                        i++;
                        result.moveToNext();
                    }
                    if (querycount > 4)
                        pageNum = pageNum + 5;

                    mListAdapter_recently.notifyDataSetChanged();
                    break;
            }
        }
    };

    public void printGuLay() {
        mSelect_gu_text = (TextView) header.findViewById(R.id.select_gu_text);
        mSelect_gu_text.setText("구별");
        mSelect_dong_text = (TextView) header.findViewById(R.id.select_dong_text);
        mSelect_dong_text.setText("동별");
        mGU = "";
        mDONG.clear();
        mLay_select_gu_1 = (LinearLayout) header.findViewById(R.id.lay_select_gu_1);
        mLay_select_gu_2 = (LinearLayout) header.findViewById(R.id.lay_select_gu_2);
        mLay_select_gu_3 = (LinearLayout) header.findViewById(R.id.lay_select_gu_3);
        mLay_select_gu_4 = (LinearLayout) header.findViewById(R.id.lay_select_gu_4);
        mLay_select_gu_1.removeAllViewsInLayout();
        mLay_select_gu_2.removeAllViewsInLayout();
        mLay_select_gu_3.removeAllViewsInLayout();
        mLay_select_gu_4.removeAllViewsInLayout();
        final Button btn[] = new Button[16];
        for (int i = 0; i < btn.length; i++) {
            btn[i] = new Button(getActivity());
            btn[i].setId(i);
            btn[i].setText(gu[i]);
            btn[i].setPadding(5, 5, 5, 5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(35 * dm.density), 1);
            params.bottomMargin = size;
            params.leftMargin = size;
            if (i == 3 || i == 7 || i == 11 || i == 15)
                params.rightMargin = size;
            btn[i].setLayoutParams(params);
            btn[i].setBackgroundResource(R.drawable.select_btn);
            btn[i].setTextSize(txt_size);
            if (i < 4) {
                mLay_select_gu_1.addView(btn[i]);
            } else if (i < 8) {
                mLay_select_gu_2.addView(btn[i]);
            } else if (i < 12) {
                mLay_select_gu_3.addView(btn[i]);
            } else {
                mLay_select_gu_4.addView(btn[i]);
            }
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGU_pos = v.getId();
                    mGU = btn[mGU_pos].getText().toString();
                    mLay_select_gu.setVisibility(View.GONE);
                    is_lay_select_gu = false;
                    mSelect_gu_text.setText(mGU);
                    mLay_select_dong.setVisibility(View.VISIBLE);
                    is_lay_select_dong = true;
                    mGu_indicator.setImageResource(R.mipmap.arrow_down);
                    mDong_indicator.setImageResource(R.mipmap.arrow_up);
                    printDongLay(mGU_pos);

                    //Log.d("btn", dong[mGU_pos][0].toString() );
                }
            });
        }

    }

    public void printDongLay(int pos) {
        mLay_select_dong_1 = (LinearLayout) header.findViewById(R.id.lay_select_dong_1);
        mLay_select_dong_2 = (LinearLayout) header.findViewById(R.id.lay_select_dong_2);
        mLay_select_dong_3 = (LinearLayout) header.findViewById(R.id.lay_select_dong_3);
        mLay_select_dong_4 = (LinearLayout) header.findViewById(R.id.lay_select_dong_4);
        mLay_select_dong_5 = (LinearLayout) header.findViewById(R.id.lay_select_dong_5);
        mLay_select_dong_6 = (LinearLayout) header.findViewById(R.id.lay_select_dong_6);
        mLay_select_dong_1.removeAllViewsInLayout();
        mLay_select_dong_2.removeAllViewsInLayout();
        mLay_select_dong_3.removeAllViewsInLayout();
        mLay_select_dong_4.removeAllViewsInLayout();
        mLay_select_dong_5.removeAllViewsInLayout();
        mLay_select_dong_6.removeAllViewsInLayout();
        mLay_select_dong_3.setVisibility(View.GONE);
        mLay_select_dong_4.setVisibility(View.GONE);
        mLay_select_dong_5.setVisibility(View.GONE);
        mLay_select_dong_6.setVisibility(View.GONE);
        if (dong[pos].length >= 8)
            mLay_select_dong_3.setVisibility(View.VISIBLE);
        if (dong[pos].length >= 12)
            mLay_select_dong_4.setVisibility(View.VISIBLE);
        if (dong[pos].length >= 16)
            mLay_select_dong_5.setVisibility(View.VISIBLE);
        if (dong[pos].length >= 20)
            mLay_select_dong_6.setVisibility(View.VISIBLE);

        final Button btn_dong[] = new Button[dong[pos].length];
        int width = 0;
        for (int i = 0; i < btn_dong.length; i++) {
            btn_dong[i] = new Button(getActivity());
            btn_dong[i].setId(i);
            btn_dong[i].setText(dong[pos][i]);
            btn_dong[i].setPadding(5, 5, 5, 5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, Math.round(35 * dm.density), 1);
            params.bottomMargin = size;
            params.leftMargin = size;
            if (i == 3 || i == 7 || i == 11 || i == 15 || i == 19)
                params.rightMargin = size;

            btn_dong[i].setLayoutParams(params);
            btn_dong[i].setBackgroundResource(R.drawable.select_btn_off);
            btn_dong[i].setTextSize(txt_size);
            if (i < 4) {
                mLay_select_dong_1.addView(btn_dong[i]);
            } else if (i < 8) {
                mLay_select_dong_2.addView(btn_dong[i]);
            } else if (i < 12) {
                mLay_select_dong_3.addView(btn_dong[i]);
            } else if (i < 16) {
                mLay_select_dong_4.addView(btn_dong[i]);
            } else if (i < 20) {
                mLay_select_dong_5.addView(btn_dong[i]);
            } else {
                mLay_select_dong_6.addView(btn_dong[i]);
            }
            btn_dong[i].setId(i);
            btn_dong[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tmp_dong = btn_dong[v.getId()].getText().toString();
                    if (!is_dong_select[mGU_pos][v.getId()]) {
                        mDONG.add(tmp_dong);
                        btn_dong[v.getId()].setBackgroundResource(R.drawable.select_btn_on);
                        String txt = "";
                        for (int j = 0; j < mDONG.size(); j++)
                            txt += " " + mDONG.get(j).toString();
                        mSelect_dong_text.setText(txt);

                        is_dong_select[mGU_pos][v.getId()] = true;
                    } else {
                        btn_dong[v.getId()].setBackgroundResource(R.drawable.select_btn_off);
                        mDONG.remove(tmp_dong);
                        String txt = "";
                        for (int j = 0; j < mDONG.size(); j++)
                            txt += " " + mDONG.get(j).toString();
                        if (txt.equals(""))
                            txt = "동별";
                        mSelect_dong_text.setText(txt);

                        is_dong_select[mGU_pos][v.getId()] = false;
                    }
                    is_lay_select_dong = true;
                }
            });
        }

        // 13
        if (dong[pos].length % 4 != 0) {
            int tmp = dong[pos].length % 4;
            Button btn_dong_add[] = new Button[4 - tmp];
            for (int i = 0; i < 4 - tmp; i++) {
                btn_dong_add[i] = new Button(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, Math.round(35 * dm.density), 1);
                params.bottomMargin = size;
                params.leftMargin = size;
                if (i == 3 - tmp) {
                    params.rightMargin = size;
                    btn_dong_add[i].setId(i);
                    btn_dong_add[i].setBackgroundResource(R.drawable.select_btn);
                    btn_dong_add[i].setPadding(5, 5, 5, 5);
                    btn_dong_add[i].setText("선택");
                    btn_dong_add[i].setTextSize(txt_size);
                } else {
                    btn_dong_add[i].setVisibility(View.INVISIBLE);
                }

                btn_dong_add[i].setLayoutParams(params);
                if (dong[pos].length < 8) {
                    mLay_select_dong_2.addView(btn_dong_add[i]);
                } else if (dong[pos].length < 12) {
                    mLay_select_dong_3.addView(btn_dong_add[i]);
                } else if (dong[pos].length < 16) {
                    mLay_select_dong_4.addView(btn_dong_add[i]);
                } else if (dong[pos].length < 20) {
                    mLay_select_dong_5.addView(btn_dong_add[i]);
                } else {
                    mLay_select_dong_6.addView(btn_dong_add[i]);
                }

                //선택버튼
                if (i == 3 - tmp) {
                    btn_dong_add[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mLay_select_dong.setVisibility(View.GONE);
                            is_lay_select_dong = false;
                            mDong_indicator.setImageResource(R.mipmap.arrow_down);
                        }
                    });
                }
            }
        } else {
            Button btn_dong_add[] = new Button[4];
            for (int i = 0; i < 4; i++) {
                btn_dong_add[i] = new Button(getActivity());
                btn_dong_add[i].setVisibility(View.INVISIBLE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, Math.round(35 * dm.density), 1);
                params.bottomMargin = size;
                params.leftMargin = size;
                if (i == 3) {
                    params.rightMargin = size;
                    btn_dong_add[i].setVisibility(View.VISIBLE);
                    btn_dong_add[i].setId(i);
                    btn_dong_add[i].setBackgroundResource(R.drawable.select_btn);
                    btn_dong_add[i].setPadding(5, 5, 5, 5);
                    btn_dong_add[i].setText("선택");
                    btn_dong_add[i].setTextSize(txt_size);
                }
                btn_dong_add[i].setLayoutParams(params);
                if (dong[pos].length < 8) {
                    mLay_select_dong_2.addView(btn_dong_add[i]);
                } else if (dong[pos].length < 12) {
                    mLay_select_dong_3.addView(btn_dong_add[i]);
                } else if (dong[pos].length < 16) {
                    mLay_select_dong_4.addView(btn_dong_add[i]);
                } else if (dong[pos].length < 20) {
                    mLay_select_dong_5.addView(btn_dong_add[i]);
                } else {
                    mLay_select_dong_6.addView(btn_dong_add[i]);
                }

                //선택버튼
                if (i == 3) {
                    btn_dong_add[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mLay_select_dong.setVisibility(View.GONE);
                            is_lay_select_dong = false;
                            mDong_indicator.setImageResource(R.mipmap.arrow_down);
                        }
                    });
                }
            }
        }
    }

    public void printTypeLay() {
        mSelect_type_text = (TextView) header.findViewById(R.id.select_type_text);
        mSelect_type_text.setText("구분");
        mTYPE.clear();
        mLay_select_type_1 = (LinearLayout) header.findViewById(R.id.lay_select_type_1);
        mLay_select_type_2 = (LinearLayout) header.findViewById(R.id.lay_select_type_2);
        mLay_select_type_1.removeAllViewsInLayout();
        mLay_select_type_2.removeAllViewsInLayout();
        final Button btn[] = new Button[6];
        for (int i = 0; i < btn.length; i++) {
            btn[i] = new Button(getActivity());
            btn[i].setId(i);
            btn[i].setText(type[i]);
            btn[i].setPadding(5, 5, 5, 5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(35 * dm.density), 1);
            params.bottomMargin = size;
            params.leftMargin = size;
            if (i == 3)
                params.rightMargin = size;
            btn[i].setLayoutParams(params);
            btn[i].setBackgroundResource(R.drawable.select_btn_off);
            btn[i].setTextSize(txt_size);
            if (i < 4) {
                mLay_select_type_1.addView(btn[i]);
            } else {
                mLay_select_type_2.addView(btn[i]);
            }
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!is_type[v.getId()]) {
                        btn[v.getId()].setBackgroundResource(R.drawable.select_btn_on);
                        is_type[v.getId()] = true;
                        mTYPE.add(type[v.getId()]);
                        String txt = "";
                        for (int i = 0; i < mTYPE.size(); i++)
                            txt += " " + mTYPE.get(i).toString();
                        mSelect_type_text.setText(txt);

                    } else {
                        btn[v.getId()].setBackgroundResource(R.drawable.select_btn_off);
                        is_type[v.getId()] = false;
                        mTYPE.remove(type[v.getId()]);
                        String txt = "";
                        for (int i = 0; i < mTYPE.size(); i++)
                            txt += " " + mTYPE.get(i).toString();
                        if (txt.equals(""))
                            txt = "구분";
                        mSelect_type_text.setText(txt);
                    }
                }
            });
        }

        Button btn_add[] = new Button[2];
        for (int i = 0; i < 2; i++) {
            btn_add[i] = new Button(getActivity());
            btn_add[i].setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(35 * dm.density), 1);
            params.bottomMargin = size;
            params.leftMargin = size;
            if (i == 1) {
                params.rightMargin = size;
                btn_add[i].setVisibility(View.VISIBLE);
                btn_add[i].setId(i);
                btn_add[i].setText("선택");
                btn_add[i].setBackgroundResource(R.drawable.select_btn);
                btn_add[i].setPadding(5, 5, 5, 5);
                btn_add[i].setTextSize(txt_size);
                btn_add[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLay_select_type.setVisibility(View.GONE);
                        is_lay_select_type = false;
                        mType_indicator.setImageResource(R.mipmap.arrow_down);
                    }
                });
            }
            btn_add[i].setLayoutParams(params);
            mLay_select_type_2.addView(btn_add[i]);
        }

    }

    public void printPriceTypeLay() {
        mPRICETYPE = "";
        mLay_select_pricetype_1 = (LinearLayout) header.findViewById(R.id.lay_select_pricetype_1);
        mLay_select_pricetype_1.removeAllViewsInLayout();
        mSelect_pricetype_text = (TextView) header.findViewById(R.id.select_pricetype_text);
        mSelect_pricetype_text.setText("전/월세");
        final Button btn[] = new Button[2];
        for (int i = 0; i < btn.length; i++) {
            btn[i] = new Button(getActivity());
            btn[i].setId(i);
            btn[i].setText(pricetype[i]);
            btn[i].setPadding(5, 5, 5, 5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(35 * dm.density), 1);
            params.bottomMargin = size;
            params.leftMargin = size;
            btn[i].setLayoutParams(params);
            btn[i].setBackgroundResource(R.drawable.select_btn);
            btn[i].setTextSize(txt_size);
            mLay_select_pricetype_1.addView(btn[i]);
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPRICETYPE = btn[v.getId()].getText().toString();
                    mSelect_pricetype_text.setText(mPRICETYPE);
                    mLay_select_pricetype.setVisibility(View.GONE);
                    is_lay_select_pricetype = false;
                    mPricetype_indicator.setImageResource(R.mipmap.arrow_down);
                }
            });
        }
        final Button btn_add[] = new Button[2];
        for (int i = 0; i < btn_add.length; i++) {
            btn_add[i] = new Button(getActivity());
            btn_add[i].setId(i);
            btn_add[i].setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(35 * dm.density), 1);
            params.bottomMargin = size;
            params.leftMargin = size;
            if (i == 1) {
                //btn_add[i].setVisibility(View.VISIBLE);
                params.rightMargin = size;
                btn_add[i].setText("선택");
                btn_add[i].setPadding(5, 5, 5, 5);
                btn_add[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPRICETYPE = btn[v.getId()].getText().toString();
                        mLay_select_pricetype.setVisibility(View.GONE);
                        is_lay_select_pricetype = false;
                        mPricetype_indicator.setImageResource(R.mipmap.arrow_down);
                    }
                });
            }
            btn_add[i].setTextSize(txt_size);
            btn_add[i].setLayoutParams(params);
            btn_add[i].setBackgroundResource(R.drawable.select_btn);
            mLay_select_pricetype_1.addView(btn_add[i]);
        }
    }

    public void getBangInfoFromJSON() {
        String url = "http://dlawogus1.cafe24.com/app/new_bang_list.php";
        try {
            String line = MainActivity.getStringFromUrl(url);

            // 원격에서 읽어온 데이터로 JSON 객체 생성
            JSONObject object = new JSONObject(line);
            JSONArray objectArray = new JSONArray(object.getString("bang"));
            for (int i = 0; i < objectArray.length(); i++) {
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
                Cursor count = MainActivity.mdb.rawQuery("SELECT count(*) from favorite", null);
                count.moveToFirst();
                cnt = count.getInt(0);
                if (cnt == 1) datainfo.setIs_favorite("1");
                else datainfo.setIs_favorite("0");
                mBang_array.add(datainfo);
            }
            Log.d("json받은값", line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class BangListTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            if (!MainActivity.isNetworkStat(con)) {
                this.onCancelled();
            }
            pd = new ProgressDialog(con);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            pd.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Void doInBackground(String... strs) {
            if (MainActivity.appFirstStart)
                getBangInfoFromJSON();

            return null;
        } // doInBackground : 백그라운드 작업을 진행한다.

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pd.dismiss();
            mListAdapter.notifyDataSetChanged();
        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    } // JsonLoa

}
