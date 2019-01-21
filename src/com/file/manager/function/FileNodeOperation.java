package com.file.manager.function;

import com.file.manager.normal.NormalConstant;

import javax.swing.*;
import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @Auther: CQ02
 * @Date: 2018/12/26 09:49
 * @Description: 节点的系列操作 比如是保存节点还是删除节点还是其它操作等等
 */
public class FileNodeOperation {
    private I_Node node = null;
    //存储复制的文件节点
    private Vector<Vector<I_Node>> copiedList = new Vector<Vector<I_Node>>();

    private Vector<I_Node> copyFile = null;

    //存储选中的节点
    private Vector<I_Node> multiSelectedNodeList = new Vector<>();

    //存储复制文件的路径
    private Vector<String> pastedFilePath = new Vector<>();

    //存储搜索结果的节点
    private static Vector<I_Node> searchNodeList = new Vector<>();

    //标记是否有
    private int index = 0;

    //标记文件名在路径中的位置
    private int pos = 0;

    private int newNodeIndex = 0;

    private static I_Node tempNode = new FileNode();

    public static I_Node getTempNode() {
        return tempNode;
    }

    public FileNodeOperation() {
    }

    /**
     * 获取复制文件列表
     *
     * @param
     * @return
     */
    private void setCopyList(I_Node copiedNode) {
        if (index++ == 0) {
            copyFile = new Vector<>();
            copyFile.add(copiedNode);
            copiedList.add(copyFile);
            //获取文件节点名开始的索引
            pos = copiedNode.getPath().lastIndexOf(File.separator);
        }
        File[] copieFiles = copiedNode.getFile().listFiles();
        if (copieFiles != null) {
            for (File file : copieFiles) {
                if (file.isDirectory()) {
                    copyFile = new Vector<>();
                    I_Node tNode = new FileNode(file);
                    copyFile.add(tNode);
                    copiedList.add(copyFile);
                    //递归
                    setCopyList(tNode);
                } else {
                    copyFile = new Vector<>();
                    I_Node tNode = new FileNode(file);
                    copyFile.add(tNode);
                    copiedList.add(copyFile);
                }
            }
        }
    }

    public static File newFile(String fileName) throws IOException {
        // 创建文件对象
        File file;
        file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        // 返回文件
        return file;
    }

    /**
     * 将多个文件压缩
     *
     * @param fileList    待压缩的文件列表
     * @param zipFileName 压缩文件名
     * @return 返回压缩好的文件
     * @throws IOException
     */
    public File zipFile(List<I_Node> fileList, String zipFileName) throws IOException {
        File zipFile = newFile(zipFileName);
        // 文件输出流
        FileOutputStream outputStream = new FileOutputStream(zipFile);
        // 压缩流
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

        int size = fileList.size();
        // 压缩列表中的文件
        for (int i = 0; i < size; i++) {
            File file = fileList.get(i).getFile();
            zipFile(file, zipOutputStream, file.getName(), true);
        }
        // 关闭压缩流、文件流
        zipOutputStream.close();
        outputStream.close();
        return zipFile;
    }

    /**
     * 将文件数据写入文件压缩流
     *
     * @param file            带压缩文件
     * @param zipOutputStream 压缩文件流
     * @throws IOException
     */
    private static void zipFile(File file, ZipOutputStream zipOutputStream, String fileName, boolean flag) throws IOException {
        if (file.exists()) {
            if (file.isFile()) {
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ZipEntry entry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(entry);
                // 最大流为10MB
                final int MAX_BYTE = 10 * 1024 * 1024;
                // 接收流的容量
                long streamTotal = 0;
                // 需要分开的流数目
                int streamNum = 0;
                // 文件剩下的字符数
                int leaveByte = 0;
                // byte数据接受文件的数据
                byte[] buffer;

                // 获取流的最大字符数
                streamTotal = bis.available();
                streamNum = (int) Math.floor(streamTotal / MAX_BYTE);
                leaveByte = (int) (streamTotal % MAX_BYTE);

                if (streamNum > 0) {
                    for (int i = 0; i < streamNum; i++) {
                        buffer = new byte[MAX_BYTE];
                        bis.read(buffer, 0, MAX_BYTE);
                        zipOutputStream.write(buffer, 0, MAX_BYTE);
                    }
                }

                // 写入剩下的流数据
                buffer = new byte[leaveByte];
                // 读入流
                bis.read(buffer, 0, leaveByte);
                // 写入流
                zipOutputStream.write(buffer, 0, leaveByte);
                // 关闭当前的zip entry
                zipOutputStream.closeEntry();

                // 关闭输入流
                bis.close();
                fis.close();
            } else {
                File[] listFiles = file.listFiles();
                if (listFiles == null || listFiles.length == 0) {
                    // 需要保留原来的文件结构时,需要对空文件夹进行处理
                    if (flag) {
                        // 空文件夹的处理
                        zipOutputStream.putNextEntry(new ZipEntry(fileName + "\\"));
                        // 没有文件，不需要文件的copy
                        zipOutputStream.closeEntry();
                    }
                } else {
                    for (File file1 : listFiles) {
                        // 判断是否需要保留原来的文件结构
                        if (flag) {
                            zipFile(file1, zipOutputStream, fileName + "\\" + file1.getName(), flag);
                        } else {
                            zipFile(file1, zipOutputStream, file1.getName(), flag);
                        }
                    }
                }
            }
        }
    }

    /**
     * 解压文件
     *
     * @param
     * @return
     */

    public int jzipFiles(String zipFileNodeSource, String fileNodeTarget) {

        try {
            File file = new File(zipFileNodeSource);
            ZipFile zipFile = new ZipFile(file);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.isDirectory()) {
                    String dirPath = fileNodeTarget + "\\" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    File outFile = new File(fileNodeTarget + File.separator + entry.getName());
                    if (!outFile.getParentFile().exists()) {
                        outFile.getParentFile().mkdir();
                    }
                    if (!outFile.exists()) {
                        outFile.createNewFile();
                    }
                    InputStream input = zipFile.getInputStream(entry);
                    FileOutputStream output = new FileOutputStream(outFile);
                    int temp = 0;
                    while ((temp = input.read()) != -1) {
                        output.write(temp);
                    }
                    input.close();
                    output.close();
                }
            }
            zipFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;

    }

    /**
     * 复制文件节点
     *
     * @param
     * @return
     */
    public int copy(I_Node copyFileNode, I_Node pasteFileNode) {
        try {
            setCopyList(copyFileNode);
            for (Vector<I_Node> copyFile : copiedList) {
                String copyPath = copyFile.get(0).getPath();
                String pastePath = pasteFileNode.getPath()
                        + copyPath.substring(pos);
                File file = new File(pastePath);
                if (file.exists()) {
                    JOptionPane.showMessageDialog(null,
                            file.getName() + "已存在", null,
                            JOptionPane.ERROR_MESSAGE, null);

                    return 0;
                } else {
                    pastedFilePath.add(copyPath.substring(pos));
                    if (copyFile.get(0).getFile().isFile()) {
                        copyFile(copyPath, pastePath);
                    } else if (copyFile.get(0).getFile().isDirectory()) {
                        new File(pastePath).mkdir();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 清空复制文件节点列表
     *
     * @param
     * @return
     */
    public void resetCopiedList() {
        copiedList.removeAllElements();
    }

    /**
     * 清空复制文件节点列表
     *
     * @param
     * @return
     */
    public void resetPastedFilePath() {
        pastedFilePath.removeAllElements();
    }

    /**
     * 获取粘贴节点
     *
     * @param
     * @return
     */
    public Vector<I_Node> getPastedNode(I_Node pastedFileNode) {
        Vector<I_Node> pastedNodeList = new Vector<>();
        if (pastedNodeList.size() != 0) {
            pastedNodeList.removeAllElements();
        }
        for (String path : pastedFilePath) {
            String pastedNodePath = pastedFileNode + path;
            if ((path.lastIndexOf(File.separator)) == 0) {
                I_Node pastedNode = new FileNode(new File(pastedNodePath));
                pastedNodeList.add(pastedNode);
            }
        }
        return pastedNodeList;
    }

    /**
     * 设置文件节点
     *
     * @param
     * @return
     */
    public void setFileNode(I_Node node) {
        resetIndex();
        if (copiedList.size() != 0) {
            copiedList.removeAllElements();
        }
        this.node = node;
    }

    /**
     * 设置索引为0
     *
     * @param
     * @return
     */
    private void resetIndex() {
        index = 0;
    }

    /**
     * 设置文件节点为空
     *
     * @param
     * @return
     */
    public void resetFileNode() {
        this.node = null;
    }

    /**
     * 添加复制节点
     *
     * @param
     * @return
     */
    public void setFileNodeList(I_Node node) {
        multiSelectedNodeList.add(node);
    }

    /**
     * 判断剪切板是否为空
     *
     * @param
     * @return
     */
    public boolean isClipboardEmpty() {
        return (this.node == null && multiSelectedNodeList.size() == 0);
    }

    /**
     * 添加搜索节点
     *
     * @param
     * @return
     */
    public static void setSearchNodeList(I_Node node) {
        searchNodeList.add(node);
    }

    /**
     * 获取搜索节点
     *
     * @param
     * @return
     */
    public static Vector<I_Node> getSearchNodeList() {
        return searchNodeList;
    }

    /**
     * 清空选择节点列表
     *
     * @param
     * @return
     */
    public void removeAllFileNode() {
        if (multiSelectedNodeList.size() != 0) {
            multiSelectedNodeList.removeAllElements();
        }
    }

    /**
     * 获取选中复制节点列表
     *
     * @param
     * @return
     */
    public Vector<I_Node> getMultiSelectedNodeList() {
        return this.multiSelectedNodeList;
    }

    /**
     * 获取当前节点
     *
     * @param
     * @return
     */
    public I_Node getNode() {
        return node;
    }

    /**
     * 删除节点
     *
     * @param
     * @return
     */
    public void deleteNode(I_Node node) {
        File file = node.getFile();
        deleteFile(file);
    }

    /**
     * 删除文件或文件夹
     *
     * @param
     * @return
     */
    private void deleteFile(File file) {
        if (file.isDirectory() && file.listFiles() != null) {
            File[] fileList = file.listFiles();
            for (File oneFile : fileList) {
                if (oneFile.isDirectory()) {
                    deleteFile(oneFile);
                } else {
                    oneFile.delete();
                }
            }
            file.delete();
        } else {
            file.delete();
        }
    }

    /**
     * 重命名节点文件并返回新的路径
     *
     * @param
     * @return
     */
    public String rename(I_Node node, String newFileName) {
        String path = "";
        if (node != null) {
            File file = node.getFile();
            path = ((I_Node) node.getParent()).getPath() + File.separator + newFileName;
            file.renameTo(new File(path));
        }
        return path;
    }

    /**
     * 新建文件夹
     *
     * @param
     * @return
     */
    public void createFolder() {
        String path = node.getPath() + File.separator + NormalConstant.NEW_FOLDER;

        int index = getNewFolderIndex(path);
        if (index != 0) {
            path = path + "(" + index + ")";
        }
        File folder = new File(path);
        folder.mkdir();
        I_Node newFolder = new FileNode(folder);
        node.addChild(newFolder);
        newNodeIndex = 0;
    }

    /**
     * 新建文件
     *
     * @param
     * @return
     */
    public void createNewFile(String type) {
        String path = null;
        switch (type) {
            case NormalConstant.TXT:
                path = node.getPath() + File.separator + "新建文本文档"
                        + NormalConstant.TXT;
                break;
            case NormalConstant.DOC:
                path = node.getPath() + File.separator + "新建Word文档"
                        + NormalConstant.DOC;
                break;
            case NormalConstant.DOCX:
                path = node.getPath() + File.separator + "新建Word文档"
                        + NormalConstant.DOCX;
                break;
            case NormalConstant.XLS:
                path = node.getPath() + File.separator + "新建Excel文档"
                        + NormalConstant.XLS;
                break;
            case NormalConstant.XLSX:
                path = node.getPath() + File.separator + "新建Excel文档"
                        + NormalConstant.XLSX;
                break;
        }
        int index = getOneFileIndex(path);

        if (index != 0) {
            switch (type) {
                case NormalConstant.TXT:
                    path = path.split("\\.")[0] + "(" + index + ")"
                            + NormalConstant.TXT;
                    break;
                case NormalConstant.DOC:
                    path = path.split("\\.")[0] + "(" + index + ")"
                            + NormalConstant.DOC;
                    break;
                case NormalConstant.DOCX:
                    path = path.split("\\.")[0] + "(" + index + ")"
                            + NormalConstant.DOCX;
                    break;
                case NormalConstant.XLS:
                    path = path.split("\\.")[0] + "(" + index + ")"
                            + NormalConstant.XLS;
                    break;
                case NormalConstant.XLSX:
                    String[] paths = path.split("\\.");
                    path = paths[0] + "(" + index + ")"
                            + NormalConstant.XLSX;
                    break;
            }
        }

        File file = new File(path);
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        I_Node newFile = new FileNode(file);
        node.addChild(newFile);
        newNodeIndex = 0;
    }

    /**
     * 获取图标
     *
     * @param
     * @return
     */
    public Icon getIcon(String iconName) {
        String currentDir = null;
        try {
            currentDir = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //新建icon包中的图标并返回
        return new ImageIcon(this.getClass().getResource("../icon/" + iconName));
    }

    /**
     * 获取已新建文件夹最大索引
     *
     * @param
     * @return
     */
    private int getNewFolderIndex(String path) {
        File file = new File(path);
        if (file.exists()) {
            newNodeIndex++;
            if (file.getName().indexOf("(") > 0) {
                int postion = file.getPath().lastIndexOf("(");
                String filePath = file.getPath().substring(0, postion) +
                        "(" + newNodeIndex + ")";
                //递归判断加1后的索引存在不
                getNewFolderIndex(filePath);
            } else {
                String filePath = file.getPath() + "(" + newNodeIndex + ")";
                getNewFolderIndex(filePath);
            }
        }
        return newNodeIndex;
    }

    /**
     * 复制文件
     *
     * @param
     * @return
     */
    private void copyFile(String copiedPath, String pastedPath) {

        try {
            File file = new File(copiedPath);
            FileInputStream fileInputStream = new FileInputStream(file);
            File pasteFile = new File(pastedPath);
            FileOutputStream fileOutputStream = new FileOutputStream(pasteFile);
            byte[] buffer = new byte[10240];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取已新建同类型文件的最大索引
     *
     * @param
     * @return
     */
    private int getOneFileIndex(String path) {
        File file = new File(path);
        String fileType = path.split("\\.")[1];

        //判断文件是否存在
        if (file.exists()) {
            newNodeIndex++;
            if (file.getName().indexOf("(") > 0) {
                int postion = file.getPath().lastIndexOf("(");
                //新文件路径
                String filePath = file.getPath().substring(0, postion) + "(" +
                        (newNodeIndex) + ")"
                        + "." + fileType;
                //递归判断文件是否存在
                getOneFileIndex(filePath);
            } else {
                String filePath = file.getPath().substring(0,
                        file.getPath().lastIndexOf("."));
                filePath = filePath
                        + "("
                        + (newNodeIndex)
                        + ")" + "." + fileType;
                getOneFileIndex(filePath);
            }
        }
        return newNodeIndex;
    }
}
