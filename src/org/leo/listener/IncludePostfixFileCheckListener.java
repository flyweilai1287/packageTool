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
public class IncludePostfixFileCheckListener implements FileCheckListener {
    public boolean isTrue(File file,ConfigEntity entity){
        boolean result=true;
        if(entity.getIncludePostfixs()!=null&&entity.getIncludePostfixs().trim().length()>0){
            String[] splitStr=file.getName().split("\\.");
            if(entity.getIncludePostfixs().contains(splitStr[splitStr.length-1]+",")){
                result= true;
            }else{
                result= false;
            }
        }else{
            LogUtils.log("includePostfixs属性为空，继续其他步骤");
        }
        return result;
    }
}
