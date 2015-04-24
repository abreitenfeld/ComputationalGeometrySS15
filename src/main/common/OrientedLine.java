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

    private Point[] getTwoPoints() {
        Point a, b;

        if (this._n.getY() != 0) {
            a = new Point(0, -this._c / this._n.getY());
            b = a.add(this._n.rotate90Right());
        } else {
            a = new Point(0, 0);
            b = new Point(0, 1);
        }

        return new Point[]{a, b};
    }

    /**
     * http://en.wikipedia.org/wiki/Line–line_intersection#Given_two_points_on_each_line
     * @param s
     * @return
     */
    public Point intersection(OrientedLine s) {
        double dot = this._n.dotProduct(s.getN());
        if (dot == 1 || dot == -1) {
            if (this._c == s.getC()) {
                return Point.UndefinedPoint();
            } else {
                return Point.UndefinedPoint();
            }
        } else {
            Point[] points = this.getTwoPoints();
            // TODO
            return null;
        }
    }

    /**
     * Returns the distance with sign of point from the line.
     *
     * @param p
     * @return Interpreteation when distance has positive sign point lies in positive half space. Otherwise in negative half space.
     */
    public double distance(Point p) {
        return this._n.dotProduct(p) - this._c;
    }

    @Override
    public String toString() {
        return "OrientedLine{" +
                "n=" + _n +
                ", c=" + _c +
                '}';
    }
}
