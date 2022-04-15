package com.ke.diff.algorithm.object;

import com.google.gson.JsonObject;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;

/**
 * 左匹配对象比较器：当对两个JsonObject进行比较时，只对第一个对象中keySet中存在keyValue值进行比较。
 *
 * 举例：
 * {"a":1,"b":2,"c":3}和{"a":1,"b":4,"c":3,"d":4}进行比较
 * 只会比较第一个对象中有的字段，即比较第一个对象中有的"a","b","c"3个字段，第二个对象中的"d"字段没有被比较
 *
 * @Author JingWei
 * @create 2022/2/18
 */
public class LeftJoinObjectComparator extends AbstractObject  {
    /**
     * @param a  要比较的第一个JsonObject
     * @param b  要比较的第二个JsonObject
     * @param pathModule 路径模型
     * @return 不同的比较结果
     */
    @Override
    public DiffContext diff(JsonObject a, JsonObject b, PathModule pathModule){
        //用a的keySet作为遍历集合。
        return diffValueByKey(a, b, a.keySet(), pathModule);
    }

}