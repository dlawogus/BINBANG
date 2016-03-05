package kr.app.help;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;

import kr.app.adapter.HelpPagerAdapter;
import kr.app.binbang.R;

public class HelpFirst extends Activity {
    private String token;
    // 값 저장하기
    private void savePreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TelephonyManager telephony = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        token = telephony.getDeviceId();    //device id
        savePreferences();

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new HelpPagerAdapter(this));

/*
        mClosebtn = (ImageButton)findViewById(R.id.btn_close);
        mClosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
*/

/*        mIndicater1 = (ImageView)findViewById(R.id.slide_scroll_1);
        mIndicater2 = (ImageView)findViewById(R.id.slide_scroll_2);
        mIndicater3 = (ImageView)findViewById(R.id.slide_scroll_3);
        mIndicater4 = (ImageView)findViewById(R.id.slide_scroll_4);*/
        //position=pager.getCurrentItem();

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // if listener is set - when using an indicator, must update that here
                //mIndicator.setCurrentItem(position);
/*                if (position == 0) {
                    mIndicater1.setImageResource(R.drawable.slide_scroll_1);
                    mIndicater2.setImageResource(R.drawable.slide_scroll);
                    mIndicater3.setImageResource(R.drawable.slide_scroll);
                    mIndicater4.setImageResource(R.drawable.slide_scroll);
                } else if (position == 1) {
                    mIndicater1.setImageResource(R.drawable.slide_scroll);
                    mIndicater2.setImageResource(R.drawable.slide_scroll_1);
                    mIndicater3.setImageResource(R.drawable.slide_scroll);
                    mIndicater4.setImageResource(R.drawable.slide_scroll);
                } else if (position == 2) {
                    mIndicater1.setImageResource(R.drawable.slide_scroll);
                    mIndicater2.setImageResource(R.drawable.slide_scroll);
                    mIndicater3.setImageResource(R.drawable.slide_scroll_1);
                    mIndicater4.setImageResource(R.drawable.slide_scroll);
                } else if (position == 3) {
                    mIndicater1.setImageResource(R.drawable.slide_scroll);
                    mIndicater2.setImageResource(R.drawable.slide_scroll);
                    mIndicater3.setImageResource(R.drawable.slide_scroll);
                    mIndicater4.setImageResource(R.drawable.slide_scroll_1);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {
            }
        });

    }

}
