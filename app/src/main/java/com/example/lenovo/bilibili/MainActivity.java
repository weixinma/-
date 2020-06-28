package com.example.lenovo.bilibili;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private homeFragment homefragment;//声明三个界面Fragment
    private FindFragment findfragment;//
    private WeFragment wefragment;//
    private Fragment[] fragments;//用于储存三个Fragment
    private int lastfragment;//用于记录上个选择的Fragment
    private IntentFilter intentFilter;//声明 IntentFilter 过滤器
    private NetworkChangeBroadcastReceiver networkChangeBroadcastReceiver;///声明内部类，用于动态广播
    NotificationManager manager;//通知 管理器用于管理通知Notification对象，可以管理多个对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        Receiver();
    }

    private void initFragment() {
        homefragment = new homeFragment();
        findfragment = new FindFragment();
        wefragment = new WeFragment();
        fragments = new Fragment[]{homefragment, findfragment, wefragment};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainview, homefragment).show(homefragment).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //切换Fragment
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.mainview, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();


    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    return true;
                case R.id.navigation_find:
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }
                    return true;
                case R.id.navigation_we:
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                    }
                    return true;
            }
            return false;
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            Toast.makeText(MainActivity.this,
                    "你点击了登录", Toast.LENGTH_LONG).show();
            Intent intent0 = new Intent(this, LoginActivity.class);
            startActivity(intent0);//构建Intent对象，用于活动跳转
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Receiver() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeBroadcastReceiver = new NetworkChangeBroadcastReceiver();
        registerReceiver(networkChangeBroadcastReceiver, intentFilter);
    }



    class NetworkChangeBroadcastReceiver extends BroadcastReceiver {//内部类
        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(context,"网络状态改变了",Toast.LENGTH_LONG).show();
            ConnectivityManager connectionManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);//实例化
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();//调用getActiveNetworkInfo()
            if (networkInfo != null && networkInfo.isAvailable()) {//判断有无网络
                Toast.makeText(context, "网络可用", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeBroadcastReceiver);
    }

    public void Notification() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);//通过getSystemService获得通知管理器对象
        Button sendNotice = findViewById(R.id.button_find1);
       // Button sendNotice1 = findViewById(R.id.button_find2);
        sendNotice.setOnClickListener(this);//为按钮设置点击事件监听器
        //sendNotice1.setOnClickListener(this);//为按钮设置点击事件监听器
    }

    public void onClick(View v) {//事件触发后执行这个方法
        switch (v.getId()) {//根据控件ID判断点击的是哪个按钮
            case R.id.button_find1://点击发送通知的按钮
                if(Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){ //在这里判断系统版本是否大于8.0，大于8.0则创建一个Channel
                    //构建通知渠道对象，每个通知都需要依附于一个通知渠道，三个参数分别表示渠道ID、渠道名称（用于可以根据名称选择是否允许弹出通知）、通知的优先级。
                    NotificationChannel notificationChannel = new NotificationChannel("channel","channelname",NotificationManager.IMPORTANCE_HIGH);
                    //通知管理器管理渠道，创建通知渠道
                    manager.createNotificationChannel(notificationChannel);
                }
                Toast.makeText(MainActivity.this, "老番茄发来信息", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, NotificationActivity.class);//构建Intent对象，用于活动跳转
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);//构建PendingIntent对象，用于点击通知后做跳转
                Notification notification = new NotificationCompat.Builder(this, "channel")//创建通知对象，第二个参数表示通知渠道的id，用于将当前的通知和渠道进行绑定
                        .setContentTitle("你关注的主播老番茄直播了")//通知标题
                        .setContentText("要相信量变是可以导致质变的!")//通知的详细文本信息
                        .setWhen(System.currentTimeMillis())//什么时候弹出通知信息
                        .setSmallIcon(R.mipmap.ic_launcher)//设置小图标
                        .setContentIntent(pi)//设置跳转需要用到的PendingIntent对象
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//设置大图标
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture
                                (BitmapFactory.decodeResource(getResources(), R.drawable.im)))//通知里面引入超大图片
                        .build();
                manager.notify(1, notification);//通知管理器管理通知并发送通知
                break;

            default:
                break;
        }
    }
}
