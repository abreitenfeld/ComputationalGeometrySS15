package main.common;


public class Point {

    private double _x;
    private double _y;

    public Point(double x, double y) {
        this._x = x;
        this._y = y;
    }

    public static Point UndefinedPoint() {
        return new Point(Double.NaN, Double.NaN);
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

    public double dotProduct(Point b) {
        return b._x * this._x + b._y * this._y;
    }

    public Point add(Point b) {
        return new Point(this.getX() + b.getX(), this.getY() + b.getY());
    }

    public Point subtract(Point b) {
        return new Point(this.getX() - b.getX(), this.getY() - b.getY());
    }

    public double magnitude() {
        return Math.sqrt(this._x * this._x + this._y * this._y);
    }

    public Point normalize() {
        double magnitude = this.magnitude();
        return new Point(this._x / magnitude, this._y / magnitude);
    }

    public Point rotate90Left() {
        return new Point(
                -this._y,
                this._x
        );
    }

    public Point rotate90Right() {
        return new Point(
                this._y,
                -this._x
        );
    }

    public Point rotate(double deg) {
        return rotateRad(Math.toRadians(deg));
    }

    private Point rotateRad(double rad) {
        return new Point(
                Math.cos(rad) * this._x - Math.sin(rad) * this._y,
                Math.sin(rad) * this._x + Math.cos(rad) * this._y
        );
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
