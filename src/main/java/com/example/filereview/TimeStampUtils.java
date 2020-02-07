package com.example.filereview;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.Date;

public class TimeStampUtils {
    int year;
    int month;
    int date;
    int hour;
    int minute;
    int second;

    //此处获得的时间时模拟器的时区，需要更改时区需要到手机模拟器里面的设置中完成
    public static String getTimeStamp(){
        long timeStamp = System.currentTimeMillis();
        return String.valueOf(timeStamp);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int[] getTime(){
        int[] time = new int[6];
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date = c.get(Calendar.DATE);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        second = c.get(Calendar.SECOND);
        time[0] = year;
        time[1] = month;
        time[2] = date;
        time[3] = hour;
        time[4] = minute;
        time[5]= second;
        Log.d("xxxxx", year + "/" + (month + 1) + "/" + date + " " + hour + ":" + minute + ":" + second);
        return time;
    }

    //时间戳换为时间
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String stampToDate(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    //单独的分钟时间时用来判断消息的间隔时间
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String stampToMinute(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public  static long dateToLong(String timedata){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        try {
            Date date = simpleDateFormat.parse(timedata);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
