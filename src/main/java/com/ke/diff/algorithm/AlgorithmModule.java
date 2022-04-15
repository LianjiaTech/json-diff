package com.ke.diff.algorithm;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.ke.diff.algorithm.array.ArrayComparator;
import com.ke.diff.algorithm.nulls.NullComparator;
import com.ke.diff.algorithm.object.ObjectComparator;
import com.ke.diff.algorithm.other.OtherComparator;
import com.ke.diff.algorithm.primitive.PrimitiveComparator;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;

/**
 * 算法模型
 * 算法模型包含5种比较器，对象、数组、基本类型、空类型、其他类型比较器。
 * 当2个JsonElement的子类同时为对象、数组、基本类型、空类型，使用前4种比较器。当2个JsonElement的子类类型不相同时，使用其它类型比较器。
 * @Author JingWei
 * @create 2022/1/13
 */
public class AlgorithmModule{
    protected ObjectComparator objectAlgorithm;
    protected ArrayComparator arrayAlgorithm;
    protected PrimitiveComparator primitiveComparator;
    protected NullComparator nullComparator;
    protected OtherComparator otherComparator;

    public AlgorithmModule(ObjectComparator objectAlgorithm, ArrayComparator arrayAlgorithm,
                           PrimitiveComparator primitiveComparator, NullComparator nullComparator, OtherComparator otherComparator) {
        this.arrayAlgorithm = arrayAlgorithm;
        this.objectAlgorithm = objectAlgorithm;
        this.primitiveComparator = primitiveComparator;
        this.nullComparator = nullComparator;
        this.otherComparator = otherComparator;
        objectAlgorithm.constructAlgorithmModule(this);
        arrayAlgorithm.constructAlgorithmModule(this);
    }

    /**
     * 判断要比较的两个JsonElement的类型，并根据类型调用对应的算法进行比较
     * @param a 要比较的第一个元素
     * @param b	要比较的第二个元素
     * @param pathModule 路径模型
     * @return 返回比较结果
     */
    public DiffContext diffElement(JsonElement a, JsonElement b, PathModule pathModule) {
        if (a instanceof JsonObject && b instanceof JsonObject) {
            return objectAlgorithm.diff( (JsonObject) a, (JsonObject) b, pathModule);
        } else if (a instanceof JsonArray && b instanceof JsonArray) {
            return arrayAlgorithm.diffArray((JsonArray) a, (JsonArray) b, pathModule);
        } else if (a instanceof JsonPrimitive && b instanceof JsonPrimitive) {
            return  primitiveComparator.diff((JsonPrimitive) a, (JsonPrimitive) b, pathModule);
        } else if (a instanceof JsonNull && b instanceof JsonNull) {
            return nullComparator.diff((JsonNull) a, (JsonNull) b, pathModule);
        } else  {
            return otherComparator.diff(a, b, pathModule);
        }
    }

    public ArrayComparator getArrayAlgorithm() {
        return arrayAlgorithm;
    }

}
