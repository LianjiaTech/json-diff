package com.ke.diff.algorithm.nulls;

import com.google.gson.JsonNull;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;

/**
 * 当要比较的两个JsonElement都为空类型时，默认实现的算法比较器
 * @Author JingWei
 * @create 2022/1/10
 */
public class DefaultNullComparator implements NullComparator {

    /**
     * 当要比较的两个JsonElement是空类型时，默认比较方法
     * @param a 第一个空类型
     * @param b 第二个空类型
     * @param pathModule 路径模型
     * @return	返回结果
     */
    @Override
    public DiffContext diff(JsonNull a, JsonNull b, PathModule pathModule){
        return new DiffContext();
    }


}