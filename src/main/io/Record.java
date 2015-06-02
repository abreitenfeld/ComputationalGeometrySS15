package main.io;

import java.util.ArrayList;
import java.util.List;

public class Record extends ArrayList<DataItem> {

    protected final List<DataItem> _cells;

    public Record(String[] values) {
        this._cells = new ArrayList<DataItem>(values.length);

        for (String value : values) {

        }

    }

    public List<DataItem> getCells() {
        return this._cells;
    }
}
