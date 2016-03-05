package kr.app.customalert;

/**
 * Created by imjaehyun on 15. 9. 19..
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import kr.app.binbang.R;

public class CustomDialog extends Dialog {
    private TextView mTitleView;
    private TextView mContentView;
    private Button mLeftButton;
    private Button mRightButton;
    private String mTitle;
    private String mContent;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    public CustomDialog(Context context, String title,
                        View.OnClickListener leftListener, View.OnClickListener rightListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog);

        mTitleView = (TextView) findViewById(R.id.tv_title);
        mLeftButton = (Button) findViewById(R.id.bt_left);
        mRightButton = (Button) findViewById(R.id.bt_right);

        mTitleView.setText(mTitle);

        setClickListener(mLeftClickListener, mRightClickListener);
    }

    private void setClickListener(View.OnClickListener left , View.OnClickListener right){
        if(left!=null && right!=null){
            mLeftButton.setOnClickListener(left);
            mRightButton.setOnClickListener(right);
        }else if(left!=null && right==null){
            mLeftButton.setOnClickListener(left);
        }else {

        }
    }

}