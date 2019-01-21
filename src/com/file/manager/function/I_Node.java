package com.file.manager.function;

import javax.swing.*;
import java.io.File;

/**
 * @Auther: CQ02
 * @Date: 2018/12/26 09:53
 * @Description:
 */
public interface I_Node {
    /**
     * 获取指定索引节点文件的子文件节点
     *
     * @param
     * @return
     */
    Object getChild(String fileKind, int index);

    /**
     * 获取指定子文件
     *
     * @param
     * @return
     */
    Object getChildFile(int index);

    /**
     * 返回根节点
     *
     * @param
     * @return
     */
    Object getRoot();

    /**
     * 获取子节点数目
     *
     * @param
     * @return
     */
    int getChildCount(String fileKind);

    /**
     * 获取文件名
     *
     * @param
     * @return
     */
    String getFileName();

    /**
     * 获取文件图标
     *
     * @param
     * @return
     */
    Icon getIcon();

    /**
     * 判断是否是叶节点
     *
     * @param
     * @return
     */
    boolean isLeaf();

    /**
     * 获取路径
     *
     * @param
     * @return
     */
    String getPath();

    /**
     * 获取父节点
     *
     * @param
     * @return
     */
    Object getParent();

    /**
     * 获取当前节点
     *
     * @param
     * @return
     */
    Object getCurrent();

    /**
     * 添加孩子节点
     *
     * @param
     * @return
     */
    void addChild(I_Node node);

    /**
     * 获取当前文件
     *
     * @param
     * @return
     */
    File getFile();

    /**
     * 删除节点文件
     *
     * @param
     * @return
     */
    void deleteChild(I_Node node);

    /**
     * 获取文件大小
     *
     * @param
     * @return
     */
    long getSize();

    /**
     * 删除所有孩子节点
     *
     * @param
     * @return
     */
    void removeAllChildren();

}
