package com.examdfple.mykcb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.examdfple.mykcb.Tools.Home.GetWeeks;
import com.examdfple.mykcb.Tools.Home.SetKcb;
import com.examdfple.mykcb.view.Help.help;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainHome_My extends AppCompatActivity {
    public ImageView  darwimg;
    public SharedPreferences sharedPreferences;
    private final String ASKKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBJZCI6ODgzODczNzM2MjQ0Njc4NjU2LCJnZXRNYW5hZ2VtZW50SWQiOjcyNDU2MzA5MzU4Nzg0MTAyNCwiVElNRSI6MTY5OTg0MzUzMDU0Nn0.JgTbBUCkLwa7Iy5SD-7-me-buLgky92JNk0Bb_cwcPw";
    public SharedPreferences.Editor editor;
    public GetWeeks Getweek= new GetWeeks();
    public String imgurl = "";
    public setFile setFiles = new setFile(MainHome_My.this);
    private final String DATNAME = "Datpack.json";
    @Override
    @SuppressLint({"MissingInflatedId", "LocalSuppress", "WrongViewCast", "SetJavaScriptEnabled"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_main);
        darwimg = findViewById(R.id.brawimgbanner);
        setImg();
        announcement();

        //
        sharedPreferences= getSharedPreferences("my_data", MODE_PRIVATE);
        editor= sharedPreferences.edit();
        WebView ww = findViewById(R.id.webview);
//        getSupportActionBar().setTitle("My Activity");
        ww.getSettings().setDomStorageEnabled(true);
        ww.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
        ww.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

        // 解决跨域问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            ww.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }else{
            try {
                Class<?> clazz = ww.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                method.invoke(ww.getSettings(), true);
            } catch (Exception e) {

            }
        }

        String username = sharedPreferences.getString("username","null");
        String password = sharedPreferences.getString("password","null");
        // 判断用户是否登录
        if (username.equals("")) {
            //    账号为空
            Toast.makeText(this, "登录异常", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        if (password.equals("")) {
            //  密码不能为空
            Toast.makeText(this, "登录异常", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        String url ="file:///android_asset/index.html?username="+username+"&password="+password;
        try {
            setFiles.read(DATNAME);
        } catch (Exception e) {
            // 初始化配置文件
             setFiles.initialization_Json("js/data/Dat.json");
        }finally {
            try {
                // 配置Cookie
                SetKcb set = new SetKcb();
                ww = set.SetViewCookie(new JSONObject(setFiles.read(DATNAME)),ww);
            }catch (Exception d){}
        }
        ww.loadUrl(url);
        // 设置标题栏
        Toolbar todl = findViewById(R.id.toolbar);
        todl.setTitleTextColor(getColor(R.color.white));
        todl.setTitle(Getweek.getWeeekStr());
        todl.setNavigationIcon(R.drawable.toolcaricon);
        DrawerLayout draw = findViewById(R.id.sdds);
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


        darwimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainHome_My.this, "广告图", Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * @apiNote 设置广告图
     */
    public void setImg() {
        new Thread(() -> {
            OkHttpClient client1 = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)  // 连接超时
                    .readTimeout(30, TimeUnit.SECONDS)  // 读取超时
                    .writeTimeout(30, TimeUnit.SECONDS)  // 写入超时
                    .build();
            Request request1 = new Request.Builder()
                    .url("https://potato.xudakj.com/api/getDoc?key=guanggaotu")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("askKey", ASKKey)
                    .build();
            try {
                Response response1 = client1.newCall(request1).execute();
                if (response1.code() == 200) {
                    byte[] byteArray = response1.body().bytes();
                    String responseBody = new String(byteArray, StandardCharsets.UTF_8);
                    JSONObject jsob = new JSONObject(responseBody);
                    JSONObject jsda = new JSONObject(jsob.get("data").toString());
                    imgurl = jsda.get("docContent").toString();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)  // 连接超时
                    .readTimeout(30, TimeUnit.SECONDS)  // 读取超时
                    .writeTimeout(30, TimeUnit.SECONDS)  // 写入超时
                    .build();


            Request request = new Request.Builder()
                    .url(imgurl)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    byte[] byteArray = response.body().bytes();
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                    // 在完成时，通过 Handler 将修改 UI 的操作发送到 UI 线程
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 在这里修改 UI
                            darwimg.setImageBitmap(bitmap1);
                        }
                    });

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void Tiaozhuan(View view) {
        startActivity(new Intent(this, MainKcb.class));
    }

    public void announcement() {
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)  // 连接超时
                    .readTimeout(30, TimeUnit.SECONDS)  // 读取超时
                    .writeTimeout(30, TimeUnit.SECONDS)  // 写入超时
                    .build();
            Request request = new Request.Builder()
                    .url("https://potato.xudakj.com/api/getNotice")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("askKey", ASKKey)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                byte[] byteArray = response.body().bytes();
                String responseBody = new String(byteArray, StandardCharsets.UTF_8);
                JSONObject jsob = new JSONObject(responseBody);
                JSONObject jsda = new JSONObject(jsob.get("data").toString());
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 在主线程上执行操作，例如显示AlertDialog
                        AlertDialog myerro = null;
                        try {
                            myerro = new AlertDialog.Builder(MainHome_My.this)
                                    .setTitle(jsda.get("head").toString())
                                    .setMessage("\n" + jsda.get("str") + "\n\n" + jsda.get("createdDate"))
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

    public void drawstructure() {
        // 设置侧滑栏内容
        RecyclerView reclviewa = findViewById(R.id.myRecyclerView);
        ArrayList<Draw_Dome_Model> datalist = new ArrayList<>();
        // 动态 创建数据
        // 创建列表 图标
//        https://www.iconfont.cn/collections/detail?spm=a313x.collections_index.i1.d9df05512.3fc13a81xqvmWG&cid=44906
        int lenss = 4;
        int[] itemicon = new int[lenss];
        itemicon[0] = R.mipmap.guanyu;
        itemicon[1] = R.mipmap.lianxiwomen;
        itemicon[2] = R.mipmap.helps;
        itemicon[3] = R.mipmap.setup;
        // 创建列表 标题
        String[] itemname = new String[lenss];
        itemname[0] = "关于我们";
        itemname[1] ="联系我们";
        itemname[2] = "帮助" ;
        itemname[3] ="设置";
        // 创建列表 跳转界面
        Class[] itemActivity = new Class[lenss];
        itemActivity[0] = About_Me.class;
        itemActivity[1] = Contact_me.class;
        itemActivity[2] = help.class;
        itemActivity[3] = Setup.class;
        for (int i = 0; i < lenss; i++) {
            datalist.add(new Draw_Dome_Model(itemname[i],itemicon[i],itemActivity[i]));
        }
        Eceely_Demo_Adpdet ecadpyer = new Eceely_Demo_Adpdet(MainHome_My.this, datalist);
        reclviewa.setAdapter(ecadpyer);
        reclviewa.setLayoutManager(new LinearLayoutManager(MainHome_My.this));

    }

}
