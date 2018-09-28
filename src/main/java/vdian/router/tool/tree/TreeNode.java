package vdian.router.tool.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>文件名称：NodeStorageHolder.java</p>
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2011-2099</p>
 * <p>公   司： 口袋购物 </p>
 * <p>内容摘要： </p>
 * <p>其他说明： </p>
 * <p>完成日期：2018年8月8日</p>
 *
 * @author dengkui@weidian.com
 * @version 1.0
 */
public class TreeNode<D> {
    public String            id;
    public String            pid;
    public D                 data;
    public List<TreeNode<D>> children;

    public TreeNode(String id, String pid, D data) {
        this.id = id;
        this.pid = pid;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public List<TreeNode<D>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<D>> children) {
        this.children = children;
    }

    private static List<TreeNode> list2Tree(List<TreeNode> list) {
        List<TreeNode> treeList = new ArrayList<TreeNode>();
        for (TreeNode tree : list) {
            if (tree.getPid().equals("0")) {
                treeList.add(tree);
            }

            for (TreeNode treeNode : list) {
                if (treeNode.getPid().equals(tree.getId())) {
                    if (tree.getChildren() == null) {
                        tree.setChildren(new ArrayList<TreeNode>());
                    }
                    tree.getChildren().add(treeNode);
                }
            }
        }
        return treeList;
    }
}