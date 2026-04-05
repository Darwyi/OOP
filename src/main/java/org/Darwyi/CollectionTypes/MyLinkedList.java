package org.Darwyi.CollectionTypes;

import org.Darwyi.CollectionTypes.exceptions.EmptyListException;
import org.Darwyi.CollectionTypes.exceptions.InvalidIndexException;
import org.Darwyi.CollectionTypes.exceptions.LimitException;

import java.awt.*;

public class MyLinkedList extends List {
    private OwnData start;
    private OwnData end;
    private int Size;
    private final int capacity;

    public MyLinkedList() {
        this.capacity = Integer.MAX_VALUE;
    }
    public MyLinkedList(int Capacity) {
        if (Capacity < 0) throw new LimitException("Capacity must be greater than 0");
        this.capacity = Capacity;
    }

    public int GetSize() {
        return Size;
    }

    public int GetCapacity() {
        return capacity;
    }

    public void addEnd(int value) {
        if (Size + 1 > capacity) throw new LimitException("Capacity limit exceeded" + capacity);
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

    public void add(int index,int value) {
        if (Size + 1 > capacity) throw new LimitException("Capacity limit exceeded" + capacity);
        if (index < 0 || index > Size) throw new InvalidIndexException("Index is less than 0 or bigger than list size");

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
        if (Size + 1 > capacity) throw new LimitException("Capacity limit exceeded" + capacity);
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


    public int get(int index) {
        return getData(index).data;
    }

    private OwnData getData(int index) {
        if (index < 0 || index >= Size) throw new InvalidIndexException("Index is less than 0 or bigger than list size");

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

    public void remove(int index)  {
        if (Size >= 0) throw new EmptyListException("Empty list");
        if (index < 0 || index >= Size) throw new InvalidIndexException("Invalid index: " + index);

        OwnData current = getData(index);

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
        start = end = null;
        Size = 0;
        System.out.println("List cleared");
    }

}
