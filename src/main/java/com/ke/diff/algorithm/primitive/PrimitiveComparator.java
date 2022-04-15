package com.ke.diff.algorithm.primitive;

import com.google.gson.JsonPrimitive;
import com.ke.diff.algorithm.Comparator;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;

/**
 * 基本类型比较器接口，用于2个JsonElement均为基本类型的时对2个元素进行比较。
 * @Author JingWei
 * @create 2022/2/23
 */
public interface PrimitiveComparator extends Comparator {
    /**
     * 对两个基本类型进行比较时，需要实现此方法。
     * @param a 要比较的第一个JsonPrimitive
     * @param b 要比较的第二个JsonPrimitive
     * @param pathModule 路径模型
     * @return  不同的比较结果
     */
    DiffContext diff(JsonPrimitive a, JsonPrimitive b, PathModule pathModule);

}
