package com.examdfple.mykcb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences sharedPreferences;
    public  EditText username;
    public EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        username= findViewById(R.id.edit_text_account);
        password = findViewById(R.id.edit_text_password);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);//这两句设置禁止所有检查
        setValue();

    }

    /**
     * @ 自动t填充 密码账号
     * */
    private void setValue() {
        String usernames= sharedPreferences.getString("username","");
        String passwords= sharedPreferences.getString("password","");
        if(!usernames.equals("") && !passwords.equals("")){
            username.setText(usernames);
            password.setText(passwords);
        }
    }

    public Boolean login(View view){

        if (username.getText().toString().equals("")){
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return  false;
        }
        if (password.getText().toString().equals("")){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return  false;
        }
        //  假设条件成立，跳转显示课程表页面
        startActivity(new Intent(this,MainKcb.class));
        //  关闭当前试图
        // 保存数据到
        //
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.commit();
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();
            String json = "{\"username\": \""+username.getText().toString()+"\", \"password\": "+password.getText().toString()+"}";
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            Request request = new Request.Builder()
                    .url("http://localhost:3038/getkcb")
                    .post(requestBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    // 处理响应内容
//                        Toast.makeText(this, responseBody, Toast.LENGTH_SHORT).show();
//                    System.out.println("Response: " + responseBody);
                } else {
                    // 处理请求失败的情况
                    System.out.println("Request failed with code: " + response.code());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        // 检查是否已获得网络权限
        this.finish();
        return true;
    }
    }