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

    protected List<Record> getData() {
        return this._data;
    }

    protected KDTreeNode getLowerThanNode() {
        return this._childNodes[0];
    }

    protected KDTreeNode getEqualNode() {
        return this._childNodes[1];
    }

    protected KDTreeNode getGreaterThanNode() {
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
     * Returns a dimenion set reduced by the split dimension.
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
        // sort records by dimension - not the best solution!
        this._data.sortByRank(dimension.AttributeIdx);
        if (this._data.size() % 2 == 0) {
            return this._data.get((int) Math.ceil(this._data.size() / 2)).get(dimension.AttributeIdx);
        } else {
            return this._data.get(this._data.size() / 2).get(dimension.AttributeIdx);
        }
    }

    public void split(Dimension dimension) {
        this._splitDimension = dimension;
        // only split if there are more than one dimension
        if (this._dimensions.length > 1 && this.getData().size() > 1) {
            List<Record> ltSet = new LinkedList<Record>();
            List<Record> eqSet = new LinkedList<Record>();
            List<Record> gtSet = new LinkedList<Record>();

            // get median of dimension
            final DataValue median = this.getMedian(dimension);
            // iterate over data set
            for (Record record : this._data) {
                if (record.get(dimension.AttributeIdx).getType() == dimension.Type) {
                    switch (record.get(dimension.AttributeIdx).getValue().compareTo(median.getValue())) {
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
            this._childNodes[0] = new KDTreeNode(this, new DataSet(ltSet), this._dimensions);
            this._childNodes[1] = new KDTreeNode(this, new DataSet(eqSet), this.getReducedDimensionSet());
            this._childNodes[2] = new KDTreeNode(this, new DataSet(gtSet), this._dimensions);

            // split child nodes by subsequent dimension
            Dimension nextDim = this.getSubsequentDimension();
            for (KDTreeNode node : this._childNodes) {
                if (node != null) {
                    node.split(nextDim);
                }
            }
        }
    }

    @Override
    public String toString() {
        if (this._splitDimension != null) {
            return "KDTreeNode{" +
                    "SplitDimension=" + _splitDimension +
                    ", Data=" + _data.size() +
                    '}';
        } else {
            return "KDTreeNode{" +
                    "Data=" + _data.size() +
                    '}';
        }
    }
}
