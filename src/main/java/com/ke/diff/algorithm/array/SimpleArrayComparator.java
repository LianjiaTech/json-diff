package com.ke.diff.algorithm.array;

import com.google.gson.JsonArray;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;

/**
 * 简单数组比较器：数组对比时按照索引号顺序依次进行比较
 * 例：[A,B,C] 与 [C,B,A]比较时，依次找索引号为0、为1、为2进行比较，相同结果对为<A,C> <B,B> <C,A>，不同结果<A,C> <C,A>会加入到返回结果中。
 * @Author JingWei
 * @create 2022/1/14
 */
public class SimpleArrayComparator extends AbstractArray  {
    /**
     * 对两个JsonArray进行比较的方法。
     * @param a  要比较的第一个数组
     * @param b  要比较的第二个数组
     * @param pathModule 路径模型
     * @return 返回不相等的比较结果
     */
    @Override
    public DiffContext diffArray(JsonArray a, JsonArray b, PathModule pathModule) {
        DiffContext arrayDiffContext = new DiffContext();
        int maxLength = Math.max(a.size(), b.size());
        //根据数组a和b长度的大的值进行遍历
        for (int i = 0; i < maxLength; i++) {
            pathModule.addAllpath(constructArrayPath(i));
            DiffContext diffContext =  generateDiffResult(a,b,i,pathModule);
            parentContextAddChildContext(arrayDiffContext, diffContext);
        }
        return arrayDiffContext;
    }

    /**
     * 生成比较结果,分以下3种情况考虑
     *  i < a.size() && i < b.size()
     *  a.size() <= i
     *  b.size() <= i
     * @param a 数组a
     * @param b 数组b
     * @param i 数组a和b中正在比较的元素索引号
     * @param pathModule 路径模型
     * @return 返回不相等的比较结果
     */
    private DiffContext generateDiffResult(JsonArray a, JsonArray b, int i, PathModule pathModule) {
        if(i >= a.size() && i >= b.size()){
            throw new RuntimeException("数组索引号入参超过数组长度。 索引号:" + i + " 数组a:" + a + "数组b：" + b);
        }
        DiffContext diffContext;
        if(i < a.size() && i < b.size()){
            diffContext = diffElement(a.get(i), b.get(i), pathModule);
        }else if (i >= a.size()){
            diffContext = diffElement(null, b.get(i), pathModule);
        }else{
            diffContext = diffElement(a.get(i), null, pathModule);
        }
        return diffContext;
    }


}

