package org.Darwyi.CollectionTypes;

public class OwnData<T extends Comparable<T>> implements Comparable<OwnData<T>> {

    T data;
    OwnData<T> next;
    OwnData<T> prev;

    public OwnData(T data) {
        this.data = data;
    }

    @Override
    public int compareTo(OwnData<T> other) {
        return this.data.compareTo(other.data);
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }
}