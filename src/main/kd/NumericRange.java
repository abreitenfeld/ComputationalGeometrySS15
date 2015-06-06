package main.kd;

import main.io.DataValue;

public class NumericRange extends QueryRange<Double> {

    public NumericRange(int attributeIdx, double from, double to) {
        super(attributeIdx, DataValue.ValueType.NUMERIC, from, to);
    }

}
