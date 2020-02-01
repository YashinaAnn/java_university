package com.pack.task4;

import java.util.*;

public class CollectionsTask {

    public static <T extends Comparable> void sortDescending(ArrayList<T> array) {
        if (null == array) {
            throw new IllegalArgumentException("Bad data");
        }
        array.sort(Comparator.reverseOrder());
    }

    public static double sumOfArray(List<Double> array) {
        if (null == array) {
            throw new IllegalArgumentException("Bad data");
        }
        return array.stream().reduce(0.0, (left, right) -> left + right);
    }

    public static <K, V> boolean isContains(Map<K, V> map, K key) {
        if (null == map || null == key) {
            throw new IllegalArgumentException("Bad data");
        }
        return map.containsKey(key);
    }

    public static <T> void addElement(LinkedList<T> list, T elem) {
        if (null == list || null == elem) {
            throw new IllegalArgumentException("Bad data");
        }
        list.addFirst(elem);
        list.addLast(elem);
    }

    public static <T> int findElem(ArrayList<T> list, T elem) {
        if (null == list || null == elem) {
            throw new IllegalArgumentException("Bad data");
        }
        return list.indexOf(elem);
    }

}
