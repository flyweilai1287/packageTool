package org.leo.listener;

import org.leo.config.ConfigEntity;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 14-7-11
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public interface FileCheckListener {

    public boolean isTrue(File file,ConfigEntity entity);
}
