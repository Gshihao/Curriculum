package com.examdfple.mykcb;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class setFile{
    private final Context mContext;
    private final String DATNAME = "Datpack.json";
    public setFile(Context mContext){
        super();
        this.mContext = mContext;
    }
    /** 文件名
     * @param {*} 文件名
     * */
    public Boolean write(String fielname,String obj){
        try{
            FileOutputStream output = mContext.openFileOutput(fielname, Context.MODE_PRIVATE);
            output.write(obj.getBytes());  //将String字符串以字节流的形式写入到输出流中
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public String read(String filename) throws IOException {
        //打开文件输入流
        FileInputStream input = mContext.openFileInput(filename);
        byte[] temp = new byte[1024];
        StringBuilder sb = new StringBuilder("");
        int len = 0;
        //读取文件内容:
        while ((len = input.read(temp)) > 0) {
            sb.append(new String(temp, 0, len));
        }
        //关闭输入流
        input.close();
        return sb.toString();
    }
    public JSONArray assetct (Context context,String fileName) throws Exception{
        AssetManager am = context.getAssets();
        InputStream is = am.open("sidebardirectory.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        String json = sb.toString();
        return new JSONArray(json);
    }
    /**初始化配置文件*/
    public String initialization_Json(String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = mContext.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            FileOutputStream output = mContext.openFileOutput(DATNAME, Context.MODE_PRIVATE);
            output.write(stringBuilder.toString().getBytes());  //将String字符串以字节流的形式写入到输出流中
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
