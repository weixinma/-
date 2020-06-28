package com.example.lenovo.bilibili;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*读取系统联系人的内容提取器*/
public class ContactActivity extends AppCompatActivity {
    List<String> data = new ArrayList<>();//用于ArrayList这个类实现List接口
    ArrayAdapter adapter;//声明适配器
    ListView list;//声明ListView组件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        list=findViewById(R.id.listView);
        Button query=findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter=new ArrayAdapter(ContactActivity.this,android.R.layout. simple_list_item_1,data);
                list.setAdapter(adapter);
                if (ContextCompat.checkSelfPermission(ContactActivity.this,"android.permission.READ_CONTACTS")!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ContactActivity.this,new String[]{"android.permission.READ_CONTACTS"},1);
                }else {
                    //查询联系人
                    readContacts();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //查询联系人
                readContacts();
            }else {
                Toast.makeText(this, "用户拒绝了读取联系人权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            ContentResolver contentResolver=getContentResolver();
            // 查询联系人数据  URI=content://com.android.contacts.provider/phones
            cursor = contentResolver.query
                    (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 获取联系人姓名
                    String displayName = cursor.getString(cursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    // 获取联系人手机号
                    String number = cursor.getString(cursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.NUMBER));
                    data.add(displayName + "\n" + number);//添加信息到LitsView
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
