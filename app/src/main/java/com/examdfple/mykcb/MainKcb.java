package com.examdfple.mykcb;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainKcb extends MainActivity {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mykcb);
        sharedPreferences= getSharedPreferences("my_data", MODE_PRIVATE);
        editor= sharedPreferences.edit();
        WebView ww = findViewById(R.id.webview);

//        getSupportActionBar().setTitle("My Activity");

        ww.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
        String username = sharedPreferences.getString("username","null");
        String password = sharedPreferences.getString("password","null");
        String url ="file:///android_asset/index.html?username="+username+"&password="+password;
        ww.loadUrl(url);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1001,1,"更多");
        menu.add(2,1002,1,"切换主题");
        menu.add(3,1003,1,"功能一");
        menu.add(4,1004,1,"功能二");
        menu.add(5,1005,1,"功能三");
        menu.add(6,1006,1,"退出登录");
        menu.add(7,1007,1,"设置");
        menu.add(8,1008,1,"退出");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1001 :
                break;
            case 1002:
                break;
            case 1003:
                break;
            case 1004:
                break;
            case 1005:
                break;
            case 1006:
                editor.clear();
                editor.apply();
                this.finish();

                break;
            case 1007:
//                this.finish();
                break;
            case 1008:
//                关闭页面
                this.finish();
                break;
        }
        return true;
    }
}
