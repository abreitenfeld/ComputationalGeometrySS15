package main.kd;


import main.io.DataSet;
import main.io.Record;

import java.util.List;

public class KDTree {

    protected final Dimension[] _dimensions;
    protected final KDTreeNode _root;

    public KDTree(DataSet data, Dimension... dimensions) {
        this._dimensions = dimensions;
        this._root = new KDTreeNode(null, data, dimensions);
        this._root.split(this._dimensions[0]);
    }

    public Dimension[] getDimensions() {
        return this._dimensions;
    }

    public List<Record> search(QueryRange... ranges) {
        return null;
    }
}
