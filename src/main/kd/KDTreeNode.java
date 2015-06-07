package main.kd;


import main.io.DataSet;
import main.io.DataValue;
import main.io.Record;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KDTreeNode {

    protected final KDTreeNode _parent;
    protected final DataSet _data;
    protected final KDTreeNode[] _childNodes = new KDTreeNode[3];
    protected final Dimension[] _dimensions;

    protected DataValue _splitValue;
    protected Dimension _splitDimension;

    /**
     * Constructor of KDTreeNode
     *
     * @param parent
     * @param data
     */
    public KDTreeNode(KDTreeNode parent, DataSet data, Dimension... dimensions) {
        this._parent = parent;
        this._data = data;
        this._dimensions = dimensions;
    }

    /**
     * Returns true if the node is leaf.
     *
     * @return
     */
    public boolean isLeaf() {
        return this.getLowerThanNode() == null &&
                this.getEqualNode() == null &&
                this.getGreaterThanNode() == null;
    }

    public List<Record> getData() {
        return this._data;
    }

    public Dimension getSplitDimension() {
        return this._splitDimension;
    }

    public DataValue getSplitValue() {
        return this._splitValue;
    }

    public KDTreeNode[] getChildNodes() {
        return this._childNodes;
    }

    public KDTreeNode getLowerThanNode() {
        return this._childNodes[0];
    }

    public KDTreeNode getEqualNode() {
        return this._childNodes[1];
    }

    public KDTreeNode getGreaterThanNode() {
        return this._childNodes[2];
    }

    /**
     * Returns the subsequent dimension from the split dimension.
     *
     * @return
     */
    protected Dimension getSubsequentDimension() {
        for (int i = 0; i < this._dimensions.length; i++) {
            if (this._dimensions[i].AttributeIdx == this._splitDimension.AttributeIdx) {
                return this._dimensions[(i + 1) % this._dimensions.length];
            }
        }
        return null;
    }

    /**
     * Returns a dimension set reduced by the split dimension.
     *
     * @return
     */
    protected Dimension[] getReducedDimensionSet() {
        List<Dimension> reducedDimensions = new ArrayList<Dimension>(this._dimensions.length - 1);
        for (Dimension dimension : this._dimensions) {
            if (dimension.AttributeIdx != this._splitDimension.AttributeIdx) {
                reducedDimensions.add(dimension);
            }
        }
        return reducedDimensions.toArray(new Dimension[reducedDimensions.size()]);
    }

    /**
     * Returns the median of a given dimension.
     *
     * @param dimension
     * @return DataValue containg median
     */
    protected DataValue getMedian(Dimension dimension) {
        // sort records by dimension
        this._data.sortByRank(dimension.AttributeIdx);
        if (this._data.size() % 2 == 0) {
            // return upper median
            return this._data.get((int) Math.ceil(this._data.size() / 2)).get(dimension.AttributeIdx);
        } else {
            return this._data.get(this._data.size() / 2).get(dimension.AttributeIdx);
        }
    }

    /**
     * Splits a node by the given dimension into lower, equal and greater than median.
     * @param dimension
     */
    public void split(Dimension dimension) {
        this._splitDimension = dimension;
        // only split if there are more than one dimension
        if (this._dimensions.length > 0 && this.getData().size() > 1) {
            // get median of dimension
            this._splitValue = this.getMedian(dimension);
            System.out.println("Split: " + this.toString());

            List<Record> ltSet = new LinkedList<Record>();
            List<Record> eqSet = new LinkedList<Record>();
            List<Record> gtSet = new LinkedList<Record>();

            // iterate over data set
            for (Record record : this._data) {
                if (record.get(dimension.AttributeIdx).getType() == dimension.Type) {
                    switch (record.get(dimension.AttributeIdx).getValue().compareTo(this._splitValue.getValue())) {
                        case -1:
                            ltSet.add(record);
                            break;
                        case 0:
                            eqSet.add(record);
                            break;
                        case 1:
                            gtSet.add(record);
                            break;
                    }
                }
            }

            // create child nodes
            if (!ltSet.isEmpty()) this._childNodes[0] = new KDTreeNode(this, new DataSet(ltSet), this._dimensions);
            if (!eqSet.isEmpty())
                this._childNodes[1] = new KDTreeNode(this, new DataSet(eqSet), this.getReducedDimensionSet());
            if (!gtSet.isEmpty()) this._childNodes[2] = new KDTreeNode(this, new DataSet(gtSet), this._dimensions);

            // split child nodes by subsequent dimension
            Dimension nextDim = this.getSubsequentDimension();
            for (KDTreeNode node : this._childNodes) {
                if (node != null) {
                    node.split(nextDim);
                }
            }
        } else if (!this.getData().isEmpty()) {
            this._splitValue = this._data.get(0).get(dimension.AttributeIdx);
        }
    }

    @Override
    public String toString() {
        if (this._splitDimension != null) {
            return "KDTreeNode{" +
                    "SplitDimension=" + _splitDimension +
                    ", SplitValue=" + _splitValue.getValue() +
                    ", Data=" + _data.size() +
                    '}';
        } else {
            return "KDTreeNode{" +
                    "Data=" + _data.size() +
                    '}';
        }
    }
}
