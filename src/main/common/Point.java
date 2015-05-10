package main.common;


import java.awt.*;

public class Point implements Drawable {

    private double _x;
    private double _y;

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
        final int outlineDiameter = 6;
        final int insertDiameter = 4;
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.BLACK);
        //g.fillOval((int)(this._x - outlineDiameter * 0.5f), (int)(this._y - outlineDiameter * 0.5f), outlineDiameter, outlineDiameter);
        g.setColor(Color.BLUE);
        g.fillOval((int) (this._x - insertDiameter * 0.5f), (int) (this._y - insertDiameter * 0.5f), insertDiameter, insertDiameter);
    }

    @Override
    public void drawCaption(Graphics2D g) {

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
