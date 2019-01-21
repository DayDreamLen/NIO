package com.file.manager.frame;

import com.file.manager.function.I_Node;
import com.file.manager.normal.NormalConstant;

import javax.swing.*;
import javax.swing.event.ListDataListener;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 10:15
 * @Description: 接口定义方法组件（如 JList），这些组件用于获取列表中每个单元格的值以及列表的长度。在逻辑上，模型是一个向量，索引范围从 0 到 ListDataModel.getSize() - 1。
 */
public class FileListModel implements ListModel {

    private I_Node node = null;

    public I_Node getNode() {
        return node;
    }

    public void setNode(I_Node node) {
        this.node = node;
    }

    /**
     * 获取节点的子节点数
     *
     * @param
     * @return
     */
    @Override
    public int getSize() {
        if (node != null) {
            //getChildCount获取子节点数目
            return this.node.getChildCount(NormalConstant.FILES);
        } else {
            return 0;
        }
    }

    /**
     * 获取节点的指定子节点
     *
     * @param
     * @return
     */
    @Override
    public Object getElementAt(int index) {
        if (node != null) {
            //getChild获取指定索引节点文件的子文件节点
            return this.node.getChild(NormalConstant.FILES, index);
        } else {
            return null;
        }
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
