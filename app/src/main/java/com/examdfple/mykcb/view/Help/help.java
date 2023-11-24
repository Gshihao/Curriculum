package com.examdfple.mykcb.view.Help;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class help extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // https://note.youdao.com/s/7kAPAX2w// 创建一个 Intent，用于启动浏览器
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String url = "https://note.youdao.com/s/7kAPAX2w";
        intent.setData(Uri.parse(url));
        startActivity(intent);
        this.finish();
    }
}
