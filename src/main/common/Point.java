package main.common;


import java.awt.*;
import java.util.Comparator;

public class Point implements Drawable {

    private double _x;
    private double _y;

    public static final int CIRCLE_DIAMETER = 7;

    /**
     * Compares two points by y-coordinate.
     */
    public static final Comparator<Point> Y_ORDER = new YOrder();

    /**
     * Compares two points by polar angle (between 0 and 2pi) with respect to this point.
     */
    public final Comparator<Point> POLAR_ORDER = new PolarOrder();


    /**
     * Constructor of a point.
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Point(double x, double y) {
        this._x = x;
        this._y = y;
    }

    /**
     * Undefined point representation.
     * @return A point with NaN as coordinates
     */
    public static Point Undefined() {
        return new Point(Double.NaN, Double.NaN);
    }

    public static Point Zero() {
        return new Point(0, 0);
    }

    public static Point One() {
        return new Point(1, 1);
    }

    public static Point Up() {
        return new Point(0, 1);
    }

    public static Point Right() {
        return new Point(1, 0);
    }

    /**
     * Is a->b->c a counterclockwise turn?
     *
     * @param a first point
     * @param b second point
     * @param c third point
     * @return { -1, 0, +1 } if a->b->c is a { clockwise, collinear; counterclocwise } turn.
     */
    public static int ccw(Point a, Point b, Point c) {
        double area2 = (b._x - a._x) * (c._y - a._y) - (b._y - a._y) * (c._x - a._x);
        return Double.compare(area2, 0);
    }

    public double getY() {
        return this._y;
    }

    public void setY(double y) {
        this._y = y;
    }

    public double getX() {
        return this._x;
    }

    public void setX(double x) {
        this._x = x;
    }

    /**
     * Dot product operator for two points.
     *
     * @param b
     * @return
     */
    public double dotProduct(Point b) {
        return b._x * this._x + b._y * this._y;
    }

    /**
     * Add operator for two points.
     *
     * @param b
     * @return
     */
    public Point add(Point b) {
        return new Point(this._x + b._x, this._y + b._y);
    }

    /**
     * Substract operator for two points.
     *
     * @param b
     * @return
     */
    public Point subtract(Point b) {
        return new Point(this._x - b._x, this._y - b._y);
    }

    /**
     * Multiplies the point with a given scalar.
     *
     * @param scalar Multiplication scalar
     * @return
     */
    public Point multiply(double scalar) {
        return new Point(this._x * scalar, this._y * scalar);
    }

    /**
     * Multiplies the point with a given point.
     *
     * @param b Second point
     * @return
     */
    public Point multiply(Point b) {
        return new Point(this._x * b._x, this._y * b._y);
    }

    /**
     * Divides the point with a given scalar.
     *
     * @param scalar Division scalar
     * @return
     */
    public Point divide(double scalar) {
        return new Point(this._x / scalar, this._y / scalar);
    }

    /**
     * Divides the point with a given point.
     *
     * @param b Second point
     * @return
     */
    public Point divide(Point b) {
        return new Point(this._x / b._x, this._y / b._y);
    }

    /**
     * Calculates the length of the point.
     *
     * @return
     */
    public double magnitude() {
        return Math.sqrt(this._x * this._x + this._y * this._y);
    }

    /**
     * Calculates the length of the point.
     *
     * @return
     */
    public double sqrMagnitude() {
        return this._x * this._x + this._y * this._y;
    }

    /**
     * Normalizes the point.
     *
     * @return
     */
    public Point normalize() {
        double magnitude = this.magnitude();
        return new Point(this._x / magnitude, this._y / magnitude);
    }

    /**
     * Rotate the point 90 degrees counter-clockwise.
     *
     * @return
     */
    public Point rotate90CCW() {
        return new Point(
                -this._y,
                this._x
        );
    }

    /**
     * Rotate the point 90 degrees clockwise.
     *
     * @return
     */
    public Point rotate90CW() {
        return new Point(
                this._y,
                -this._x
        );
    }

    /**
     * Rotate the point in plane from given degrees.
     *
     * @param deg
     * @return
     */
    public Point rotate(double deg) {
        return rotateRad(Math.toRadians(deg));
    }

    /**
     * Rotate the point in plane from given radian.
     *
     * @param rad
     * @return
     */
    private Point rotateRad(double rad) {
        return new Point(
                Math.cos(rad) * this._x - Math.sin(rad) * this._y,
                Math.sin(rad) * this._x + Math.cos(rad) * this._y
        );
    }

    public static double angle(Point a, Point b) {
        double cos = a.dotProduct(b) / (a.magnitude() * b.magnitude());
        return Math.toDegrees(Math.acos(cos));
    }

    public static double distance(Point a, Point b) {
        return b.subtract(a).magnitude();
    }

    public static double sqrDistance(Point a, Point b) {
        return b.subtract(a).sqrMagnitude();
    }

    @Override
    public void draw(Graphics2D g) {
        this.draw(g, CIRCLE_DIAMETER, Color.blue);
    }

    public void draw(Graphics2D g, int diameter, Color dotColor) {
        final int outlineDiameter = diameter + 2;
        final int insertDiameter = diameter;
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.darkGray);
        //g.fillOval((int)(this._x - outlineDiameter * 0.5f), (int)(this._y - outlineDiameter * 0.5f), outlineDiameter, outlineDiameter);
        g.setColor(dotColor);
        g.fillOval((int) (this._x - insertDiameter * 0.5f), (int) (this._y - insertDiameter * 0.5f), insertDiameter, insertDiameter);
    }

    @Override
    public void drawCaption(Graphics2D g) {

    }

    // compare points according to their y-coordinate
    private static class YOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            return Double.compare(a.getY(), b.getY());
        }
    }

    // compare other points relative to polar angle (between 0 and 2pi) they make with this Point
    private class PolarOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            // calculate slope
            double dx1 = a._x - Point.this._x;
            double dy1 = a._y - Point.this._y;
            double dx2 = b._x - Point.this._x;
            double dy2 = b._y - Point.this._y;

            if (dy1 >= 0 && dy2 < 0) {
                // q1 above; q2 below
                return -1;
            } else if (dy2 >= 0 && dy1 < 0) {
                // q1 below; q2 above
                return +1;
            } else if (dy1 == 0 && dy2 == 0) {
                // 3-collinear and horizontal
                if (dx1 >= 0 && dx2 < 0) {
                    return -1;
                } else if (dx2 >= 0 && dx1 < 0) {
                    return +1;
                } else {
                    return 0;
                }
            } else {
                // both above or below
                return -ccw(Point.this, a, b);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        Point b = (Point) obj;
        return b._x == this._x && b._y == this._y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + _x +
                ", y=" + _y +
                '}';
    }
}
