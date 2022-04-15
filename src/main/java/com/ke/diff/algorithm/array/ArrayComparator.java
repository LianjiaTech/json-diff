package com.ke.diff.algorithm.array;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.ke.diff.algorithm.AlgorithmModule;
import com.ke.diff.algorithm.Comparator;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;


/**
 * 数组类型比较器接口，用于2个JsonElement均为数组时对2个元素进行比较。
 * @Author JingWei
 * @create 2022/2/23
 */
public interface ArrayComparator extends Comparator {
    /**
     * 对两个JsonArray进行比较的方法
     * @param a 要比较的第一个JsonArray
     * @param b 要比较的第二个JsonArray
     * @param pathModule  路径模型
     * @return  返回不相等的结果
     */
    DiffContext diffArray(JsonArray a, JsonArray b, PathModule pathModule) ;

    /**
     * 对象内部包含其他非数组类型，对这些类型比较需要使用JsonElement比较方法
     * @param a 要比较的第一个JsonElement
     * @param b 要比较的第一个JsonElement
     * @param pathModule 路径模型
     * @return 返回不相等的结果
     */
    DiffContext diffElement(JsonElement a, JsonElement b, PathModule pathModule);

    /**
     * 构造算法模型，数组中元素比较需要使用到其他非数组算法
     * @param algorithmModule 算法模型：包含对象、数组、基本类型、空类型、其他类型算法
     */
    void constructAlgorithmModule(AlgorithmModule algorithmModule);



}
