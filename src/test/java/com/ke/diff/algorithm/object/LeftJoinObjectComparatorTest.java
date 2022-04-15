package com.ke.diff.algorithm.object;

import com.google.common.collect.Lists;
import com.ke.diff.AlgorithmEnum;
import com.ke.diff.Diff;
import com.ke.diff.model.Result;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @Author JingWei
 * @create 2022/2/22
 */
public class LeftJoinObjectComparatorTest {
    /**
     * 测试算法是否有问题
     */
    @Test
    public void testDiff(){
        Pair<String, String> case1 = Pair.of(
                "[{\"a\":1,\"b\":2,\"c\":3}]"
                ,"[{\"a\":7,\"b\":5,\"c\":6,\"d\":9}]"
        );
        Pair<String, String> case2 = Pair.of(
                "[{\"a\":1,\"b\":2,\"c\":3}]"
                ,"[{\"a\":7,\"b\":5}]"
        );
        List<String> noiseList1 = Lists.newArrayList("msg", "cost" );
        List<String> noiseList2 = Lists.newArrayList("a");
        List<Result> diff1 = new Diff().withNoisePahList(noiseList1).withAlgorithmEnum(AlgorithmEnum.SIMLAR_ARRAY_AND_LEFTJOIN_OBJECT).diff(case1.getLeft(), case1.getRight());
        List<Result> diff2 =  new Diff().withNoisePahList(noiseList1).withAlgorithmEnum(AlgorithmEnum.SIMLAR_ARRAY_AND_LEFTJOIN_OBJECT).diff(case2.getLeft(), case2.getRight());
        List<Result> diff3 = new Diff().withNoisePahList(noiseList2).withAlgorithmEnum(AlgorithmEnum.SIMLAR_ARRAY_AND_LEFTJOIN_OBJECT).diff(case1.getLeft(), case1.getRight());
        List<Result> diff4 = new Diff().withNoisePahList(noiseList2).withAlgorithmEnum(AlgorithmEnum.SIMLAR_ARRAY_AND_LEFTJOIN_OBJECT).diff(case1.getLeft(), case1.getRight());
        assertEquals(diff1.size(),3);
        assertEquals(diff2.size(),3);
        assertEquals(diff3.size(),2);
        assertEquals(diff4.size(),2);
        //特殊路径测试
        Pair<String, String> case3 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"
                , "[{\"a\":1,\"b\":2,\"c\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );
        LinkedList<String> specailPath = Lists.newLinkedList(Lists.newArrayList("a"));
        List<Result> diff5 = new Diff().withNoisePahList(noiseList1).withSpecialPath(specailPath).withAlgorithmEnum(AlgorithmEnum.SIMLAR_ARRAY_AND_LEFTJOIN_OBJECT).diff(case3.getLeft(), case3.getRight());
        assertEquals(diff5.size(), 4);
    }
}