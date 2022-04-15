package com.ke.diff.algorithm.other;

import com.google.common.base.Joiner;
import com.google.gson.JsonElement;
import com.ke.diff.algorithm.AbstractPrimitiveAndOther;
import com.ke.diff.model.Constants;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;
import com.ke.diff.model.SingleNodeDifference;

import java.util.ArrayList;
import java.util.List;

/**
 * 当要比较的两个JsonElement的不同时为对象、组数、空、基本类型时，默认实现的比较器。
 * @Author JingWei
 * @create 2022/1/10
 */
public class DefaultOtherComparator extends AbstractPrimitiveAndOther implements OtherComparator {

    /**
     * @param a 要比较的第一个JsonElement
     * @param b 要比较的第二个JsonElement
     * @param pathModule 路径模型
     * @return 不同的比较结果
     */
    @Override
    public DiffContext diff(JsonElement a, JsonElement b, PathModule pathModule){
        //比较结果一定会不同，因为要比较的a和b类型不同才会调用该方法。
        DiffContext otherDiffContext = new DiffContext(Constants.DIFFERENT);
        List<SingleNodeDifference> singleNodeDifferences = new ArrayList<>();
        singleNodeDifferences.add(new SingleNodeDifference(Joiner.on(Constants.MERGE_PATH).join(pathModule.getLeftPath()), Joiner.on(Constants.MERGE_PATH).join(pathModule.getRightPath()), jsonElement2Str(a), jsonElement2Str(b)));
        otherDiffContext.setDiffResultModels(singleNodeDifferences);
        return otherDiffContext;
    }


}
