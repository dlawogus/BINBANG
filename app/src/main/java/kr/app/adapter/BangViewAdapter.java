package kr.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import kr.app.binbang.MainActivity;
import kr.app.binbang.R;

/**
 * Created by imjaehyun on 15. 9. 6..
 */
public class BangViewAdapter extends PagerAdapter {
    private LayoutInflater mInflater;
    public Context con;
    private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();
    private ArrayList<String> mItemList;
    private DisplayImageOptions options;

    public BangViewAdapter(Context context ,ArrayList<String> urllist) {
        super();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        con = context;
        this.mItemList = urllist;

        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.list_default)          //Error image
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
                .build();
    }

    // PagerAdapter에서 관리할 View 개수를 반환합니다.
    public int getCount() { return mItemList.size(); }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        View view=null;

        //새로운 View 객체를 Layoutinflater를 이용해서 생성
        view= mInflater.inflate(R.layout.view_fr_1, null);

        //만들어진 View안에 있는 ImageView 객체 참조
        //위에서 inflated 되어 만들어진 view로부터 findViewById()를 해야 하는 것에 주의.
        ImageView img= (ImageView) view.findViewById(R.id.imageView);
        Drawable alpha = ((ImageView) view.findViewById(R.id.mark)).getDrawable();
        alpha.setAlpha(80);

        ImageLoader.getInstance().displayImage(mItemList.get(position), img, options, animateFirstListener);

        //ImageLoader imageLoader = ImageLoader.getInstance();
        //ImageAware imageAware = new ImageViewAware(img, false);
        //imageLoader.displayImage(mItemList.get(position), imageAware, MainActivity.options);

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