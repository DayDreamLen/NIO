package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.frame.SelectedNode;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 16:34
 * @Description:
 */
public class RefreshEvent implements ActionListener {
    private FileList fileSystemList = null;
    private FileNodeOperation fileNodeOperation = null;

    public RefreshEvent(FileNodeOperation fileNodeOperation,
                        FileList fileSystemList) {
        this.fileNodeOperation = fileNodeOperation;
        this.fileSystemList = fileSystemList;
    }

    public void execute() {
        I_Node selectedNode = (I_Node) fileSystemList.getSelectedValue();
        if (selectedNode != null) {
            I_Node parent = (I_Node) selectedNode.getParent();
            fileSystemList.setList(parent);
        } else {
            I_Node node = SelectedNode.getSelectedNode();
            fileSystemList.setList(node);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
    }
}
