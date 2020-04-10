package org.zero.utils.tree;

import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 树节点
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2019/11/28 17:19
 **/
@Data
public class TreeNode<T> {

    /**
     * 元素
     **/
    private T item;

    /**
     * 子结点集合
     **/
    Set<TreeNode<?>> children = new LinkedHashSet<>();

}
