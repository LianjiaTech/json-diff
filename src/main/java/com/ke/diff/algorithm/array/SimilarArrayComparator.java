package com.ke.diff.algorithm.array;

import com.google.gson.JsonArray;
import com.ke.diff.model.DiffContext;
import com.ke.diff.model.PathModule;
import com.ke.diff.model.SingleNodeDifference;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 相似度数组算法比较器:当数组进行比较找不到相等元素时，通过算法优先找最接近的元素进行匹配
 *
 * 举例：
 *"[{\"a\":7,\"b\":5,\"c\":6},{\"a\":6,\"b\":2,\"c\":3}]"和
 *"[{\"a\":1,\"b\":2,\"c\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"比较
 * 数组一中第一个元素依次和数组二中元素比较，分别有3组不同，1组不同，2组不同
 * 数组一中第二个元素依次和数组二中元素比较，分别有1组不同，3组不同，3组不同
 * 一、得到相似矩阵如下
 *  3   1   2
 *  1   3   3
 * 二、遍历矩阵所有元素，找到值最小的元素，并且行和列的其他元素不能再被使用。行和列即为要比较的2个元素在各自数组中的索引号
 *  第一次找到1，第二次找到另外一个1.
 * 三、遍历矩阵中未使用所有列，列索引号为多余的元素在数组中的位置。
 *  只有第三列可以使用 ，第二组中第3个元素为新增元素。
 *
 * @Author JingWei
 * @create 2022/2/24
 */
public class SimilarArrayComparator extends AbstractArray {
    /**
     * USEABLE表示当前位置可以使用
     */
    private final boolean USEABLE = false;

    /**
     * USED表示当前位置已经被使用过
     */
    private final boolean USED = true;


    /**
     * @param a 要比较的第一个数组
     * @param b 要比较的第二个数组
     * @param pathModule 路径模型
     * @return 返回不相等的结果
     */
    @Override
    public DiffContext diffArray(JsonArray a, JsonArray b, PathModule pathModule) {
        DiffContext diffContext;
        //a数组长度小于等于b，直接使用diff数组比较算法
        if(a.size() <= b.size()){
            diffContext = diff(a, b, pathModule);
        }
        //当a数组长度大于b时，需要交换pathModule中a和b路径, 并交换数组a和b，再使用diff数组比较算法
        else {
            exchangeLeftAndRightPath(pathModule);
            diffContext = diff(b, a, pathModule);
            exchangeLeftAndRightPath(pathModule);
            exchangeResult(diffContext);
        }
        return diffContext;
    }

    /**
     * 交换返回结果中每一个路径和结果
     * @param diffContext 返回结果
     */
    private void exchangeResult(DiffContext diffContext) {
        List<SingleNodeDifference> singleNodeDifferences = diffContext.getDiffResultModels();
        for(SingleNodeDifference singleNodeDifference : singleNodeDifferences){
            exchangePathAndResult(singleNodeDifference);
        }
    }

    /**
     * 交换返回结果中的路径和结果
     * @param singleNodeDifference 返回结果
     */
    private void exchangePathAndResult(SingleNodeDifference singleNodeDifference) {
        String tempStringA = singleNodeDifference.getLeftPath();
        Object tempLeft = singleNodeDifference.getLeft();
        singleNodeDifference.setLeftPath(singleNodeDifference.getRightPath());
        singleNodeDifference.setRightPath(tempStringA);
        singleNodeDifference.setLeft(singleNodeDifference.getRight());
        singleNodeDifference.setRight(tempLeft);
    }


    /**
     * 将路径模型中a和b的路径交换
     * @param pathModule 路径模型
     */
    private void exchangeLeftAndRightPath(PathModule pathModule) {
        LinkedList<String> tempA = pathModule.getLeftPath();
        pathModule.setLeftPath(pathModule.getRightPath());
        pathModule.setRightPath(tempA);
    }


    /**
     * 数组比较核心算法，支持数组a长度小于b长度时使用
     */
    DiffContext diff(JsonArray a, JsonArray b, PathModule pathModule ) {
        int rowlength = a.size();
        int linelength = b.size();
        //a数组中m个元素，与b数组中n个元素比较，共比较m*n次，所有比较结果会得组成一个矩阵，矩阵中的数字用来存储比较结果。
        int [][] similarMatrix = new int[rowlength][linelength];
        //创建一个行和列数组 用来判断当前行和列是否被使用过
        boolean []row = new boolean[rowlength];
        boolean []line = new boolean[linelength];
        for (int i = 0; i < rowlength; i++) {
            pathModule.addLeftPath(constructArrayPath(i));
            //a数组中m个元素，与b数组中n个元素比较，比较结果生成一个m*n的矩阵
            constructSimilarMatrix(a, b, i, pathModule, similarMatrix, row, line);
            pathModule.removeLastLeftPath();
        }
        return obtainDiffResult(a, b, pathModule, row, line, similarMatrix);
    }


    /**
     * 通过相似度矩阵，获取比较结果。 第一步在矩阵找到最小的数字，即找到最接近的几对结果。   第二步找剩余未使用的列数，即多余的元素
     */
    private DiffContext obtainDiffResult(JsonArray a, JsonArray b, PathModule pathModule, boolean[] row, boolean[] line, int[][] similarMatrix) {
        DiffContext arrayDiffContext = new DiffContext();
        //找到a和b都有的结果，并且是最接近的几对结果
        obtainModifyDiffResult(a,b,pathModule,row,line,similarMatrix, arrayDiffContext);
        //a没有b有的结果，即新增的结果
        obtainAddDiffResult(b,pathModule,line, arrayDiffContext);

        return arrayDiffContext;
    }


    /**
     * 得到新增的结果。由于b数组长度大于a，会有几个元素多余。在选出a和b最接近的几对元素后，剩下的几个元素被认为是新增的。
     */
    private void obtainAddDiffResult(JsonArray b, PathModule pathModule, boolean[] line, DiffContext arrayDiffContext) {
        for (int j = 0; j < line.length; j++) {
            if (line[j] == USED) {
                continue;
            }
            DiffContext addOrDeleteDiffContext = constructAddContext(b, j, pathModule);
            parentContextAddChildContext(arrayDiffContext, addOrDeleteDiffContext);
        }
    }

    /**
     * 从m*n的矩阵中选出最接近的几组结果，矩阵中的数字越小，说明2个元素比较时结果相差越少
     */
    private void obtainModifyDiffResult(JsonArray a, JsonArray b, PathModule pathModule, boolean[] row, boolean[] line, int[][] similarMatrix, DiffContext arrayDiffContext) {
        int counts = 0;
        //找到还未使用行的数量(行数小于列数)
        for (boolean value : row) {
            if (Objects.equals(USEABLE, value)) {
                counts++;
            }
        }
        //不同结果对数等于未使用的行数
        for (int n = 0; n < counts; n++) {
            int bestLineIndex = 0;
            int bestRowIndex = 0;
            int minDiffPair = Integer.MAX_VALUE;
            //遍历矩阵中所有元素，找最小的数字，矩阵中的数字越小，说明2个元素比较时结果相差越少
            for (int i = 0; i < row.length; i++) {
                for (int j = 0; j < line.length; j++) {
                    //如果行或列被使用，跳过该行
                    if (row[i] == USED || line[j] == USED) {
                        continue;
                    }
                    //如果当前元素数字更小，那么把当前行和列保存下来，数字更新为当前最优结果
                    if (similarMatrix[i][j] < minDiffPair) {
                        bestRowIndex = i;
                        bestLineIndex = j;
                        minDiffPair = similarMatrix[i][j];
                    }
                }
            }
            //将找到的最优结果添加到返回结果中
            DiffContext modifyDiffContext = constructModifyContext(a, b, bestRowIndex, bestLineIndex, pathModule);
            row[bestRowIndex] = USED;
            line[bestLineIndex] = USED;
            parentContextAddChildContext(arrayDiffContext, modifyDiffContext);
        }
    }

    /**
     * 此时a数组没有该元素，b数组有元素，结果为新增
     * @param b 数组
     * @param index 索引号
     * @param pathModule 路径模型
     * @return 生成的不同结果
     */
    private DiffContext constructAddContext(JsonArray b, int index, PathModule pathModule) {
        pathModule.addAllpath(constructArrayPath(index));
        DiffContext diffContext = diffElement(null, b.get(index), pathModule);
        pathModule.removeAllLastPath();
        return diffContext;
    }

    /**
     * 返回两个数组对应索引号元素比较结果
     * @param a 数组a
     * @param b 数组b
     * @param i 数组a中元素的索引号
     * @param bestLineIndex 数组b中元素的索引号，即找到的和a中元素接接近的元素索引号。
     * @param pathModule 路径模型
     * @return 返回不同的结果
     */
    private DiffContext constructModifyContext(JsonArray a, JsonArray b, int i, int bestLineIndex, PathModule pathModule) {
        pathModule.addLeftPath(constructArrayPath(i));
        pathModule.addRightPath(constructArrayPath(bestLineIndex));
        DiffContext diffContext = diffElement(a.get(i), b.get(bestLineIndex), pathModule);
        pathModule.removeAllLastPath();
        return diffContext;
    }

    /**
     * 用数组a的一个元素与数组b中所有元素分别比较，会得到n次比较结果，结果为不相等的结果对数，在矩阵中更新n次结果。
     */
    private void constructSimilarMatrix(JsonArray arrayA, JsonArray arrayB, int  rowIndex, PathModule pathModule, int [][]similarArray, boolean[] row, boolean[] line) {
        if(rowIndex < 0 || rowIndex >= arrayB.size()){
            throw new RuntimeException("索引号入参超出数组长度。 索引号：" + rowIndex +" 数组B:" + arrayB);
        }

        for (int j = 0; j < arrayB.size(); j++) {
            if (line[j] == USEABLE) {
                pathModule.addRightPath(constructArrayPath(j));
                DiffContext diffContext = diffElement(arrayA.get(rowIndex), arrayB.get(j), pathModule);
                pathModule.removeLastRightPath();
                if (diffContext.isSame()) {
                    row[rowIndex] = USED;
                    line[j] = USED;
                    return;
                } else if(existSpecialPath(diffContext.getSpecialPathResult())){
                    similarArray[rowIndex][j] = 0 ;
                }  else {
                    similarArray[rowIndex][j] = diffContext.getDiffResultModels().size();
                }
            }
        }
    }

    /**
     * 判断比较结果是否有特殊路径
     */
    private boolean existSpecialPath(LinkedList<String> specialPathResult) {
        return specialPathResult != null && !specialPathResult.isEmpty();
    }
}
