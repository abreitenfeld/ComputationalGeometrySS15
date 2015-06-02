package main.io;

public abstract class DataItem<T extends Comparable<T>> {

    protected final T _value;

    public DataItem(T value) {
        this._value = value;
    }

    public abstract T getValue();
}
