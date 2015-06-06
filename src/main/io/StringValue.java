package main.io;

public class StringValue extends DataValue<String> {

    public StringValue(String value) {
        super(value);
    }

    @Override
    public ValueType getType() {
        return ValueType.TEXT;
    }
}
