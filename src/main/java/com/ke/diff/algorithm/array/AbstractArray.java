package com.ke.diff.algorithm.array;

import com.ke.diff.algorithm.AbstractObjectAndArray;

/**
 * 数组比较的公有方法抽象类
 * @Author JingWei
 * @create 2022/3/1
 */
public abstract class AbstractArray extends AbstractObjectAndArray implements ArrayComparator {
    /**
     * 数组索引号加一个中括号表示数组路径
     * @param i 数组元素的索引号
     * @return 索引号增加中括号
     */
    protected String  constructArrayPath(Integer i){
        if(i == null || i < 0 ){
            throw new RuntimeException("数组索引号入参为空或者为负。 入参:" + i);
        }
        return "[" + i + "]";
    }
}
