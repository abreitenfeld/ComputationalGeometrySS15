package main.io;


public class DoubleValue extends DataValue<Double> {

    public DoubleValue(Double value) {
        super(value);
    }

    @Override
    public ValueType getType() {
        return ValueType.NUMERIC;
    }
}
