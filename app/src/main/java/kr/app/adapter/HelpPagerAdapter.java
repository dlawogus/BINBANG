package kr.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import kr.app.binbang.R;

/**
 * Created by imjaehyun on 15. 9. 4..
 */
public class HelpPagerAdapter extends PagerAdapter {
    private LayoutInflater mInflater;
    public Context mContext;

    public HelpPagerAdapter(Context context) {
        super();
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    // ViewPager에서 사용할 View를 생성하고 등록해줍니다.
    // PagerAdapter에서 관리할 View 개수를 반환합니다.
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // TODO Auto-generated method stub

        View view=null;
        //새로운 View 객체를 Layoutinflater를 이용해서 생성
        //만들어질 View의 설계는 res폴더>>layout폴더>>viewpater_childview.xml 레이아웃 파일 사용
        view= mInflater.inflate(R.layout.help_fr_1, null);
        //만들어진 View안에 있는 ImageView 객체 참조
        //위에서 inflated 되어 만들어진 view로부터 findViewById()를 해야 하는 것에 주의.
        ImageView img= (ImageView)view.findViewById(R.id.help_image);

        if( position == 0){
            img.setImageResource(R.mipmap.ic_launcher);
        }else if( position == 1){
            img.setImageResource(R.mipmap.ic_launcher);
        }else if( position == 2){
            img.setImageResource(R.mipmap.ic_launcher);
        }else if( position == 3){
            img.setImageResource(R.mipmap.ic_launcher);
        }else{
            img.setImageResource(R.mipmap.ic_launcher);
        }
        //ViewPager에 만들어 낸 View 추가
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