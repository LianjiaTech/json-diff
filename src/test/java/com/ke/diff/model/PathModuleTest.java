package com.ke.diff.model;

import org.junit.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * PathModule Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>2月 28, 2022</pre>
 */
public class PathModuleTest {
    /**
     * 测试新增路径方法是否正常
     * Method: addAllpath(String lastPath)
     */
    @Test
    public void testAddAllpath() {
        PathModule pathModule = new PathModule();
        pathModule.addAllpath("a");
        assertEquals("a", pathModule.getLeftPath().getLast());
        //判断路径是否可以加入null
        pathModule.addAllpath(null);
        assertNull(pathModule.getLeftPath().getLast());
        assertEquals(2, pathModule.getLeftPath().size());
    }

    /**
     * 测试删除路径方法是否正常
     * Method: removeAllLastPath()
     */
    @Test
    public void testRemoveAllLastPath()  {
        PathModule pathModule = new PathModule();
        pathModule.addAllpath("a");
        assertEquals("a", pathModule.getLeftPath().getLast());
        //判断路径是否可以加入null
        pathModule.addAllpath(null);
        pathModule.addAllpath(null);
        //删除路径
        pathModule.removeAllLastPath();
        assertNull(pathModule.getLeftPath().getLast());
        assertEquals(2, pathModule.getLeftPath().size());
        //删除路径
        pathModule.removeAllLastPath();
        assertEquals(pathModule.getLeftPath().getLast(),"a");
        assertEquals(1, pathModule.getLeftPath().size());
        //路径为空时
        pathModule.removeAllLastPath();
        assertEquals(pathModule.getLeftPath(),new LinkedList<>());
        assertEquals(0, pathModule.getLeftPath().size());
    }

    /**
     * 如果路径为空时删除路径，抛出异常
     * Method: removeAllLastPath1()
     */
    @Test(expected = NoSuchElementException.class)
    public void testRemoveAllLastPath1() {
        PathModule pathModule = new PathModule();
        //如果路径为空时删除路径，抛出异常
        pathModule.removeAllLastPath();
    }
}