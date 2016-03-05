package kr.app.binbang;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import kr.app.adapter.BangListFavoriteApt;
import kr.app.datainfo.BangDataInfo;


public class MainFragment_2 extends Fragment {

    //즐겨찾기
    public static ArrayList<BangDataInfo> mBang_array_favorite;
    private ListView mListView;
    public static BangListFavoriteApt mListAdapter_favorite;

    //공지사항
    private TextView mNotice1;
    private TextView mNotice2;
    private TextView mNotice3;
    private TextView mNotice4;
    private TextView mNotice5;
    private LinearLayout mLayNotive1;
    private LinearLayout mLayNotive2;
    private LinearLayout mLayNotive3;
    private LinearLayout mLayNotive4;
    private LinearLayout mLayNotive5;
    //더보기
    private Button mMore_btn;
    private int page = 2;
    private int pageNum = 10;

    private ImageButton order_gunmul;
    private ImageButton order_price;
    private ImageButton order_juso;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.main_fr_2, container, false);

        MainActivity.Fragment_two_exe = true;

        mBang_array_favorite = new ArrayList<BangDataInfo>();
        int cnt_favorite;
        Cursor mCount_favorite = MainActivity.mdb.rawQuery("SELECT _id from favorite Order by _id desc Limit 5" , null);
        cnt_favorite = mCount_favorite.getCount();
        Cursor result_favorite = MainActivity.mdb.rawQuery("SELECT * from favorite Order by _id desc Limit 5", null);
        result_favorite.moveToFirst();
        int i=0;
        while(i < cnt_favorite) {
            BangDataInfo item = new BangDataInfo();
            int _id = result_favorite.getInt(0);
            item.set_id(result_favorite.getString(1));
            item.setIs_available(result_favorite.getString(2));
            item.setBuilding_name(result_favorite.getString(3));
            item.setBuilding_hosu(result_favorite.getString(4));
            item.setDong(result_favorite.getString(5));
            item.setSangse_juso(result_favorite.getString(6));
            item.setDeposit(result_favorite.getString(7));
            item.setMonthly_rental(result_favorite.getString(8));
            item.setEmpty(result_favorite.getString(9));
            item.setPrice_type(result_favorite.getString(10));
            item.setManage_price(result_favorite.getString(11));
            item.setBang_type(result_favorite.getString(12));
            item.setCall(result_favorite.getString(13));
            item.setLat(result_favorite.getString(14));
            item.setLng(result_favorite.getString(15));
            item.setImg_url(result_favorite.getString(16));
            item.setIs_favorite("1");
            mBang_array_favorite.add(item);
            i++;
            result_favorite.moveToNext();
        }


        mListView = (ListView) v.findViewById(R.id.listView);
        mListAdapter_favorite = new BangListFavoriteApt(getActivity(), mBang_array_favorite);

        View footer = getActivity().getLayoutInflater().inflate(R.layout.list_footer, null, false);
        View header = getActivity().getLayoutInflater().inflate(R.layout.search_header, null, false);
        mNotice1 = (TextView)footer.findViewById(R.id.notice_1);
        mNotice2 = (TextView)footer.findViewById(R.id.notice_2);
        mNotice3 = (TextView)footer.findViewById(R.id.notice_3);
        mNotice4 = (TextView)footer.findViewById(R.id.notice_4);
        mNotice5 = (TextView)footer.findViewById(R.id.notice_5);
        mLayNotive1 = (LinearLayout) footer.findViewById(R.id.lay_notice_1);
        mLayNotive2 = (LinearLayout) footer.findViewById(R.id.lay_notice_2);
        mLayNotive3 = (LinearLayout) footer.findViewById(R.id.lay_notice_3);
        mLayNotive4 = (LinearLayout) footer.findViewById(R.id.lay_notice_4);
        mLayNotive5 = (LinearLayout) footer.findViewById(R.id.lay_notice_5);
        mMore_btn = (Button) footer.findViewById(R.id._more);
        if (MainActivity.is_notice_set) {
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
        mListView.addFooterView(footer, null, false);
        mListView.addHeaderView(header, null, false);
        mListView.setAdapter(mListAdapter_favorite);

        order_gunmul = (ImageButton) header.findViewById(R.id.order_gunmul);
        order_price = (ImageButton) header.findViewById(R.id.order_price);
        order_juso = (ImageButton) header.findViewById(R.id.order_juso);
        order_gunmul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mBang_array_favorite, MainFragment_1.compareGunmul);
                mListAdapter_favorite.notifyDataSetChanged();
                order_gunmul.setImageResource(R.mipmap.od_gunmul_on);
                order_price.setImageResource(R.mipmap.od_price_off);
                order_juso.setImageResource(R.mipmap.od_juso_off);
            }
        });
        order_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mBang_array_favorite, MainFragment_1.comparePrice);
                mListAdapter_favorite.notifyDataSetChanged();
                order_gunmul.setImageResource(R.mipmap.od_gunmul_off);
                order_price.setImageResource(R.mipmap.od_price_on);
                order_juso.setImageResource(R.mipmap.od_juso_off);
            }
        });
        order_juso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mBang_array_favorite, MainFragment_1.compareJuso);
                mListAdapter_favorite.notifyDataSetChanged();
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
                    Intent intent = new Intent(getActivity(), BangViewActivity.class);
                    intent.putExtra("bang_id", mBang_array_favorite.get(pos).get_id());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        return  v;
    }

    //푸터레이아웃 클릭 리스너
    Button.OnClickListener footerClick = new View.OnClickListener(){
        public void onClick(View v) {
            Intent intent;
            switch(v.getId()){
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
                    int cnt_favorite;
                    Cursor mCount = MainActivity.mdb.rawQuery("SELECT _id from favorite Order by _id desc Limit "+pageNum, null);
                    cnt_favorite = mCount.getCount();
                    Cursor result_favorite = MainActivity.mdb.rawQuery("SELECT * from favorite Order by _id desc Limit " + pageNum, null);
                    result_favorite.moveToFirst();
                    int i=0;
                    while(i < cnt_favorite) {
                        BangDataInfo item = new BangDataInfo();
                        int _id = result_favorite.getInt(0);
                        item.set_id(result_favorite.getString(1));
                        item.setIs_available(result_favorite.getString(2));
                        item.setBuilding_name(result_favorite.getString(3));
                        item.setBuilding_hosu(result_favorite.getString(4));
                        item.setDong(result_favorite.getString(5));
                        item.setSangse_juso(result_favorite.getString(6));
                        item.setDeposit(result_favorite.getString(7));
                        item.setMonthly_rental(result_favorite.getString(8));
                        item.setEmpty(result_favorite.getString(9));
                        item.setPrice_type(result_favorite.getString(10));
                        item.setManage_price(result_favorite.getString(11));
                        item.setBang_type(result_favorite.getString(12));
                        item.setCall(result_favorite.getString(13));
                        item.setLat(result_favorite.getString(14));
                        item.setLng(result_favorite.getString(15));
                        item.setImg_url(result_favorite.getString(16));
                        item.setIs_favorite("1");
                        int cnt = 0;
                        for(int j = 0; j< mBang_array_favorite.size(); j++){
                            if( item.get_id().equals(mBang_array_favorite.get(j).get_id()) ){
                                cnt++;
                                break;
                            }
                        }
                        if( cnt == 0 )
                            mBang_array_favorite.add(item);
                        i++;
                        result_favorite.moveToNext();
                    }

                    if( cnt_favorite > 4)
                        pageNum = pageNum + 5;

                    mListAdapter_favorite.notifyDataSetChanged();
                    break;
            }
        }
    };
}
