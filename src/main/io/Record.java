package main.io;

import java.util.ArrayList;

public class Record extends ArrayList<DataValue> {

    /**
     * Constructor of a record
     *
     * @param values
     */
    public Record(String[] values) {
        // encapsulate each value by an object of DataValue
        for (String value : values) {
            try {
                // try to parse double
                Double dVal = Double.parseDouble(value);
                this.add(new DoubleValue(dVal));
            } catch (NumberFormatException e) {
                // use string value as default
                this.add(new StringValue(value));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for (DataValue value : this) {
            sb.append(value.getValue().toString() + " ");
        }
        sb.append("}");

        return sb.toString();
    }
}
