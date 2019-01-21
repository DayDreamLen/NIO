package com.file.manager.frame;

import com.file.manager.Event.*;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.normal.NormalConstant;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * @Auther: CQ02
 * @Date: 2018/12/26 09:42
 * @Description: 主界面
 */
public class MainFrame {
    private JScrollPane treeScrollPane = null;
    private JScrollPane listScrollPane = null;
    private JFrame mainFrame = null;
    private JLabel lblAddress = null;
    public static JTextField tfdAddress = null;
    public static JTextField searchField = null;
    public static JButton returnButton = null;
    public static JButton forwardButton = null;
    public static JButton openFile = null;
    public static JButton newFile = null;
    public static JButton refreshButton = null;
    public static JMenu nMenu;
    public static JMenuItem openItem;
    public static JMenuItem nItem;
    public static JMenuItem deleteItem;
    public static JMenuItem renameItem;
    public static JMenuItem propertyItem;
    public static JMenuItem cutItem;
    public static JMenuItem copyItem;
    public static JMenuItem pasteItem;
    public static JMenuItem zipItem;
    public static JMenuItem JzipItem;
    public static JMenuItem passwordItem;
    public static JMenuItem JpasswordItem;
    public static JLabel pLabel = null;
    //JToolBar 提供了一个用来显示常用的 Action 或控件的组件
    private JToolBar toolBar = null;
    private JSplitPane pnlMain = null;

    //列举系统文件夹，获取系统图标
    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();

    public void show() {
        //地址文本编辑框
        tfdAddress = new JTextField();
        FileNodeOperation fileNodeOperation = new FileNodeOperation();
        //文件列表结构
        FileList fileSystemList = new FileList(fileNodeOperation);

        //返回按钮
        Icon returnIcon = fileNodeOperation.getIcon(NormalConstant.RETURN_ICON);
        returnButton = new JButton(returnIcon);
        returnButton.setToolTipText("返回");
        returnButton.setBounds(5, 0, 40, 30);
        returnButton.setEnabled(false);
        // 添加一个返回事件的监听器
        returnButton.addActionListener(new ReturnEvent(fileSystemList));

        //前进按钮
        Icon forward = fileNodeOperation.getIcon(NormalConstant.FORWARD_ICON);
        forwardButton = new JButton(forward);
        forwardButton.setToolTipText("前进");
        forwardButton.setBounds(45, 0, 40, 30);
        forwardButton.setEnabled(false);
        forwardButton.addActionListener(new ForwardEvent(fileSystemList));

        JMenu fileMenu = new JMenu("文件（F）");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        //菜单栏
        nMenu = new JMenu("新建");
        JMenuItem newFolder = new JMenuItem("文件夹");
        newFolder.addActionListener(new NewFolderEvent(fileNodeOperation, fileSystemList));
        JMenuItem newTxt = new JMenuItem("文本文档");
        newTxt.addActionListener(new NewFileEvent(fileNodeOperation, fileSystemList, NormalConstant.TXT));
        JMenuItem newWord = new JMenuItem("Word文档");
        newWord.addActionListener(new NewFileEvent(fileNodeOperation, fileSystemList, NormalConstant.DOCX));
        JMenuItem newExcel = new JMenuItem("Excel文档");
        newExcel.addActionListener(new NewFileEvent(fileNodeOperation, fileSystemList, NormalConstant.XLSX));
        nMenu.add(newFolder);
        nMenu.add(newTxt);
        nMenu.add(newWord);
        nMenu.add(newExcel);

        openItem = new JMenuItem("打开");
        openItem.setEnabled(false);
        openItem.addActionListener(new OpenFileEvent(fileSystemList));
        deleteItem = new JMenuItem("删除");
        deleteItem.setEnabled(false);
        //添加删除文件的监听器
        deleteItem.addActionListener(new DeleteEvent(fileNodeOperation, fileSystemList));
        renameItem = new JMenuItem("重命名");
        renameItem.setEnabled(false);
        //添加文件重命名的监听器
        renameItem.addActionListener(new RenameEvent(fileNodeOperation, fileSystemList));
        propertyItem = new JMenuItem("属性");
        propertyItem.setEnabled(false);
        propertyItem.addActionListener(new PropertyEvent(fileNodeOperation, fileSystemList));
        JMenuItem exitItem = new JMenuItem("退出(X)");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //点击退出按钮的时候 便退出整个程序
                System.exit(0);
            }
        });
        //设置组合键，表示当我们输入Ctrl-X的时候便退出
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        fileMenu.add(openItem);
        fileMenu.add(deleteItem);
        fileMenu.add(nMenu);
        fileMenu.add(renameItem);
        fileMenu.add(propertyItem);
        fileMenu.add(exitItem);

        //菜单栏“编辑”
        JMenu editMenu = new JMenu("编辑(E)");
        editMenu.setMnemonic(KeyEvent.VK_E);
        cutItem = new JMenuItem("剪切");
        cutItem.setEnabled(false);
        cutItem.addActionListener(new CopyEvent(fileNodeOperation, fileSystemList, NormalConstant.CUT));
        copyItem = new JMenuItem("复制");
        copyItem.setEnabled(false);
        copyItem.addActionListener(new CopyEvent(fileNodeOperation, fileSystemList));
        pasteItem = new JMenuItem("粘贴");
        pasteItem.setEnabled(false);
        pasteItem.addActionListener(new PasteEvent(fileNodeOperation, fileSystemList));
        //添加压缩实现模块
        zipItem = new JMenuItem("压缩");
        zipItem.setEnabled(false);
        zipItem.addActionListener(new ZipFilesEvent(fileNodeOperation, fileSystemList));
        //添加解压缩实现模块
        JzipItem = new JMenuItem("解压");
        JzipItem.setEnabled(false);
        JzipItem.addActionListener(new JZipFilesEvent(fileNodeOperation, fileSystemList));
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(zipItem);
        editMenu.add(JzipItem);

        //主菜单选项把分菜单选项添加到一起
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.setBounds(170, 30, 60, 25);

        //打开按钮
        openFile = new JButton("打开");
        openFile.setToolTipText("打开选中文件");
        openFile.setBounds(5, 0, 60, 25);
        openFile.setEnabled(false);

        //新建文件夹按钮
        newFile = new JButton("新建文件夹");
        newFile.setToolTipText("创建一个空的文件夹");
        newFile.setBounds(65, 0, 100, 25);
        pLabel = new JLabel();
        pLabel.setToolTipText("选中文件属性");
        pLabel.setBounds(350, 0, 400, 25);
        //设置滚动条
        listScrollPane = new JScrollPane(fileSystemList);
        listScrollPane.setPreferredSize(new Dimension(
                350, 300));
        fileNodeOperation = new FileNodeOperation();
        //添加鼠标右击响应事件
        fileSystemList.addMouseListener(new RightClickEvent(
                fileNodeOperation, fileSystemList));

        //添加打开文件响应事件
        openFile.addActionListener(new OpenFileEvent(fileSystemList));
        //添加新建文件夹事件
        newFile.addActionListener(new NewFolderEvent(fileNodeOperation, fileSystemList));

        //文件树结构
        FileTree fileSystemTree = new FileTree(fileSystemList);
        treeScrollPane = new JScrollPane(fileSystemTree);
        treeScrollPane.setPreferredSize(new Dimension(
                150, 300));
        //添加到分隔面板
        JSplitPane explorerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                treeScrollPane, listScrollPane);
        //设置分隔条的位置。
        explorerPane.setDividerLocation(180);
        //设置分隔条的大小。
        explorerPane.setDividerSize(8);

        //搜索栏
        searchField = new JTextField();
        searchField.setBounds(480, 0, 260, 30);
        searchField.setToolTipText("输入在左侧地址下搜索的完整文件名");
        //搜索按钮
//        Icon searchIcon = fileNodeOperation.getIcon(NormalConstant.SEARCH_ICON);
        JButton btnSearch = new JButton();
        btnSearch.setBounds(740, 0, 40, 30);
        btnSearch.setToolTipText("搜索");
        //添加搜索响应事件
        btnSearch.addActionListener(new SearchEvent(fileSystemList));

        //刷新按钮
        Icon refreshIcon = fileNodeOperation.getIcon(NormalConstant.REFRESH_ICON);
        refreshButton = new JButton(refreshIcon);
        refreshButton.setToolTipText("刷新");
        refreshButton.setBounds(420, 0, 40, 30);
        refreshButton.addActionListener(new RefreshEvent(fileNodeOperation, fileSystemList));

        tfdAddress.setBounds(90, 0, 330, 30);
        //fileSystemView 列举系统文件夹，获取系统图标
        tfdAddress.setText(fileSystemView.getHomeDirectory().getPath());
        //设置此组件的首选大小。
        tfdAddress.setPreferredSize(new Dimension(300, 25));

        //添加一个JPanel 将这些按钮等都添加到一起
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(750, 30));
        jPanel.setLayout(null);
        jPanel.add(returnButton);
        jPanel.add(forwardButton);
        jPanel.add(tfdAddress);
        jPanel.add(refreshButton);
        jPanel.add(searchField);
        jPanel.add(btnSearch);

        //把新建文件夹 、打开文件 和显示文件信息的标签添加到一起
        JPanel p2 = new JPanel();
        p2.setPreferredSize(new Dimension(750, 25));
        p2.setLayout(null);
        p2.add(openFile);
        p2.add(newFile);
        p2.add(pLabel);

        //将这两部分组件用分隔符分隔开
        pnlMain = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jPanel, p2);
        pnlMain.setDividerLocation(30);
        pnlMain.setDividerSize(5);
        //再把合成的部分与刚才前面的浏览具体文件列表的部分合到一起
        JSplitPane mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                pnlMain, explorerPane);
        mainFrame = new JFrame();
        mainFrame.setTitle("文件管理器");
        mainFrame.add(mainPane);
        mainFrame.setBounds(100, 50, 800, 600);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setVisible(true);
        //点击右上角的按钮也可以退出
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    class JMenuDemo extends JMenuBar implements ActionListener {
        JMenuItem item1;

        public JMenuDemo() {
            add(createJMenuone());
        }

        public JMenu createJMenuone() {
            JMenu menu = new JMenu("文件(F)");
            menu.setMnemonic(KeyEvent.VK_F);
            JMenuItem item = new JMenuItem("新建(N)", KeyEvent.VK_N);
            //表示键盘或等效输入设置上的键操作的 KeyStroke。
            //CTRL_MASK是Ctrl 修饰符
            // getKeyStroke 返回 KeyStroke 的共享实例，前者表示指定字符的 KEY_TYPED 事件
            //setAccelerator设置组合键，它能直接调用菜单项的操作侦听器而不必显示菜单的层次结构。
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
            menu.add(item);
            item1 = new JMenuItem("退出(X)", KeyEvent.VK_X);
            item1.addActionListener((ActionListener) this);
            item1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
            menu.add(item1);
            return menu;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //  自动生成方法存根
            if (e.getSource() == item1) {
                //得到最初发生 Event 的对象
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.show();
    }
}
