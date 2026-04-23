package org.Darwyi.CollectionTypes;

import org.Darwyi.CollectionTypes.exceptions.EmptyListException;
import org.Darwyi.CollectionTypes.exceptions.InvalidIndexException;
import org.Darwyi.CollectionTypes.exceptions.LimitException;
import org.Darwyi.CollectionTypes.exceptions.ValueIsNull;

public class MyLinkedList<T> {
    private OwnData<T> start;
    private OwnData<T> end;
    private int size;
    private final int capacity;

    public MyLinkedList() {
        this.capacity = Integer.MAX_VALUE;
    }

    public MyLinkedList(int capacity) {
        if (capacity < 0) throw new LimitException("Capacity must be greater than 0");
        this.capacity = capacity;
    }

    public int GetSize() {
        return size;
    }

    public int GetCapacity() {
        return capacity;
    }

    public T get(int index) {
        return getData(index).data;
    }

    public int size() {
        return size;
    }

    public void addEnd(T value) throws ValueIsNull {
        checkNull(value);
        checkCapacity();
        OwnData<T> data = new OwnData<>(value);

        if (start == null) {
            start = end = data;
        } else {
            end.next = data;
            data.prev = end;
            end = data;
        }
        size++;
        System.out.println("Added at the end: " + data.data);
    }

    public void add(int index, T value) throws ValueIsNull {
        checkNull(value);
        checkCapacity();
        if (index < 0 || index > size)
            throw new InvalidIndexException("Index is less than 0 or bigger than list size");

        if (index == 0) {
            addStart(value);
            return;
        }
        if (index == size) {
            addEnd(value);
            return;
        }

        OwnData<T> current = getData(index);
        OwnData<T> newData = new OwnData<>(value);

        OwnData<T> prev = current.prev;
        prev.next = newData;
        newData.prev = prev;
        newData.next = current;
        current.prev = newData;
        size++;
        System.out.println("Added at position " + index + " with value: " + newData.data);
    }

    public void addStart(T value) throws ValueIsNull {
        checkNull(value);
        checkCapacity();
        OwnData<T> data = new OwnData<>(value);

        if (start == null) {
            start = end = data;
        } else {
            start.prev = data;
            data.next = start;
            start = data;
        }
        size++;
        System.out.println("Added at the start: " + data.data);
    }

    public void remove(int index) {
        if (size <= 0) throw new EmptyListException("Empty list");
        if (index < 0 || index >= size)
            throw new InvalidIndexException("Invalid index: " + index);

        OwnData<T> current = getData(index);

        if (current.prev != null) current.prev.next = current.next;
        else                      start = current.next;

        if (current.next != null) current.next.prev = current.prev;
        else                      end = current.prev;

        size--;
        System.out.println("Removed value " + current.data + " at index " + index);
    }

    public void clear() {
        start = end = null;
        size = 0;
        System.out.println("List cleared");
    }

    private OwnData<T> getData(int index) {
        if (index < 0 || index >= size)
            throw new InvalidIndexException("Index is less than 0 or bigger than list size");

        OwnData<T> current;
        if (index < size / 2) {
            current = start;
            for (int i = 0; i < index; i++) current = current.next;
        } else {
            current = end;
            for (int i = size - 1; i > index; i--) current = current.prev;
        }
        return current;
    }

    private void checkCapacity() {
        if (size + 1 > capacity)
            throw new LimitException("Capacity limit exceeded: " + capacity);
    }

    private void checkNull(T value) throws ValueIsNull {
        if (value == null) throw new ValueIsNull("Null value is not allowed");
    }
}