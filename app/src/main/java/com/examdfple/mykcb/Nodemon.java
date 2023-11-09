package com.examdfple.mykcb;

import static android.content.Intent.getIntent;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;

public class Nodemon extends AppWidgetProvider {
    public setFile sef1;
    private final String DATASRC = "datam.json";
    public Context mcontent ;
    public AppWidgetManager appw;
    public int[] ina;
    @Override
    public void onReceive(Context context, Intent intent) {
        //没接收一次广播消息就调用一次，使用频繁
        super.onReceive(context, intent);
        // 每次重构一次 widget 页面
        Refactoringpages();
    }
    public void shuaxin(){
        onUpdate(mcontent,appw,ina);
    }
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

//        this.mcontent = context;
//        this.appw = appWidgetManager;
//        this.ina = appWidgetIds;

        // 组件被 加入 到桌面时调用
       sef1 = new setFile(context);
        try {
            sef1.read(DATASRC);
            // 有数据会读取成功
        } catch (IOException e) {
          new widgetSetOv().setLoginlayout(context,appWidgetIds,appWidgetManager);

        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
    /**
     * @apiNote 重构widget 页面
     * */
    private void Refactoringpages() {
    }
}
