package com.ke.diff.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * diff比较上下文
 * @Author JingWei
 * @create 2022/3/2
 */
public class DiffContext {
    /**
     * 比较结果是否相同
     */
    private boolean isSame;

    /**
     * 比较结果不同时，存储所有不同的结果对
     */
    private List<SingleNodeDifference> singleNodeDifferences;

    /**
     * 比较结果中，出现了特殊路径下值相等的情况，会存储该特殊路径。
     */
    private LinkedList<String> specialPathResult;

    public DiffContext(boolean isSame) {
        this.isSame = isSame;
        this.singleNodeDifferences = new ArrayList<>();
    }

    public boolean isSame() {
        return isSame;
    }

    public void setSame(boolean same) {
        isSame = same;
    }

    public List<SingleNodeDifference> getDiffResultModels() {
        return singleNodeDifferences;
    }

    public void setDiffResultModels(List<SingleNodeDifference> singleNodeDifferences) {
        this.singleNodeDifferences = singleNodeDifferences;
    }

    public DiffContext() {
        this.isSame = true;
        this.singleNodeDifferences = new ArrayList<>();
    }

    public LinkedList<String> getSpecialPathResult() {
        return specialPathResult;
    }

    public void setSpecialPathResult(LinkedList<String> specialPathResult) {
        this.specialPathResult = specialPathResult;
    }
}
