package org.leo.helper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 14-6-23
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class LogUtils {

    public static String fileName="log.txt";
    public static void log(String message){
         log(fileName,message);
    }
    public static void log(String fileName,String message){
        try {
            OutputStream outputStream=new FileOutputStream(fileName,true);
            try {
//                System.out.println(message);
                outputStream.write(message.getBytes());
            } catch (IOException e) {
                System.err.print("无法写入日志："+e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.err.print("无法生成日志文件："+e.getMessage());
        }
    }
    public static void error(String message){
        System.err.print("出现错误："+message);
        log(fileName,message);
    }
}
