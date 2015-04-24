package main.common;


public class OrientedLine {

    private Point _n;
    private double _c;

    public OrientedLine(Point n, double c) {
        this._n = n;
        this._c = c;
    }

    public OrientedLine(Segment s) {
        this(s.getP(), s.getQ());
    }

    public OrientedLine(Point p, Point q) {
        this._n = q.subtract(p).rotate90Left().normalize();
        this._c = -p.dotProduct(this._n);
    }

    public Point getN() {
        return this._n;
    }

    public void setN(Point n) {
        this._n = n.normalize();
    }

    public double getC() {
        return this._c;
    }

    public void setC(double c) {
        this._c = c;
    }

    /**
     * @param s
     * @return
     */
    public Point intersection(OrientedLine s) {
        if (this._n.equals(s.getN())) {
            return new Point(Double.NaN, Double.NaN);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "OrientedLine{" +
                "n=" + _n +
                ", c=" + _c +
                '}';
    }
}
