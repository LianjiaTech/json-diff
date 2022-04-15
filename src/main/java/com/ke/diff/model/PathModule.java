package com.ke.diff.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 路径模型
 * A和B比较时，实时更新当前正在进行比较的元素路径。
 *
 * @Author JingWei
 * @create 2022/2/15
 */
public class PathModule {
    /**
     * 对象A当前遍历到的路径
     */
    private LinkedList<String> leftPath;
    /**
     * 对象B当前遍历到的路径
     */
    private LinkedList<String> rightPath;
    /**
     * 特殊路径集合。当前路径符合特殊路径且特殊路径下比较结果相同，会在返回结果中做额外标识标识。
     */
    private List<String> specialPath;
    /**
     * 噪音字段集合。如果当前路径符合噪音字段路径，则不会比较。
     */
    private List<String> noisePahList;

    public PathModule() {
        this.leftPath = new LinkedList<>();
        this.rightPath = new LinkedList<>();
    }

    public PathModule(List<String> noisePahList) {
        this.leftPath = new LinkedList<>();
        this.rightPath = new LinkedList<>();
        this.noisePahList = noisePahList;
    }

    public PathModule(List<String> noisePahList, List<String> specialPath) {
        this.leftPath = new LinkedList<>();
        this.rightPath = new LinkedList<>();
        this.noisePahList = noisePahList;
        this.specialPath = specialPath;
    }

    public List<String> getNoisePahList() {
        return noisePahList;
    }

    public void setNoisePahList(List<String> noisePahList) {
        this.noisePahList = noisePahList;
    }

    public List<String> getSpecialPath() {
        return specialPath;
    }

    public void setSpecialPath(LinkedList<String> specialPath) {
        this.specialPath = specialPath;
    }

    public LinkedList<String> getLeftPath() {
        return leftPath;
    }

    public void setLeftPath(LinkedList<String> leftPath) {
        this.leftPath = leftPath;
    }

    public LinkedList<String> getRightPath() {
        return rightPath;
    }

    public void setRightPath(LinkedList<String> rightPath) {
        this.rightPath = rightPath;
    }

    /**
     * 同时在A和B路径列表最后加上一个Path路径
     */
    public void addAllpath(String lastPath) {
        leftPath.add(lastPath);
        rightPath.add(lastPath);
    }

    public void addLeftPath(String lastPath) {
        leftPath.add(lastPath);
    }

    public void addRightPath(String lastPath) {
        rightPath.add(lastPath);
    }


    /**
     * 同时移除A和B路径列表中最后的一个路径
     */
    public void removeAllLastPath() {
        leftPath.removeLast();
        rightPath.removeLast();
    }

    /**
     * 移除A路径列表中最后的一个路径
     */
    public void removeLastLeftPath() {
        leftPath.removeLast();
    }

    /**
     * 移除B路径列表中最后的一个路径
     */
    public void removeLastRightPath() {
        rightPath.removeLast();
    }


}

