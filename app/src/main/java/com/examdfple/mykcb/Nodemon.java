package com.examdfple.mykcb;

import static android.content.Intent.getIntent;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;

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
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context, Nodemon.class);
        int[] allAppWidgetsIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        Refactoringpages(context,allAppWidgetsIds,appWidgetManager);
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
    public void Refactoringpages(Context context, int[] appWidgetIds, AppWidgetManager appWidgetManager) {
       // 重构widget 页面
        RemoteViews views = new RemoteViews(context.getPackageName(),
                // 这个layout就是我们之前定义的initiallayout
                R.layout.my_demo_one);
       views.setTextViewText(R.id.Nextsection,"课程");  // 课程
        views.setTextViewText(R.id.TeachersName,"教师"); // 教师
        views.setTextViewText(R.id.TeachingClassroom,"教室");  // 上课教室
        views.setTextViewText(R.id.Weeks,"周数");  // 周数
        views.setTextViewText(R.id.week,"周几"); // 周几
        views.setTextViewText(R.id.Abriefremark,getAbriefremark()); // 一言
        appWidgetManager.updateAppWidget(appWidgetIds,views);   //
    }
    public String getAbriefremark(){
        String[] yiyan =new String[6];
        yiyan[0] = "让过去过去，给时间时间";
        yiyan[1] = "生活原本沉闷，但跑起来就会有风";
        yiyan[2] = "青春不再等待，用自由书写你的故事.";
        yiyan[3] = "如果失败，接受失败就好了";
        yiyan[4] = "倘若南风知我意莫将晚霞落黄昏";
        yiyan[5] = "深情不及长伴  厚爱无需多言";
        return yiyan[(int)(Math.random() * yiyan.length)];
    }
}
