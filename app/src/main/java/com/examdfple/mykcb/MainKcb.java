package com.examdfple.mykcb;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.Objects;

public class MainKcb extends AppCompatActivity {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mykcb);
        sharedPreferences= getSharedPreferences("my_data", MODE_PRIVATE);
        editor= sharedPreferences.edit();
        WebView ww = findViewById(R.id.webview);

//        getSupportActionBar().setTitle("My Activity");
        ww.getSettings().setDomStorageEnabled(true);
        ww.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
        String username = sharedPreferences.getString("username","null");
        String password = sharedPreferences.getString("password","null");
        String url ="file:///android_asset/index.html?username="+username+"&password="+password;
        ww.loadUrl(url);
        Toolbar todl = findViewById(R.id.toolbar);
        todl.setTitleTextColor(getColor(R.color.white));
        todl.setTitle("关于");
        todl.setNavigationIcon(R.mipmap.back);
        DrawableCompat.setTint(Objects.requireNonNull(todl.getNavigationIcon()), Color.WHITE); // 设置透明背景色
        setSupportActionBar(todl);
        todl.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainKcb.this.finish();
                // start 为 左  end 为 右
            }
        });
    }
}
