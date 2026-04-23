package org.Darwyi.CollectionTypes;

import org.Darwyi.CollectionTypes.exceptions.EmptyListException;
import org.Darwyi.CollectionTypes.exceptions.InvalidIndexException;
import org.Darwyi.CollectionTypes.exceptions.LimitException;
import org.Darwyi.CollectionTypes.exceptions.ValueIsNull;

import java.util.Comparator;

public class MyLinkedList<T extends Comparable<T>>
        implements Comparable<MyLinkedList<T>> {

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

    public T get(int index) {
        return getData(index).data;
    }

    public int size() {
        return size;
    }

    public int GetSize()     { return size; }
    public int GetCapacity() { return capacity; }

    @Override
    public int compareTo(MyLinkedList<T> other) {
        return Integer.compare(this.size, other.size);
    }

    public void sort() {
        sort(Comparable::compareTo);
    }

    public void sort(Comparator<T> comparator) {
        if (size <= 1) return;
        boolean swapped;
        do {
            swapped = false;
            OwnData<T> current = start;
            while (current.next != null) {
                if (comparator.compare(current.data, current.next.data) > 0) {
                    T temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
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
    }

    public void add(int index, T value) throws ValueIsNull {
        try { checkNull(value); } catch (ValueIsNull e) { throw new RuntimeException(e); }
        checkCapacity();
        if (index < 0 || index > size)
            throw new InvalidIndexException("Index out of bounds: " + index);
        if (index == 0)    {
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
    }

    public void addStart(T value) {
        try { checkNull(value); } catch (ValueIsNull e) { throw new RuntimeException(e); }
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
    }

    public void remove(int index) {
        if (size <= 0) throw new EmptyListException("Empty list");
        if (index < 0 || index >= size) throw new InvalidIndexException("Invalid index: " + index);
        OwnData<T> current = getData(index);
        if (current.prev != null) {
            current.prev.next = current.next;
        }
        else {
            start = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        }
        else {
            end = current.prev;
        }
        size--;
    }

    public void clear() {
        start = end = null;
        size = 0;
    }

    public void print() {
        String output = "{";
        OwnData<T> current = start;
        while (current != null) {
            if (output.equals("{")) {
                output = String.format("%s %s", output, current.data);
                current = current.next;
            } else {
                output = String.format("%s, %s", output, current.data);
                current = current.next;
            }
        }
        System.out.println(String.format("%s }", output));
    }

    private OwnData<T> getData(int index) {
        if (index < 0 || index >= size)
            throw new InvalidIndexException("Index out of bounds: " + index);
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