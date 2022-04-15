package com.ke.diff.algorithm.nulls;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @Author JingWei
 * @create 2022/3/1
 */
public class DefaultNullComparatorTest {
    /**
     * 测试JsonNull和JsonNull比较结果。
     */
    @Test
    public void diff() {
        JsonObject a0 = (JsonObject)new JsonParser().parse("{\"a\":null}");
        JsonObject b0 = (JsonObject)new JsonParser().parse("{\"a\":null}");
        JsonNull a =(JsonNull) a0.get("a");
        JsonNull b =(JsonNull) b0.get("a");
        DiffContext diff = new DefaultNullComparator().diff(a, b, new PathModule());
        assertTrue(diff.isSame());
    }
}