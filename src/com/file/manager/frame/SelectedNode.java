package com.file.manager.frame;

import com.file.manager.function.I_Node;

import java.util.Vector;

/**
 * @Auther: CQ02
 * @Date: 2018/12/28 10:21
 * @Description: 选中显示的节点
 */
public class SelectedNode {
    public static int recordSize;
    private static Vector<I_Node> recordNodes = new Vector<>();
    private static I_Node selectedNode = null;

    /**
     * @Auther: CQ02
     * @Date: 2018/12/28 10:21
     * @Description: 设置为选中显示的节点
     */
    public static void setSelectedNode(I_Node temp) {
        selectedNode = temp;
        recordNodes.add(selectedNode);
        recordSize = recordNodes.size() - 1;
    }

    /**
     * @Auther: CQ02
     * @Date: 2018/12/28 10:21
     * @Description: 获取选中显示的节点
     */
    public static I_Node getSelectedNode() {
        return selectedNode;
    }

    /**
     * @Auther: CQ02
     * @Date: 2018/12/28 10:21
     * @Description: 重设选中显示的节点
     */
    public static void resetRecordNodes() {
        int len = recordNodes.size() - recordSize - 1;
        int index = recordSize + 1;
        for (int i = 1; i <= len; i++) {
            recordNodes.removeElementAt(index);
        }
        recordSize = recordNodes.size() - 1;
    }

    /**
     * @Auther: CQ02
     * @Date: 2018/12/28 10:21
     * @Description: 获取选中显示节点的数量
     */
    public static int getRecordNodesSize() {
        return recordNodes.size();
    }

    /**
     * @Auther: CQ02
     * @Date: 2018/12/28 10:21
     * @Description: 获取选中节点向量
     */
    public static Vector<I_Node> getRecordNode() {
        return recordNodes;
    }
}
