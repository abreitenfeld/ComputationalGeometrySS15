package main.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CSVParser {

    private final Reader _reader;
    private final String _delimiter;

    /**
     * Constructor of the parser.
     *
     * @param reader
     * @param delimiter
     */
    public CSVParser(Reader reader, String delimiter) {
        this._reader = reader;
        this._delimiter = delimiter;
    }

    public List<Record> getRecords() throws IOException {
        BufferedReader br = new BufferedReader(this._reader);

        LinkedList<Record> result = new LinkedList<Record>();
        String line;
        while ((line = br.readLine()) != null) {
            result.add(new Record(line.split(this._delimiter)));
        }

        // check if first record contains column header
        if (result.size() > 1) {
            boolean discardFirstRecord = false;
            for (int i = 0; i < result.get(0).size(); i++) {
                if (result.getFirst().get(i).getType() != result.get(1).get(i).getType()) {
                    discardFirstRecord = true;
                    break;
                }
            }
            if (discardFirstRecord) {
                result.remove(0);
            }
        }

        return result;
    }

    /**
     * Closes the file reader.
     * @throws IOException
     */
    public void close() throws IOException {
        this._reader.close();
    }

}
