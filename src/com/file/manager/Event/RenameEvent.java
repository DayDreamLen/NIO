package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.function.FileNode;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;
import com.file.manager.normal.NormalConstant;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 15:33
 * @Description:
 */
public class RenameEvent implements ActionListener {

    private FileList fileSystemList = null;

    private FileNodeOperation fileNodeOperation = null;

    private I_Node node = null;

    private String operaType = null;

    public RenameEvent(FileNodeOperation fileNodeOperation, FileList fileSystemList) {

        this.fileNodeOperation = fileNodeOperation;
        this.fileSystemList = fileSystemList;

        this.operaType = NormalConstant.FILESYSTEMLIST;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (operaType.equals(NormalConstant.FILESYSTEMLIST)) {
            node = (I_Node) fileSystemList.getSelectedValue();
            if (node != null) {
                String name = node.getFile().getName();
                String newName = JOptionPane.showInputDialog("请输入修改名称",
                        name);
                if (newName != null) {
                    //重命名后返回新的路径
                    String newPath = fileNodeOperation.rename(node, newName);
                    //普通模式下显示
                    if (RightClickEvent.getViewFlg().equals(
                            "Common Model View.")) {
                        I_Node parent = (I_Node) node.getParent();
                        fileSystemList.setList(parent);
                    } else if (RightClickEvent.getViewFlg().equals(
                            "Search Model View.")) {
                        //搜索模式下显示
                        int index = 0;
                        for (I_Node tnode : FileNodeOperation.getSearchNodeList()) {
                            //删掉重命名前的文件
                            if (tnode.getPath().equals(node.getPath())) {
                                FileNodeOperation.getSearchNodeList().remove(
                                        tnode);
                                break;
                            }
                            index++;
                        }
                        //添加改名后的文件到搜索列表
                        FileNodeOperation.getSearchNodeList().add(index, new FileNode(new File(newPath)));
                        I_Node searchResult = FileNodeOperation.getTempNode();
                        searchResult.removeAllChildren();
                        for (I_Node tempNode : FileNodeOperation.getSearchNodeList()) {
                            searchResult.addChild(tempNode);
                        }
                        //显示列表
                        fileSystemList.setList(searchResult);
                    }
                }
            }
        }
    }
}
