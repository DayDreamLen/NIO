package com.file.manager.frame;

import com.file.manager.function.I_Node;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * @Auther: CQ02
 * @Date: 2018/12/28 09:37
 * @Description: 文件树元素渲染器
 */
public class TreeRenderer extends DefaultTreeCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {
        I_Node node = (I_Node) value;
        Icon icon = node.getIcon();
        setLeafIcon(icon);
        setOpenIcon(icon);
        setClosedIcon(icon);
        return super.getTreeCellRendererComponent(tree, node.getFileName(), sel, expanded,
                leaf, row, hasFocus);
    }
}
