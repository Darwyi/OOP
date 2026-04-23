package org.Darwyi.CollectionTypes;

public class OwnData<T> {
    T data;
    OwnData<T> next;
    OwnData<T> prev;

    public OwnData(T data) {
        this.data = data;
    }
}
