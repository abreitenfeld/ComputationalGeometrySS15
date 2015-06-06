package main.kd;

import main.io.DataValue;

public abstract class QueryRange<T extends Comparable> extends Dimension {

    public final T From;
    public final T To;

    public QueryRange(int attributeIdx, DataValue.ValueType type, T from, T to) {
        super(attributeIdx, type);
        this.From = from;
        this.To = to;
    }

}
