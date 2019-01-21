package com.file.manager.frame;

import com.file.manager.function.I_Node;

import javax.swing.*;
import java.awt.*;

/**
 * @Auther: CQ02
 * @Date: 2018/12/27 14:21
 * @Description: 文件列表元素渲染器
 */
public class FileRenderer extends JLabel implements ListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        I_Node node = (I_Node) value;
        setIcon(node.getIcon());
        setText(((I_Node) value).getFileName().toString());
        //选中时背景为蓝色，未选中为白色
        setBackground(isSelected ? Color.blue : Color.WHITE);
        //选中时文件名字为白色，未选中为黑色
        setForeground(isSelected ? Color.WHITE : Color.BLACK);
        return this;
    }

    public FileRenderer() {
        setOpaque(true);
    }

}
