package com.ke.diff.algorithm.object;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ke.diff.algorithm.AlgorithmModule;
import com.ke.diff.algorithm.Comparator;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;

/**
 * 对象类型比较器接口，用于2个JsonElement均为JsonObject时对2个元素进行比较。
 * @Author JingWei
 * @create 2022/2/23
 */
public interface ObjectComparator extends Comparator {

    /**
     * 对两个JsonObject进行比较时，需要实现此方法。
     * @param a 要比较的第一个JsonObject
     * @param b 要比较的第二个JsonObject
     *          @param pathModule 路径模型
     * @return  返回不同的比较结果
     */
    DiffContext diff(JsonObject a, JsonObject b, PathModule pathModule);

    /**
     * 对象内部包含其他非JsonObject类型，对这些类型比较需要使用JsonElement比较方法
     * @param a 元素a
     * @param b 元素b
     * @param pathModule 路径模型
     * @return 返回不同的比较结果
     */
    DiffContext diffElement(JsonElement a, JsonElement b, PathModule pathModule);

    /**
     * 构造算法模型，对象比较时，内部元素比较需要使用到其他非数组算法
     * @param algorithmModule 算法模型：包含对象、数组、基本类型、空类型、其他类型算法
     */
    void constructAlgorithmModule(AlgorithmModule algorithmModule);
}