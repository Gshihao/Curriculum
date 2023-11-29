package com.examdfple.mykcb.Tools.Home;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class SetKcb {
    public WebView SetViewCookie(JSONObject obj, WebView we) {
        int i = 0 ;
        WebSettings webSettings = we.getSettings();
        String webViewStr = webSettings.getClass().getName();
        if (!"android.webkit.WebView".equals(webViewStr)) {
            // 如果不是WebView，那么你可能需要使用其他方式来设置sessionStorage。
        }
        final String[] urla = new String[1];
        we.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 在这里设置sessionStorage。
                String s = String.valueOf('"');
                for (Iterator<String> it = obj.keys(); it.hasNext(); ) {
                    String k = it.next();
                    try {
                         view.evaluateJavascript("sessionStorage.setItem(`"+k+"`,`"+obj.get(k)+"`)", null);
                    } catch (Exception e) {
                        Log.d("失败", "onPageFinished: "+e);
                    }
                }
            }
        });
        return we;
    }
}
