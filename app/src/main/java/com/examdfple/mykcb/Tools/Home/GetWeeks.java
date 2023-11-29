package com.examdfple.mykcb.Tools.Home;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @apiNote 周数
 * */
public class GetWeeks {
    // 上学年的开学日期 结束日期
    public final String LastYear_School_befuer = "9/4";
    public final String LastYear_School_after = "2/13";

   // 下半学年的开学日期 结束日期
    /**
     * @return <String> 返回字符串 为  “第 X 周 周X”
     * @apiNote 获取当前为第几周
     * */
    public String  getNewWeek(){
        Date d = new Date();
        LocalDate currentDate = LocalDate.now();
      return "";
    }
    /**
     * 获取时间字符串
     * @return 2022/11/12
     */
    @SuppressLint("SimpleDateFormat")
    public String getWeeekStr(){
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
