package kr.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import kr.app.dbhandle.DbAdapter;
import kr.app.binbang.MainActivity;
import kr.app.binbang.MainFragment_1;
import kr.app.binbang.MainFragment_2;
import kr.app.binbang.MapActivity;
import kr.app.binbang.R;
import kr.app.customalert.CustomDialog;
import kr.app.datainfo.BangDataInfo;

/**
 * Created by imjaehyun on 15. 9. 4..
 */
public class BangListFavoriteApt extends BaseAdapter {
    private ArrayList<BangDataInfo> mItemList;
    private Context con;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;
    private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();
    private DbAdapter dbadapter;
    private CustomDialog alert;

    public BangListFavoriteApt(Context context, ArrayList<BangDataInfo> objects) {
        //super(context, resource, objects);
        this.mItemList = objects;
        this.con = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dbadapter = new DbAdapter(con);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final int pos = position;
        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_item_bang, null);
            viewHolder.img_bang = (ImageView) v.findViewById(R.id.img_bang);
            viewHolder.img_bang_empty = (ImageView) v.findViewById(R.id.img_bang_empty);
            viewHolder.text_gunmul = (TextView) v.findViewById(R.id.text_gunmul);
            viewHolder.text_empty = (TextView) v.findViewById(R.id.text_empty);
            viewHolder.text_juso = (TextView) v.findViewById(R.id.text_juso);
            viewHolder.text_price = (TextView) v.findViewById(R.id.text_price);
            viewHolder.text_pricetype = (TextView) v.findViewById(R.id.text_pricetype);
            viewHolder.text_manageprice = (TextView) v.findViewById(R.id.text_manageprice);
            viewHolder.img_bangtype = (ImageView) v.findViewById(R.id.img_bangtype);
            viewHolder.text_bangtype = (TextView) v.findViewById(R.id.text_bangtype);
            viewHolder.btn_img_call_open = (ImageButton) v.findViewById(R.id.btn_img_call_open);
            viewHolder.btn_img_call = (ImageButton) v.findViewById(R.id.btn_img_call);
            viewHolder.btn_img_loc = (ImageButton) v.findViewById(R.id.btn_img_loc);
            viewHolder.btn_img_favorite = (ImageButton) v.findViewById(R.id.btn_img_favorite);
            viewHolder.layout_call = (ViewGroup) v.findViewById(R.id.layout_call);
            viewHolder.call_point = (ImageView) v.findViewById(R.id.call_point);
            viewHolder.content = (ViewGroup) v.findViewById(R.id.content);
            setGlobalFont(viewHolder.content);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        if( mItemList.get(pos).getIs_available().equals("1") ) {
            viewHolder.text_gunmul.setText(mItemList.get(pos).getBuilding_name()+" "+mItemList.get(pos).getBuilding_hosu());
            viewHolder.text_empty.setText(mItemList.get(pos).getEmpty());
            viewHolder.text_juso.setText(mItemList.get(pos).getDong()+" "+mItemList.get(pos).getSangse_juso());
            //viewHolder.text_price.setText(mItemList.get(pos).getPrice() );
            if (mItemList.get(pos).getMonthly_rental().equals("0")) {
                viewHolder.text_price.setText(mItemList.get(pos).getDeposit());
            } else {
                viewHolder.text_price.setText(mItemList.get(pos).getDeposit() + "/" + mItemList.get(pos).getMonthly_rental());
            }
            viewHolder.text_pricetype.setText(mItemList.get(pos).getPrice_type());
            if( !mItemList.get(pos).getManage_price().equals("") )
                viewHolder.text_manageprice.setText("(관리비 "+mItemList.get(pos).getManage_price()+")");
            else
                viewHolder.text_manageprice.setText("");
            viewHolder.text_bangtype.setText(mItemList.get(pos).getBang_type());
            String t = mItemList.get(pos).getBang_type();
            if( t.equals("원룸") )
                viewHolder.img_bangtype.setImageResource(R.mipmap.bang_1);
            else if ( t.equals("원룸 오픈형") )
                viewHolder.img_bangtype.setImageResource(R.mipmap.bang_1);
            else if ( t.equals("원룸(오픈형)"))
                viewHolder.img_bangtype.setImageResource(R.mipmap.bang_1);
            else if ( t.equals("원룸 분리형") )
                viewHolder.img_bangtype.setImageResource(R.mipmap.bang_1_half);
            else if ( t.equals("원룸(분리형)") )
                viewHolder.img_bangtype.setImageResource(R.mipmap.bang_1_half);
            else if( t.matches(".*투룸.*"))
                viewHolder.img_bangtype.setImageResource(R.mipmap.bang_2);
            else if( t.matches(".*쓰리룸.*") )
                viewHolder.img_bangtype.setImageResource(R.mipmap.bang_3);
            else if( t.matches(".*오피스텔.*") )
                viewHolder.img_bangtype.setImageResource(R.mipmap.bang_office);
            else
                viewHolder.img_bangtype.setImageResource(R.mipmap.bang_ex);
        }

        //전화 펼침 버튼
        viewHolder.btn_img_call_open.setFocusable(false);
        viewHolder.btn_img_call_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !mItemList.get(pos).getIs_calllay_open() ) {
                    for(int i=0; i<mItemList.size(); i++){
                        mItemList.get(i).setIs_calllay_open(false);
                    }
                    mItemList.get(pos).setIs_calllay_open(true);
                    //MainFragment_1.mListAdapter.notifyDataSetChanged();
                    MainFragment_1.mListAdapter_recently.notifyDataSetChanged();
                    MainFragment_2.mListAdapter_favorite.notifyDataSetChanged();
                }else{
                    mItemList.get(pos).setIs_calllay_open(false);
                    //MainFragment_1.mListAdapter.notifyDataSetChanged();
                    MainFragment_1.mListAdapter_recently.notifyDataSetChanged();
                    MainFragment_2.mListAdapter_favorite.notifyDataSetChanged();
                }
            }
        });

        //전화걸기
        viewHolder.btn_img_call.setFocusable(false);
        viewHolder.btn_img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert = new CustomDialog(con,mItemList.get(pos).getCall(),
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                alert.dismiss();
                            }
                        },
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mItemList.get(pos).getCall()));
                                con.startActivity(intent);
                                alert.dismiss();
                            }
                        }
                );
                alert.show();
            }
        });

        //위치보기
        viewHolder.btn_img_loc.setFocusable(false);
        viewHolder.btn_img_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !mItemList.get(pos).getLat().equals("") && !mItemList.get(pos).getLng().equals("") ) {
                    Intent intent = new Intent(con, MapActivity.class);
                    intent.putExtra("index", 1);
                    intent.putExtra("lat", mItemList.get(pos).getLat());
                    intent.putExtra("lng", mItemList.get(pos).getLng());
                    con.startActivity(intent);
                }else{
                    Toast.makeText(con, "위치데이터가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //즐겨찾기
        viewHolder.btn_img_favorite.setFocusable(false);
        viewHolder.btn_img_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mItemList.get(pos).getIs_favorite().equals("1") ){
                    dbadapter.remove_favorite( mItemList.get(pos) );
                    mItemList.remove(pos);
                    MainFragment_1.mListAdapter_recently.notifyDataSetChanged();
                    MainFragment_2.mListAdapter_favorite.notifyDataSetChanged();
                }else{
                    dbadapter.insert_favorite(mItemList.get(pos));
                    mItemList.get(pos).setIs_favorite("1");
                    MainFragment_1.mListAdapter_recently.notifyDataSetChanged();
                    MainFragment_2.mListAdapter_favorite.notifyDataSetChanged();
                }
            }
        });

        //레이아웃 펼침
        if( mItemList.get(pos).getIs_calllay_open() ) {
            viewHolder.layout_call.setVisibility(View.VISIBLE);
            viewHolder.call_point.setVisibility(View.VISIBLE);
        }else {
            viewHolder.layout_call.setVisibility(View.GONE);
            viewHolder.call_point.setVisibility(View.INVISIBLE);
        }

        //방 이미지
        if( mItemList.get(pos).getImg_url().equals("") ) {
            viewHolder.img_bang.setVisibility(View.GONE);
            viewHolder.img_bang_empty.setVisibility(View.VISIBLE);
        }else {
            viewHolder.img_bang.setVisibility(View.VISIBLE);
            viewHolder.img_bang_empty.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(mItemList.get(pos).getImg_url(), viewHolder.img_bang, MainActivity.options, animateFirstListener);
            Log.d("list_img", mItemList.get(pos).getImg_url().toString());
        }

        //즐겨찾기
        if( mItemList.get(pos).getIs_favorite().equals("1") )
            viewHolder.btn_img_favorite.setImageResource(R.mipmap.list_favorite_on);



        return v;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void setGlobalFont(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (child instanceof TextView)
                ((TextView)child).setTypeface(MainActivity.mTypeface);
            else if (child instanceof ViewGroup)
                setGlobalFont((ViewGroup)child);
        }
    }
    /*
     * ViewHolder
     * getView의 속도 향상을 위해 쓴다.
     * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
     */
    class ViewHolder {
        private ImageView img_bang;
        private ImageView img_bang_empty;
        private TextView text_gunmul;
        private TextView text_empty;
        private TextView text_juso;
        private TextView text_bangtype;
        private TextView text_price;
        private TextView text_pricetype;
        private TextView text_manageprice;
        private ImageView img_bangtype;
        private ImageView call_point;
        private ImageButton btn_img_call_open;
        private ImageButton btn_img_call;
        private ImageButton btn_img_loc;
        private ImageButton btn_img_favorite;
        private ViewGroup layout_call;
        private ViewGroup content;
    }
}