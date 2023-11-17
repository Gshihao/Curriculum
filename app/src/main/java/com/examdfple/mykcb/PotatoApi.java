package com.examdfple.mykcb;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PotatoApi {
    // key
    private final String ASKKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBJZCI6ODgzODczNzM2MjQ0Njc4NjU2LCJnZXRNYW5hZ2VtZW50SWQiOjcyNDU2MzA5MzU4Nzg0MTAyNCwiVElNRSI6MTY5OTg0MzUzMDU0Nn0.JgTbBUCkLwa7Iy5SD-7-me-buLgky92JNk0Bb_cwcPw";
    private final String reURL = "https://potato.xudakj.com";
    public  JSONObject jsonb;
    private Context mcontent;

    public PotatoApi(Context mcontent) {
        this.mcontent = mcontent;
    }

    public JSONObject ifupdate(){

        // 首先获取版本号
        String versionName = "";
        try {
            PackageInfo packageInfo = mcontent.getPackageManager().getPackageInfo(mcontent.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // 请求接口
          jsonb = new JSONObject();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)  // 连接超时
                .readTimeout(30, TimeUnit.SECONDS)  // 读取超时
                .writeTimeout(30, TimeUnit.SECONDS)  // 写入超时
                .build();
        Log.d("url_______________", "ifupdate: "+reURL+"/api/updateApp?version="+versionName);
        Request request = new Request.Builder()
                .url(reURL+"/api/updateApp?version="+versionName)
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addHeader("askKey",ASKKey)
                .build();
        try {
            Response response = client.newCall(request).execute();
            byte[] byteArray = response.body().bytes();
            String responseBody = new String(byteArray, StandardCharsets.UTF_8);
            JSONObject jsob = new JSONObject(responseBody);
            JSONObject jsodat = new JSONObject(jsob.get("data").toString());
            if(jsodat.get("isUpdate").equals(true)){
                JSONObject jsood = new JSONObject(jsodat.get("data").toString());
                jsonb.put("ifreq",true); // 请求状态
                jsonb.put("isUpdate",jsodat.get("isUpdate")); // 是否更新
                jsonb.put("modifiedDate",jsood.get("modifiedDate"));  // 最后修改时间
                jsonb.put("updateUrl",jsood.get("updateUrl"));  // 更新地址
                jsonb.put("updateState",jsood.get("updateState"));  // 是否强制更新
                jsonb.put("version",jsood.get("version"));  // 最新版本号
                jsonb.put("versionStr",jsood.get("versionStr"));  // 更新内容
            }else {
                jsonb.put("ifreq","false"); // 请求状态
                jsonb.put("msg","已经是最新版"); // 请求状态
            }
        }catch (Exception e){
            try {
                jsonb.put("ifreq","false"); // 请求状态
                jsonb.put("msg","用户版本获取失败"); // 请求状态
                jsonb.put("ex",""+e); // 请求状态
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        }
        return jsonb;
    }
}
