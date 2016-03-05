package kr.app.dbhandle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "binbangDB.db";
    private static final String DB_TABLE_1 = "recently_find";
    private static final String DB_TABLE_2 = "favorite";
    private static final String DB_TABLE_3 = "memo";
    private static final int DB_VERSION = 1;

    private Context context;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE favorite(_id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "bang_id INTEGER NOT NULL, " +
                "is_available	INTEGER, " +
                "building_name	TEXT, " +
                "building_hosu	TEXT, " +
                "dong	TEXT, " +
                "sangse_juso	TEXT, " +
                "deposit	INTEGER, " +
                "monthly_rental	INTEGER, " +
                "empty	TEXT, " +
                "price_type	TEXT, " +
                "manage_price TEXT, " +
                "bang_type	TEXT, " +
                "call	TEXT, " +
                "lat	NUMERIC, " +
                "lng	NUMERIC, " +
                "img_url TEXT)";

        db.execSQL(sql);

        sql = "CREATE TABLE recently_find ( _id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "bang_id	INTEGER NOT NULL, " +
                "is_available	INTEGER DEFAULT 1, " +
                "building_name	TEXT, " +
                "building_hosu	TEXT, " +
                "dong	TEXT, " +
                "sangse_juso	TEXT, " +
                "deposit	INTEGER, " +
                "monthly_rental	INTEGER, " +
                "empty	TEXT, " +
                "price_type	TEXT, " +
                "manage_price	TEXT, " +
                "bang_type	TEXT, " +
                "call	TEXT, " +
                "lat	NUMERIC, " +
                "lng	NUMERIC, " +
                "img_url	TEXT)";


        db.execSQL(sql);

        sql = "CREATE TABLE memo ( _id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "bang_id	INTEGER NOT NULL, " +
                "contents	TEXT)";

        db.execSQL(sql);

        Log.d("db_create", "db create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_1 );
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_2 );
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_3 );
            onCreate(db);
        }
    }
}
