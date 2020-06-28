package com.example.lenovo.bilibili;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageView;

public class WeFragment extends Fragment implements View.OnClickListener {
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_we,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button sendNotice = getActivity().findViewById(R.id.button_profil);
        Button sendNotice1 =getActivity().findViewById(R.id.button_profil1);
        Button sendNotice2 = getActivity().findViewById(R.id.button_profil2);
        sendNotice.setOnClickListener(this);//为按钮设置点击事件监听器
        sendNotice1.setOnClickListener(this);//为按钮设置点击事件监听器
        sendNotice2.setOnClickListener(this);//为按钮设置点击事件监听器

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {//根据控件ID判断点击的是哪个按钮
            case R.id.button_profil:
                Intent intent0 = new Intent(getActivity(), ContactActivity.class);//构建Intent
                startActivity(intent0);//跳转目标活动
                Toast.makeText(getActivity(), "通讯录提取器", Toast.LENGTH_LONG).show();
                break;
            case R.id.button_profil1:
                Intent intent1 = new Intent(getActivity(), DataActivity.class);//构建Intent
                startActivity(intent1);//跳转目标活动
                Toast.makeText(getActivity(), "数据库", Toast.LENGTH_LONG).show();
                break;
            case R.id.button_profil2:
                Intent intent2 = new Intent("android.intent.action.Login");//构建Intent
                //使用了Intent的另一个构造函数，直接将action的字符串传了进去
                startActivity(intent2);//跳转目标活动
                Toast.makeText(getActivity(), "登录", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
