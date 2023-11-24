package com.examdfple.mykcb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


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
        nextClass(context,appWidgetIds,appWidgetManager);
        RemoteViews views = new RemoteViews(context.getPackageName(),
                // 这个layout就是我们之前定义的initiallayout
                R.layout.my_demo_one);
        JSONObject retudat ;
        String TeachersName = "歇会吧";
        String TeachingClassroom = "歇会吧";
//        Log.d("df____________", String.valueOf(nextClass(context, appWidgetIds, appWidgetManager) == null));
        if(nextClass(context, appWidgetIds, appWidgetManager) == null){
            retudat = new JSONObject();
            try {
                retudat.put("croommc","歇会吧");
                retudat.put("jxbmc","歇会吧");
                retudat.put("tmc","歇会吧");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }else{
            //
            try {
                retudat = new JSONObject(nextClass(context, appWidgetIds, appWidgetManager).toString());
                String regex = "<a[^>]+>([^<]+)<\\/a>";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(retudat.get("tmc")+"");;
                if (matcher.find()) { // 老师
                    TeachersName = matcher.group(1);
                }

                Pattern pattern2 = Pattern.compile(regex);
                Matcher matcher2 = pattern2.matcher(retudat.get("croommc")+"");;
                if (matcher2.find()) { // 教室
                    TeachingClassroom = matcher2.group(1);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
       // 定义正则
         // 获取准确的教师和教室


        try {
            views.setTextViewText(R.id.Nextsection,retudat.get("jxbmc").toString());  // 课程
            views.setTextViewText(R.id.TeachersName,TeachersName); // 教师
            views.setTextViewText(R.id.TeachingClassroom,TeachingClassroom);  // 上课教室
            views.setTextViewText(R.id.Weeks,"周数");  // 周数
            views.setTextViewText(R.id.week,"周"+MgetDayOfWeek()); // 周几
            views.setTextViewText(R.id.Abriefremark,getAbriefremark()); // 一言
            appWidgetManager.updateAppWidget(appWidgetIds,views);   //
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @apiNote 随机返回一句话
     * */
    public String getAbriefremark(){
        String[] yiyan =new String[9];
        yiyan[0] = "让过去过去，给时间时间";
        yiyan[1] = "生活原本沉闷，但跑起来就会有风";
        yiyan[2] = "请优先考虑优先考虑你的人";
        yiyan[3] = "如果失败，接受失败就好了";
        yiyan[4] = "倘若南风知我意莫将晚霞落黄昏";
        yiyan[5] = "深情不及长伴  厚爱无需多言";
        yiyan[6] = "每一次尝试都是一次进步，加油！";
        yiyan[7] = "你好";
        yiyan[8] = "遇见即是上上签";
        return yiyan[(int)(Math.random() * yiyan.length)];
    }
    /**
     * @apiNote 返回下一节 的信息
     * */
    public Object nextClass(Context context, int[] appWidgetIds, AppWidgetManager appWidgetManager){
        Date thistime = new Date(); // 获取现在的时间
        sef1 = new setFile(context);
        String filedat = ""; // 文明的内容
        Object[][] weekclass = new Object[8][8]; // 保存一周内的课，对书进行拆分
        String[] week_time = timeArrye();

        // 判断是否存在数据
        try{
            filedat = sef1.read(DATASRC); // 对数据进行赋值
        }catch(IOException e){
            // 跳转为未登录布局 ，并退出方法
            new widgetSetOv().setLoginlayout(context,appWidgetIds,appWidgetManager);
            return null;
        }

        //对数据进行拆分
        try {
            JSONArray yuan1 = new JSONArray(filedat); // 对数据进行 转换
            for (int i = 0 ; i < yuan1.length() ;i++){
                JSONObject myjava = new JSONObject(yuan1.get(i).toString());
                String Local_weeks = (String) myjava.get("rqxl");
                int week1_1 = Integer.parseInt(String.valueOf(Local_weeks.charAt(0))) - 1; // 周几的课
                int week1_2 = Integer.parseInt(String.valueOf(Local_weeks.charAt(2))) - 1;//  第几节课
                weekclass[week1_1][week1_2] = myjava;  // 装填数据
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 判断 和 返回最近一节课
        // 定义 现在时几点
        // 获取当前时间 时分
        LocalDateTime now = LocalDateTime.now();
        int hours = now.getHour();
        int minute = now.getMinute();

        for (int b = 0 ; b < weekclass.length ; b++){
            // 循环 查找当天的课程
            if (MgetDayOfWeek() - 1 == b){
                int zhou_b = b - 1; // 具体的天数
//                ifBool(week_time,hours+":"+minute) != -1? weekclass[b][ifBool(week_time,hours+":"+minute)].toString():null
                // ifBool(week_time,hours+":"+minute) 返回 4
                try{
                    // 当 为周六 时，返回为无数据
                    if (b == 5){
                        return null;
                    }
                    return weekclass[b][ifBool(week_time,hours+":"+minute)];
                }catch (Exception s){
                    return null;
                }
//                try{
//                    return ifBool(week_time,hours+":"+minute) != -1? weekclass[b][ifBool(week_time,hours+":"+minute)]:null ;
//                }catch (Exception e){
//                    return null;
//                }
            }
        }
        return null;
    }

    public int MgetDayOfWeek(){
        LocalDate currentDate = LocalDate.now(); // 获取当前日期
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek(); // 获取星期几

        switch (dayOfWeek) {
            case MONDAY:
                return 1;
            case TUESDAY:
                return 2;
            case WEDNESDAY:
                return 3;
            case THURSDAY:
                return 4;
            case FRIDAY:
                return 5;
            case SATURDAY:
                return 6;
            case SUNDAY:
                return 7;
        }
        return  1 ; // 默认返回周一
    }
    /***
     * @apiNote 返回时间
     */
    public String[] timeArrye(){
        String[] tims = new String[8];
        tims[0] = "8:30-9:15";
        tims[1] = "9:15-10:00";
        tims[2] = "10:15-11:00";
        tims[3] = "11:00-11:45";
        tims[4] = "13:50-14:35";
        tims[5] = "14:35-15:20";
        tims[6] = "15:35-16:20";
        tims[7] = "16:20-17:00";
        return tims;
    }
    /***
     *  时间对比 返回-1 为结束本天课程
     */
    public int ifBool(String[] ify, String time){
        //
        for (int i = 0; i < ify.length; i++) {
            String ify_Sp1 = (new String(ify[i]).split("-"))[0];
            String[] ifTime_sp1 = ify_Sp1.split(":");
            String[] time_Sp1 = time.split(":");
            if (Integer.parseInt(time_Sp1[0]) < Integer.parseInt(ifTime_sp1[0])){
                // 当时间小于 这一节的时间时，返回 i
                Log.d("log____d",i+"");
                return i;
            }
        }
        return -1;
    }
}
