package com.ke.diff.algorithm.primitive;

import com.google.common.base.Joiner;
import com.google.gson.JsonPrimitive;
import com.ke.diff.algorithm.AbstractPrimitiveAndOther;
import com.ke.diff.model.Constants;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;
import com.ke.diff.model.SingleNodeDifference;

import java.util.ArrayList;
import java.util.List;

/**
 * 当要比较的两个JsonElement是基本类型时，默认实现的比较器。
 * @Author JingWei
 * @create 2022/1/10
 */
public class DefaultPrimitiveComparator extends AbstractPrimitiveAndOther implements PrimitiveComparator {
    /**
     * @param a 要比较的第一个JsonPrimitive
     * @param b 要比较的第二个JsonPrimitive
     * @param pathModule 路径模型
     * @return 不同的比较结果
     */
    @Override
    public DiffContext diff(JsonPrimitive a, JsonPrimitive b, PathModule pathModule){
        DiffContext primitiveDiffContext = new DiffContext();
        //如果a和b不相等，返回a和b的比较结果。
        if(Constants.DIFFERENT == a.equals(b)) {
            List<SingleNodeDifference> singleNodeDifferences = new ArrayList<>();
            singleNodeDifferences.add(new SingleNodeDifference(Joiner.on(Constants.MERGE_PATH).join(pathModule.getLeftPath()), Joiner.on(Constants.MERGE_PATH).join(pathModule.getRightPath()), jsonElement2Str(a), jsonElement2Str(b)));
            primitiveDiffContext.setDiffResultModels(singleNodeDifferences);
            primitiveDiffContext.setSame(Constants.DIFFERENT);
        }
        return primitiveDiffContext;
    }

}
