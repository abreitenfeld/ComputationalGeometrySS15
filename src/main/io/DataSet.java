package main.io;


import java.util.List;

public class DataSet {

    protected final List<Record> _records;

    public DataSet(List<Record> records) {
        this._records = records;
    }

    public List<Record> getRecords() {
        return this._records;
    }
}
