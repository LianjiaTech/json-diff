package com.ke.diff.algorithm.object;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;

import java.util.Set;

/**
 * 简单对象比较器：当对两个JsonObject进行比较时，两个对象所有keySet中key对应的Value都会被比较。
 * 举例：
 * {"b":2,"c":3}和{"a":1,"d":4}进行比较
 * 会比较两个对象中所有的字段，即比较两个对象中并集"a","b","c","d"4个字段
 * @Author JingWei
 * @create 2022/2/16
 */
public class SimpleObjectComparator extends AbstractObject  {
    /**
     * @param a 要比较的第一个JsonObject
     * @param b 要比较的第二个JsonObject
     * @param pathModule 路径模型
     * @return 不同的比较结果
     */
    @Override
    public DiffContext diff(JsonObject a, JsonObject b, PathModule pathModule) {
        Set<String> unionSet = Sets.union(a.keySet(), b.keySet());
        //用a和b的keySet的并集作为遍历集合。
        return diffValueByKey(a, b, unionSet, pathModule);
    }


}
