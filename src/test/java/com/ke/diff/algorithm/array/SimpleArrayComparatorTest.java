package com.ke.diff.algorithm.array;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.ke.diff.AlgorithmEnum;
import com.ke.diff.Diff;
import com.ke.diff.algorithm.AlgorithmModule;
import com.ke.diff.algorithm.nulls.DefaultNullComparator;
import com.ke.diff.algorithm.object.LeftJoinObjectComparator;
import com.ke.diff.algorithm.object.SimpleObjectComparator;
import com.ke.diff.algorithm.other.DefaultOtherComparator;
import com.ke.diff.algorithm.primitive.DefaultPrimitiveComparator;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;
import com.ke.diff.model.Result;
import com.ke.diff.model.SingleNodeDifference;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @Author JingWei
 * @create 2022/2/22
 */
public class SimpleArrayComparatorTest {
    /**
     * 数组中的元素按照索引号顺序进行比较
     * 测试A组
     */
    @Test
    public void testDiff1() {
        Pair<String, String> case1 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        List<String> noiseList = Lists.newArrayList("msg", "cost" );
        //数组a大于数组b
        List<Result> diff = new Diff().withNoisePahList(noiseList).withAlgorithmEnum(AlgorithmEnum.SIMPLE_ARRAY_AND_LEFTJOIN_OBJECT).diff(case1.getLeft(), case1.getRight());
        StringBuilder leftResult = new StringBuilder();
        for (Result printModel : diff) {
            leftResult.append(printModel.getLeft()).append(" ");
        }
        StringBuilder rightResult = new StringBuilder();
        for (Result result : diff) {
            rightResult.append(result.getRight()).append(" ");
        }
        //结果验证
        assertEquals(7, diff.size());
        assertEquals("7 5 6 6 2 3 null ", leftResult.toString());
        assertEquals("1 2 3 4 5 6 {省略内部字段} ", rightResult.toString());
    }

    /**
     * 测试B组
     * 对照组A，左右结果互换
     */
    @Test
    public void testDiff2() {
        Pair<String, String> case1 = Pair.of(
                "[{\"a\":1,\"b\":2,\"c\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
                , "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
        );
        List<String> noiseList = Lists.newArrayList("msg", "cost");
        List<Result> diff =  new Diff().withNoisePahList(noiseList).withAlgorithmEnum(AlgorithmEnum.SIMPLE_ARRAY_AND_LEFTJOIN_OBJECT).diff(case1.getLeft(), case1.getRight());
        StringBuilder leftResult = new StringBuilder();
        for (Result result : diff) {
            leftResult.append(result.getLeft()).append(" ");
        }
        StringBuilder rightResult = new StringBuilder();
        for (Result result : diff) {
            rightResult.append(result.getRight()).append(" ");
        }
        //结果验证: 长度 左边结果 右边结果
        assertEquals(7, diff.size());
        assertEquals("1 2 3 4 5 6 {省略内部字段} ", leftResult.toString());
        assertEquals("7 5 6 6 2 3 null ", rightResult.toString());
    }

    /**
     * 测试C组
     * 对照组A，比A组增加噪音字段a，过滤掉带a的2条结果
     */
    @Test
    public void testDiff3() {
        Pair<String, String> case1 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        List<String> noiseList = Lists.newArrayList("a");
        LinkedList<String> specailPath = Lists.newLinkedList(Lists.newArrayList("a"));
        List<Result> diff = new Diff().withNoisePahList(noiseList).withSpecialPath(specailPath).withAlgorithmEnum(AlgorithmEnum.SIMPLE_ARRAY_AND_LEFTJOIN_OBJECT).diff(case1.getLeft(), case1.getRight());
        StringBuilder leftResult = new StringBuilder();
        for (Result result : diff) {
            leftResult.append(result.getLeft()).append(" ");
        }
        StringBuilder rightResult = new StringBuilder();
        for (Result result : diff) {
            rightResult.append(result.getRight()).append(" ");
        }
        //结果验证: 长度 左边结果 右边结果
        assertEquals(5, diff.size());
        assertEquals("5 6 2 3 null ", leftResult.toString());
        assertEquals("2 3 5 6 {省略内部字段} ", rightResult.toString());
    }


    /**
     * 测试GenerateDiffResult方法
     * 比较两个数组中索引号为index的2个元素
     */
    @Test
    public void testGenerateDiffResult1() throws Exception {
        //反射调用private方法 数组算法SimpleArrayComparator
        AlgorithmModule algorithmModule = new AlgorithmModule(new SimpleObjectComparator(), new SimpleArrayComparator(),
                new DefaultPrimitiveComparator(), new DefaultNullComparator(), new DefaultOtherComparator());
        Class<SimpleArrayComparator> clazz = SimpleArrayComparator.class;
        SimpleArrayComparator simpleArrayComparator = (SimpleArrayComparator) algorithmModule.getArrayAlgorithm();
        Method method = clazz.getDeclaredMethod("generateDiffResult", JsonArray.class, JsonArray.class, int.class, PathModule.class);
        method.setAccessible(true);
        //拿两个数组中索引号为0的两对元素比较
        Pair<String, String> case1 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3, \"d\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        JsonArray array11 = (JsonArray) new JsonParser().parse(case1.getLeft());
        JsonArray array12 = (JsonArray) new JsonParser().parse(case1.getRight());
        //比较数组中第一个元素
        int index = 0;
        DiffContext diffContext = (DiffContext) method.invoke(simpleArrayComparator, array11, array12, index, new PathModule());
        List<SingleNodeDifference> diff = diffContext.getDiffResultModels();
        StringBuilder leftResult = new StringBuilder();
        for (SingleNodeDifference singleNodeDifference : diff) {
            leftResult.append(singleNodeDifference.getLeft()).append(" ");
        }
        StringBuilder rightResult = new StringBuilder();
        for (SingleNodeDifference singleNodeDifference : diff) {
            rightResult.append(singleNodeDifference.getRight()).append(" ");
        }
        //结果验证: 长度 左边结果 右边结果
        assertEquals(4, diff.size());
        assertEquals("7 5 6 null ", leftResult.toString());
        assertEquals("1 2 3 3 ", rightResult.toString());
    }

    /**
     * 测试GenerateDiffResult方法 增加噪音字段
     */
    @Test
    public void testGenerateDiffResult2() throws Exception {
        //反射调用private方法   数组算法SimpleArrayComparator
        AlgorithmModule algorithmModule = new AlgorithmModule(new SimpleObjectComparator(), new SimpleArrayComparator(),
                new DefaultPrimitiveComparator(), new DefaultNullComparator(), new DefaultOtherComparator());
        Class<SimpleArrayComparator> clazz = SimpleArrayComparator.class;
        SimpleArrayComparator simpleArrayComparator = (SimpleArrayComparator) algorithmModule.getArrayAlgorithm();
        Method method = clazz.getDeclaredMethod("generateDiffResult", JsonArray.class, JsonArray.class, int.class, PathModule.class);
        method.setAccessible(true);
        //拿两个数组中索引号为1的两对元素比较，加入噪音字段a
        Pair<String, String> case2 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3},{\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        List<String> noiseList1 = Lists.newArrayList("msg", "a");
        JsonArray array21 = (JsonArray) new JsonParser().parse(case2.getLeft());
        JsonArray array22 = (JsonArray) new JsonParser().parse(case2.getRight());
        //比较数组中第二个元素
        int index = 1;
        DiffContext diffContext = (DiffContext) method.invoke(simpleArrayComparator, array21, array22, index, new PathModule(noiseList1));
        List<SingleNodeDifference> diff = diffContext.getDiffResultModels();
        StringBuilder leftResult = new StringBuilder();
        for (SingleNodeDifference singleNodeDifference : diff) {
            leftResult.append(singleNodeDifference.getLeft()).append(" ");
        }
        StringBuilder rightResult = new StringBuilder();
        for (SingleNodeDifference singleNodeDifference : diff) {
            rightResult.append(singleNodeDifference.getRight()).append(" ");
        }
        //结果验证: 长度 左边结果 右边结果
        assertEquals(2, diff.size());
        assertEquals("2 3 ", leftResult.toString());
        assertEquals("5 6 ", rightResult.toString());
    }

    /**
     * 测试GenerateDiffResult方法
     */
    @Test
    public void testGenerateDiffResult3() throws Exception {
        //反射调用private方法   数组算法SimpleArrayComparator
        AlgorithmModule algorithmModule = new AlgorithmModule(new LeftJoinObjectComparator(), new SimpleArrayComparator(),
                new DefaultPrimitiveComparator(), new DefaultNullComparator(), new DefaultOtherComparator());
        Class<SimpleArrayComparator> clazz = SimpleArrayComparator.class;
        SimpleArrayComparator simpleArrayComparator = (SimpleArrayComparator) algorithmModule.getArrayAlgorithm();
        Method method = clazz.getDeclaredMethod("generateDiffResult", JsonArray.class, JsonArray.class, int.class, PathModule.class);
        method.setAccessible(true);
        Pair<String, String> case1 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3,\"d\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        JsonArray array11 = (JsonArray) new JsonParser().parse(case1.getLeft());
        JsonArray array12 = (JsonArray) new JsonParser().parse(case1.getRight());
        //比较数组中第一个元素
        int index = 0;
        DiffContext diffContext1 = (DiffContext) method.invoke(simpleArrayComparator, array11, array12, index, new PathModule());
        List<SingleNodeDifference> diff = diffContext1.getDiffResultModels();
        StringBuilder leftResult = new StringBuilder();
        for (SingleNodeDifference singleNodeDifference : diff) {
            leftResult.append(singleNodeDifference.getLeft()).append(" ");
        }
        StringBuilder rightResult = new StringBuilder();
        for (SingleNodeDifference singleNodeDifference : diff) {
            rightResult.append(singleNodeDifference.getRight()).append(" ");
        }
        //结果验证: 长度 左边结果 右边结果
        assertEquals(3, diff.size());
        assertEquals("7 5 6 ", leftResult.toString());
        assertEquals("1 2 3 ", rightResult.toString());
    }

    /**
     * 测试GenerateDiffResult方法 增加噪音字段
     */
    @Test
    public void testGenerateDiffResult4() throws Exception {
        //反射调用private方法   数组算法SimpleArrayComparator
        AlgorithmModule algorithmModule = new AlgorithmModule(new LeftJoinObjectComparator(), new SimpleArrayComparator(),
                new DefaultPrimitiveComparator(), new DefaultNullComparator(), new DefaultOtherComparator());
        Class<SimpleArrayComparator> clazz = SimpleArrayComparator.class;
        SimpleArrayComparator simpleArrayComparator = (SimpleArrayComparator) algorithmModule.getArrayAlgorithm();
        Method method = clazz.getDeclaredMethod("generateDiffResult", JsonArray.class, JsonArray.class, int.class, PathModule.class);
        method.setAccessible(true);
        Pair<String, String> case2 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3},{\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        List<String> noiseList1 = Lists.newArrayList("msg", "a");
        JsonArray array1 = (JsonArray) new JsonParser().parse(case2.getLeft());
        JsonArray array2 = (JsonArray) new JsonParser().parse(case2.getRight());
        //比较数组中第3个元素
        int index = 2;
        DiffContext diffContext = (DiffContext) method.invoke(simpleArrayComparator, array1, array2, index, new PathModule(noiseList1));
        List<SingleNodeDifference> diff = diffContext.getDiffResultModels();
        StringBuilder leftResult = new StringBuilder();
        for (SingleNodeDifference singleNodeDifference : diff) {
            leftResult.append(singleNodeDifference.getLeft()).append(" ");
        }
        StringBuilder rightResult = new StringBuilder();
        for (SingleNodeDifference singleNodeDifference : diff) {
            rightResult.append(singleNodeDifference.getRight()).append(" ");
        }
        //结果验证: 长度 左边结果 右边结果
        assertEquals(1, diff.size());
        assertEquals("null ", leftResult.toString());
        assertEquals("{省略内部字段} ", rightResult.toString());
    }

    /**
     * 测试GenerateDiffResult方法 测试简单数组
     */
    @Test
    public void testGenerateDiffResult5() throws Exception {
        //反射调用private方法   数组算法SimpleArrayComparator
        AlgorithmModule algorithmModule = new AlgorithmModule(new LeftJoinObjectComparator(), new SimpleArrayComparator(),
                new DefaultPrimitiveComparator(), new DefaultNullComparator(), new DefaultOtherComparator());
        Class<SimpleArrayComparator> clazz = SimpleArrayComparator.class;
        SimpleArrayComparator simpleArrayComparator = (SimpleArrayComparator) algorithmModule.getArrayAlgorithm();
        Method method = clazz.getDeclaredMethod("generateDiffResult", JsonArray.class, JsonArray.class, int.class, PathModule.class);
        method.setAccessible(true);
        Pair<String, String> case1 = Pair.of(
                "[1,3,2]"
                , "[2,3,1]"
        );
        JsonArray array11 = (JsonArray) new JsonParser().parse(case1.getLeft());
        JsonArray array12 = (JsonArray) new JsonParser().parse(case1.getRight());
        //比较数组中第一个元素
        int index = 0;
        DiffContext diffContext = (DiffContext) method.invoke(simpleArrayComparator, array11, array12, index, new PathModule());
        List<SingleNodeDifference> diff = diffContext.getDiffResultModels();
        StringBuilder leftResult = new StringBuilder();
        for (SingleNodeDifference singleNodeDifference : diff) {
            leftResult.append(singleNodeDifference.getLeft()).append(" ");
        }
        StringBuilder rightResult = new StringBuilder();
        for (SingleNodeDifference singleNodeDifference : diff) {
            rightResult.append(singleNodeDifference.getRight()).append(" ");
        }
        //结果验证: 长度 左边结果 右边结果
        assertEquals(1, diff.size());
        assertEquals("1 ", leftResult.toString());
        assertEquals("2 ", rightResult.toString());
    }

    /**
     * 测试索引号超出数组长度
     */
    @Test(expected = InvocationTargetException.class)
    public void testGenerateDiffResult6() throws Exception {
        //反射调用private方法
        AlgorithmModule algorithmModule = new AlgorithmModule(new SimpleObjectComparator(), new SimpleArrayComparator(),
                new DefaultPrimitiveComparator(), new DefaultNullComparator(), new DefaultOtherComparator());
        Class<SimpleArrayComparator> clazz = SimpleArrayComparator.class;
        SimpleArrayComparator simpleArrayComparator = (SimpleArrayComparator) algorithmModule.getArrayAlgorithm();
        Method method = clazz.getDeclaredMethod("generateDiffResult", JsonArray.class, JsonArray.class, int.class, PathModule.class);
        method.setAccessible(true);
        Pair<String, String> case4 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3},{\"a\":6,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        JsonArray array41 = (JsonArray) new JsonParser().parse(case4.getLeft());
        JsonArray array42 = (JsonArray) new JsonParser().parse(case4.getRight());
        //索引号为数组第4个元素，数组长度为3
        int index = 3;
        //索引号超出数组长度 抛异常
        DiffContext diffContext4 = (DiffContext) method.invoke(simpleArrayComparator, array41, array42, index, new PathModule());
    }
}
