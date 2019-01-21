package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 15:53
 * @Description:
 */
public class JZipFilesEvent implements ActionListener {

    private FileList fileSystemList = null;
    private FileNodeOperation fileNodeOperation = null;
    private RefreshEvent refreshEvent;

    public JZipFilesEvent(FileNodeOperation fileNodeOperation, FileList fileSystemList) {
        this.fileNodeOperation = fileNodeOperation;
        this.fileSystemList = fileSystemList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        I_Node node = (I_Node) fileSystemList.getSelectedValue();
        System.out.println(node.getPath().split("\\.")[0]);
        fileNodeOperation.jzipFiles(node.getPath(), node.getPath().split("\\.")[0]);
        refreshEvent = new RefreshEvent(fileNodeOperation, fileSystemList);
        refreshEvent.execute();
    }
}
