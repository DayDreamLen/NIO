package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.frame.MainFrame;
import com.file.manager.frame.SelectedNode;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;
import com.sun.org.apache.bcel.internal.generic.Select;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 14:15
 * @Description:
 */
public class DoubleClickEvent extends MouseAdapter {
    private long clickTime = 0;
    private FileNodeOperation fileNodeOperation = null;
    private FileList fileSystemList = null;

    /**
     * @Auther: CQ02
     * @Date: 2018/12/28 10:21
     * @Description: 当产生双击事件的时候   要对文件节点进行操作 还要罗列出文件列表
     */
    public DoubleClickEvent(FileNodeOperation fileNodeOperation, FileList fileSystemList) {
        this.fileNodeOperation = fileNodeOperation;
        this.fileSystemList = fileSystemList;
    }

    /**
     * @Auther: CQ02
     * @Date: 2018/12/28 10:21
     * @Description: 响应函数
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (isDoubleClick()) {
                //设为普通模式
                RightClickEvent.setViewFlg("Common Model View.");
                //选中的节点
                I_Node node = (I_Node) fileSystemList.getSelectedValue();
                File file = new File(node.getPath());
                if (file.isDirectory()) {
                    if (SelectedNode.recordSize < SelectedNode.getRecordNodesSize() - 1) {
                        SelectedNode.resetRecordNodes();
                        MainFrame.returnButton.setEnabled(true);
                    }
                }
                //设为临时节点
                SelectedNode.setSelectedNode(node);
                //如果是文件就直接打开
                if (file.isFile()) {
                    SelectedNode.resetRecordNodes();
                    Runtime run = Runtime.getRuntime();
                    try {
                        //调用系统命令执行文件
                        Process process = run.exec("cmd /c call " + "\""
                                + file.getPath() + "\"");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        System.exit(0);
                    }
                } else {
                    //判断是否是库
                    if (node.getFile().getName().equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}")) {
                        MainFrame.tfdAddress.setText("库");
                        MainFrame.newFile.setEnabled(false);
                        MainFrame.nMenu.setEnabled(false);
                        MainFrame.propertyItem.setEnabled(false);
                        MainFrame.pasteItem.setEnabled(false);
                    } else if (node.getFile().getName().equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}")) {
                        //判断是否是计算机
                        MainFrame.tfdAddress.setText("计算机");
                        MainFrame.newFile.setEnabled(false);
                        MainFrame.nMenu.setEnabled(false);
                        MainFrame.propertyItem.setEnabled(false);
                        MainFrame.pasteItem.setEnabled(false);
                    } else if (node.getFile().getName().equals("::{F02C1A0D-BE21-4350-88B0-7367FC96EF3C}")) {
                        MainFrame.tfdAddress.setText("网络");
                        MainFrame.newFile.setEnabled(false);
                        MainFrame.nMenu.setEnabled(false);
                        MainFrame.propertyItem.setEnabled(false);
                        MainFrame.pasteItem.setEnabled(false);
                    } else {
                        if (fileNodeOperation.isClipboardEmpty()) {
                            MainFrame.pasteItem.setEnabled(false);
                        } else {
                            MainFrame.pasteItem.setEnabled(true);
                        }
                        MainFrame.tfdAddress.setText(node.getPath());

                    }
                    //显示该节点下的文件节点
                    fileSystemList.setList(node);
                }
            } else {
                if (fileNodeOperation.isClipboardEmpty()) {
                    MainFrame.pasteItem.setEnabled(false);
                } else {
                    MainFrame.pasteItem.setEnabled(true);
                }
                MainFrame.newFile.setEnabled(true);
                MainFrame.nMenu.setEnabled(true);
                MainFrame.openFile.setEnabled(true);
                MainFrame.openItem.setEnabled(true);
                MainFrame.deleteItem.setEnabled(true);
                MainFrame.renameItem.setEnabled(true);
                MainFrame.propertyItem.setEnabled(true);
                MainFrame.copyItem.setEnabled(true);
                MainFrame.cutItem.setEnabled(true);
                //选中的节点
                I_Node node1 = (I_Node) fileSystemList.getSelectedValue();
                //父节点
                I_Node parent = (I_Node) node1.getParent();
                if (node1.getFile().isDirectory()) {
                    int num = node1.getChildCount("files");
                    long longTime = node1.getFile().lastModified();
                    Date date = new Date(longTime);
                    SimpleDateFormat simpleFormat = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    String updateTime = simpleFormat.format(date);
                    MainFrame.pLabel.setText("文件类型:文件夹  " + num + "个对象 " + "修改日期:" + updateTime);
                } else if (node1.getFile().isFile()) {
                    long longTime = node1.getFile().lastModified();
                    Date date = new Date(longTime);
                    SimpleDateFormat simpleFormat = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    String updateTime = simpleFormat.format(date);
                    int pos = node1.getFile().getName().lastIndexOf(
                            '.');
                    String kind = node1.getFile().getName().substring(
                            pos + 1);
                    MainFrame.pLabel.setText(node1.getFile().getName() + " " + kind + "文件   修改日期:" + updateTime);
                } else {
                    int num = node1.getChildCount("files");
                    MainFrame.pLabel.setText(num + "个对象");
                }
                if (node1.getFile().getName().equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}") ||
                        node1.getFile().getName().equals("::{F02C1A0D-BE21-4350-88B0-7367FC96EF3C}") ||
                        node1.getFile().getName().equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}") ||
                        node1.getFile().getName().equals("C:\\Users\\jone") ||
                        parent.getFile().getName().equals("C:\\Users\\jone") ||
                        parent.getFile().getName().equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}") ||
                        parent.getFile().getName().equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}")) {
                    MainFrame.newFile.setEnabled(false);
                    MainFrame.nMenu.setEnabled(false);
                    MainFrame.openItem.setEnabled(false);
                    MainFrame.deleteItem.setEnabled(false);
                    MainFrame.renameItem.setEnabled(false);
                    MainFrame.propertyItem.setEnabled(false);
                    MainFrame.copyItem.setEnabled(false);
                    MainFrame.cutItem.setEnabled(false);
                    MainFrame.pasteItem.setEnabled(false);
                }
            }
        }
    }

    /**
     * @Auther: CQ02
     * @Date: 2018/12/28 10:21
     * @Description: 判断是否为双击事件
     */
    public boolean isDoubleClick() {
        long nowTime = System.currentTimeMillis();

        if (nowTime - clickTime < 300) {
            clickTime = 0;
            return true;
        } else {
            clickTime = nowTime;
            return false;
        }
    }
}
