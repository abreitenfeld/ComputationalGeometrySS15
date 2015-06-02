package test;

import main.io.CSVParser;
import main.io.DataSet;
import main.kd.KDTree;

import java.io.*;

public class KDTreeTest {

    public static void main(String[] args) throws Exception {
        // open file reader
        String fileNameDefined = "orte_deutschland.txt";
        File file = new File(fileNameDefined);

        // parse input data
        final Reader reader = new FileReader(file);
        final CSVParser parser = new CSVParser(reader, "\t");
        final DataSet data = new DataSet(parser.getRecords());
        // close parser
        parser.close();

        // instantiate KD-Tree
        final KDTree tree = new KDTree(data);


    }
}
