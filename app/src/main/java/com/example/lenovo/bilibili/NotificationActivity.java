package com.example.lenovo.bilibili;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    private VideoView videoView;//声明VideoView控件
    /*视频播放器*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        videoView = (VideoView) findViewById(R.id.video);
        Button play = (Button) findViewById(R.id.play1);
        Button pause = (Button) findViewById(R.id.pause1);
        Button replay = (Button) findViewById(R.id.replay1);
        play.setOnClickListener(this);//播放按钮
        pause.setOnClickListener(this);//暂停按钮
        replay.setOnClickListener(this);//重播按钮
        /*播放器权限处理*/
        if (ContextCompat.checkSelfPermission(NotificationActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NotificationActivity.this,
                    new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
        } else {
            initVideoPath(); // 初始化MediaPlayer
        }
    }
    private void initVideoPath() {
        //指定路径
        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+ R.raw.daoko);
        videoView.setVideoURI(uri);// 指定视频文件的路径
        // File file = new File(Environment.getExternalStorageDirectory(), "movie.mp4");
        // videoView.setVideoPath(file.getPath()); // 指定视频文件的路径
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoPath();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play1:
                if (!videoView.isPlaying()) {
                    videoView.start(); // 开始播放
                }
                break;
            case R.id.pause1:
                if (videoView.isPlaying()) {
                    videoView.pause(); // 暂停播放
                }
                break;
            case R.id.replay1:
                if (videoView.isPlaying()) {
                    videoView.resume(); // 重新播放
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
