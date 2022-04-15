package com.ke.diff.algorithm;

import com.google.gson.JsonElement;

/**
 * 基本类型和其它类型比较的公有方法抽象类
 * @Author JingWei
 * @create 2022/1/11
 */
public abstract class AbstractPrimitiveAndOther {
    /**
     * 将比较的元素转换成String类型方便结果展示
     * @param element 元素
     * @return 元素转换成的字符串
     */
    protected static String jsonElement2Str(JsonElement element){
        //该对象不存在的情况
        if(element == null){
            return null;
        } else if (element.isJsonObject()) {
            return "{省略内部字段}";
        } else if (element.isJsonArray()) {
            return "[省略内部元素]";
        } else if (element.isJsonPrimitive()) {
            return element.getAsJsonPrimitive().getAsString();
        } else if (element.isJsonNull()) {
            return "null";
        }else{
            throw new RuntimeException("异常");
        }
    }


}
