package org.leo.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 14-6-23
 * Time: 下午9:07
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {
    private static SimpleDateFormat simpleTime=new SimpleDateFormat("yyyyMMdd HH:mm");
    private static SimpleDateFormat simpleDate=new SimpleDateFormat("yyyyMMdd");

    public static Date strToDate(String dateStr){
        try {
           return simpleTime.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String dateToStr(Date date){
        return simpleTime.format(date);
    }
    public static long strToLong(String dateStr){
        return  strToDate(dateStr).getTime();
    }

}
