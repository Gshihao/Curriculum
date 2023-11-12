package com.examdfple.mykcb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainHome_My extends MainActivity{
    public ImageView imgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_main);
        imgs = findViewById(R.id.sddsfsdfssfd);
        setImg();
    }
    /**
     *
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
}
