package com.ke.diff.algorithm.other;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @Author JingWei
 * @create 2022/2/28
 */
public class DefaultOtherComparatorTest {

    /**
     * 测试"a"和JsonNull比较结果。
     */
    @Test
    public void diff1() {
        JsonPrimitive a = (JsonPrimitive) new JsonParser().parse("a");
        JsonObject b0 = (JsonObject)new JsonParser().parse("{\"a\":null}");
        JsonNull b =(JsonNull) b0.get("a");
        DiffContext diff = new DefaultOtherComparator().diff(a, b, new PathModule());
        assertEquals("a",diff.getDiffResultModels().get(0).getLeft());
        assertEquals("null",diff.getDiffResultModels().get(0).getRight());
    }
}