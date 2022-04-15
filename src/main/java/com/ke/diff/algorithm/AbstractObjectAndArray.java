package com.ke.diff.algorithm;

import com.google.gson.JsonElement;
import com.ke.diff.model.Constants;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;
import com.ke.diff.model.SingleNodeDifference;

/**
 * 数组和对象比较的公有方法抽象类
 * @Author JingWei
 * @create 2022/3/1
 */
public abstract class AbstractObjectAndArray  {
    protected AlgorithmModule algorithmModule;

    /**
     * diff算法比较核心方法，比较2个JsonElement的入口。
     * @param a 比较的第一个JsonElement
     * @param b 比较的第二个JsonElement
     * @param pathModule 路径模型
     * @return 不同的比较结果
     */
    public DiffContext diffElement(JsonElement a, JsonElement b, PathModule pathModule) {
        return algorithmModule.diffElement(a, b, pathModule);
    }

    /**
     * 构造算法模型,比如使用对象和数组类型算法比较时，内部会用到其他类型的算法。
     * @param algorithmModule 算法模型
     */
    public void constructAlgorithmModule(AlgorithmModule algorithmModule) {
        this.algorithmModule = algorithmModule;
    }

    /**
     * 如果下层diff结果不同，会把下层diff结果加入到上层diff结果中去。
     * @param parentResult 上层结果
     * @param childResult 下层结果
     */
    public void parentContextAddChildContext(DiffContext parentResult, DiffContext childResult) {
        if(childResult.isSame() == Constants.DIFFERENT) {
            for (SingleNodeDifference singleNodeDifference : childResult.getDiffResultModels()) {
                parentResult.getDiffResultModels().add(singleNodeDifference);
            }
            parentResult.setSame(false);
        }
    }
}