package com.ke.diff.algorithm.object;

import com.google.common.base.Joiner;
import com.google.gson.JsonObject;
import com.ke.diff.algorithm.AbstractObjectAndArray;
import com.ke.diff.model.Constants;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 对象比较的一些公有方法抽象类
 * @Author JingWei
 * @create 2022/2/24
 */
public abstract class AbstractObject extends AbstractObjectAndArray implements ObjectComparator {

    /**
     * 对两个对象进行比较：遍历keySet中的所有key， 在a和b对象中找对应的value值进行比较
     * @param a 要比较的第一个对象
     * @param b 要比较的第二个对象
     * @param keySet key的集合
     * @param pathModule 路径模型
     * @return 不同的比较结果
     */
    protected DiffContext diffValueByKey(JsonObject a, JsonObject b, Set<String> keySet, PathModule pathModule) {
        DiffContext objectDiffContext = new DiffContext();
        LinkedList<String> specialPathResult = new LinkedList<>();
        for (String key : keySet) {
            //更新a和b当前路径
            pathModule.addAllpath(key);
            //如果对象当前路径在噪音路径集合中，直接跳过比较
            if (!needDiff(pathModule.getNoisePahList(), pathModule.getLeftPath())) {
                pathModule.removeAllLastPath();
                continue;
            }
            //生成比较结果
            DiffContext diffContext = diffElement(a.get(key), b.get(key), pathModule);
            parentContextAddChildContext(objectDiffContext, diffContext);
            //特殊路径处理
            specialPathHandle(diffContext.isSame(), specialPathResult, pathModule);
            pathModule.removeAllLastPath();

        }
        objectDiffContext.setSpecialPathResult(specialPathResult);
        return objectDiffContext;
    }

    /**
     * 如果比较的路径为特殊路径并且比较相等，特殊路径会被标识，添加到返回结果中。
     * @param isSame 比较结果是否相等
     * @param specialPathResult 返回的特殊路径结果
     * @param pathModule 路径模型
     */
    private void specialPathHandle(boolean isSame,LinkedList<String> specialPathResult, PathModule pathModule) {
        //如果比较结果不等，直接返回。
        if (!isSame){
            return;
        }
        //如果存在特殊路径，将特殊路径加入到集合中
        String specialPath = getSpecialPath(pathModule);
        if( existPath(specialPath)){
            specialPathResult.add(specialPath);
        }
    }

    /**
     * 校验路径是否存在，即路径是否为空
     * @param specialPath 特殊路径
     * @return 特殊路径是否为空
     */
    private boolean existPath(String specialPath) {
        return specialPath != null;
    }

    /**
     * 判断当前路径是否在特殊路径集合中，如果在，则返回特殊路径。
     * @param pathModule 路径模型
     * @return 返回的特殊路径字符串
     */
    protected String getSpecialPath(PathModule pathModule) {
        if(pathModule == null || pathModule.getSpecialPath() == null || pathModule.getSpecialPath().isEmpty()){
            return null;
        }
        String currentPath = listJoin(pathModule.getLeftPath());
        if(pathModule.getSpecialPath().contains(currentPath)){
            return currentPath;
        }
        return null;
    }

    /**
     * 判断当前字段是否需要diff，如果在噪音字段集合中，则不需要diff，返回false。
     * @param noisePahList 噪音路径列表
     * @param pathList 当前路径
     * @return 当前路径是否在噪音路径中
     */
    protected boolean needDiff(List<String> noisePahList, LinkedList<String> pathList) {
        if(noisePahList == null || pathList == null ||  noisePahList.isEmpty() || pathList.isEmpty()){
            return true;
        }
        String path = listJoin(pathList);
        if(noisePahList.contains(path)){
            return false;
        }
        return true;
    }

    /**
     * 将path路径列表改为字符串
     * @param path 当前路径
     * @return 当前路径字符串
     */
    protected String listJoin(LinkedList<String> path) {
        if(path == null){
            throw new RuntimeException("当前路径不能为空");
        }
        List<String> collect = path.stream().filter(e -> e.charAt(0) != '[').collect(Collectors.toList());
        return Joiner.on(Constants.MERGE_PATH).join(collect);
    }



}