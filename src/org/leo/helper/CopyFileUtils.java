package org.leo.helper;

import org.leo.config.ConfigEntity;
import org.leo.listener.FileCheckListener;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 14-6-23
 * Time: 下午4:06
 * To change this template use File | Settings | File Templates.
 */
public class CopyFileUtils {

        /**
     * 查找返回满足条件的文件列表
     * @param entity
     * @return
     */
    public static void getFileListsByModifyTime(String absolutePath,final ConfigEntity entity){
        if(absolutePath==null||absolutePath.trim().length()==0){
            absolutePath=entity.getAbsolutePath();
        }
        final String path=absolutePath;
        File file=new File(absolutePath);
        File[] list=null;
        if(file.isDirectory()){
            list=file.listFiles(new FileFilter() {
                public boolean accept(File tmpFile) {
                    if(tmpFile.isDirectory()){
                        getFileListsByModifyTime(path+"/"+tmpFile.getName(),entity);
                        return false;
                    } else{
                        return checkFile(tmpFile,entity);
                    }
                }
            });
            for(int i=0;i<list.length;i++){
//                System.out.println(list[i]);
                entity.getFileList().add(list[i]);
            }
        } else{
            LogUtils.log("您提供的路径是一个文件而不是路径，将只是复制该文件");
            entity.getFileList().add(file);
        }
    }
    /**
     * 检查file是否满足要求，如果满足要求就返回true，否则返回false；
     * @param file
     * @param entity
     * @return
     */
    public static boolean checkFile(File file,ConfigEntity entity){
        boolean result=true;
//        System.out.println(file.getName());
        if(!file.isDirectory()){
            //先检查include，如果include为空则继续执行，如果不为空，则所有返回true的文件必须是指定的类型。
          for(FileCheckListener listener:entity.getListeners()){
                  if(listener.isTrue(file,entity)==false){
                      result=false;
                      break;
                  }
          }
        }
        return result;
    }
    public static void copyFileList(ConfigEntity entity){
        if(entity.getFileList().size()==0){
            CopyFileUtils.getFileListsByModifyTime(null,entity);
        }
        copyFileList(entity.getFileList(),entity.getAbsolutePath(),entity.getDestDir(),entity.getUploadFlag(),entity.getBakPath());
    }

    /**
     *
     * @param fileArray
     * @param sourceDir
     * @param destDir
     * @param uploadFlag 0表示是复制，1表示是从生产机根据文件进行备份，2表示更新，3表示1和2一起操作。
     * @param bakDir
     */
    public static void copyFileList(List<File> fileArray,String sourceDir,String destDir,int uploadFlag,String bakDir){
        for(int i=0;i<fileArray.size();i++){
             File file=fileArray.get(i);
            String filePath=file.getAbsolutePath();
//            System.out.println(filePath);
            filePath=filePath.replaceAll("\\\\","/");
            sourceDir=sourceDir.replaceAll("\\\\","/");
            destDir=destDir.replaceAll("\\\\","/");
            String destPath=filePath.replace(sourceDir,destDir);

            System.out.println(destPath);
            try {
                if(uploadFlag==1||uploadFlag==3){
                    bakDir=bakDir.replaceAll("\\\\","/");
                    String bakPath=filePath.replace(sourceDir,bakDir);
                    if(bakPath.equals(filePath)){
                        System.out.println("两个路径不能相同");
                        return;
                    }
                    copyFile(destPath,bakPath);
                    System.out.println("备份成功！");
                }
                if(uploadFlag!=1){
                    if(filePath.equals(destPath)){
                        System.out.println("两个路径不能相同");
                        return;
                    }
                    copyFile(filePath,destPath);
                }

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            System.out.println((i+1)+"\t"+filePath+"-->"+destPath);
        }
    }
    public static void printlList(String fileName,String source,String dest){
        String destPath=fileName.replace(source, dest);
        System.out.println(destPath);
    }
    public static void copyFile(String source,String dest) throws Exception{
        InputStream inputStream=null;
        long lastModified=0;
        try{
            File sourceFile=new File(source);
            inputStream=new FileInputStream(sourceFile);
            lastModified=sourceFile.lastModified();
        }catch (FileNotFoundException e){
             LogUtils.error("找不到文件："+source+"\n");
        }
        if(inputStream==null)return;
        File file1=new File(dest);
        if(!file1.exists()){
                file1.getParentFile().mkdirs();
        }
        OutputStream outputStream=new FileOutputStream(dest);
        byte[] bytes=new byte[inputStream.available()];
        inputStream.read(bytes);
        outputStream.write(bytes);
        inputStream.close();
        outputStream.close();

        File destFile=new File(dest);
        destFile.setLastModified(lastModified);

    }
    public static void test1(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(2007,1,1);
        ConfigEntity configEntity=new ConfigEntity();
        configEntity.setAbsolutePath("F:/leo/book");
        configEntity.setMinTime(calendar.getTimeInMillis());
        configEntity.setIncludePostfixs(null);
        configEntity.setExcludePostfixs("pdf,rar,zip,");
        System.out.println(configEntity.getFileList().size());
        CopyFileUtils.getFileListsByModifyTime(null,configEntity);
        System.out.println(configEntity.getFileList().size());
//        CopyFileUtils.copyFileList(fileList, "F:\\leo\\book\\", "F:\\leo\\book\\bak\\");
//        CopyFileUtils.printlList("F:\\leo\\book\\JDK_API_1_6_zh_CN.CHM","F:\\leo","E:\\leo111");
//        System.out.println("F:\\leo\\book\\JDK_API_1_6_zh_CN.CHM".replace("F:\\leo","E:\\leo111"));
//        try {
        configEntity.setDestDir("F:/leo111");
        CopyFileUtils.copyFileList(configEntity);
    }
    public static void test2(){
        System.out.println("更新部署方式");
        ConfigEntity configEntity=new ConfigEntity();
        configEntity.setAbsolutePath("F:/leo111");
        configEntity.setDestDir("F:/leo112");
        configEntity.setUploadFlag(3);
        configEntity.setBakPath("F:/leo1113");
        CopyFileUtils.getFileListsByModifyTime(null,configEntity);
        CopyFileUtils.copyFileList(configEntity);
    }
    public static void main(String[] args){

        String filePath="F:\\leo\\artifacts\\packageTool\\111111";
        String sourceDir="F:\\leo\\artifacts\\packageTool";
        String destDir=sourceDir+"\\update\\%dateStr%";
        sourceDir=sourceDir.replaceAll("\\\\","/");
        filePath=filePath.replaceAll("\\\\","/");
        destDir=destDir.replaceAll("\\\\","/");
        String destPath=filePath.replace(sourceDir,destDir);
        System.out.println(destPath) ;
//        test2();

    }
}
