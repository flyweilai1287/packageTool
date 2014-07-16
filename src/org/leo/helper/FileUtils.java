package org.leo.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 14-6-23
 * Time: 下午4:06
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils {

    public static List<File> fileList=new ArrayList<File>();

    /**
     *根据文件路径找到所有的文件
     * @param path
     */
    public static List<File> findFileLists(List<File> fileList,String path){
        File file=new File(path);
        if(file.isDirectory()){
            String[] list=file.list();
            for(int i=0;i<list.length;i++){
                findFileLists(fileList,path+"/"+list[i]);
            }
        } else{
            fileList.add(file);
        }
        return fileList;
    }

    /**
     * 查找返回满足条件的文件列表
     * @param path
     * @param minTime
     * @param includePostfix
     * @param excludePostfix
     * @return
     */
    public static void getFileListsByModifyTime(final String path,final long minTime,final String includePostfix,final String excludePostfix){
        File file=new File(path);
        File[] list=null;
        if(file.isDirectory()){
            list=file.listFiles(new FileFilter() {
                public boolean accept(File tmpFile) {
                    if(tmpFile.isDirectory()){
                        getFileListsByModifyTime(path+"/"+tmpFile.getName(),minTime,includePostfix,excludePostfix);
                        return false;
                    } else{
                        return checkFile(tmpFile,minTime,includePostfix,excludePostfix);
                    }
                }
            });
            for(int i=0;i<list.length;i++){
                fileList.add(list[i]);
            }
        } else{
            LogUtils.log("您提供的路径是一个文件而不是路径，将只是复制该文件");
            fileList.add(file);
        }
    }
    /**
     * 检查file是否满足要求，如果满足要求就返回true，否则返回false；
     * @param file
     * @param minTime
     * @param includePostfix
     * @param excludePostfix
     * @return
     */
    public static boolean checkFile(File file,long minTime,String includePostfix,String excludePostfix){
        boolean includeResult=true;
        boolean timeResult=true;
        boolean excludeResult=true;
        if(!file.isDirectory()){
            //先检查include，如果include为空则继续执行，如果不为空，则所有返回true的文件必须是指定的类型。
            if(includePostfix!=null&&includePostfix.trim().length()>0){
                String[] splitStr=file.getName().split("\\.");
                if(includePostfix.contains(splitStr[splitStr.length-1]+",")){
                    includeResult=true;
                }else{
                    includeResult=false;
                }
            }
            if(includeResult==true){
                if(file.lastModified()>=minTime){
                      timeResult=true;
                }else{
                    timeResult=false;
                }
            }
            if(excludePostfix!=null&&excludePostfix.trim().length()>0){
                String[] splitStr=file.getName().split("\\.");
//                System.out.println(splitStr.length);
                if(splitStr.length>0&&excludePostfix.contains(splitStr[splitStr.length-1]+",")){
                    includeResult=false;
                }else{
                    includeResult=true;
                }
            }
        }
        return includeResult&&timeResult&&excludeResult;
    }
    public static void copyFileList(List<File> fileArray,String source,String dest){
        for(int i=0;i<fileArray.size();i++){
             File file=fileArray.get(i);
            String filePath=file.getAbsolutePath();
//            System.out.println(filePath);
            String destPath=filePath.replace(source, dest);
//            System.out.println(destPath);
            try {
                copyFile(filePath,destPath);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            System.out.println(filePath+" 复制成功");
        }
    }
    public static void printlList(String fileName,String source,String dest){
        String destPath=fileName.replace(source, dest);
        System.out.println(destPath);
    }
    public static void copyFile(String source,String dest) throws Exception{
        InputStream inputStream=new FileInputStream(source);
        File file1=new File(dest);
        if(!file1.exists()){
                file1.getParentFile().mkdirs();
//            file1.createNewFile();
        }
        OutputStream outputStream=new FileOutputStream(dest);
        byte[] bytes=new byte[inputStream.available()];
        inputStream.read(bytes);
        outputStream.write(bytes);
        inputStream.close();
        outputStream.close();

    }
    public static void main(String[] args){
//        String[] k="大话模式源代码.rar".split("\\.");
//        System.out.println(k[0]);
        Calendar calendar=Calendar.getInstance();
        calendar.set(2007,1,1);
//        File[] fileArray=
                FileUtils.getFileListsByModifyTime("F:/leo/book",calendar.getTimeInMillis(),null,"pdf,rar,zip,");
       System.out.println(fileList.size());
        FileUtils.copyFileList(fileList,"F:\\leo\\book\\","F:\\leo\\book\\bak\\");
//        FileUtils.printlList("F:\\leo\\book\\JDK_API_1_6_zh_CN.CHM","F:\\leo","E:\\leo111");
//        System.out.println("F:\\leo\\book\\JDK_API_1_6_zh_CN.CHM".replace("F:\\leo","E:\\leo111"));
//        try {
//            FileUtils.copyFile("F:\\leo\\book\\w3school.CHM","F:\\leo\\book\\bak\\w3school.CHM");
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
    }
}
