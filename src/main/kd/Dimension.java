package main.kd;


import main.io.DataValue;

public class Dimension {

    public final int AttributeIdx;
    public final DataValue.ValueType Type;

    public Dimension(int attributeIdx, DataValue.ValueType type) {
        this.AttributeIdx = attributeIdx;
        this.Type = type;
    }

    @Override
    public String toString() {
        return "Dimension{" +
                "AttributeIdx=" + AttributeIdx +
                ", Type=" + Type +
                '}';
    }
}
