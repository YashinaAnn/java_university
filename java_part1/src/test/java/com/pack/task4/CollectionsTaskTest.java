package com.pack.task4;

import org.junit.Test;

import java.util.*;

import static com.pack.task4.CollectionsTask.*;
import static org.junit.Assert.*;

public class CollectionsTaskTest {

    @Test
    public void testSortingDescending() {
        ArrayList<Double> list = new ArrayList<>();
        list.add(1.0);
        list.add(5.0);
        list.add(2.0);
        list.add(6.0);
        list.add(6.0);
        list.add(0.0);
        list.add(3.0);
        CollectionsTask.sortDescending(list);
        assertEquals(Arrays.asList(6.0, 6.0, 5.0, 3.0, 2.0, 1.0, 0.0), list);
    }

    @Test
    public void testSumOfArray() {
        List<Double> list = new ArrayList<>();
        list.add(1.0);
        list.add(1.0);
        list.add(2.0);
        assertEquals(4.0, sumOfArray(list), 0.001);
    }

    @Test
    public void testIsContainsPositive() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        assertFalse(isContains(map, "c"));
        assertTrue(isContains(map, "a"));
    }

    @Test
    public void testAddElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        assertEquals(2, list.size());
        addElement(list, 3);
        assertEquals(4, list.size());
        assertEquals(3, (int) list.getFirst());
        assertEquals(3, (int) list.getLast());
    }

    @Test
    public void testFindElem() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        assertEquals(0, findElem(list, 1));
        assertEquals(-1, findElem(list, 3));
    }
}


