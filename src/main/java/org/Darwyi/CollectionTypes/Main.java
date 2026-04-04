package org.Darwyi.CollectionTypes;

public class Main {
    public static void main(String[] args) throws Exception {
        MyLinkedList list = new MyLinkedList();
        list.add(0, 100);
        list.addEnd(12);
        list.addStart(13);
        System.out.println("Data at index 0: " + list.get(0));
        System.out.println("Data at index 2: " + list.get(2));
        System.out.println("Data at index 1: " + list.get(1));
        System.out.println("Size: " + list.GetSize());
        System.out.println("Capacity: " + list.GetCapacity());
        list.remove(0);
        list.clear();
    }
}
