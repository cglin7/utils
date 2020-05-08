package org.zero.utils.tree;

import lombok.AllArgsConstructor;

import java.util.Comparator;

/**
 * 树节点元素比较器（排序字段暂时只支持Integer、Long、String类型，后续如需要可扩展其它类型字段的比较方法）
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2019/11/28 17:19
 **/
@AllArgsConstructor
public class ItemComparator<T> implements Comparator<T> {

    /**
     * 排序字段
     **/
    private String sortField;

    @Override
    public int compare(T o1, T o2) {
        try {
            Object obj1 = o1.getClass().getMethod("get" + sortField).invoke(o1);
            Object obj2 = o2.getClass().getMethod("get" + sortField).invoke(o2);
            if (obj1 instanceof Integer) {
                Integer val1 = (Integer) obj1;
                Integer val2 = (Integer) obj2;
                if (val1 < val2) {
                    return -1;
                } else if (val1 > val2) {
                    return 1;
                }
                return 0;
            } else if (obj1 instanceof Long) {
                Long val1 = (Long) obj1;
                Long val2 = (Long) obj2;
                if (val1 < val2) {
                    return -1;
                } else if (val1 > val2) {
                    return 1;
                }
                return 0;
            } else if (obj1 instanceof String) {
                String val1 = obj1.toString();
                String val2 = obj2.toString();
                return val1.compareTo(val2);
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }
}
