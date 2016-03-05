package kr.app.binbang;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class JoinActivity extends FontActivity {
    private ImageButton mBack;
    private Button mJoin_ceo;
    private Button mJoin_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_join);
        View top = (View)findViewById(R.id.top_m);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            top.setVisibility(View.GONE);
        }
        mBack = (ImageButton)findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mJoin_ceo = (Button)findViewById(R.id.join_ceo_btn);
        mJoin_ceo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this,JoinCeoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mJoin_member = (Button)findViewById(R.id.join_member_btn);
        mJoin_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this,JoinMemberActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
