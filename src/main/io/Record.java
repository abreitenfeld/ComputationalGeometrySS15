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
        for (int i = 0; i < values.length; i++) {
            try {
                // try to parse double
                Double dVal = Double.parseDouble(values[i]);
                this.add(new DoubleValue(dVal));
            } catch (NumberFormatException e) {
                // use string value as default
                this.add(new StringValue(values[i]));
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
