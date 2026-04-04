package org.Darwyi.CollectionTypes;

import java.awt.*;

public class MyLinkedList extends List {
    private OwnData start;
    private OwnData end;
    private int Size;

    public int GetSize() {
        return Size;
    }

    public int GetCapacity() {
        return Size;
    }

    public int getCapacity() {
        return 1;
    }
    public void addEnd(int value) {
        OwnData data = new OwnData(value);

        if (start == null) {
            start = end = data;
        } else {
            end.next = data;
            data.prev = end;
            end = data;
        }
        Size++;

        System.out.println("Added at the end: " + data.data);
    }

    public void add(int index,int value) throws Exception {
        if (index < 0) {
            throw new Exception("Index is less than zero or bigger then size");
        }

        if (index == 0 ) {
            addStart(value);
            return;
        }

        if (index == GetSize()) {
            addEnd(value);
            return;
        }

        OwnData current = getData(index);
        OwnData newData = new OwnData(value);

        OwnData prev = current.prev;
        prev.next = newData;
        newData.prev = prev;

        newData.next = current;
        current.prev = newData;
        Size++;

        System.out.println("Added at the: " + index + " position, with " + newData.data + " value.");
    }

    public void addStart(int value) {
        OwnData data = new OwnData(value);
        if (start == null) {
            start = end = data;
        } else {
            start.prev = data;
            data.next = start;
            start = data;
        }
        Size++;

        System.out.println("Added at the Start: " + data.data);
    }


    public int get(int index) throws Exception {
        return getData(index).data;
    }

    private OwnData getData(int index) throws Exception {
        if (index < 0) {
            throw new Exception();
        }

        OwnData currentData;

        if (index < GetSize() / 2) {
            currentData = start;
            for (int i = 0; i < index; i++) {
                currentData = currentData.next;
            }
        } else {
            currentData = end;
            for (int i = GetSize() - 1; i > index; i--) {
                currentData = currentData.prev;
            }
        }
        return currentData;
    }

    public void remove(int index) {
        if (index < 0 || index > GetSize()) throw new IndexOutOfBoundsException();

        OwnData current = null;

        try {
            current = getData(index);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (current.prev != null) {
            current.prev.next = current.next;
            for (int i = 0; i < GetSize() / 2; i++) {
                current.prev = current.next.next;
            }
        } else {
            start = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            end = current.prev;
        }
        Size--;
        System.out.println("Data: " + current.data + " with index " + index + " successfully removed");
    }

    @Override
    public void clear() {
//        for (int i = 0; i < GetSize(); i++) {
//            OwnData current = null;
//            try {
//                current = getData(i);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//            System.out.println("Size: " + GetSize()+ "\n" + "Data: " + current.prev + " " + current + " " + current.next);
//            current.prev = null;
//        }
//        Size = 0;
        start = end = null;
        Size = 0;
        System.out.println("List cleared");
    }

}
