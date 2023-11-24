package com.examdfple.mykcb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.Objects;

public class Contact_me extends AppCompatActivity {
    @Override
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_me);

        Toolbar todl = findViewById(R.id.toolbar);
        todl.setTitleTextColor(getColor(R.color.white));
        todl.setTitle("联系我们");
        todl.setNavigationIcon(R.mipmap.back);
        DrawableCompat.setTint(Objects.requireNonNull(todl.getNavigationIcon()), Color.WHITE); // 设置透明背景色
        setSupportActionBar(todl);
        todl.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact_me.this.finish();
                // start 为 左  end 为 右
            }
        });

      //        跳转QQ

        try {
            String urlQQ = "mqqwpa://im/chat?chat_type=wpa&uin=1462905973&version=1";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));
        }catch (Exception e){
            Log.d("sd_______", "onCreate: "+e);
            Toast.makeText(this, "您还没有安装QQ，请先安装软件\n"+e, Toast.LENGTH_SHORT).show();
        }
    }
}
