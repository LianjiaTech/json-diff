package com.ke.diff;

import com.ke.diff.algorithm.AlgorithmModule;
import com.ke.diff.algorithm.array.SimilarArrayComparator;
import com.ke.diff.algorithm.array.SimpleArrayComparator;
import com.ke.diff.algorithm.nulls.DefaultNullComparator;
import com.ke.diff.algorithm.object.LeftJoinObjectComparator;
import com.ke.diff.algorithm.object.SimpleObjectComparator;
import com.ke.diff.algorithm.other.DefaultOtherComparator;
import com.ke.diff.algorithm.primitive.DefaultPrimitiveComparator;

/**
 * 算法模型枚举类：提供一些默认实现的算法模型
 * @Author JingWei
 * @create 2022/3/2
 */
public enum AlgorithmEnum {

    /**
     * 默认的比较算法模型
     */
    DEFAULT(defaultAlgorithmModule()),

    /**
     * 数组比较采用Simple，对象比较采用Simple
     */
    SIMPLE_ARRAY_AND_SIMPLE_OBJECT(simpleAndSimpleAlgorithmModule()),

    /**
     * 数组比较采用Simple，对象比较采用LeftJoin
     */
    SIMPLE_ARRAY_AND_LEFTJOIN_OBJECT(simpleAndLeftJoinAlgorithmModule()),

    /**
     * 数组比较采用Similar，对象比较采用LeftJoin
     */
    SIMLAR_ARRAY_AND_LEFTJOIN_OBJECT(similarAndLeftJoinAlgorithmModule()),

    /**
     * 数组比较采用Similar，对象比较采用Simple
     */
    MOST_COMMONLY_USED(similarAndSimpleAlgorithmModule());

    final private AlgorithmModule algorithmModule;

    AlgorithmEnum(AlgorithmModule algorithmModule) {
        this.algorithmModule = algorithmModule;
    }


    public AlgorithmModule getAlgorithmModule() {
        return algorithmModule;
    }

    private static AlgorithmModule defaultAlgorithmModule() {
        return new AlgorithmModule(new SimpleObjectComparator(), new SimilarArrayComparator(),
                new DefaultPrimitiveComparator(), new DefaultNullComparator(), new DefaultOtherComparator());

    }

    private static AlgorithmModule simpleAndSimpleAlgorithmModule() {
        return new AlgorithmModule(new SimpleObjectComparator(), new SimpleArrayComparator(),
                new DefaultPrimitiveComparator(), new DefaultNullComparator(), new DefaultOtherComparator());
    }


    private static AlgorithmModule simpleAndLeftJoinAlgorithmModule() {
        return new AlgorithmModule(new LeftJoinObjectComparator(), new SimpleArrayComparator(),
                new DefaultPrimitiveComparator(), new DefaultNullComparator(), new DefaultOtherComparator());
    }

    private static AlgorithmModule similarAndLeftJoinAlgorithmModule() {
        return new AlgorithmModule(new LeftJoinObjectComparator(), new SimilarArrayComparator(),
                new DefaultPrimitiveComparator(), new DefaultNullComparator(), new DefaultOtherComparator());
    }

    private static AlgorithmModule similarAndSimpleAlgorithmModule() {
        return new AlgorithmModule(new SimpleObjectComparator(), new SimilarArrayComparator(),
                new DefaultPrimitiveComparator(), new DefaultNullComparator(), new DefaultOtherComparator());
    }

}