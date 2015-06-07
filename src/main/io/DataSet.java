package main.io;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class to store records.
 */
public class DataSet extends ArrayList<Record> {

    protected class RankComparator implements Comparator<Record> {

        protected final int _idx;

        public RankComparator(int idx) {
            this._idx = idx;
        }

        @Override
        public int compare(Record recordA, Record recordB) {
            return recordA.get(this._idx).getValue().compareTo(recordB.get(this._idx).getValue());
        }
    }

    /**
     * Constructor of data set.
     *
     * @param records
     */
    public DataSet(List<Record> records) {
        super(records);
    }

    /**
     * Sorts the dat set by rank.
     * @param idx
     */
    public void sortByRank(int idx) {
        Collections.sort(this, new RankComparator(idx));
    }

}
