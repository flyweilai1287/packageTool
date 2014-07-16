package org.leo.helper;

import org.leo.config.ConfigEntity;
import org.leo.exception.EntityException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 14-7-11
 * Time: 上午9:43
 * To change this template use File | Settings | File Templates.
 */
public class CommonUtils {
    /**
     * 检查ConfigEntity对象，不能为空
     * @param entity
     * @return
     */
    public static boolean checkConfigEntity(ConfigEntity entity){
        boolean result=true;
        try{
            if(entity==null){
                result=false;
                throw new EntityException("ConfigEntity配置文件实体为null");
            }
        }catch (EntityException e){
            LogUtils.log(e.getMessage());
        }
        return result;
    }

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
     * 根据File得到对应的FileName的集合，将file路径中的sourceDir部分替换成destDir部分
     * @param fileArray
     * @param sourceDir
     * @param destDir
     * @return
     */
    public static List<String> getFileNameList(List<File> fileArray,String sourceDir,String destDir){
        List<String> fileNameList=new ArrayList<String>();
        for(int i=0;i<fileArray.size();i++){
            File file=fileArray.get(i);
            String filePath=file.getAbsolutePath();
            filePath=filePath.replaceAll("\\\\","/");
            fileNameList.add(filePath.replace(sourceDir,destDir));
        }
        return fileNameList;
    }

    /**
     * 检查filename是否包含prefixStr部分
     * @param fileName
     * @param prefixStr
     * @return
     */
    public static boolean checkFileName(String fileName,String prefixStr){
        boolean result=false;
        if(fileName.startsWith(prefixStr)){
            result=true;
        } else{
            result=false;
        }
        return result;
    }
}
