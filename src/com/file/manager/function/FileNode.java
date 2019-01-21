package com.file.manager.function;

import com.file.manager.normal.NormalConstant;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Vector;

/**
 * @Auther: CQ02
 * @Date: 2018/12/26 09:55
 * @Description:
 */
public class FileNode implements I_Node {

    private Vector<File> allFiles = new Vector<>();

    private Vector<File> folder = new Vector<>();

    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();

    private File file = null;

    private String name;

    private long dirLenth = 0;

    public FileNode() {
        file = fileSystemView.getHomeDirectory();
        addChildren();
    }

    public FileNode(File file) {
        this.file = file;
        addChildren();
    }

    @Override
    public Object getChild(String fileKind, int index) {
        if (fileKind.equals(NormalConstant.FILES)) {
            return new FileNode(allFiles.get(index));
        } else if (fileKind.equals(NormalConstant.FOLDER)) {
            return new FileNode(folder.get(index));
        }
        return null;
    }

    @Override
    public Object getChildFile(int index) {
        if (index < allFiles.size()) {
            return allFiles.get(index);
        }
        return null;
    }

    @Override
    public Object getRoot() {
        return this;
    }

    @Override
    public int getChildCount(String fileKind) {
        if (fileKind.equals(NormalConstant.FILES)) {
            return allFiles.size();
        } else if (fileKind.equals(NormalConstant.FOLDER)) {
            return folder.size();
        }
        return 0;
    }

    @Override
    public String getFileName() {
        return fileSystemView.getSystemDisplayName(file);
    }

    @Override
    public Icon getIcon() {
        return fileSystemView.getSystemIcon(file);
    }

    @Override
    public boolean isLeaf() {
        return folder.size() == 0;
    }

    @Override
    public String getPath() {
        return this.file.getPath();
    }

    @Override
    public Object getParent() {
        return new FileNode(file.getParentFile());
    }

    @Override
    public Object getCurrent() {
        return this;
    }

    @Override
    public void addChild(I_Node node) {
        allFiles.add(node.getFile());
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public void deleteChild(I_Node node) {
        allFiles.remove(node.getFile());
    }

    @Override
    public long getSize() {
        if (this.getFile().isDirectory()) {
            return getDirSize(this.getFile().getPath());
        } else {
            return getFileSize();
        }
    }

    @Override
    public void removeAllChildren() {
        this.allFiles.removeAllElements();
    }

    //添加多个孩子文件
    private void addChildren() {
        File[] fileList = fileSystemView.getFiles(file, true);
        for (File file : fileList) {
            allFiles.add(file);
            if (file.isDirectory()
                    && !file.getName().toLowerCase().endsWith(".lnk")) {
                folder.add(file);
            }
        }
    }

    //获取文件大小
    private long getFileSize() {
        return this.getFile().length();
    }

    //获取文件夹大小
    private long getDirSize(String path) {
        File[] fileList = new File(path).listFiles();
        for (File file : fileList) {
            if (file.isDirectory()) {
                getDirSize(file.getPath());
            } else {
                dirLenth = dirLenth + file.length();
            }
        }
        return dirLenth;
    }

}
