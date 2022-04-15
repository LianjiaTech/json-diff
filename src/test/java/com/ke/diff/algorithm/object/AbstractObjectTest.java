package com.ke.diff.algorithm.object;

import com.ke.diff.model.PathModule;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * AbstractObject Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>2月 24, 2022</pre>
 */
public class AbstractObjectTest {
    /**
     * 测试当前路径在特殊路径中，该路径是否能加到返回结果中
     * Method: specialPathHandle(boolean isSame, LinkedList<String> specialPathResult, PathModule pathModule)
     */
    @Test
    public void testSpecialPathHandle() throws Exception {
        Class<AbstractObject> clazz = AbstractObject.class;
        LeftJoinObjectComparator abstractObject = new LeftJoinObjectComparator();
        Method method = clazz.getDeclaredMethod("specialPathHandle", boolean.class, LinkedList.class, PathModule.class);
        method.setAccessible(true);
        PathModule pathModule = new PathModule();
        pathModule.setNoisePahList(null);
        LinkedList<String> leftPath = new LinkedList<>();
        leftPath.add("a");
        leftPath.add("b");
        LinkedList<String> specialPath = new LinkedList<>();
        specialPath.add("a.b");
        //设置特殊路径
        pathModule.setSpecialPath(specialPath);
        LinkedList<String> specialPathResult = new LinkedList<>();
        //设置当前路径
        pathModule.setLeftPath(leftPath);
        //调用方法
        method.invoke(abstractObject, true, specialPathResult, pathModule);
        //测试结果，当前路径被加入到返回结果中
        assertEquals(1, specialPathResult.size());
        assertEquals(specialPathResult.get(0), "a.b");
    }

    /**
     *	测试路径为空返回是否正确
     *  Method: existPath(String specialPath)
     */
    @Test
    public void testExistPath() throws Exception {
        String specialPath1 = "a";
        String specialPath2 = null;
        String specialPaht3 = "";
        String specialPath4 = "";
        Class<AbstractObject> clazz = AbstractObject.class;
        LeftJoinObjectComparator abstractObject = new LeftJoinObjectComparator();
        Method method = clazz.getDeclaredMethod("existPath", String.class);
        method.setAccessible(true);
        assertEquals(true, method.invoke(abstractObject, specialPath1));
        //路径为空时
        assertEquals(false, method.invoke(abstractObject, specialPath2));
        assertEquals(true, method.invoke(abstractObject, specialPaht3));
        assertEquals(true, method.invoke(abstractObject, specialPath4));
    }
    /**
     * 测试生成路径能否自动过滤数组
     * @throws Exception 反射异常
     */
    @Test
    public void testListJoin() throws Exception {
        Class<AbstractObject> clazz = AbstractObject.class;
        LeftJoinObjectComparator abstractObject = new LeftJoinObjectComparator();
        Method method = clazz.getDeclaredMethod("listJoin", LinkedList.class);
        method.setAccessible(true);
        LinkedList<String> path = new LinkedList<>();
        path.add("a");
        path.add("[1]");
        path.add("b");
        Object invoke = method.invoke(abstractObject, path);
        String result = (String) invoke;
        assertEquals("a.b", result);
    }
}