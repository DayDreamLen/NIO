package com.file.manager.frame;

import com.file.manager.Event.RightClickEvent;
import com.file.manager.function.FileNode;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 16:38
 * @Description: 文件目录树结构组件
 */
public class FileTree extends JTree {

    //文件列表组件
    private FileList fileSystemList = null;

    private FileTreeModel fileSystemTreeModel = null;

    public FileTree(FileList fileSystemList) {
        this.fileSystemList = fileSystemList;
        //构建主目录节点
        FileNode fileNode = new FileNode();
        //构建树模型
        fileSystemTreeModel = new FileTreeModel(fileNode);
        this.setModel(fileSystemTreeModel);
        //添加渲染器
        this.setCellRenderer(new TreeRenderer());
        //显示主目录节点
        fileSystemList.setList(fileNode);
        //添加选择事件
        this.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {

            }
        });
        this.setSelectionRow(0);
    }

    /**
     * 获取树模型
     *
     * @param
     * @return
     */
    public FileTreeModel getFileSystemTreeModel() {
        return this.fileSystemTreeModel;
    }

    /**
     * 树响应事件
     *
     * @param
     * @return
     */
    @Override
    public void fireValueChanged(TreeSelectionEvent tse) {
        //设为普通模式
        RightClickEvent.setViewFlg("Common Model View.");
        FileNodeOperation.getSearchNodeList().removeAllElements();
        TreePath path = tse.getNewLeadSelectionPath();
        I_Node node = (I_Node) path.getLastPathComponent();

        //如果选中的节点不为计算机、网络或库三个节点则新建操作有效
        if (!node.getFile().getName().equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}") &&
                !node.getFile().getName().equals("::{F02C1A0D-BE21-4350-88B0-7367FC96EF3C}") &&
                !node.getFile().getName().equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}")) {
            MainFrame.newFile.setEnabled(true);
            MainFrame.nMenu.setEnabled(true);
            MainFrame.openItem.setEnabled(true);
        } else {
            MainFrame.newFile.setEnabled(false);
            MainFrame.nMenu.setEnabled(false);
            MainFrame.openItem.setEnabled(false);
        }
        String nodePath = node.getPath();
        //网络节点
        if (nodePath.equals("::{F02C1A0D-BE21-4350-88B0-7367FC96EF3C}")) {
            MainFrame.tfdAddress.setText("网络");
        }
        //计算机节点
        else if (nodePath.equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}")) {
            MainFrame.tfdAddress.setText("计算机");
        }
        //库节点
        else if (nodePath.equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}")) {
            MainFrame.tfdAddress.setText("库");
        }
        //其他节点
        else {
            MainFrame.tfdAddress.setText(nodePath);
        }
        //重新设置记录节点向量
        if (SelectedNode.recordSize < (SelectedNode.getRecordNodesSize() - 1)) {
            SelectedNode.resetRecordNodes();
        }

        //设为临时节点
        SelectedNode.setSelectedNode(node);
        //显示节点
        fileSystemList.setList(node);
        //返回按钮有效
        MainFrame.returnButton.setEnabled(true);
    }

    /**
     * 通知已注册对获得此事件类型通知感兴趣的所有侦听器
     *
     * @param
     * @return
     */
    @Override
    public void fireTreeCollapsed(TreePath path) {
        super.fireTreeCollapsed(path);
        //返回选中的第一个节点的路径
        TreePath curpath = getSelectionPath();
        if (path.isDescendant(curpath)) {
            setSelectionPath(path);
        }
    }
}
