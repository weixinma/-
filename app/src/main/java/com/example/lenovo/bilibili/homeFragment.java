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
import android.widget.ImageView;
import android.widget.Toast;


public class homeFragment extends Fragment implements View.OnClickListener {
    private Button button;
    private ImageView ImageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView=(ImageView)getActivity().findViewById(R.id.BangumiImageView01);
        ImageView.setOnClickListener(this);//为按钮设置点击事件监听器
    }


    @Override
    public void onClick(View view) {
        Toast.makeText(getActivity(),
                "你点击了daoko", Toast.LENGTH_LONG).show();
        Intent intent0 = new Intent(getActivity(), NotificationActivity.class);//构建Intent对象，用于活动跳转
        startActivity(intent0);
    }
}
