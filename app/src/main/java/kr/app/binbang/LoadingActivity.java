package kr.app.binbang;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;



public class LoadingActivity extends Activity implements Runnable{


    @Override
    public void finish(){
        super.finish();
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
        setContentView(R.layout.activity_loading);
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}
        // 화면 이동
        //인트로 화면으로 돌아오지 않도록 인트로 화면을 종료
        if( !isNetworkStat(LoadingActivity.this) ){
            finish();
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("login","1");
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        //Adapter가 있으면 어댑터에서 생성한 recycle메소드를 실행
        GarbageCollection.recursiveRecycle(getWindow().getDecorView());
        System.gc();
        super.onDestroy();
    }

    public boolean isNetworkStat( Context context ) {
        ConnectivityManager manager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo lte_4g = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
        boolean blte_4g = false;
        if(lte_4g != null)
            blte_4g = lte_4g.isConnected();
        if( mobile != null ) {
            if (mobile.isConnected() || wifi.isConnected() || blte_4g)
                return true;
        } else {
            if ( wifi.isConnected() || blte_4g )
                return true;
        }

        //Intent intent = new Intent(context, NetworkAlram.class);
        //context.startActivity(intent);
        //Toast.makeText(context, "인터넷 연결을 확인해주세요", Toast.LENGTH_LONG).show();
        mWeatherRunnableHandler.sendEmptyMessage(0);

        return false;
    }
    public Handler mWeatherRunnableHandler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==0){
                Toast.makeText(LoadingActivity.this, "인터넷 연결을 확인해주세요", Toast.LENGTH_LONG).show();
            }
        }
    };


}

