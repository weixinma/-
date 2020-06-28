package com.example.lenovo.bilibili;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    /*把建表语句定义成了一个字符串常量*/
    public static final String CREATE_STUDENT = "create table Student ("
            + "id integer primary key autoincrement, "
            + "stuName text, "
            + "stuNumber text, "
            + "stuAge integer, "
            + "stuAddress text, "
            + "stuColleage text)";
    private Context mContext;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT);//执行建表,
        Toast.makeText(mContext, "创建成功", Toast.LENGTH_SHORT).show();
    }
    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Student");
        onCreate(db);
    }

}
