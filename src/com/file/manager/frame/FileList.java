package com.file.manager.frame;

import com.file.manager.Event.DoubleClickEvent;
import com.file.manager.Event.RightClickEvent;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;

import javax.swing.*;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 11:35
 * @Description: 文件列表组件类
 */
public class FileList extends JList {
    //文件列表模型
    private FileListModel model = null;
    private DoubleClickEvent mouseDoubleEvent = null;
    //文件节点操作
    private FileNodeOperation fileNodeOperation = null;

    public FileList(FileNodeOperation fileNodeOperation) {
        //添加文件列表模型
        model = new FileListModel();
        this.setModel(model);
        this.fileNodeOperation = fileNodeOperation;
        //添加元素渲染器
        this.setCellRenderer(new FileRenderer());
        mouseDoubleEvent = new DoubleClickEvent(this.fileNodeOperation, this);
        //添加双击事件
        this.addMouseListener(mouseDoubleEvent);
    }

    /**
     * 在列表中显示该节点下的内容
     *
     * @param
     * @return
     */
    public void setList(I_Node node) {
        //撤销双击事件
        this.removeMouseListener(mouseDoubleEvent);
        model.setNode(node);
        this.setModel(model);
        this.updateUI();
        //主界面打开按钮失效
        MainFrame.openFile.setEnabled(false);
        //右击菜单中删除菜单失效
        RightClickEvent.deleteFile.setEnabled(false);
        //右击菜单中复制菜单失效
        RightClickEvent.copyFile.setEnabled(false);
        //右击菜单中剪切菜单失效
        RightClickEvent.cutFile.setEnabled(false);
        RightClickEvent.zipFile.setEnabled(false);
        RightClickEvent.JzipFile.setEnabled(false);
        RightClickEvent.passwordFile.setEnabled(false);
        RightClickEvent.jPasswordFile.setEnabled(false);
        //主界面文件菜单栏打开菜单无效
        MainFrame.openItem.setEnabled(false);
        //主界面编辑菜单栏删除菜单无效
        MainFrame.deleteItem.setEnabled(false);
        //主界面编辑菜单栏重命名菜单无效
        MainFrame.renameItem.setEnabled(false);
        //主界面编辑菜单栏复制菜单无效
        MainFrame.copyItem.setEnabled(false);
        //主界面编辑菜单栏打剪切菜单无效
        MainFrame.cutItem.setEnabled(false);
        MainFrame.zipItem.setEnabled(false);
        MainFrame.JzipItem.setEnabled(false);
//        MainFrame.passwordItem.setEnabled(false);
//        MainFrame.JpasswordItem.setEnabled(false);
        this.addMouseListener(mouseDoubleEvent);
    }


}
