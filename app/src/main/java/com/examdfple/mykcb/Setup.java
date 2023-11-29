package com.examdfple.mykcb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;

import com.examdfple.mykcb.Tools.Setup.SetupsItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class Setup extends AppCompatActivity {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public JSONObject datJS ;
    private final String DATNAME = "Datpack.json";
    public SetupsItem setitem = new SetupsItem(Setup.this);
    @Override
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_up);

        Toolbar todl = findViewById(R.id.toolbar);
        todl.setTitleTextColor(getColor(R.color.white));
        todl.setTitle("设置");
        todl.setNavigationIcon(R.mipmap.back);
        DrawableCompat.setTint(Objects.requireNonNull(todl.getNavigationIcon()), Color.WHITE); // 设置透明背景色
        setSupportActionBar(todl);
        todl.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setup.this.finish();
                // start 为 左  end 为 右
            }
        });
        SetupsItem setuptime = new SetupsItem(Setup.this);
        sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        editor =  sharedPreferences.edit();
        ifSHIlihua();

        setFile setl = new setFile(Setup.this);
        try {
            datJS = new JSONObject(setl.read(DATNAME));
            // 课程表设置
            SetuoItemsKcb(datJS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        /*退出登录*/
        LinearLayout Logoutandloginssss = findViewById(R.id.Logoutandloginssss);
        Logoutandloginssss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog myerro = new AlertDialog.Builder(Setup.this)
                        .setTitle("退出登录")
                        .setMessage("确定要退出登录吗，将清空数据，已经保存的数据将会清除，请确认后清除")
                        .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editor.remove("password");
                                editor.remove("username");
                                editor.commit();
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                myerro.show();
            }
        });

    }
    public void SetupitemsJc(){}
    /**配置课程表信息*/
    public void SetuoItemsKcb(JSONObject jso) throws Exception {
      // 日期文字颜色值
        LinearLayout datetime = findViewById(R.id.Setuoitemstime_s);
        datetime.setBackgroundColor(Color.parseColor(jso.get("DatenavigationColor").toString()));
        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  修改颜色值
                try {
                    if( setitem.Modify_Color(new AlertDialog.Builder(Setup.this),jso.get("DatenavigationColor").toString(),jso)){
                       // 修改成功
                    }else {
                        Toast.makeText(Setup.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void ifSHIlihua(){
        try {
            String instantiation  = sharedPreferences.getString("instantiation","true");
            if (instantiation.equals("true")){
                // 为空时 初始化
                editor.putString("instantiation","false");
                editor.commit();
                setFile setFil = new setFile(Setup.this);
                setFil.initialization_Json("js/data/Dat.json");
                Log.d("Sdsd", "ifSHIlihua: "+ "初始化完成");
            }else {
            }
        }catch (Exception e){
        }
    }
}
