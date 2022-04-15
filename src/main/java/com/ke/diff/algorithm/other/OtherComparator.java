package com.ke.diff.algorithm.other;

import com.google.gson.JsonElement;
import com.ke.diff.algorithm.Comparator;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;

/**
 * 其他类型比较器接口，用于2个JsonElement不同时为对象、数组、空、基本类型时对2个元素进行比较。
 *
 * 举例：第一个要比较类型为对象类型，第二个要比较类型为数组类型。
 * @Author JingWei
 * @create 2022/2/23
 */
public interface OtherComparator extends Comparator {
    /**
     * 对两个JsonElement进行比较并且两个JsonElement的类型不相等时，需要实现此方法。
     * @param a 要比较的第一个JsonElement
     * @param b 要比较的第二个JsonElement
     * @return  不同的比较结果
     */
    DiffContext diff(JsonElement a, JsonElement b, PathModule pathModule);

}
