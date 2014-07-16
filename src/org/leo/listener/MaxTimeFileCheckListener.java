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
public class MaxTimeFileCheckListener implements FileCheckListener {
    public boolean isTrue(File file,ConfigEntity entity){
        boolean result=true;
        if(entity.getMaxTime()>0){
        if(file.lastModified()<=entity.getMaxTime()){
            result=true;
        }else{
            LogUtils.log("不满足最大时间，返回false");
            result=false;
        }
        }else{
            LogUtils.log("最大时间为空，忽略");
        }
        return result;
    }
}
