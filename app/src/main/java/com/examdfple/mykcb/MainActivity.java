package com.examdfple.mykcb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences sharedPreferences;
    public EditText username;
    public EditText password;
    private AlertDialog.Builder dialog;
    private JSONObject oub;
    private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private final String DATASRC = "datam.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        username = findViewById(R.id.edit_text_account);
        password = findViewById(R.id.edit_text_password);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);//这两句设置禁止所有检查
        setValue();

//        setFile setf2 = new setFile(this);
//        try {
//            Log.d("dat",setf2.read(DATASRC));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    /**
     * @ 自动t填充 密码账号
     */
    private void setValue() {
        String usernames = sharedPreferences.getString("username", "");
        String passwords = sharedPreferences.getString("password", "");
        if (!usernames.equals("") && !passwords.equals("")) {
            username.setText(usernames);
            password.setText(passwords);
        }
    }

    @SuppressLint("WrongViewCast")
    public Boolean login(View view) {

        if (username.getText().toString().equals("")) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        //  假设条件成立，跳转显示课程表页面

        //  关闭当前试图
        // 保存数据到
        //
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.commit();
        dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("");
        dialog.setView(R.layout.mulodingxmk);
        dialog.setCancelable(false);
        dialog.create();
        AlertDialog viewasds = dialog.show();

        new Thread(() -> {
            String url = "http://shaotools.com:3038/getkcb?username="+username.getText()+"&password="+password.getText();
//            String params = "null";
            Log.d("ur",url);
            String json = "{}";
            RequestBody body = RequestBody.create(MEDIA_TYPE, json);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)  // 连接超时
                    .readTimeout(30, TimeUnit.SECONDS)  // 读取超时
                    .writeTimeout(30, TimeUnit.SECONDS)  // 写入超时
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Log.d("json", response.code() + "");
                if (response.code() == 200) {
                    // 登录成功

                    viewasds.dismiss();
                    byte[] byteArray = response.body().bytes();
                    String responseBody = new String(byteArray, StandardCharsets.UTF_8);
                    // 处理返回的结果 Object Array
                    JSONArray retudat = new JSONArray(responseBody);

                    // 登录成功，保存数据
                    setFile setf1 = new setFile(this);
                    setf1.write(DATASRC,responseBody);

                    // 刷新 界面 widget

                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(MainActivity.this);
                    ComponentName thisAppWidget = new ComponentName(MainActivity.this, Nodemon.class);
//                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                    int[] allAppWidgetsIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
                    Log.d("allAppWidgetsIds", Arrays.toString(allAppWidgetsIds));
                    widgetSetOv wid = new widgetSetOv();
                    wid.setshowlayout(MainActivity.this,allAppWidgetsIds,appWidgetManager);


                    startActivity(new Intent(this, MainKcb.class));
                } else if (response.code() == 400) {
                    // 关闭加载框
                    viewasds.dismiss();
                    // 当 返回类型为JSON 时

                    try {
                        byte[] byteArray = response.body().bytes();
                        String responseBody = new String(byteArray, StandardCharsets.UTF_8);
                        oub = new JSONObject(responseBody);
                        Log.d("erro", oub.getString("msg"));
                    } catch (JSONException a) {

                    }
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 在主线程上执行操作，例如显示AlertDialog
                            AlertDialog myerro = null;
                            try {
                                myerro = new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("错误")
                                        .setMessage(oub.getString("msg") + "\n 可以试着检查 密码和 账号")
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

                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();

            }

//      里面放验证逻辑
        }).start();
        // 检查是否已获得网络权限
//        this.finish();
        return true;
    }
}
//文件操作
