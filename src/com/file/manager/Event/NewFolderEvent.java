package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.frame.MainFrame;
import com.file.manager.frame.SelectedNode;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 15:18
 * @Description:
 */
public class NewFolderEvent implements ActionListener {

    private FileList fileSystemList = null;

    private FileNodeOperation fileNodeOperation = null;

    public NewFolderEvent(FileNodeOperation fileNodeOperation, FileList fileSystemList) {
        this.fileNodeOperation = fileNodeOperation;
        this.fileSystemList = fileSystemList;
    }

    private void execute() {
        I_Node selectedNode = (I_Node) fileSystemList.getSelectedValue();
        if (RightClickEvent.getViewFlg().equals("Search Model View.")) {
            MainFrame.newFile.setEnabled(false);
            MainFrame.nMenu.setEnabled(false);
        }
        if (RightClickEvent.getViewFlg().equals(
                "Common Model View.")) {
            if (selectedNode != null) {
                I_Node parentNode = (I_Node) selectedNode.getParent();
                fileNodeOperation.setFileNode((parentNode));
                fileNodeOperation.createFolder();
                fileSystemList.setList(parentNode);
                fileNodeOperation.resetFileNode();

            } else {
                I_Node node = SelectedNode.getSelectedNode();
                if (node != null) {
                    fileNodeOperation.setFileNode(node);
                    fileNodeOperation.createFolder();
                    fileSystemList.setList(node);
                    fileNodeOperation.resetFileNode();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
    }
}
