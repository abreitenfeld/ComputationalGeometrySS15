package main.common;


public class Segment {

    private Point _p;
    private Point _q;

    public Segment(Point p, Point q) {
        this._p = p;
        this._q = q;
    }

    public Point getP() {
        return this._p;
    }

    public void setP(Point p) {
        this._p = p;
    }

    public Point getQ() {
        return this._q;
    }

    public void setQ(Point q) {
        this._q = q;
    }

    public double length() {
        return this.getP().subtract(this.getQ()).magnitude();
    }



}
