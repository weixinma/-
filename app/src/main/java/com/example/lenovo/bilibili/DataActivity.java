package com.example.lenovo.bilibili;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DataActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;//声明帮助类
    TextView textView;//声明textView控件,显示数据库的数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

            textView= (TextView) findViewById(R.id.textView);
            dbHelper = new MyDatabaseHelper(this, "StudentInfo.db",
                    null, 4);//构建MyDatabaseHelper对象
            Button createDatabase = (Button) findViewById(R.id.create_database);
            createDatabase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.getWritableDatabase();//判断有无这个数据库，无则创建。
                }
            });
            Button addData = (Button) findViewById(R.id.add_data);
            addData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // String Sql=editText.getText().toString();
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    //db.execSQL("insert into Book(name,author,pages,price)values(?,?,?,?)",new String[]{"Android","google","800","50"});
                    // editText.setText("");
                    ContentValues values = new ContentValues();//临时变量
                    // 开始组装第一条数据
                    values.put("stuName", "翁嘉鸣");
                    values.put("stuNumber", "26");
                    values.put("stuAge", 20);
                    values.put("stuAddress", "广东");
                    values.put("stuColleage", "广东理工学院");
                    db.insert("Student", null, values); // 插入第一条数据
                    values.clear();
                    // 开始组装第二条数据
                    values.put("stuName", "陈尚韬");
                    values.put("stuNumber", "06");
                    values.put("stuAge", 20);
                    values.put("stuAddress", "广东");
                    values.put("stuColleage", "广东理工");
                    db.insert("Student", null, values);// 插入第二条数据
                }
            });
            Button updateData = (Button) findViewById(R.id.update_data);
            updateData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("stuColleage", "广东理工学院信息技术学院");
                    db.update("Student", values, "stuColleage=?",
                            new String[]{"广东理工学院"});
                /*把"stuColleage"="广东理工学院"的更新为"广东理工学院信息技术学院"*/
                }
            });
            Button deleteButton = (Button) findViewById(R.id.delete_data);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete("Student", "stuColleage=?",
                            new String[]{"广东理工学院信息技术学院"});
                /*把"stuColleage"="广东理工"的删除掉"*/
                }
            });
            Button queryButton = (Button) findViewById(R.id.query_data);
            queryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    // 查询表中所有的数据
                    Cursor cursor = db.query("Student", null, null, null, null, null, null);
                    StringBuilder content=new StringBuilder();//转义字符
                    content.append("id"+"\t\t\t"+"stuName"+"\t\t\t"+"stuNumber"+"\t\t\t"+"stuAge"+"\t\t\t"+"stuAddress"+"\t\t\t"+"stuColleage"+"\n");
                    if (cursor.moveToFirst()) {
                        do {
                            // 遍历Cursor对象，取出数据并打印
                            int id=cursor.getInt(cursor.getColumnIndex("id"));
                            String name = cursor.getString(cursor.getColumnIndex("stuName"));
                            String number = cursor.getString(cursor.getColumnIndex("stuNumber"));
                            int age = cursor.getInt(cursor.getColumnIndex("stuAge"));
                            String address = cursor.getString(cursor.getColumnIndex("stuAddress"));
                            String colleage = cursor.getString(cursor.getColumnIndex("stuColleage"));
                            content.append(id+"\t\t\t"+name+"\t\t"+number+"\t\t"+age+"\t\t"+address+"\t\t"+colleage+"\n");
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    textView.setText(content.toString());
                }
            });
        }
}
