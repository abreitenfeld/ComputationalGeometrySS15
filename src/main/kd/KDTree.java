package main.kd;


import main.io.DataSet;
import main.io.Record;

import java.util.*;

public class KDTree {

    protected final Dimension[] _dimensions;
    protected final KDTreeNode _root;

    public KDTree(DataSet data, Dimension... dimensions) {
        AssertUniqueDimension(dimensions);
        this._dimensions = dimensions;
        this._root = new KDTreeNode(null, data, dimensions);
        this._root.split(this._dimensions[0]);
    }

    /**
     * Assert that there are no duplicate dimension.
     *
     * @param dimensions
     */
    private static void AssertUniqueDimension(Dimension... dimensions) {
        Set<Integer> attributes = new HashSet<Integer>();
        for (Dimension dimension : dimensions) {
            if (!attributes.contains(dimension.AttributeIdx)) {
                attributes.add(dimension.AttributeIdx);
            } else {
                // TODO throw exception
            }
        }
    }

    public Dimension[] getDimensions() {
        return this._dimensions;
    }

    /**
     * Returns the QueryRange for a given dimension.
     *
     * @param dimension
     * @param ranges
     * @return
     */
    private static QueryRange findQueryForDimension(Dimension dimension, QueryRange... ranges) {
        for (QueryRange range : ranges) {
            if (range.AttributeIdx == dimension.AttributeIdx) {
                return range;
            }
        }
        return null;
    }

    /**
     * Searches for records with the provided QueryRanges.
     *
     * @param ranges
     * @return
     */
    public List<Record> search(QueryRange... ranges) {
        final List<Record> resultSet = new LinkedList<Record>();
        final Queue<KDTreeNode> nodes = new LinkedList<KDTreeNode>();
        nodes.add(this._root);

        while (!nodes.isEmpty()) {
            KDTreeNode current = nodes.remove();
            if (current != null) {
                // check if node is lead; if so check data stored in node
                if (current.isLeaf()) {
                    for (Record record : current.getData()) {
                        boolean addRecord = true;
                        // check if the values are within range
                        for (QueryRange range : ranges) {
                            addRecord &= range.valueInRange(record.get(range.AttributeIdx).getValue());
                        }
                        if (addRecord) {
                            // add records result set
                            resultSet.add(record);
                        }
                    }
                } else {
                    // check if there is a query range for dimension of the node
                    QueryRange range = findQueryForDimension(current.getSplitDimension(), ranges);
                    if (range != null) {
                        int compFrom = current.getSplitValue().getValue().compareTo(range.From);
                        int compTo = current.getSplitValue().getValue().compareTo(range.To);

                        // check lowerThan node
                        if (current.getLowerThanNode() != null && compFrom > 0) {
                            nodes.add(current.getLowerThanNode());
                        }
                        // check equal node
                        if (current.getEqualNode() != null && compFrom >= 0 && compTo <= 0) {
                            nodes.add(current.getEqualNode());
                        }
                        // check greaterThan node
                        if (current.getGreaterThanNode() != null && compTo < 0) {
                            nodes.add(current.getGreaterThanNode());
                        }

                    } else {
                        // add all children if no query parameter were provided
                        nodes.addAll(Arrays.asList(current.getChildNodes()));
                    }
                }
            }
        }
        return resultSet;
    }
}
