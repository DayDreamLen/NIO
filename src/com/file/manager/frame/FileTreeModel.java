package com.file.manager.frame;

import com.file.manager.function.I_Node;
import com.file.manager.normal.NormalConstant;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * @Auther: CQ02
 * @Date: 2018/12/28 09:30
 * @Description: 文件树模型各类操作
 */
public class FileTreeModel implements TreeModel {

    private I_Node node = null;
    protected EventListenerList listenerList = new EventListenerList();

    public FileTreeModel(I_Node fileNode) {
        this.node = fileNode;
    }

    @Override
    public Object getRoot() {
        return this.node;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return ((I_Node) parent).getChild(NormalConstant.FOLDER, index);
    }

    @Override
    public int getChildCount(Object parent) {
        return ((I_Node) parent).getChildCount(NormalConstant.FOLDER);
    }

    @Override
    public boolean isLeaf(Object node) {
        return ((I_Node) node).isLeaf();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return 0;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listenerList.remove(TreeModelListener.class, l);
    }
}
