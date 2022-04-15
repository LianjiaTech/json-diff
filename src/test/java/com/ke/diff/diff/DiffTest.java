package com.ke.diff.diff;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ke.diff.AlgorithmEnum;
import com.ke.diff.Diff;
import com.ke.diff.algorithm.array.SimilarArrayComparator;
import com.ke.diff.algorithm.nulls.DefaultNullComparator;
import com.ke.diff.algorithm.object.SimpleObjectComparator;
import com.ke.diff.algorithm.other.DefaultOtherComparator;
import com.ke.diff.algorithm.primitive.DefaultPrimitiveComparator;
import com.ke.diff.model.Result;
import com.ke.diff.model.ResultConvertUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @Author JingWei
 * @create 2022/2/28
 */
public class DiffTest {
    /**
     * 测试withA withB diff方法
     */
    @Test
    public void diff() {
        String str1 = "{\"a\":null}";
        String str2 = "{\"a\":\"\"}";
        List<Result> diff = new Diff().diff(str1,str2);
        assertEquals(ResultConvertUtil.TYPE_MODIFY,diff.get(0).getDiffType());
    }

    /**
     * 测试withNoisePahList方法
     */
    @Test
    public void diff1() {
        String str1 = "{\"a\":null}";
        String str2 = "{\"a\":\"\"}";
        List<String> noiseList = Lists.newArrayList("a");
        List<Result> diff = new Diff().withNoisePahList(noiseList).diff(str1, str2);
        assertEquals(0,diff.size());
    }

    /**
     * 测试withPathModule withAlgorithmEnum方法
     */
    @Test
    public void diff3() {
        String str1 = "{\"a\":null}";
        String str2 = "{\"a\":\"\"}";
        List<String> noiseList = Lists.newArrayList("a");
        List<Result> diff = new Diff().withNoisePahList(noiseList).withAlgorithmEnum(null).diff(str1, str2);
        assertEquals(0,diff.size());
    }

    /**
     * 测试withSpecialPath withAlgorithmEnum withA withB方法
     */
    @Test
    public void diff4() {
        String str1 = "{\"a\":null}";
        String str2 = "{\"a\":\"\"}";
        JsonElement a = new JsonParser().parse(str1);
        JsonElement b = new JsonParser().parse(str2);
        List<String> specialPath = new ArrayList<>();
        List<Result> diff = new Diff().withNoisePahList(null).withSpecialPath(specialPath).withAlgorithmEnum(AlgorithmEnum.SIMLAR_ARRAY_AND_LEFTJOIN_OBJECT).diffElement(a, b);
        assertEquals(ResultConvertUtil.TYPE_MODIFY, diff.get(0).getDiffType());
    }

    /**
     * 测试withObjectAlgorithm withArrayAlgorithm withPrimitiveAlgorithm withNullAlgorithm withOtherAlgorithm方法
     */
    @Test
    public void diff5() {
        String str1 = "{\"a\":null}";
        String str2 = "{\"a\":null}";
        JsonElement a = new JsonParser().parse(str1);
        JsonElement b = new JsonParser().parse(str2);
        List<String> specialPath = new ArrayList<>();
        List<Result> diff = new Diff().withNoisePahList(null).withSpecialPath(specialPath)
                .withObjectComparator(new SimpleObjectComparator()).withArrayComparator(new SimilarArrayComparator())
                .withPrimitiveAlgorithm(new DefaultPrimitiveComparator()).withNullComparator(new DefaultNullComparator())
                .withOtheComparator(new DefaultOtherComparator()).diffElement(a, b);
        assertEquals(0,diff.size());
    }

    /**
     * 测试withObjectAlgorithm withArrayAlgorithm withPrimitiveAlgorithm withNullAlgorithm withOtherAlgorithm方法
     */
    @Test
    public void diff6() {
        String str1 = "{\"a\":null}";
        String str2 = "{\"a\":[1,2,3]}";
        JsonElement a = new JsonParser().parse(str1);
        JsonElement b = new JsonParser().parse(str2);
        List<String> specialPath = new ArrayList<>();
        List<Result> diff = new Diff().withNoisePahList(null).withSpecialPath(specialPath)
                .withObjectComparator(new SimpleObjectComparator()).withArrayComparator(new SimilarArrayComparator())
                .withPrimitiveAlgorithm(new DefaultPrimitiveComparator()).withNullComparator(new DefaultNullComparator())
                .withOtheComparator(new DefaultOtherComparator()).diffElement(a, b);
        assertEquals(ResultConvertUtil.TYPE_MODIFY, diff.get(0).getDiffType());
    }

    /**
     * 测试constrcutDefaultComparator方法
     */
    @Test
    public void diff7() {
        String str1 = "{\"a\":null}";
        String str2 = "{\"a\":[1,2,3]}";
        JsonElement a = new JsonParser().parse(str1);
        JsonElement b = new JsonParser().parse(str2);
        List<String> specialPath = new ArrayList<>();
        List<Result> diff1 = new Diff().withNoisePahList(null).withSpecialPath(specialPath)
                .withObjectComparator(new SimpleObjectComparator()).diffElement(a, b);
        List<Result> diff2 = new Diff().withNoisePahList(null).withSpecialPath(specialPath)
                .withArrayComparator(new SimilarArrayComparator()).diffElement(a, b);
        assertEquals(ResultConvertUtil.TYPE_MODIFY, diff1.get(0).getDiffType());
        assertEquals(ResultConvertUtil.TYPE_MODIFY, diff2.get(0).getDiffType());
    }
}