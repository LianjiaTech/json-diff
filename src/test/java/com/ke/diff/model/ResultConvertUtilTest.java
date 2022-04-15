package com.ke.diff.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * ResultConvertUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>2月 28, 2022</pre>
 */
public class ResultConvertUtilTest {
    private static final String TYPE_MODIFY = "MODIFY";
    private static final String TYPE_ADD = "ADD";
    private static final String TYPE_DELETE = "DELETE";
    /**
     * 测试能否给diff比较结果打上正确的修改、新增、删除标签
     * Method: constructResult(DiffResult diffResult)
     */
    @Test
    public void testConstructResult()  {
        //a和b都有，比较结果为修改
        SingleNodeDifference singleNodeDifference1 = new SingleNodeDifference("leftPath","rightPath", " a", "b");
        List<SingleNodeDifference> diffResultModels1 = new ArrayList<>();
        diffResultModels1.add(singleNodeDifference1);
        DiffContext diffContext1 = new DiffContext();
        diffContext1.setSame(false);
        diffContext1.setDiffResultModels(diffResultModels1);
        ArrayList<Result> diffPrintModels1 = ResultConvertUtil.constructResult(diffContext1);
        assertEquals(TYPE_MODIFY, diffPrintModels1.get(0).getDiffType());
        //a和b都有，比较结果为修改
        SingleNodeDifference singleNodeDifference2 = new SingleNodeDifference("leftPath","rightPath", "a", "");
        List<SingleNodeDifference> diffResultModels2 = new ArrayList<>();
        diffResultModels2.add(singleNodeDifference2);
        DiffContext diffContext2 = new DiffContext();
        diffContext2.setSame(false);
        diffContext2.setDiffResultModels(diffResultModels2);
        ArrayList<Result> diffPrintModels2 = ResultConvertUtil.constructResult(diffContext2);
        assertEquals(TYPE_MODIFY, diffPrintModels2.get(0).getDiffType());
        //b没有，比较结果为删除
        SingleNodeDifference singleNodeDifference3 = new SingleNodeDifference("leftPath","rightPath", "a", null);
        List<SingleNodeDifference> diffResultModels3 = new ArrayList<>();
        diffResultModels3.add(singleNodeDifference3);
        DiffContext diffContext3 = new DiffContext();
        diffContext3.setSame(false);
        diffContext3.setDiffResultModels(diffResultModels3);
        ArrayList<Result> diffPrintModels3 = ResultConvertUtil.constructResult(diffContext3);
        assertEquals(TYPE_DELETE, diffPrintModels3.get(0).getDiffType());
        //a和b都有，比较结果为修改
        SingleNodeDifference singleNodeDifference4 = new SingleNodeDifference("leftPath","rightPath", "", "b");
        List<SingleNodeDifference> diffResultModels4 = new ArrayList<>();
        diffResultModels4.add(singleNodeDifference4);
        DiffContext diffContext4 = new DiffContext();
        diffContext4.setSame(false);
        diffContext4.setDiffResultModels(diffResultModels4);
        ArrayList<Result> diffPrintModels4 = ResultConvertUtil.constructResult(diffContext4);
        assertEquals(TYPE_MODIFY, diffPrintModels4.get(0).getDiffType());
        //a没有，比较结果为新增
        SingleNodeDifference singleNodeDifference5 = new SingleNodeDifference("leftPath","rightPath", null, "b");
        List<SingleNodeDifference> diffResultModels5 = new ArrayList<>();
        diffResultModels5.add(singleNodeDifference5);
        DiffContext diffContext5 = new DiffContext();
        diffContext5.setSame(false);
        diffContext5.setDiffResultModels(diffResultModels5);
        ArrayList<Result> diffPrintModels5 = ResultConvertUtil.constructResult(diffContext5);
        assertEquals(TYPE_ADD, diffPrintModels5.get(0).getDiffType());
    }
}