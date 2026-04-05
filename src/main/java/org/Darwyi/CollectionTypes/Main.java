package org.Darwyi.CollectionTypes;

import org.Darwyi.CollectionTypes.exceptions.InvalidIndexException;
import org.Darwyi.CollectionTypes.exceptions.LimitException;

public class Main {
    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList(1);
        try {
            list.add(-1, 100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            list.addStart(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            list.addStart(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            MyLinkedList Rlist = new MyLinkedList();
            Rlist.addEnd(12);
            Rlist.add(1,14);
            Rlist.addStart(13);
            System.out.println("Data at index 0: " + Rlist.get(0));
            System.out.println("Data at index 1: " + Rlist.get(1));
            System.out.println("Data at index 2: " + Rlist.get(2));
            System.out.println("Size: " + Rlist.GetSize());
            System.out.println("Capacity: " + Rlist.GetCapacity());
            Rlist.remove(0);
            Rlist.clear();
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }

        try {
            MyLinkedList testList = new MyLinkedList(-1);
        } catch (LimitException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            list.get(10);
        } catch (InvalidIndexException e) {
            System.out.println("Index error: " + e.getMessage());
        }
    }
}
