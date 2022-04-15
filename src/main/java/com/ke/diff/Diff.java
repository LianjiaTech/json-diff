package com.ke.diff;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ke.diff.algorithm.AlgorithmModule;
import com.ke.diff.algorithm.array.ArrayComparator;
import com.ke.diff.algorithm.array.SimilarArrayComparator;
import com.ke.diff.algorithm.nulls.DefaultNullComparator;
import com.ke.diff.algorithm.nulls.NullComparator;
import com.ke.diff.algorithm.object.ObjectComparator;
import com.ke.diff.algorithm.object.SimpleObjectComparator;
import com.ke.diff.algorithm.other.DefaultOtherComparator;
import com.ke.diff.algorithm.other.OtherComparator;
import com.ke.diff.algorithm.primitive.DefaultPrimitiveComparator;
import com.ke.diff.algorithm.primitive.PrimitiveComparator;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;
import com.ke.diff.model.Result;
import com.ke.diff.model.ResultConvertUtil;

import java.util.List;

/**
 * DIFF入口，用于比较两个Json字符串
 * @Author JingWei
 * @create 2022/2/25
 */
public class Diff {
    /**
     * 算法模型。根据传入的算法模型使用该算法模型进行diff比较。
     */
    private AlgorithmEnum algorithmEnum;
    /**
     * 特殊路径集合。当前路径符合特殊路径且特殊路径下比较结果相同，会在返回结果中做额外标识标识。
     */
    private List<String> specialPath;
    /**
     * 噪音字段集合。如果当前路径符合噪音字段路径，则不会比较。
     */
    private List<String> noisePahList;
    /**
     * 以下为5种可以自定义的比较器，条件是algorithmEnum为空（如果algorithmEnum有值，直接使用algorithmEnum对应算法模型，比较器不生效）
     */
    private ObjectComparator objectComparator;
    private ArrayComparator arrayComparator;
    private PrimitiveComparator primitiveComparator;
    private NullComparator nullComparator;
    private OtherComparator otherComparator;


    /**
     * @param a 要比较的第一个JsonElement
     * @param b 要比较的第二个JsonElement
     * @return 用来展示的diff结果
     */
    public List<Result> diffElement(JsonElement a, JsonElement b) {
        DiffContext diffContext;
        //用噪音路径和自定义路径构造路径模型
        PathModule pathModule = new PathModule(noisePahList, specialPath);
        //如果有算法模型直接使用算法模型
        if(algorithmEnum != null){
            diffContext =  algorithmEnum.getAlgorithmModule().diffElement(a, b, pathModule);
        }//如果也没有比较器，直接用默认算法模型
        else if(objectComparator == null && arrayComparator == null && primitiveComparator == null && nullComparator == null && otherComparator == null){
            diffContext = AlgorithmEnum.DEFAULT.getAlgorithmModule().diffElement(a, b, pathModule);
        }//如果有比较器，为空的比较器用默认替换，然后构造算法模型，
        else {
            constrcutDefaultComparator();
            diffContext = new AlgorithmModule(objectComparator, arrayComparator, primitiveComparator, nullComparator, otherComparator).diffElement(a, b, pathModule);
        }
        return ResultConvertUtil.constructResult(diffContext);
    }

    /**
     * @param strA 要比较的第一个字符串
     * @param strB 要比较的第二个字符串
     * @return 用来展示的diff结果
     */
    public List<Result> diff(String strA, String strB) {
        return diffElement(new JsonParser().parse(strA), new JsonParser().parse(strB));
    }

    public Diff() {
    }

    /**
     * 没有初始的比较器替换成默认的
     */
    private void constrcutDefaultComparator() {
        //如果没有初始化算法，则采用默认的算法。
        if(objectComparator == null){
            objectComparator = defaultObjectComparator();
        }
        if(arrayComparator == null){
            arrayComparator = defaultArrayComparator();
        }
        if(primitiveComparator == null){
            primitiveComparator = defaultPrimitiveComparator();
        }
        if(nullComparator == null){
            nullComparator = defaultNullComparator();
        }
        if(otherComparator == null){
            otherComparator = defaultOtherComparator();
        }
    }

    private ObjectComparator defaultObjectComparator() {
        return new SimpleObjectComparator();
    }

    private ArrayComparator defaultArrayComparator() {
        return new SimilarArrayComparator();
    }

    private PrimitiveComparator defaultPrimitiveComparator() {
        return new DefaultPrimitiveComparator();
    }

    private NullComparator defaultNullComparator() {
        return new DefaultNullComparator();
    }

    private OtherComparator defaultOtherComparator() {
        return new DefaultOtherComparator();
    }

    /**
     * 选择算法模型
     * @param algorithmEnum 算法模型枚举类
     * @return 对象本身
     */
    public Diff withAlgorithmEnum(AlgorithmEnum algorithmEnum){
        this.algorithmEnum = algorithmEnum;
        return this;
    }

    /**
     * 设置自定义路径
     * @param specialPath 自定义路径列表
     * @return 对象本身
     */
    public Diff withSpecialPath(List<String> specialPath){
        this.specialPath = specialPath;
        return this;
    }

    /**
     * 设置噪音路径
     * @param noisePahList 噪音路径列表
     * @return 对象本身
     */
    public Diff withNoisePahList(List<String> noisePahList){
        this.noisePahList = noisePahList;
        return this;
    }

    /**
     * 自定义对象比较器
     * @param objectComparator 对象比较器
     * @return 对象本身
     */
    public Diff withObjectComparator(ObjectComparator objectComparator) {
        this.objectComparator = objectComparator;
        return this;
    }

    /**
     * 自定义数组比较器
     * @param arrayComparator 数组比较器
     * @return 对象本身
     */
    public Diff withArrayComparator(ArrayComparator arrayComparator) {
        this.arrayComparator = arrayComparator;
        return this;
    }


    /**
     * 自定义基本类型比较器
     * @param primitiveComparator 基本类型比较器
     * @return 对象本身
     */
    public Diff withPrimitiveAlgorithm(PrimitiveComparator primitiveComparator) {
        this.primitiveComparator = primitiveComparator;
        return this;
    }

    /**
     * 自定义空类型比较器
     * @param nullComparator 空类型比较器
     * @return 对象本身
     */
    public Diff withNullComparator(NullComparator nullComparator) {
        this.nullComparator = nullComparator;
        return this;
    }

    /**
     * 自定义其他类型比较器
     * @param otherComparator 其他类型比较器
     * @return 对象本身
     */
    public Diff withOtheComparator(OtherComparator otherComparator) {
        this.otherComparator = otherComparator;
        return this;
    }
}
