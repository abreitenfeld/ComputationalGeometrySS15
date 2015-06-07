package test;

import main.io.*;
import main.kd.Dimension;
import main.kd.KDTree;
import main.kd.NumericRange;
import main.kd.QueryRange;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class KDTreeTest {


    public static void main(String[] args) throws Exception {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(new JFrame());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // open file reader
            File file = fc.getSelectedFile();

            // parse input data
            final Reader reader = new FileReader(file);
            final CSVParser parser = new CSVParser(reader, "\t");
            final DataSet data = new DataSet(parser.getRecords());
            // close parser
            parser.close();

            // instantiate KD-Tree
            final KDTree tree = new KDTree(data,
                    new Dimension(3, DataValue.ValueType.NUMERIC),  // City Region
                    new Dimension(4, DataValue.ValueType.NUMERIC),  // Latitude
                    new Dimension(5, DataValue.ValueType.NUMERIC)   // Longitude
            );

            // (1) query by latitude and longitude
            query(tree,
                    new NumericRange(4, 52, 53),
                    new NumericRange(5, 13, 14)
            );

            // (2) query by longitude only
            query(tree,
                    new NumericRange(5, 13, 14)
            );

            // (3) query by exact latitude and longitude of Zehlendorf
            query(tree,
                    new NumericRange(4, 52.7833333),
                    new NumericRange(5, 13.3833333)
            );
        }
    }

    private static void query(KDTree tree, QueryRange... ranges) {
        List<Record> result = tree.search(ranges);

        System.out.print("\nQuery: ");
        for (QueryRange range : ranges) {
            System.out.print(range.toString() + " ");
        }
        System.out.println();

        System.out.println("Record count: " + result.size());
        for (Record record : result) {
            System.out.println(record.toString());
        }
        validateResult(result, ranges);
    }

    /**
     * Tests if some records in the result set violates the range.
     *
     * @param records
     * @param ranges
     */
    private static void validateResult(List<Record> records, QueryRange... ranges) {
        List<Record> invalidRecords = new ArrayList<Record>();
        for (Record record : records) {
            for (QueryRange range : ranges) {
                if (record.get(range.AttributeIdx).getValue().compareTo(range.From) < 0 ||
                        record.get(range.AttributeIdx).getValue().compareTo(range.To) > 0) {
                    invalidRecords.add(record);
                }
            }
        }

        if (!invalidRecords.isEmpty()) {
            System.err.println("\nInvalid count: " + invalidRecords.size());
            for (Record record : invalidRecords) {
                System.err.println("Invalid: " + record.toString());
            }
        }
    }
}
