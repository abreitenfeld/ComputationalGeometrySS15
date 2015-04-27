package main.common;


public class OrientedLine {

    private Point _n;
    private double _c;

    /**
     * Constructs a line from vector n and scalar c directly.
     *
     * @param n Normal vector n
     * @param c Scalar c
     */
    public OrientedLine(Point n, double c) {
        this._n = n;
        this._c = c;
    }

    /**
     * Constructs a line from a given segment.
     *
     * @param s Segement
     */
    public OrientedLine(Segment s) {
        this(s.getP(), s.getQ());
    }

    /**
     * Constructs a line form two points.
     *
     * @param p Start point
     * @param q End point
     */
    public OrientedLine(Point p, Point q) {
        this._n = q.subtract(p).rotate90CCW().normalize();
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
     * Calculates two representative points of the line.
     *
     * @return An array of two points
     */
    private Point[] getTwoPoints() {
        Point a, b;

        if (this._n.getY() != 0) {
            a = new Point(0, -this._c / this._n.getY());
            b = a.add(this._n.rotate90CW());
        } else {
            a = new Point(-this._c / this._n.getX(), 0);
            b = a.add(this._n.rotate90CW());
        }

        return new Point[]{a, b};
    }

    /**
     * Intersection of two lines given by four points.
     * (http://en.wikipedia.org/wiki/Line–line_intersection#Given_two_points_on_each_line)
     *
     * @param lineOne
     * @param lineTwo
     * @return Returns a valid point if an intersection exists. Otherwise returns an undefined point.
     */
    public static Point intersectionFromPoints(Point[] lineOne, Point[] lineTwo) {
        double x_1 = lineOne[0].getX(), y_1 = lineOne[0].getY();
        double x_2 = lineOne[1].getX(), y_2 = lineOne[1].getY();
        double x_3 = lineTwo[0].getX(), y_3 = lineTwo[0].getY();
        double x_4 = lineTwo[1].getX(), y_4 = lineTwo[1].getY();

        double divisor = (x_1 - x_2) * (y_3 - y_4) - (y_1 - y_2) * (x_3 - x_4);
        if (divisor != 0) {
            double a = ((x_1 * y_2 - y_1 * x_2) * (x_3 - x_4) - (x_1 - x_2) * (x_3 * y_4 - y_3 * x_4)) / divisor;
            double b = ((x_1 * y_2 - y_1 * x_2) * (y_3 - y_4) - (y_1 - y_2) * (x_3 * y_4 - y_3 * x_4)) / divisor;
            return new Point(a, b);
        } else {
            // case lines are parallel
            return Point.UndefinedPoint();
        }
    }

    /**
     * Calculates the intersection to a given line
     *
     * @param s The line to intersect with
     * @return The intersection point if it exists. Otherwise an undefined point.
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
            return intersectionFromPoints(this.getTwoPoints(), s.getTwoPoints());
        }
    }

    /**
     * Returns the distance with sign of a point from the line.
     *
     * @param p
     * @return Interpretation when distance has positive sign point lies in positive half space. Otherwise in negative half space.
     */
    public double distance(Point p) {
        return this._n.dotProduct(p) + this._c;
    }

    @Override
    public String toString() {
        return "OrientedLine{" +
                "n=" + _n +
                ", c=" + _c +
                '}';
    }
}
