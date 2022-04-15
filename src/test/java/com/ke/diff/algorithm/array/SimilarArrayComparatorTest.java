package com.ke.diff.algorithm.array;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.ke.diff.AlgorithmEnum;
import com.ke.diff.Diff;
import com.ke.diff.algorithm.AlgorithmModule;
import com.ke.diff.algorithm.nulls.DefaultNullComparator;
import com.ke.diff.algorithm.object.SimpleObjectComparator;
import com.ke.diff.algorithm.other.DefaultOtherComparator;
import com.ke.diff.algorithm.primitive.DefaultPrimitiveComparator;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;
import com.ke.diff.model.Result;
import com.ke.diff.model.SingleNodeDifference;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @Author JingWei
 * @create 2022/2/28
 */
public class SimilarArrayComparatorTest {
    /**
     * 测试构造相似度矩阵方法
     */
    @Test
    public void testConstructSimilarMatrix() {
        AlgorithmModule algorithmModule = new AlgorithmModule(new SimpleObjectComparator(), new SimilarArrayComparator(),
                new DefaultPrimitiveComparator(), new DefaultNullComparator(), new DefaultOtherComparator());
        Class<SimilarArrayComparator> clazz = SimilarArrayComparator.class;
        SimilarArrayComparator similarArrayAlgorithm = (SimilarArrayComparator) algorithmModule.getArrayAlgorithm();
        Method method;
        Pair<String, String> case1 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        JsonArray a = (JsonArray) new JsonParser().parse(case1.getLeft());
        JsonArray b = (JsonArray) new JsonParser().parse(case1.getRight());
        int[][] similarMatrix = new int[a.size()][b.size()];
        //创建一个行和列数组 用来判断当前行和列是否被使用过
        boolean[] row = new boolean[a.size()];
        boolean[] line = new boolean[b.size()];
        int rowIndex = 0;
        try {
            method = clazz.getDeclaredMethod("constructSimilarMatrix", JsonArray.class, JsonArray.class, int.class, PathModule.class, int[][].class, boolean[].class, boolean[].class);
            method.setAccessible(true);
            method.invoke(similarArrayAlgorithm, a, b, rowIndex, new PathModule(), similarMatrix, row, line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        for (int j = 0; j < b.size(); j++) {
            stringBuilder1.append(similarMatrix[rowIndex][j]).append(" ");
        }
        for (int j = 0; j < b.size(); j++) {
            stringBuilder2.append(similarMatrix[1][j]).append(" ");
        }
		/*
		    相似矩阵
		    3   1   2
		    1   3   3
		 */
        //验证矩阵结果
        assertEquals("3 1 2 ", stringBuilder1.toString());
        assertEquals("0 0 0 ", stringBuilder2.toString());
    }

    /**
     * 验证能否从矩阵拿到相似结果
     */
    @Test
    public void testObtainModifyDiffResult() {
        Class<SimilarArrayComparator> clazz = SimilarArrayComparator.class;
        Method method ;
        Pair<String, String> case1 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        JsonArray a = (JsonArray) new JsonParser().parse(case1.getLeft());
        JsonArray b = (JsonArray) new JsonParser().parse(case1.getRight());
        int[][] similarMatrix = new int[a.size()][b.size()];
		/*
		    随机创建的矩阵
		    5   3   2
		    4   1   3
		 */
        similarMatrix[0][0] = 5;
        similarMatrix[0][1] = 3;
        similarMatrix[0][2] = 2;
        similarMatrix[1][0] = 4;
        similarMatrix[1][1] = 1;
        similarMatrix[1][2] = 3;
        //创建一个行和列数组 用来判断当前行和列是否被使用过
        boolean[] row = new boolean[a.size()];
        boolean[] line = new boolean[b.size()];
        DiffContext diffContext = new DiffContext();
        try {
            method = clazz.getDeclaredMethod("obtainModifyDiffResult", JsonArray.class, JsonArray.class, PathModule.class, boolean[].class, boolean[].class, int[][].class, DiffContext.class);
            method.setAccessible(true);
            JsonArray parse1 = (JsonArray) new JsonParser().parse(case1.getLeft());
            JsonArray parse2 = (JsonArray) new JsonParser().parse(case1.getRight());
            method.invoke(AlgorithmEnum.DEFAULT.getAlgorithmModule().getArrayAlgorithm(),parse1, parse2,
                    new PathModule(), row, line, similarMatrix, diffContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        assertEquals(5, diff.size());
        assertEquals("6 2 3 5 6 ", leftResult.toString());
        assertEquals("4 5 6 8 9 ", rightResult.toString());
        //验证列是否打上被使用过标签
        assertFalse(line[0]);
        assertTrue(line[1]);
        assertTrue(line[2]);
    }

    /**
     * 测试算法是否有问题
     */
    @Test
    public void testDiff1() {
        Pair<String, String> case1 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        List<String> noiseList1 = Lists.newArrayList("msg", "cost");
		/*
		    相似矩阵
		    3   1   2
		    1   3   3
		 */

        List<Result> diff =  new Diff().withNoisePahList(noiseList1).withAlgorithmEnum(AlgorithmEnum.MOST_COMMONLY_USED).diff(case1.getLeft(), case1.getRight());
        StringBuilder leftResult = new StringBuilder();
        for (Result result : diff) {
            leftResult.append(result.getLeft()).append(" ");
        }
        StringBuilder rightResult = new StringBuilder();
        for (Result result : diff) {
            rightResult.append(result.getRight()).append(" ");
        }
        //结果验证: 长度 左边结果 右边结果
        assertEquals(3, diff.size());
        assertEquals("7 6 null ", leftResult.toString());
        assertEquals("4 1 {省略内部字段} ", rightResult.toString());
    }

    /**
     * 测试算法是否有问题
     */
    @Test
    public void testDiff2() {
        Pair<String, String> case2 = Pair.of(
                "[{\"a\":1,\"b\":2,\"c\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
                , "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
        );
        List<String> noiseList1 = Lists.newArrayList("msg", "cost");
		/*
		    相似矩阵
		   3  1
		   1  3
		   2  3
		 */

        List<Result> diff = new Diff().withNoisePahList(noiseList1).withAlgorithmEnum(AlgorithmEnum.MOST_COMMONLY_USED).diff(case2.getLeft(), case2.getRight());
        StringBuilder leftResult = new StringBuilder();
        for (Result result : diff) {
            leftResult.append(result.getLeft()).append(" ");
        }
        StringBuilder rightResult = new StringBuilder();
        for (Result result : diff) {
            rightResult.append(result.getRight()).append(" ");
        }
        //结果验证: 长度 左边结果 右边结果
        assertEquals(3, diff.size());
        assertEquals("4 1 {省略内部字段} ", leftResult.toString());
        assertEquals("7 6 null ", rightResult.toString());
    }

    /**
     * 测试算法是否有问题
     */
    @Test
    public void testDiff3() {
        Pair<String, String> case1 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        List<String> noiseList2 = Lists.newArrayList("a");
        LinkedList<String> specailPath = Lists.newLinkedList(Lists.newArrayList("a"));
		/*
		    相似矩阵
		    2   0   2
		    0   2   2
		 */
        List<Result> diff3 =  new Diff().withNoisePahList(noiseList2).withSpecialPath(specailPath).withAlgorithmEnum(AlgorithmEnum.MOST_COMMONLY_USED).diff(case1.getLeft(), case1.getRight());
        assertEquals(1, diff3.size());
    }

    /**
     * 测试算法是否有问题
     */
    @Test
    public void testDiff4() {
        Pair<String, String> case1 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        List<String> noiseList1 = Lists.newArrayList("msg", "cost");
        LinkedList<String> specailPath = Lists.newLinkedList(Lists.newArrayList("a"));
        //特殊路径测试
        List<Result> diff4 = new Diff().withNoisePahList(noiseList1).withSpecialPath(specailPath).withAlgorithmEnum(AlgorithmEnum.MOST_COMMONLY_USED).diff(case1.getLeft(), case1.getRight());
        assertEquals(4, diff4.size());
    }

    /**
     * 测试算法是否有问题
     */
    @Test
    public void testDiff5() {
        Pair<String, String> case3 = Pair.of(
                "[]"
                , "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
        );
        List<String> noiseList2 = Lists.newArrayList("a");
        LinkedList<String> specailPath = Lists.newLinkedList(Lists.newArrayList("a"));
        List<Result> diff5 = new Diff().withNoisePahList(noiseList2).withSpecialPath(specailPath).withAlgorithmEnum(AlgorithmEnum.MOST_COMMONLY_USED).diff(case3.getLeft(), case3.getRight());
        assertEquals(2, diff5.size());
    }

    /**
     * 测试算法是否有问题
     */
    @Test
    public void testDiff6() {
        Pair<String, String> case4 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[]"
        );
        List<String> noiseList2 = Lists.newArrayList("a");
        LinkedList<String> specailPath = Lists.newLinkedList(Lists.newArrayList("a"));
        List<Result> diff6 = new Diff().withNoisePahList(noiseList2).withSpecialPath(specailPath).withAlgorithmEnum(AlgorithmEnum.MOST_COMMONLY_USED).diff(case4.getLeft(), case4.getRight());
        assertEquals(2, diff6.size());
    }
}

