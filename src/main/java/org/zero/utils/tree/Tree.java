package org.zero.utils.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * 树数据结构
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2019/11/28 17:19
 **/
@Data
public class Tree {

    /**
     * 根节点
     **/
    private TreeNode<?> root;

    /**
     * 根节点标识
     **/
    @JsonIgnore
    private String rootKey;

    private final static String DEFAULT_ROOT_KEY = "root";

    /**
     * 维护结点标识与结点关系的map
     **/
    @JsonIgnore
    private HashMap<String, TreeNode<?>> map = new HashMap<>();

    /**
     * 生成树形结构数据
     *
     * @param list: 元素列表（列表内的元素须按结点层级进行升序排序，因为树是按层级来构建的，如果未预先排序，则可能出现增加某个结点时，该结点的父结点还未在树里，造成增加结点失败）
     * @return void:
     * @author : cgl
     * @since 2019/11/28 17:20
     **/
    public static <T> Tree build(List<T> list) throws Exception {
        return build(list, null, null, "Id", "ParentId", false, "Level");
    }

    /**
     * 生成树形结构数据
     *
     * @param list:      元素列表（列表内的元素须按结点层级进行升序排序，因为树是按层级来构建的，如果未预先排序，则可能出现增加某个结点时，该结点的父结点还未在树里，造成增加结点失败）
     * @param nodeGroup: 结点组名（通常设置为元素类型名）
     * @return void:
     * @author : cgl
     * @since 2019/11/28 17:20
     **/
    public static <T> Tree build(List<T> list, String nodeGroup) throws Exception {
        return build(list, nodeGroup, null, "Id", "ParentId", false, "Level");
    }

    /**
     * 生成树形结构数据
     *
     * @param list:      元素列表（列表内的元素须按结点层级进行升序排序，因为树是按层级来构建的，如果未预先排序，则可能出现增加某个结点时，该结点的父结点还未在树里，造成增加结点失败）
     * @param nodeGroup: 结点组名（通常设置为元素类型名）
     * @param rootKey:   根节点标识（默认为root, 如果该树是其它树的子树，那么需要设置根节点标识，以便查找其它树的叶子结点以拼接）
     * @return void:
     * @author : cgl
     * @since 2019/11/28 17:20
     **/
    public static <T> Tree build(List<T> list, String nodeGroup, String rootKey) throws Exception {
        return build(list, nodeGroup, rootKey, "Id", "ParentId", false, "Level");
    }

    /**
     * 从根结点开始构建树形结构数据
     *
     * @param list:          元素列表
     * @param nodeGroup:     结点组名（通常设置为元素类型名）
     * @param rootKey:       根节点标识（默认为root, 如果该树是其它树的子树，那么需要设置根节点标识，以便查找其它树的叶子结点以拼接）
     * @param idField:       主键名称（首字母大写，以便调用元素的getIdField方法）
     * @param parentIdField: 父主键名称（首字母大写，以便调用元素的getParentIdField方法）
     * @param needSort:      是否需要对list进行结点层级排序（原则上不在这里排序，尽量先对list排好序再传进来）
     * @param levelField:    list结点层级排序字段（首字母大写，以便调用元素的getLevelField方法）
     * @return void:
     * @author : cgl
     * @since 2019/11/28 17:21
     **/
    public static <T> Tree build(List<T> list, String nodeGroup, String rootKey, String idField, String parentIdField, boolean needSort, String levelField) throws Exception {
        Tree tree = new Tree();
        // 创建根节点
        tree.root = new TreeNode<T>();
        tree.rootKey = DEFAULT_ROOT_KEY;
        tree.map.put(DEFAULT_ROOT_KEY, tree.root);

        if (list == null || list.size() == 0) {
            return tree;
        }
        // 结点组名为空时，取元素类名
        if (nodeGroup == null || "".equals(nodeGroup)) {
            nodeGroup = list.get(0).getClass().getSimpleName().toLowerCase().replace("dto", "");
        }
        if (rootKey == null || "".equals(rootKey)) {
            rootKey = DEFAULT_ROOT_KEY;
        }
        if (idField == null || "".equals(idField)) {
            idField = "Id";
        }
        if (parentIdField == null || "".equals(parentIdField)) {
            parentIdField = "ParentId";
        }
        if (levelField == null || "".equals(levelField)) {
            levelField = "Level";
        }

        if (needSort) {
            ItemComparator<T> comparator = new ItemComparator<>(levelField);
            list.sort(comparator);
        }

        boolean hasParentField = true;
        String parentKey = "";

        // 将第一个结点的排序字段的值最为层级最小值(默认为0)
        int minLevel = 0;
        boolean levelIsNumber = false;
        try {
            Object minLevelObj = list.get(0).getClass().getMethod("get" + levelField).invoke(list.get(0));
            if (minLevelObj instanceof Integer || minLevelObj instanceof Long) {
                minLevel = Long.valueOf(minLevelObj.toString()).intValue();
                levelIsNumber = true;
            }
        } catch (Exception e) {
            System.out.println("结点层级字段不存在");
        }

        // 遍历列表，将结点添加到树
        for (T item : list) {
            TreeNode<T> node = new TreeNode<>();
            node.setItem(item);

            // 一开始先假设存在parentId字段，如果不存在，则固定使用rootKey作为父结点标识，所有元素都作为root的一级子结点
            if (hasParentField) {
                try {
                    parentKey = nodeGroup + item.getClass().getMethod("get" + parentIdField).invoke(item);
                } catch (NoSuchMethodException e) {
                    parentKey = DEFAULT_ROOT_KEY;
                    hasParentField = false;
                }
            }

            // 结点的父结点存在，则添加到父结点下，否则根据结点的层级是否为最小层级，来决定是否添加到根结点
            if (tree.map.containsKey(parentKey)) {
                tree.map.get(parentKey).getChildren().add(node);
            } else if (levelIsNumber) {
                Object levelValueObj = item.getClass().getMethod("get" + levelField).invoke(item);
                int levelValue = Long.valueOf(levelValueObj.toString()).intValue();
                if (levelValue == minLevel) {
                    tree.map.get(DEFAULT_ROOT_KEY).getChildren().add(node);
                }
            }

            // 结点自身的标识
            String selfKey = nodeGroup + item.getClass().getMethod("get" + idField).invoke(item);
            tree.map.put(selfKey, node);
        }

        // 如果设置的根节点标识不为默认的root，则修改根节点标识
        if (!rootKey.equalsIgnoreCase(DEFAULT_ROOT_KEY)) {
            tree.map.put(rootKey, tree.root);
            tree.map.remove(DEFAULT_ROOT_KEY);
            tree.rootKey = rootKey;
        }

        return tree;
    }

    /**
     * 在已有树形结构上继续拼接子结点，子结点以list形式提供
     * 注：该方法目前只能添加一层子结点，如subNodeList自身还能构建成树，则不适用此方法（可将subNodeList构建成树，再使用combine(List<Tree>)方法）
     *
     * @param subNodeList:      子结点元素列表
     * @param subNodeGroup:     子结点组名
     * @param subIdField:       子结点主键名称（首字母大写，以便调用元素的getSubIdField方法）
     * @param parentNodeGroup:  父结点组名
     * @param subParentIdField: 子结点父主键名称（首字母大写，以便调用元素的getSubParentIdField方法）
     * @author : cgl
     * @since 2019/11/28 17:24
     **/
    public <T> void combine(List<T> subNodeList, String subNodeGroup, String subIdField, String parentNodeGroup, String subParentIdField) throws Exception {
        if (subNodeList == null || subNodeList.size() == 0) {
            return;
        }
        // 子节点组名为空时，取元素类名
        if (subNodeGroup == null || "".equals(subNodeGroup)) {
            subNodeGroup = subNodeList.get(0).getClass().getSimpleName().toLowerCase().replace("dto", "");
        }
        if (parentNodeGroup == null || "".equals(parentNodeGroup)) {
            parentNodeGroup = subNodeGroup;
        }
        if (subIdField == null || "".equals(subIdField)) {
            subIdField = "Id";
        }
        if (subParentIdField == null || "".equals(subParentIdField)) {
            subParentIdField = "ParentId";
        }

        String parentKey = "";

        for (T item : subNodeList) {
            TreeNode<T> node = new TreeNode<>();
            node.setItem(item);

            parentKey = parentNodeGroup + item.getClass().getMethod("get" + subParentIdField).invoke(item);

            if (this.map.containsKey(parentKey)) {
                this.map.get(parentKey).getChildren().add(node);
            }

            String selfKey = subNodeGroup + item.getClass().getMethod("get" + subIdField).invoke(item);
            this.map.put(selfKey, node);
        }

    }

    /**
     * 在已有树形结构上拼接子树
     *
     * @param subTreeList: 子树列表
     * @author : cgl
     * @since 2020/3/6 12:38
     **/
    public void combine(List<Tree> subTreeList) {
        if (subTreeList == null || subTreeList.size() == 0) {
            return;
        }
        subTreeList.forEach((subTree) -> {
            // 子树结点先全部合到父树
            this.map.get(subTree.getRootKey()).getChildren().addAll(subTree.getRoot().getChildren());
            // 子树的映射关系map也合并到父树的映射关系map
            subTree.getMap().remove(subTree.getRootKey());
            this.map.putAll(subTree.getMap());
        });

    }

}
