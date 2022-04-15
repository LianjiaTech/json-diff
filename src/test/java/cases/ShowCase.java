package cases;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.ke.diff.AlgorithmEnum;
import com.ke.diff.Diff;
import com.ke.diff.model.Result;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.List;

/**
 * 测试case
 * @Author JingWei
 * @create 2022/3/3
 */

public class ShowCase {
    public static void main(String[] args) {
        List<Pair<String, String>> caseList = getCaseList();
        for (int index = 0; index < caseList.size(); index++) {
            List<String> noiseList = Lists.newArrayList();
            List<String> noiseList1 = Lists.newArrayList("msg", "cost");
            List<String> noiseList2 = Lists.newArrayList("msg", "cost", "a.b");
            noiseList.addAll(noiseList1);
            noiseList.addAll(noiseList2);
            System.out.println("*************************");
            System.out.println("case[" + index + "] result is  ");
            LinkedList<String> specialPath = new LinkedList<>();
            //第一种diff方法 详细使用方法见README.md文件
            List<Result> diff1 = new Diff().diff(caseList.get(index).getLeft(), caseList.get(index).getRight());
            //第二种diff方法 详细使用方法见README.md文件
            List<Result> diff2 = new Diff().withNoisePahList(noiseList).withSpecialPath(specialPath).withAlgorithmEnum(AlgorithmEnum.DEFAULT).diff(caseList.get(index).getLeft(), caseList.get(index).getRight());
            System.out.println("left is : " + caseList.get(index).getLeft());
            System.out.println("right is : " + caseList.get(index).getRight());
            System.out.println("result is : " + new Gson().toJson(diff1));
            System.out.println("result is : " + new Gson().toJson(diff2));

        }
    }


    private static List<Pair<String, String>> getCaseList() {
        List<Pair<String, String>> caseList = Lists.newArrayList();

        Pair<String, String> case0 = Pair.of(
                " {}",
                "{}"
        );

        Pair<String, String> case1 = Pair.of(
                " {\"coord1nates\": [[100.0, 1.0],[100.0, 1.0],[100.0, 1.0]]}",
                "{\"coord1nates\": [[100.0, 0.0],[100.0, 1.0],[100.0, 1.0]]}"
        );

        Pair<String, String> case2 = Pair.of(
                "{\"code\":5,\"test\":7,\"data\":{}, \"data1\":5,   \"data2\":{\"data\":5}}",
                "{\"code\":6,\"test\":8,\"data\":7, \"data1\":null, \"data2\":5}"
        );

        Pair<String, String> case3 = Pair.of(
                " {\"coord1nates\": [ 8.0, 2.0, 1.0, 1.0, 1.0 ],\"coord1nates1\": 8.0}",
                "{\"coord1nates\": [ 8.0, 4.0, 3.0, 2.0, 5.0 ],\"coord1nates1\": 8.0}"
        );

        Pair<String, String> case4 = Pair.of(
                " {\"coord1nates\": 1,  code : {\"coord1nates\": 3}}",
                " {\"coord1nates\": 2,  code : {\"coord1nates\": 4}}"
        );


        Pair<String, String> case5 = Pair.of(
                "[{\"coord1nate\": [  2.0, 2.0, 1.0, 1.0 ],\"A\":2 }]   ",
                "[{\"coord1nates\": [  4.0, 2.0, 1.0, 2.0 ],\"A\":2}]  "
        );

        Pair<String, String> case6 = Pair.of(
                "[  \"1.0\",\"3.0\",\"2.0\" ]",
                "[  \"4.0\",\"1.0\",\"2.0\" ]"
        );

        Pair<String, String> case7 = Pair.of(
                "{\"A\":2}",
                "{\"B\":6}"
        );

        Pair<String, String> case8 = Pair.of(
                "{  \"name\": [{\"coord1nate\": [  {\"A\":2,\"B\":5}, {\"A\":2,\"B\":5}, {\"A\":1,\"B\":2}] } ]  } ",
                "{ \"name\": [{\"coord1nate\": [  {\"A\":2,\"B\":5}, {\"A\":1,\"B\":3}, {\"A\":9,\"B\":8} ]}  ] } "
        );

        Pair<String, String> case9 = Pair.of(
                "[{\"a\":7,\"b\":5,\"c\":6},{\"a\":4,\"b\":8,\"c\":9}]"
                , "[{\"a\":1,\"b\":2,\"c\":3},{\"a\":4,\"b\":5,\"c\":6},{\"a\":7,\"b\":8,\"c\":9}]"
        );

        caseList.addAll(Lists.newArrayList(case0, case1, case2, case3, case4, case5, case6, case7, case8, case9));
        return caseList;
    }
}