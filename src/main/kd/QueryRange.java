package main.kd;

import main.io.DataValue;

public abstract class QueryRange<T extends Comparable> extends Dimension {

    public final T From;
    public final T To;

    /**
     * Constructor of QueryRange.
     *
     * @param attributeIdx
     * @param type
     * @param from
     * @param to
     */
    public QueryRange(int attributeIdx, DataValue.ValueType type, T from, T to) {
        super(attributeIdx, type);
        this.From = from;
        this.To = to;
    }

    /**
     * Check if the provided value is within range.
     *
     * @param value
     * @return
     */
    public boolean valueInRange(T value) {
        return value.compareTo(this.To) <= 0 && value.compareTo(this.From) >= 0;
    }

    @Override
    public String toString() {
        return "Range{" +
                "From=" + From +
                ", To=" + To +
                '}';
    }
}
