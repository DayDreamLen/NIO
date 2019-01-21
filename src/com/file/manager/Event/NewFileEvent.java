package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.frame.SelectedNode;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 15:20
 * @Description:
 */
public class NewFileEvent implements ActionListener {

    private FileList fileSystemList = null;

    private FileNodeOperation fileNodeOperation = null;

    private String createType = null;

    private RefreshEvent refreshEvent;

    public NewFileEvent(FileNodeOperation fileNodeOperation, FileList fileSystemList, String fileType) {
        this.fileNodeOperation = fileNodeOperation;
        this.fileSystemList = fileSystemList;
        this.createType = fileType;
    }

    private void execute() {
        //获取第一个被选中节点
        I_Node selectedNode = (I_Node) fileSystemList.getSelectedValue();
        if (selectedNode != null) {
            //获取父节点
            I_Node parentNode = (I_Node) selectedNode.getParent();
            if (parentNode != null) {
                fileNodeOperation.setFileNode(parentNode);
                fileNodeOperation.createNewFile(createType);
                fileSystemList.setList(parentNode);
                fileNodeOperation.resetFileNode();
            }
        } else {
            I_Node node = SelectedNode.getSelectedNode();
            if (node != null) {
                fileNodeOperation.setFileNode(node);
                fileSystemList.setList(node);
                fileNodeOperation.createNewFile(createType);
                fileNodeOperation.resetFileNode();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
//        refreshEvent = new RefreshEvent(fileNodeOperation, fileSystemList);
//        refreshEvent.execute();
    }
}
