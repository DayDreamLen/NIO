package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 15:32
 * @Description:
 */
public class DeleteEvent implements ActionListener {
    private FileList fileSystemList = null;
    private FileNodeOperation fileNodeOperation = null;

    public DeleteEvent(FileNodeOperation fileNodeOperation, FileList fileSystemList) {
        this.fileNodeOperation = fileNodeOperation;
        this.fileSystemList = fileSystemList;
    }

    public void execute() {

        //多个文件被选中
        List selectedNodes = fileSystemList.getSelectedValuesList();

        if (selectedNodes.size() > 0) {
            Object[] options = {"确定", "取消"};
            //当需要删除文件并点击删除按钮的时候 那么便弹出一个提示性的对话框
            int confirmValue = JOptionPane.showOptionDialog(null,
                    "您确定要删除这些文件吗?",
                    "确认文件删除", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options,
                    options[0]);

            //普通模式下删除
            if (RightClickEvent.getViewFlg().equals("Common Model View.")) {
                //获取父节点
                I_Node parentNode = (I_Node) ((I_Node) selectedNodes.get(0)).getParent();
                if (confirmValue == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < selectedNodes.size(); i++) {
                        fileNodeOperation.setFileNode((I_Node) selectedNodes.get(i));
                        fileNodeOperation.deleteNode((I_Node) selectedNodes.get(i));
                        //从父节点中删除该子节点
                        parentNode.deleteChild((I_Node) selectedNodes.get(i));
                    }
                    fileNodeOperation.resetFileNode();
                }
                fileSystemList.setList(parentNode);
                fileNodeOperation.removeAllFileNode();
            } else if (RightClickEvent.getViewFlg().equals(
                    "Search Model View.")) {
                //在搜索结果中删掉选中的节点
                //清空临时节点的所有孩子
                FileNodeOperation.getTempNode().removeAllChildren();
                Vector<I_Node> searchNodeListClone = (Vector) FileNodeOperation.getSearchNodeList().clone();
                if (confirmValue == JOptionPane.YES_OPTION) {
                    //在搜索结果中删掉选中的节点
                    for (int index = 0; index < selectedNodes.size(); index++) {
                        for (I_Node searchNode : searchNodeListClone) {
                            if (((I_Node) selectedNodes.get(index)).getPath()
                                    .equals(searchNode.getPath())) {

                                //从搜索节点列表中删除
                                FileNodeOperation.getSearchNodeList()
                                        .remove(searchNode);
                                //删除该节点文件
                                fileNodeOperation
                                        .deleteNode((I_Node) selectedNodes.get(index));
                            }
                        }

                        for (I_Node node : FileNodeOperation.getSearchNodeList()) {
                            FileNodeOperation.getTempNode().addChild(node);
                        }
                        //显示临时节点的子节点
                        fileSystemList.setList(FileNodeOperation.getTempNode());
                        fileNodeOperation.removeAllFileNode();

                    }
                }
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fileSystemList
                .setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        execute();
        fileSystemList.setCursor(Cursor
                .getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
