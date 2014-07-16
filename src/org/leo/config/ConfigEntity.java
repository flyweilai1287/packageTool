package org.leo.config;

import org.leo.listener.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 14-7-11
 * Time: 上午9:38
 * To change this template use File | Settings | File Templates.
 */
public class ConfigEntity {
    //目录，路径
    private String path;
    //绝对地址
    private String absolutePath;
    //需包含的文件
    private String includeFiles;
    //不包含在内的文件
    private String excludeFiles;
    //包含指定后缀的文件
    private String includePostfixs;
    //不包含这些后缀的文件
    private String excludePostfixs;
    //文件列表
    private List<File> fileList=new ArrayList<File>();
    //最小时间，
    private long minTime;
    //最大时间
    private long maxTime;
    private String preListenerPackage="org.leo.listener.";
    private int uploadFlag=0;
    //该参数是在uploadflag=true的时候有用，将需要更新的文件先进行备份。备份到该路径下。
    private String bakPath;

    private List<FileCheckListener> listeners;
    public ConfigEntity(){
        super();
        addListeners();
    }
    public void addListeners(){
        listeners=new ArrayList<FileCheckListener>();
        try{
            listeners.add((FileCheckListener)IncludePostfixFileCheckListener.class.newInstance());
            listeners.add((FileCheckListener)MinTimeFileCheckListener.class.newInstance());
            listeners.add((FileCheckListener)MaxTimeFileCheckListener.class.newInstance());
            listeners.add((FileCheckListener)ExcludePostfixFileCheckListener.class.newInstance());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //目标路径
    private String destDir;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIncludeFiles() {
        return includeFiles;
    }

    public void setIncludeFiles(String includeFiles) {
        this.includeFiles = includeFiles;
    }

    public String getExcludeFiles() {
        return excludeFiles;
    }

    public void setExcludeFiles(String excludeFiles) {
        this.excludeFiles = excludeFiles;
    }

    public String getIncludePostfixs() {
        return includePostfixs;
    }

    public void setIncludePostfixs(String includePostfixs) {
        this.includePostfixs = includePostfixs;
    }

    public String getExcludePostfixs() {
        return excludePostfixs;
    }

    public void setExcludePostfixs(String excludePostfixs) {
        this.excludePostfixs = excludePostfixs;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public long getMinTime() {
        return minTime;
    }

    public void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getDestDir() {
        return destDir;
    }

    public void setDestDir(String destDir) {
        this.destDir = destDir;
    }

    public List<FileCheckListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<FileCheckListener> listeners) {
        this.listeners = listeners;
    }

    public int getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(int uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getBakPath() {
        return bakPath;
    }

    public void setBakPath(String bakPath) {
        this.bakPath = bakPath;
    }
}
