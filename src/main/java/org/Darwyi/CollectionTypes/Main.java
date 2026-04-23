package org.Darwyi.CollectionTypes;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws Exception {

        OwnData<Integer> node1 = new OwnData<>(10);
        OwnData<Integer> node2 = new OwnData<>(20);
        OwnData<Integer> node3 = new OwnData<>(10);

        System.out.println("node1(10) vs node2(20): " + node1.compareTo(node2));
        System.out.println("node2(20) vs node1(10): " + node2.compareTo(node1));
        System.out.println("node1(10) vs node3(10): " + node1.compareTo(node3));

        MyLinkedList<Integer> listA = new MyLinkedList<>();
        listA.addEnd(3);
        listA.addEnd(1);
        listA.addEnd(4);

        MyLinkedList<Integer> listB = new MyLinkedList<>();
        listB.addEnd(10);
        listB.addEnd(20);

        System.out.println("listA.size=3, listB.size=2 -> compareTo: " + listA.compareTo(listB));

        MyLinkedList<Integer> intList = new MyLinkedList<>();
        intList.addEnd(42);
        intList.addEnd(7);
        intList.addEnd(19);
        intList.addEnd(3);
        intList.addEnd(55);

        System.out.print("Before sort: ");
        intList.print();
        intList.sort();
        System.out.print("After sort(): ");
        intList.print();
        intList.sort(Comparator.reverseOrder());
        System.out.print("After sort(Reverse): ");
        intList.print();

        MyLinkedList<Integer> absList = new MyLinkedList<>();
        absList.addEnd(-15);
        absList.addEnd(3);
        absList.addEnd(-7);
        absList.addEnd(20);
        absList.addEnd(-1);

        System.out.print("Before sort: ");
        absList.print();
        absList.sort(Comparator.naturalOrder());
        System.out.print("After sort(ByAbs): ");
        absList.print();

        MyLinkedList<String> strList = new MyLinkedList<>();
        strList.addEnd("hello");
        strList.addEnd("руддщ453");
        strList.addEnd("цщкдв12");
        strList.addEnd("world");

        System.out.print("Before sort: ");
        strList.print();
        strList.sort();
        System.out.print("After sort(): ");
        strList.print();
        strList.sort(Comparator.comparingInt(String::length));
        System.out.print("After sort(ByLength): ");
        strList.print();

        MyLinkedList<Integer> lambdaList = new MyLinkedList<>();
        lambdaList.addEnd(5);
        lambdaList.addEnd(2);
        lambdaList.addEnd(9);
        lambdaList.addEnd(4);
        lambdaList.addEnd(1);

        System.out.print("Before sort: ");
        lambdaList.print();
        lambdaList.sort((a, b) -> {
            int parityA = a % 2, parityB = b % 2;
            if (parityA != parityB) {
                return Integer.compare(parityA, parityB);
            }
            return Integer.compare(a, b);
        });
        System.out.print("After sort(lambda): ");
        lambdaList.print();
    }
}