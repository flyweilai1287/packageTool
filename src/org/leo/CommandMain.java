package org.leo;

import org.leo.config.Config;
import org.leo.config.ConfigEntity;
import org.leo.helper.CopyFileUtils;
import org.leo.helper.DateUtils;
import org.leo.helper.FileUtils;

public class CommandMain {

    public static void main(String[] args) {

        System.out.println("start...");
        new Config();
        ConfigEntity entity=Config.entity;
        if(entity.getUploadFlag()!=0){
            //为true表示是在生产机执行更新操作
            System.out.println("将执行更新操作:uploadFlag=1表示仅备份不更新，2表示仅更新，3表示先备份再更新："+entity.getUploadFlag());
            if(args.length<3){
                System.out.println("参数格式：源目录 目标目录 备份目录 uploadFlag excludePostfix");
                return;
            }
            entity.setAbsolutePath(args[0]);
            entity.setDestDir(args[1]);
            entity.setBakPath(args[2]);
            if(args.length>3){
            entity.setUploadFlag(Integer.parseInt(args[3]));
            }
            if(args.length>4){
                entity.setExcludePostfixs(args[4]);
            }
            System.out.println("源目录" + entity.getAbsolutePath());
            System.out.println("目标目录" + entity.getDestDir());
            System.out.println("备份目录" + entity.getBakPath());
            System.out.println("uploadFlag:" + entity.getUploadFlag());
            System.out.println("excludePostfix:" + entity.getExcludePostfixs());
            CopyFileUtils.copyFileList(entity);
            System.out.println("更新完成" );
        }else{
            System.out.println("只执行复制操作.参数格式：日期 时间 源目录 目标目录 excludePostfix");
            if(args.length<2){
                System.out.println("参数格式：日期 时间 源目录 目标目录 excludePostfix");
                return;
            }
            entity.setMinTime(DateUtils.strToLong(args[0]+" "+args[1]));
            if(args.length>2){
                entity.setAbsolutePath(args[2]);
            }
            if(args.length>3){
                entity.setDestDir(args[3]);
            }
            if(args.length>4){
                entity.setExcludePostfixs(args[4]);
            }
            System.out.println("日期 时间" + args[0]+" "+args[1]+"->"+entity.getMinTime());
            System.out.println("源目录" + entity.getAbsolutePath());
            System.out.println("目标目录" + entity.getDestDir());
            System.out.println("excludePostfix" + entity.getExcludePostfixs());
            CopyFileUtils.getFileListsByModifyTime("",entity);
            CopyFileUtils.copyFileList(entity);
            System.out.println("备份完成" );

        }
        System.out.println("end...");
    }
}
