package com.examdfple.mykcb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1005;
    public SharedPreferences sharedPreferences;
    public EditText username;
    public EditText password;
    private AlertDialog.Builder dialog;
    private JSONObject oub;
    private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private final String DATASRC = "datam.json";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MyKCB);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        username = findViewById(R.id.edit_text_account);
        password = findViewById(R.id.edit_text_password);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);//这两句设置禁止所有检查
        setValue();

        Toolbar todl = findViewById(R.id.toolbar);
        todl.setTitleTextColor(getColor(R.color.white));
        todl.setTitle("登录");
        setSupportActionBar(todl);

        // 判断是否更新
        Determine_whether_toupdate(MainActivity.this);
    }

    private void Determine_whether_toupdate(Context content) {
        PotatoApi poapi = new PotatoApi(content);
        JSONObject jsreq = poapi.ifupdate();
        AlertDialog.Builder myerro = new AlertDialog.Builder(content);
        try {
            if (jsreq.get("ifreq").equals(true)) {
                // 请求成功时 判断是否更新
                if (jsreq.get("isUpdate").equals(true)) {
                    // 需要更新时
                    myerro.setTitle("有新版本") // 设置标题
                            .setMessage(jsreq.get("versionStr").toString())
                            .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 因条件需要，自行更新

                                    try {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        String url = null;
                                        url = jsreq.get("updateUrl").toString();
                                        intent.setData(Uri.parse(url));
                                        content.startActivity(intent);
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }

                                    // 执行下载
//                                    Download_Apk_file(jsreq);

                                }
                            }).setCancelable(false);
                    if (!jsreq.get("updateState").equals(1)) {
                        //  强制更新
                        myerro.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 下载文件
                            }
                        });
                    }
                    myerro.create()
                            .show();
                }
            } else if(jsreq.get("msg").equals("已经是最新版")){
                // 最新版
                // 点击登录按钮
                if (username.getText().toString().equals("")) {
                 //    账号为空
                    return;
                }
                if (password.getText().toString().equals("")) {
                   //  密码不能为空
                    return;
                }
                OpenAct();

            }else {
                Toast.makeText(content, "用户版本获取失败", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public static SSLSocketFactory getUnsafeOkHttpClient() {
        try {
            @SuppressLint("CustomX509TrustManager") final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    public void login(View view) {
        OpenAct();
    }
    /**
     * 打开新页面
     * */
    public boolean OpenAct(){
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
            String url = "http://shaotools.com:3038/getkcb?username=" + username.getText() + "&password=" + password.getText();
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
                if (response.code() == 200) {
                    // 登录成功

                    viewasds.dismiss();
                    byte[] byteArray = response.body().bytes();
                    String responseBody = new String(byteArray, StandardCharsets.UTF_8);
                    // 处理返回的结果 Object Array
                    JSONArray retudat = new JSONArray(responseBody);
                    // 登录成功，保存数据
                    setFile setf1 = new setFile(this);
                    setf1.write(DATASRC, responseBody);
                    // 刷新 界面 widget
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(MainActivity.this);
                    ComponentName thisAppWidget = new ComponentName(MainActivity.this, Nodemon.class);
                    int[] allAppWidgetsIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
                    widgetSetOv wid = new widgetSetOv();
                    wid.setshowlayout(MainActivity.this, allAppWidgetsIds, appWidgetManager);

                    startActivity(new Intent(this, MainHome_My.class));  // 跳转到首页
                    this.finish();
                } else if (response.code() == 400) {
                    // 关闭加载框
                    viewasds.dismiss();
                    // 当 返回类型为JSON 时
                    try {
                        byte[] byteArray = response.body().bytes();
                        String responseBody = new String(byteArray, StandardCharsets.UTF_8);
                        oub = new JSONObject(responseBody);
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
        return false;
    }
//    private void Download_Apk_file(JSONObject jsob) {
//        AlertDialog myerrs = new AlertDialog.Builder(MainActivity.this)
//                .setTitle("下载中")
//                .setView(R.layout.downdole_demo_day_one)
//                .setCancelable(false)
//                .create();
//        new Thread(() -> {
//            //                Environment.getExternalStorageDirectory() + "/download/
//            //跳转到开启apk安装权限开启的界面，让用户手动打开
//
//            String mUrl = null;
//            String mOutputPath = null;
//            try {
//                mUrl = jsob.get("updateUrl").toString();
//                // this.getFilesDir()
//                mOutputPath = "STools.apk";
////                Environment.getExternalStorageDirectory() + "/download/
//            } catch (JSONException e) {
////                Log.d("获取路径失败", e.toString());
//                throw new RuntimeException(e);
//            }
//            // 下载 文件
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
//            }
//
//            try {
//                // 从证书文件中读取证书
//                InputStream certStream = getResources().openRawResource(R.raw.fullchain);
//                CertificateFactory cf = CertificateFactory.getInstance("X.509");
//                X509Certificate caCert = (X509Certificate) cf.generateCertificate(certStream);
//
//// 创建一个信任锚，并将其添加到默认的KeyStore中
//                KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//                ks.load(null, null); // 创建一个新的KeyStore实例
//                ks.setCertificateEntry("caCert", caCert); // 将证书添加到KeyStore中，并给它一个名称"caCert"
//
//// 创建一个TrustManager，只信任我们的信任锚
//                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//                tmf.init(ks); // 初始化TrustManagerFactory，使用我们的KeyStore
//                TrustManager[] tms = tmf.getTrustManagers(); // 获取TrustManager数组，其中有一个TrustManager只信任信任锚
//                if (tms != null && tms.length == 1 && tms[0] instanceof X509TrustManager) { // 确保我们只有一个TrustManager，并且它只信任X509证书（也就是我们的信任锚）
//                    X509TrustManager trustManager = (X509TrustManager) tms[0]; // 获取我们的TrustManager
//
//                    OkHttpClient client = new OkHttpClient.Builder()
//                            .connectTimeout(10, TimeUnit.SECONDS)  // 连接超时
//                            .readTimeout(30, TimeUnit.SECONDS)  // 读取超时
//                            .writeTimeout(30, TimeUnit.SECONDS)  // 写入超时
//                            .sslSocketFactory(getUnsafeOkHttpClient(), trustManager) // 设置我们的SSLSocketFactory和TrustManager
//                            .hostnameVerifier((hostname, session) -> true)
//                            .build(); // 构建OkHttpClient实例，并将我们的TrustManager设置到这个实例中
//                    Request request = new Request.Builder()
//                            .url(mUrl)
//                            .build(); // 构建Request实例，请求指定的URL
//                    Response response = client.newCall(request).execute(); // 执行请求并获取响应
//                    byte[] byteArray = response.body().bytes(); // 获取响应体并转换为字节数组
//                    String responseBody = new String(byteArray, StandardCharsets.UTF_8); // 将字节数组转换为字符串并存储在responseBody变量中
//                    FileOutputStream output = this.openFileOutput(mOutputPath, Context.MODE_PRIVATE); // 创建一个文件输出流，用于将数据写入到指定的文件中
//                    output.write(responseBody.getBytes());  // 将字符串转换为字节数组并写入到输出流中
//                    output.close(); // 关闭输出流
////                    Log.d("Apk", "Download_Apk_file: 下载完成");
//                } else {
//                    throw new IllegalArgumentException("Unexpected number of X509TrustManagers: " + tms.length); // 如果TrustManager的数量不是1，抛出异常
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            File apkFile = new File(getFilesDir(), mOutputPath);
//
//            if (!apkFile.exists()) {
//                // 文件不存在，你需要处理这个问题
////                Log.d("Apk", "Download_Apk_file: 文件不存在");
//            } else if (!apkFile.canRead()) {
////                Log.d("Apk", "Download_Apk_file: 文件不可读");
//                // 文件不可读，你可能需要请求权限
//            } else {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                Uri uri = FileProvider.getUriForFile(this, "com.example.mykcb.fileprovider", apkFile);
//                intent.setDataAndType(uri, "application/vnd.android.package-archive");
//
//                startActivity(intent);
//            }
//        }).start();
//        myerrs.show();
//    }
}
//文件操作
