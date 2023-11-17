package com.examdfple.mykcb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainHome_My extends AppCompatActivity {
    public ImageView imgs,darwimg;
    private final String ASKKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBJZCI6ODgzODczNzM2MjQ0Njc4NjU2LCJnZXRNYW5hZ2VtZW50SWQiOjcyNDU2MzA5MzU4Nzg0MTAyNCwiVElNRSI6MTY5OTg0MzUzMDU0Nn0.JgTbBUCkLwa7Iy5SD-7-me-buLgky92JNk0Bb_cwcPw";
    @Override
    @SuppressLint({"MissingInflatedId", "LocalSuppress", "WrongViewCast"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_main);
        imgs = findViewById(R.id.sddsfsdfssfd);
        darwimg = findViewById(R.id.brawimgbanner);
        setImg();
        announcement();


        // 设置标题栏
        Toolbar todl = findViewById(R.id.toolbar);
        todl.setTitleTextColor(getColor(R.color.white));
        todl.setTitle("首页");
        todl.setNavigationIcon(R.drawable.toolcaricon);
        DrawerLayout draw= findViewById(R.id.sdds);
        DrawableCompat.setTint(Objects.requireNonNull(todl.getNavigationIcon()), Color.WHITE); // 设置透明背景色
        setSupportActionBar(todl); // 要写在监听器 前面
        todl.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draw.openDrawer(GravityCompat.START);
                // start 为 左  end 为 右
            }
        });
        // 侧滑栏
        drawstructure();

    }
    /**
     * @apiNote 设置广告图
     * */
    public void setImg(){
        new Thread(()->{
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)  // 连接超时
                    .readTimeout(30, TimeUnit.SECONDS)  // 读取超时
                    .writeTimeout(30, TimeUnit.SECONDS)  // 写入超时
                    .build();
            Request request = new Request.Builder()
                    .url("http://shp.qpic.cn/collector/1462905973/882db276-c534-46ed-b8db-2a0aecb95b6d/0")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    byte[] byteArray = response.body().bytes();
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(byteArray, 0,byteArray.length);


                    // 在完成时，通过 Handler 将修改 UI 的操作发送到 UI 线程
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 在这里修改 UI
                            imgs.setImageBitmap(bitmap1);
                            darwimg.setImageBitmap(bitmap1);
                        }
                    });

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public void Tiaozhuan(View view){
        startActivity(new Intent(this, MainKcb.class));
    }
    public void announcement(){
        new Thread(()->{
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)  // 连接超时
                    .readTimeout(30, TimeUnit.SECONDS)  // 读取超时
                    .writeTimeout(30, TimeUnit.SECONDS)  // 写入超时
                    .build();
            Request request = new Request.Builder()
                    .url("https://potato.xudakj.com/api/getNotice")
                    .addHeader("Content-Type","application/x-www-form-urlencoded")
                    .addHeader("askKey",ASKKey)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                byte[] byteArray = response.body().bytes();
                String responseBody = new String(byteArray, StandardCharsets.UTF_8);
                JSONObject jsob = new JSONObject(responseBody);
                JSONObject jsda= new JSONObject(jsob.get("data").toString());
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 在主线程上执行操作，例如显示AlertDialog
                        AlertDialog myerro = null;
                        try {
                              myerro = new AlertDialog.Builder(MainHome_My.this)
                                    .setTitle(jsda.get("head").toString())
                                    .setMessage("\n"+jsda.get("str")+"\n\n"+jsda.get("createdDate"))
                                    .setNegativeButton("知晓", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        myerro.show();
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public void Help(View view){
       // https://note.youdao.com/s/7kAPAX2w// 创建一个 Intent，用于启动浏览器
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String url = "https://note.youdao.com/s/7kAPAX2w";
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    public void drawstructure(){
        // 设置侧滑栏内容
        RecyclerView reclviewa = findViewById(R.id.myRecyclerView);
        ArrayList<Draw_Dome_Model> datalist = new ArrayList<>();
        // 动态 创建数据
        setFile setfile= new setFile(MainHome_My.this);
        try {
            JSONArray redfile =  setfile.assetct(MainHome_My.this,"sidebardirectory.json");
            for (int i = 0 ; i < redfile.length();i++){
                JSONObject jsoib = new JSONObject(redfile.get(i).toString());
                datalist.add(new Draw_Dome_Model(jsoib.get("itemname").toString()));
            }
        } catch (Exception e) {
            datalist.add(new Draw_Dome_Model("数据读取异常"));
        }
        Eceely_Demo_Adpdet ecadpyer = new Eceely_Demo_Adpdet(MainHome_My.this,datalist);
        reclviewa.setAdapter(ecadpyer);
        reclviewa.setLayoutManager(new LinearLayoutManager(MainHome_My.this));
        reclviewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sdf",v.toString());
            }
        });
    }
}
