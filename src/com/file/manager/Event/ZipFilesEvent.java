package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 15:53
 * @Description:
 */
public class ZipFilesEvent implements ActionListener {

    private FileList fileSystemList = null;
    private FileNodeOperation fileNodeOperation = null;
    private RefreshEvent refreshEvent;

    public ZipFilesEvent(FileNodeOperation fileNodeOperation, FileList fileSystemList) {
        this.fileNodeOperation = fileNodeOperation;
        this.fileSystemList = fileSystemList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List nodeList = fileSystemList.getSelectedValuesList();
        if (nodeList != null && nodeList.size() > 0) {
            try {
                String[] path = ((I_Node) nodeList.get(0)).getPath().split("\\.");
                File file = fileNodeOperation.zipFile(nodeList, path[0] + ".zip");
                refreshEvent = new RefreshEvent(fileNodeOperation, fileSystemList);
                refreshEvent.execute();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
