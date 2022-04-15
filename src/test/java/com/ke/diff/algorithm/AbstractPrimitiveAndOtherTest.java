package com.ke.diff.algorithm;


import com.ke.diff.Diff;
import com.ke.diff.model.Result;
import com.ke.diff.model.ResultConvertUtil;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * AbstractElementAlgorithm Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>2月 28, 2022</pre>
 */
public class AbstractPrimitiveAndOtherTest {
    /**
     * Method: jsonElement2Str(JsonElement element)
     */
    @Test
    public void testJsonElement2Str() {
        String str1 = "{\"a\":null}";
        String str2 = "{\"a\":\"\"}";
        String str3 = "{}";
        //测试null和""的区别  null被认为是JsonNull类型  ""被认为是JsonPrimitive类型
        List<Result> diff1 = new Diff().diff(str1, str2);
        assertEquals("null",diff1.get(0).getLeft());
        assertEquals("", diff1.get(0).getRight());
        assertEquals(ResultConvertUtil.TYPE_MODIFY, diff1.get(0).getDiffType());
        //测试一个对象缺少keySet中key的情况。
        List<Result> diff2 = new Diff().diff(str1, str3);
        assertEquals("null",diff2.get(0).getLeft());
        assertNull(diff2.get(0).getRight());
        assertEquals(ResultConvertUtil.TYPE_DELETE, diff2.get(0).getDiffType());
    }


}