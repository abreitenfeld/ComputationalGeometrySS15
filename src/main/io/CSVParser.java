package main.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    private final Reader _reader;
    private final String _delimiter;

    public CSVParser(Reader reader, String delimiter) {
        this._reader = reader;
        this._delimiter = delimiter;
    }

    public List<Record> getRecords() throws IOException {
        BufferedReader br = new BufferedReader(this._reader);

        List<Record> result = new ArrayList<Record>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(this._delimiter);
            result.add(new Record(values));
        }
        return result;
    }

    public void close() throws IOException {
        this._reader.close();
    }

}
