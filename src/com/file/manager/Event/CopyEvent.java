package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.frame.MainFrame;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;
import org.omg.CORBA.Object;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 10:34
 * @Description: 复制和剪切事件类
 */
public class CopyEvent implements ActionListener {

    private FileList fileSystemList = null;

    private FileNodeOperation fileNodeOperation = null;

    public String copyType = null;

    private String temp = null;

    /**
     * @Auther: CQ02
     * @Date: 2018/12/28 10:21
     * @Description: 剪切文件构造函数
     */
    public CopyEvent(FileNodeOperation fileNodeOperation, FileList fileSystemList, String copyType) {
        this.fileNodeOperation = fileNodeOperation;
        this.fileSystemList = fileSystemList;
        this.copyType = copyType;
    }

    /**
     * @Auther: CQ02
     * @Date: 2018/12/28 10:21
     * @Description: 复制文件构造函数
     */
    public CopyEvent(FileNodeOperation fileNodeOperation, FileList fileSystemList) {
        this.fileNodeOperation = fileNodeOperation;
        this.fileSystemList = fileSystemList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取选中文件节点
        List nodeList = fileSystemList.getSelectedValuesList();
        //复制多个文件
        if (nodeList.size() > 1) {
            //清空存储 把所有的文件节点删除
            fileNodeOperation.removeAllFileNode();
            //清空复制文件路径向量
            fileNodeOperation.resetPastedFilePath();
            for (int i = 0; i < nodeList.size(); i++) {
                //将节点存储起来 便于复制操作
                fileNodeOperation.setFileNodeList((I_Node) nodeList.get(i));
            }
        } else {
            fileNodeOperation.removeAllFileNode();
            fileNodeOperation.resetPastedFilePath();
            fileNodeOperation.setFileNode((I_Node) nodeList.get(0));
        }
        //设置可粘贴
        MainFrame.pasteItem.setEnabled(true);
    }
}
