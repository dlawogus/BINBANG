package kr.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import kr.app.binbang.MainActivity;
import kr.app.binbang.R;
import kr.app.customalert.CustomDialog;
import kr.app.datainfo.AdDataInfo;

/**
 * Created by imjaehyun on 15. 9. 6..
 */
public class AdPagerAdapter extends PagerAdapter {
    private LayoutInflater mInflater;
    public Context con;
    private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();
    private ArrayList<AdDataInfo> mItemList;
    private CustomDialog alert;

    public AdPagerAdapter(Context context, ArrayList<AdDataInfo> arraylist) {
        super();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        con = context;
        this.mItemList = arraylist;
    }

    // PagerAdapter에서 관리할 View 개수를 반환합니다.
    public int getCount() { return mItemList.size(); }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // TODO Auto-generated method stub

        View view=null;

        //새로운 View 객체를 Layoutinflater를 이용해서 생성
        //만들어질 View의 설계는 res폴더>>layout폴더>>viewpater_childview.xml 레이아웃 파일 사용
        view= mInflater.inflate(R.layout.ad_fr_1, null);

        //만들어진 View안에 있는 ImageView 객체 참조
        //위에서 inflated 되어 만들어진 view로부터 findViewById()를 해야 하는 것에 주의.
        ImageView img= (ImageView)view.findViewById(R.id.imageView);

        //ImageView에 현재 position 번째에 해당하는 이미지를 보여주기 위한 작업
        //현재 position에 해당하는 이미지를 setting
        //img.setImageResource(R.drawable.gametitle_01+position);
        //Toast.makeText(con, mItemList.get(position).getUrl().toString(), Toast.LENGTH_SHORT).show();
        ImageLoader.getInstance().displayImage(mItemList.get(position).getUrl(), img, MainActivity.options, animateFirstListener);
        //ViewPager에 만들어 낸 View 추가

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert = new CustomDialog(con,mItemList.get(position).getCall(),
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                alert.dismiss();
                            }
                        },
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mItemList.get(position).getCall()));
                                con.startActivity(intent);
                                alert.dismiss();
                            }
                        }
                );
                alert.show();
            }
        });
        container.addView(view);

        //Image가 세팅된 View를 리턴
        return view;
    }

    // View를 삭제합니다.
    public void destroyItem(View pager, int position, Object view) {
        ((ViewPager)pager).removeView((View)view);
    }

    // instantiateItem에서 생성한 객체를 이용할 것인지 여부를 반환합니다.
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }


}