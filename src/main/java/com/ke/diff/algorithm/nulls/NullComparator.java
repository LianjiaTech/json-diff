package com.ke.diff.algorithm.nulls;

import com.google.gson.JsonNull;
import com.ke.diff.algorithm.Comparator;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;

/**
 * 空类型比较器接口，用于2个JsonElement均为JsonNull时对2个元素进行比较。
 * @Author JingWei
 * @create 2022/2/23
 */
public interface NullComparator extends Comparator {

    /**
     * 对两个JsonNull进行比较时，需要实现此方法。
     * @param a 要比较的第一个JsonNull
     * @param b 要比较的第二个JsonNull
     * @param pathModule 路径模型
     * @return 返回不同的比较结果
     */
    DiffContext diff(JsonNull a, JsonNull b, PathModule pathModule);


}