package kr.app.dbhandle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import kr.app.binbang.MainActivity;
import kr.app.datainfo.BangDataInfo;

/**
 * Created by imjaehyun on 15. 9. 15..
 */
public class DbAdapter {

    static final String dbname = "binbangDB.db";

    //데이터 베이스를 이용하는 컨텍스트
    private Context context;
    //데이터 연동객체
    private SQLiteDatabase db;


    public DbAdapter(Context context) {
        this.context = context;
        this.open();
    }

    public void open() throws SQLiteException {
        try {
            db = (new DbHelper(context).getWritableDatabase());
        } catch(SQLiteException e) {
            db = (new DbHelper(context).getReadableDatabase());
        }
    }

    public void insert_recently_find(BangDataInfo item){

        try {
            this.open();
            ContentValues values = new ContentValues();
            values.put("bang_id", item.get_id() );
            values.put("is_available", item.getIs_available() );
            values.put("building_name", item.getBuilding_name());
            values.put("building_hosu", item.getBuilding_hosu());
            values.put("dong", item.getDong());
            values.put("sangse_juso", item.getSangse_juso());
            values.put("deposit", item.getDeposit());
            values.put("monthly_rental", item.getMonthly_rental());
            values.put("empty", item.getEmpty());
            values.put("price_type", item.getPrice_type());
            values.put("manage_price", item.getManage_price());
            values.put("bang_type", item.getBang_type());
            values.put("call", item.getCall());
            values.put("lat", item.getLat());
            values.put("lng", item.getLng());
            values.put("img_url", item.getImg_url());
            long result = db.insert("recently_find",null,values);
            Log.d("ddd", Long.toString(result));
            this.Close();
        } catch(Exception e) {

        }
    }

    public void insert_favorite(BangDataInfo item){
        try {
            this.open();
            ContentValues values = new ContentValues();
            values.put("bang_id", item.get_id() );
            values.put("is_available", item.getIs_available() );
            values.put("building_name", item.getBuilding_name());
            values.put("building_hosu", item.getBuilding_hosu());
            values.put("dong", item.getDong());
            values.put("sangse_juso", item.getSangse_juso());
            values.put("deposit", item.getDeposit());
            values.put("monthly_rental", item.getMonthly_rental());
            values.put("empty", item.getEmpty());
            values.put("price_type", item.getPrice_type());
            values.put("manage_price", item.getManage_price());
            values.put("bang_type", item.getBang_type());
            values.put("call", item.getCall());
            values.put("lat", item.getLat());
            values.put("lng", item.getLng());
            values.put("img_url", item.getImg_url());
            long result = db.insert("favorite",null,values);
            Log.d("ddd",Long.toString(result));
            this.Close();
        } catch(Exception e) {

        }
    }

    public void remove_favorite(BangDataInfo item){
        this.open();
        db.execSQL("DELETE FROM favorite WHERE bang_id="+item.get_id() );
        Log.d("test", "delete");
        this.Close();
    }

    public void remove_recently_find(BangDataInfo item){
        this.open();
        db.execSQL("DELETE FROM recently_find WHERE bang_id=" + item.get_id());
        Log.d("test", "delete");
        this.Close();
    }

    public void insert_memo(String bang_id, String contents){
        try {
            this.open();
            ContentValues values = new ContentValues();
            values.put("bang_id", bang_id );
            values.put("contents", contents );
            long result = db.insert("memo",null,values);
            Log.d("ddd",Long.toString(result));
            this.Close();
        } catch(Exception e) {

        }
    }

    public void remove_memo(String _id){
        this.open();
        db.execSQL("DELETE FROM memo WHERE bang_id="+_id);
        Log.d("test", "delete");
        this.Close();
    }

    public void update_recently(BangDataInfo item){
        this.open();
        try {
            Cursor mCount = MainActivity.mdb.rawQuery("SELECT * from recently_find where bang_id=" +item.get_id(), null);
            mCount.moveToFirst();
            ContentValues values = new ContentValues();
            values.put("bang_id", item.get_id() );
            values.put("is_available", item.getIs_available() );
            values.put("building_name", item.getBuilding_name());
            values.put("building_hosu", item.getBuilding_hosu());
            values.put("dong", item.getDong());
            values.put("sangse_juso", item.getSangse_juso());
            values.put("deposit", item.getDeposit());
            values.put("monthly_rental", item.getMonthly_rental());
            values.put("empty", item.getEmpty());
            values.put("price_type", item.getPrice_type());
            values.put("manage_price", item.getManage_price());
            values.put("bang_type", item.getBang_type());
            values.put("call", item.getCall());
            values.put("lat", item.getLat());
            values.put("lng", item.getLng());
            values.put("img_url", item.getImg_url());
            int t = db.update("recently_find", values, "bang_id=" + item.get_id(), null);
            Log.d("db up", Integer.toString(t));
        }catch (Exception e){}
        this.Close();
    }

    public void update_favorite(BangDataInfo item){
        this.open();
        try{
            Cursor mCount = MainActivity.mdb.rawQuery("SELECT * from favorite where bang_id="+item.get_id() , null);
            mCount.moveToFirst();
            ContentValues values = new ContentValues();
            values.put("bang_id", item.get_id() );
            values.put("is_available", item.getIs_available() );
            values.put("building_name", item.getBuilding_name());
            values.put("building_hosu", item.getBuilding_hosu());
            values.put("dong", item.getDong());
            values.put("sangse_juso", item.getSangse_juso());
            values.put("deposit", item.getDeposit());
            values.put("monthly_rental", item.getMonthly_rental());
            values.put("empty", item.getEmpty());
            values.put("price_type", item.getPrice_type());
            values.put("manage_price", item.getManage_price());
            values.put("bang_type", item.getBang_type());
            values.put("call", item.getCall());
            values.put("lat", item.getLat());
            values.put("lng", item.getLng());
            values.put("img_url", item.getImg_url());
            int t= db.update("favorite", values, "bang_id=" +item.get_id(), null);
            Log.d("db f", Integer.toString(t));
        }catch (Exception e){

        }
        this.Close();
    }

    public void Close() {
        db.close();
    }

}
