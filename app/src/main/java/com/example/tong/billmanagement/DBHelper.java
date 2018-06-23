package com.example.tong.billmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 15639 on 2018/5/24.
 */

public class DBHelper extends SQLiteOpenHelper
{
    public static final String CREATE_BILL = "create table bill("
            + "id integer primary key autoincrement,"
            + "year text,"
            + "month text,"
            + "day text,"
            + "coast text,"
            + "type text,"
            + "content text)";

    private Context mContext;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_BILL);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
