package main.io;

public abstract class DataValue<T extends Comparable<T>> implements Comparable<DataValue<T>> {

    protected final T _value;

    public enum ValueType {
        NUMERIC, TEXT
    }

    public DataValue(T value) {
        this._value = value;
    }

    public abstract ValueType getType();

    public T getValue() {
        return this._value;
    }

    @Override
    public int compareTo(DataValue<T> o) {
        return this._value.compareTo(o.getValue());
    }
}
