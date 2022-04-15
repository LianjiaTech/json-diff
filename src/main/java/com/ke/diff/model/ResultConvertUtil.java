package com.ke.diff.model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 结果转换工具:将diff上下文转换成diff最终结果
 * @Author JingWei
 * @create 2022/2/25
 */
public class ResultConvertUtil {
    public static final String OBJECT_NULL = null;
    //diff结果类型分为修改、新增、删除
    public static final String TYPE_MODIFY = "MODIFY";
    public static final String TYPE_ADD = "ADD";
    public static final String TYPE_DELETE = "DELETE";

    /**
     * 将diff上下文转换成diff最终结果
     * @param diffContext diff比较的直接结果
     * @return diff展示的结果
     */
    public static ArrayList<Result> constructResult(DiffContext diffContext) {
        ArrayList<Result> list = new ArrayList<>();
        for (SingleNodeDifference resultModel : diffContext.getDiffResultModels()) {
            Result printModel = convert(resultModel);
            boolean leftAndRightBothNull = (Objects.equals(OBJECT_NULL,resultModel.getLeft()))
                    && Objects.equals(OBJECT_NULL,resultModel.getRight()) ;
            //判断两个对象是否同时为空
            if (leftAndRightBothNull) {
                printModel.setDiffType(TYPE_MODIFY);
            }
            //这种情况为对象A中keySet没这个key，或者A数组长度小于B 数组中没这个元素。
            else if (Objects.equals(OBJECT_NULL,resultModel.getLeft()) ) {
                printModel.setDiffType(TYPE_ADD);
                printModel.setLeftPath(null);
            }
            //这种情况为对象B中keySet没这个key，或者B数组长度小于A 数组中没这个元素。
            else if (Objects.equals(OBJECT_NULL,resultModel.getRight()) ) {
                printModel.setDiffType(TYPE_DELETE);
                printModel.setRightPath(null);
            }
            //其他情况
            else {
                printModel.setDiffType(TYPE_MODIFY);
            }
            list.add(printModel);
        }
        return list;
    }


    /**
     * 数据模型转换，增加类型字段。
     * @param resultModel 比较结果
     * @return 展示模型
     */
    private static Result convert(SingleNodeDifference resultModel) {
        Result printModel = new Result();
        printModel.setLeft(resultModel.getLeft());
        printModel.setRight(resultModel.getRight());
        printModel.setLeftPath(resultModel.getLeftPath());
        printModel.setRightPath(resultModel.getRightPath());
        return printModel;
    }
}
