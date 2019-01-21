package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.frame.MainFrame;
import com.file.manager.frame.SelectedNode;
import com.file.manager.function.I_Node;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 14:39
 * @Description:
 */
public class ReturnEvent implements ActionListener {
    private FileList fileSystemList;

    public ReturnEvent(FileList fileList) {
        this.fileSystemList = fileList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.forwardButton.setEnabled(true);
        //设为普通模式
        RightClickEvent.setViewFlg("Common Model View.");
        if (SelectedNode.recordSize > 0) {
            SelectedNode.recordSize--;
            fileSystemList.setList(SelectedNode.getRecordNode().
                    elementAt(SelectedNode.recordSize));
            I_Node node = SelectedNode.getRecordNode().
                    elementAt(SelectedNode.recordSize);
            //判断是否是库
            if (node.getFile().getName().equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}")) {
                MainFrame.tfdAddress.setText("库\\" + node.getFile().getName());
            }
            //判断是否是计算机
            else if (node.getFile().getName().equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}")) {
                MainFrame.newFile.setEnabled(false);
                MainFrame.tfdAddress.setText("计算机");
                MainFrame.nMenu.setEnabled(false);
                MainFrame.propertyItem.setEnabled(false);
                MainFrame.pasteItem.setEnabled(false);
            }
            //判断是否是网络
            else if (node.getFile().getName().equals("::{F02C1A0D-BE21-4350-88B0-7367FC96EF3C}")) {
                MainFrame.newFile.setEnabled(false);
                MainFrame.nMenu.setEnabled(false);
                MainFrame.tfdAddress.setText("网络");
                MainFrame.propertyItem.setEnabled(false);
                MainFrame.pasteItem.setEnabled(false);
            } else {
                MainFrame.tfdAddress.setText(node.getPath());
            }

            if (SelectedNode.recordSize == 0) {
                MainFrame.returnButton.setEnabled(false);
            }
        }
    }
}
