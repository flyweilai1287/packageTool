package org.leo.config;

import org.leo.helper.LogUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: maco
 * Date: 14-6-23
 * Time: 下午3:18
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    private Properties configProperties=new Properties();
    private Properties customConfig=new Properties();
    private String propertyFileName="customConfig.properties";
    public static ConfigEntity entity=new ConfigEntity();
    {
        load();
    }
    public void loadConfig(Properties prop,String fileName){
        try{
            InputStream is=new BufferedInputStream(new FileInputStream(fileName));
            try {
                prop.load(is);
            } catch (IOException e) {
                LogUtils.error("读取配置文件失败："+e.getMessage());
            }
        }catch (FileNotFoundException e){
            LogUtils.error("读取配置文件失败：没找到文件"+e.getMessage()+":"+fileName);
        }
    }
    public  void load(){
        String userDir=(String)System.getProperties().get("user.dir");
        loadConfig(configProperties,userDir+"\\"+"config.properties");
        parse(configProperties);
        loadConfig(customConfig, userDir + "\\" + propertyFileName);
        parse(customConfig);
//        checkConfigEntity();
    }
    public static void parse(Properties properties){
        if(properties.containsKey("includePostfixs")){
            entity.setIncludePostfixs(properties.getProperty("includePostfixs", ""));
        }
        if(properties.containsKey("excludePostfixs")){
            entity.setExcludePostfixs(properties.getProperty("excludePostfixs", "log,bak,"));
        }
        if(properties.containsKey("sourcePath")){
            entity.setAbsolutePath(properties.getProperty("sourcePath",""));
        }
        if(properties.containsKey("destPath")){
            entity.setDestDir(properties.getProperty("destPath",""));
        }
        if(properties.containsKey("maxTime")){
            String maxTime=properties.getProperty("maxTime","now");
//            if(!maxTime.equals("now")){
//                entity.setMaxTime();
//            }
        }
        if(properties.containsKey("uploadFlag")){
            entity.setUploadFlag(Integer.parseInt(properties.getProperty("uploadFlag", "0")));
        }
        if(properties.containsKey("bakPath")){
            entity.setBakPath(properties.getProperty("bakPath",""));
        }
    }
    public void checkConfigEntity(){
         if(entity.getAbsolutePath()==null||entity.getAbsolutePath().trim().length()==0){
             LogUtils.error("absolutePath不能为空");
         }
        if(entity.getDestDir()==null||entity.getDestDir().trim().length()==0){
            LogUtils.error("DestDir不能为空");
        }

    }
    public static void main(String[] args){
//        Properties properties=System.getProperties();
//        Enumeration<String> enumeration=(Enumeration<String>)properties.propertyNames();
//        while(enumeration.hasMoreElements()){
//            String key=enumeration.nextElement();
//            System.out.println(key+"-->"+properties.get(key));
//        }
        new Config();

    }
}
