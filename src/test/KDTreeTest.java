package test;

import main.io.*;
import main.kd.Dimension;
import main.kd.KDTree;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

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
                    new Dimension(4, DataValue.ValueType.NUMERIC),
                    new Dimension(5, DataValue.ValueType.NUMERIC)
            );

            tree.toString();

        }


    }
}
