package org.Darwyi.CollectionTypes;

import org.Darwyi.CollectionTypes.exceptions.InvalidIndexException;
import org.Darwyi.CollectionTypes.exceptions.LimitException;

public class Main {
    public static void main(String[] args) throws Exception {

        MyLinkedList<Integer> intList = new MyLinkedList<>();
        intList.addEnd(10);
        intList.addEnd(20);
        intList.addStart(5);
        intList.add(1, 7);
        System.out.println("Size: " + intList.GetSize());
        System.out.println("Index 0: " + intList.get(0));
        System.out.println("Index 1: " + intList.get(1));
        System.out.println("Index 2: " + intList.get(2));
        System.out.println("Index 3: " + intList.get(3));
        intList.remove(1);
        System.out.println("index 1: " + intList.get(1));

        MyLinkedList<String> strList = new MyLinkedList<>();
        strList.addEnd("Hello");
        strList.addEnd("World");
        strList.addStart("Start");
        System.out.println("Size: " + strList.GetSize());
        System.out.println("index 0: " + strList.get(0));
        System.out.println("index 1: " + strList.get(1));
        System.out.println("index 2: " + strList.get(2));
        strList.clear();

        MyLinkedList<Double> dblList = new MyLinkedList<>();
        dblList.addEnd(82563.09);
        dblList.addEnd(15.120);
        System.out.println("index 0: " + dblList.get(0));
        System.out.println("index 1:  " + dblList.get(1));

        try {
            MyLinkedList<Integer> limited = new MyLinkedList<>(2);
            limited.addEnd(1);
            limited.addEnd(2);
            limited.addEnd(3);
        } catch (LimitException e) {
            System.out.println(e.getMessage());
        }

        try {
            intList.get(100);
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }

        try {
            strList.addEnd(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}