package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.frame.MainFrame;
import com.file.manager.frame.SelectedNode;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;
import com.file.manager.normal.NormalConstant;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 16:27
 * @Description:
 */
public class RightClickEvent extends MouseAdapter {
    private FileList fileSystemList = null;
    //菜单栏
    private JPopupMenu listPopMenu = null;
    private FileNodeOperation fileNodeOperation = null;
    private static String viewFlg = "";
    private static String nullFlg = "";
    //复制类型，复制或剪切
    private static String copyType = null;
    public static JMenuItem cutFile = null;
    public static JMenuItem copyFile = null;
    public static JMenuItem deleteFile = null;
    public static JMenuItem pasteFile = null;
    public static JMenuItem zipFile = null;
    public static JMenuItem JzipFile = null;
    public static JMenuItem passwordFile = null;
    public static JMenuItem jPasswordFile = null;
    public static JMenu createObj = null;
    public static JMenuItem openFile = null;
    public static JMenuItem rename = null;
    public static JMenuItem properties = null;

    public static void setViewFlg(String viewFlg) {
        RightClickEvent.viewFlg = viewFlg;
    }

    public static String getViewFlg() {
        return viewFlg;
    }

    public RightClickEvent(FileNodeOperation fileNodeOperation, FileList fileSystemList) {

        this.fileSystemList = fileSystemList;
        this.fileNodeOperation = fileNodeOperation;
        listPopMenu = new JPopupMenu();
        openFile = new JMenuItem("打开");
        openFile.setEnabled(false);
        openFile.addActionListener(new OpenFileEvent(fileSystemList));
        //添加快捷键助记符
        openFile.setMnemonic(KeyEvent.VK_O);
        //添加快捷键Ctrl+c
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                KeyEvent.CTRL_MASK));

        //属性菜单键
        properties = new JMenuItem("属性");
        properties.addActionListener(new PropertyEvent(fileNodeOperation,
                fileSystemList));
        properties.setEnabled(false);

        //剪切菜单键
        cutFile = new JMenuItem("剪切");
        cutFile.setEnabled(false);
        cutFile.addActionListener(new CopyEvent(fileNodeOperation, fileSystemList, "cut"));
        //添加快捷键助记符
        cutFile.setMnemonic(KeyEvent.VK_X);
        //添加快捷键Ctrl+x
        cutFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
                KeyEvent.CTRL_MASK));

        zipFile = new JMenuItem("压缩");
        zipFile.setEnabled(false);
        //添加响应事件
        zipFile.addActionListener(new ZipFilesEvent(fileNodeOperation, fileSystemList));

        JzipFile = new JMenuItem("解压");
        JzipFile.setEnabled(false);
        //添加事件响应
        JzipFile.addActionListener(new JZipFilesEvent(fileNodeOperation, fileSystemList));

        passwordFile = new JMenuItem("加密");
        passwordFile.setEnabled(false);
        //添加响应事件
        passwordFile.addActionListener(new PasswordOperationEncryptEvent(fileNodeOperation, fileSystemList));

        jPasswordFile = new JMenuItem("解密");
        jPasswordFile.setEnabled(false);
        //添加事件响应
        jPasswordFile.addActionListener(new PasswordOperationDecryptEvent(fileNodeOperation, fileSystemList));

        copyFile = new JMenuItem("复制");
        copyFile.setEnabled(false);
        //监听事件
        copyFile.addActionListener(new CopyEvent(fileNodeOperation, fileSystemList));
        //添加快捷键
        copyFile.setMnemonic(KeyEvent.VK_C);
        copyFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                KeyEvent.CTRL_MASK));

        pasteFile = new JMenuItem("粘贴");
        if (RightClickEvent.getViewFlg()
                .equals("Search Model View.")) {
            pasteFile.setEnabled(false);
        } else {
            //监听事件
            pasteFile.addActionListener(new PasteEvent(fileNodeOperation,
                    fileSystemList));
            //添加快捷键
            pasteFile.setMnemonic(KeyEvent.VK_V);
            pasteFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
        }

        JMenuItem refresh = new JMenuItem("刷新");
        //监听事件
        refresh.addActionListener(new RefreshEvent(fileNodeOperation,
                fileSystemList));
        //添加快捷键
        refresh.setMnemonic(KeyEvent.VK_E);
        refresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                KeyEvent.CTRL_MASK));

        rename = new JMenuItem("重命名");
        rename.setEnabled(false);
        rename.addActionListener(new RenameEvent(fileNodeOperation,
                fileSystemList));
        //添加快捷键
        rename.setMnemonic(KeyEvent.VK_R);
        rename.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                KeyEvent.CTRL_MASK));

        deleteFile = new JMenuItem("删除");
        deleteFile.setEnabled(false);
        //事件监听
        deleteFile.addActionListener(new DeleteEvent(fileNodeOperation,
                fileSystemList));
        //添加快捷键
        deleteFile.setMnemonic(KeyEvent.VK_D);
        deleteFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_MASK, true));

        createObj = new JMenu("新建");
        if (RightClickEvent.getViewFlg()
                .equals("Search Model View.")) {
            createObj.setEnabled(false);
        } else {
            //新建文件夹
            JMenuItem newFolder = new JMenuItem("文件夹");
            newFolder.addActionListener(new NewFolderEvent(
                    fileNodeOperation, fileSystemList));

            //新建文本文档
            JMenuItem newTextDocument = new JMenuItem(
                    "文本文档");
            newTextDocument.addActionListener(new NewFileEvent(
                    fileNodeOperation, fileSystemList,
                    NormalConstant.TXT));

            //新建Word文档
            JMenuItem newWordDocument = new JMenuItem(
                    "Word文档");
            newWordDocument.addActionListener(new NewFileEvent(
                    fileNodeOperation, fileSystemList,
                    NormalConstant.DOCX));

            //新建Excel文档
            JMenuItem newExcelDocument = new JMenuItem(
                    "Excel文档");
            newExcelDocument.addActionListener(new NewFileEvent(
                    fileNodeOperation, fileSystemList,
                    NormalConstant.XLSX));

            createObj.add(newFolder);
            createObj.addSeparator();
            createObj.add(newTextDocument);
            createObj.add(newWordDocument);
            createObj.add(newExcelDocument);
        }

        listPopMenu.add(openFile);
        listPopMenu.add(cutFile);
        listPopMenu.add(copyFile);
        listPopMenu.add(pasteFile);
        listPopMenu.add(zipFile);
        listPopMenu.add(JzipFile);
        listPopMenu.add(passwordFile);
        listPopMenu.add(jPasswordFile);
        listPopMenu.addSeparator();
        listPopMenu.add(refresh);
        listPopMenu.add(rename);
        listPopMenu.add(deleteFile);
        listPopMenu.addSeparator();
        listPopMenu.add(createObj);
        listPopMenu.addSeparator();
        listPopMenu.add(properties);
    }

    /**
     * @Auther: CQ02
     * @Date: 2018/12/28 10:21
     * @Description: 鼠标右击事件
     */
    @Override
    public void mouseClicked(MouseEvent me) {
        if (SwingUtilities.isRightMouseButton(me)) {
            //搜索模式
            if (RightClickEvent.getViewFlg().equals("Search Model View.")) {
                pasteFile.setEnabled(false);
                createObj.setEnabled(false);
            } else if (RightClickEvent.getViewFlg().equals("Common Model View.")) {
                //普通模式
                if (fileSystemList.getSelectedValuesList().size() > 1) {
                    openFile.setEnabled(false);
                    rename.setEnabled(false);
                    zipFile.setEnabled(true);
                    JzipFile.setEnabled(true);
                    passwordFile.setEnabled(true);
                    jPasswordFile.setEnabled(true);
                    properties.setEnabled(false);
                } else {
                    openFile.setEnabled(true);
                    rename.setEnabled(true);
                    zipFile.setEnabled(true);
                    JzipFile.setEnabled(true);
                    passwordFile.setEnabled(true);
                    jPasswordFile.setEnabled(true);
                    properties.setEnabled(true);
                }

                //没有复制文件就设粘贴菜单为无效
                if (fileNodeOperation.isClipboardEmpty()) {
                    pasteFile.setEnabled(false);
                } else {
                    pasteFile.setEnabled(true);
                }

                if (createObj != null) {
                    createObj.setEnabled(true);
                }

                if (fileSystemList.getSelectedValue() != null) {
                    I_Node node = (I_Node) fileSystemList.getSelectedValue();
                    I_Node parentNode = (I_Node) node.getParent();
                    if (!node.getFile().getName().equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}") &&
                            !node.getFile().getName().equals("::{F02C1A0D-BE21-4350-88B0-7367FC96EF3C}") &&
                            !node.getFile().getName().equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}") &&
                            !node.getFile().getName().equals("C:\\Users\\CQ02") &&
                            !parentNode.getFile().getName().equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}") &&
                            !parentNode.getFile().getName().equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}")) {

                        RightClickEvent.copyFile.setEnabled(true);
                        RightClickEvent.cutFile.setEnabled(true);
                        RightClickEvent.rename.setEnabled(true);
                        RightClickEvent.deleteFile.setEnabled(true);
                        RightClickEvent.createObj.setEnabled(true);
                        RightClickEvent.zipFile.setEnabled(true);
                        RightClickEvent.JzipFile.setEnabled(true);
                        RightClickEvent.passwordFile.setEnabled(true);
                        RightClickEvent.jPasswordFile.setEnabled(true);
                    } else if (parentNode.getFile().getName().equals("C:\\Users\\CQ02")) {
                        RightClickEvent.createObj.setEnabled(false);
                    } else {
                        MainFrame.newFile.setEnabled(false);
                        MainFrame.nMenu.setEnabled(false);
                        RightClickEvent.copyFile.setEnabled(false);
                        RightClickEvent.cutFile.setEnabled(false);
                        RightClickEvent.rename.setEnabled(false);
                        RightClickEvent.deleteFile.setEnabled(false);
                        RightClickEvent.createObj.setEnabled(false);
                        RightClickEvent.properties.setEnabled(false);
                        RightClickEvent.zipFile.setEnabled(false);
                        RightClickEvent.JzipFile.setEnabled(false);
                        RightClickEvent.passwordFile.setEnabled(false);
                        RightClickEvent.jPasswordFile.setEnabled(false);
                    }
                } else {
                    I_Node node = SelectedNode.getSelectedNode();
                    if (node.getFile().getName().equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}") ||
                            node.getFile().getName().equals("::{F02C1A0D-BE21-4350-88B0-7367FC96EF3C}") ||
                            node.getFile().getName().equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}") ||
                            node.getFile().getName().equals("C:\\Users\\CQ02")) {
                        RightClickEvent.properties.setEnabled(false);
                        RightClickEvent.createObj.setEnabled(false);
                    }
                    RightClickEvent.openFile.setEnabled(false);
                    RightClickEvent.rename.setEnabled(false);
                }
            }
            if (listPopMenu != null) {
                listPopMenu.show(fileSystemList, me.getX(), me.getY());
            }
        }
    }
}
