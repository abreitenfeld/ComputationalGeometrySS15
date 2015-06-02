package main.io;


public class DoubleItem extends DataItem<Double> {

    public DoubleItem(Double value) {
        super(value);
    }

    @Override
    public Double getValue() {
        return (Double) this._value;
    }
}
