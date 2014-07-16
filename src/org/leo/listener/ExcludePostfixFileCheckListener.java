package org.leo.listener;

import org.leo.config.ConfigEntity;
import org.leo.helper.LogUtils;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 14-7-11
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public class ExcludePostfixFileCheckListener implements FileCheckListener {
    public boolean isTrue(File file,ConfigEntity entity){
        boolean result=true;
        if(entity.getExcludePostfixs()!=null&&entity.getExcludePostfixs().trim().length()>0){
            String[] splitStr=file.getName().split("\\.");
            if(splitStr.length>0&&entity.getExcludePostfixs().contains(splitStr[splitStr.length-1]+",")){
                result=false;
            }else{
                result=true;
            }
        }else{
            LogUtils.log("excludePostfixs为空，忽略");
        }
        return result;
    }
}
