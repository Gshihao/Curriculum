package com.examdfple.mykcb;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

public class widgetSetOv {
    public void setLoginlayout(Context context, int[] appWidgetIds, AppWidgetManager appWidgetManager) {
        // 没有数据会报错 不展示课程
        RemoteViews views = new RemoteViews(context.getPackageName(),
                // 这个layout就是我们之前定义的initiallayout
                R.layout.weilogin);
        appWidgetManager.updateAppWidget(appWidgetIds,views);

    }
    public void setshowlayout(Context context, int[] appWidgetIds, AppWidgetManager appWidgetManager){
        RemoteViews views = new RemoteViews(context.getPackageName(),
                // 这个layout就是我们之前定义的initiallayout
                R.layout.my_demo_one);
//        Log.d("sdf0","_______________________________________________________________________");
        appWidgetManager.updateAppWidget(appWidgetIds,views);
    }
}
