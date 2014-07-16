package org.leo;

import org.leo.helper.DateUtils;
import org.leo.helper.FileUtils;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        /*
        传参说明：日期 时间 源目录 目标目录 excludePostfix

         */
        if(args.length<4){
            System.out.println("参数格式：日期 时间 源目录 目标目录 excludePostfix");
            return;
        }

        String dateStr=args[0];
        System.out.println(dateStr);
        String timeStr=args[1];
        System.out.println(timeStr);
        String source=args[2];
        System.out.println(source);
        String dest=args[3];
        System.out.println(dest);
        String excludePostfix=args[4];
        System.out.println(excludePostfix);

        System.out.println("开始打包...");
        FileUtils.getFileListsByModifyTime(source, DateUtils.strToLong(dateStr+" "+timeStr),null,excludePostfix);
        FileUtils.copyFileList(FileUtils.fileList,source,dest);
        System.out.println("打包完成...");
    }
}
