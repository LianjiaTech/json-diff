package com.ke.diff.model;

import java.io.Serializable;

/**
 * diff比较最终结果
 * @Author JingWei
 * @create 2022/3/2
 */
public class Result implements Serializable {
    /**
     * 第一个对象路径
     */
    private String leftPath;
    /**
     * 第二个对象路径
     */
    private String rightPath;
    /**
     * 第一个对象值
     */
    private Object left;
    /**
     * 第二个对象值
     */
    private Object right;
    /**
     * 比较结果
     */
    private String diffType;

    public String getLeftPath() {
        return leftPath;
    }

    public String getRightPath() {
        return rightPath;
    }

    public void setLeftPath(String leftPath) {
        this.leftPath = leftPath;
    }

    public void setRightPath(String rightPath) {
        this.rightPath = rightPath;
    }

    public Object getLeft() {
        return left;
    }

    public void setLeft(Object left) {
        this.left = left;
    }

    public Object getRight() {
        return right;
    }

    public void setRight(Object right) {
        this.right = right;
    }

    public String getDiffType() {
        return diffType;
    }

    public void setDiffType(String diffType) {
        this.diffType = diffType;
    }
}
