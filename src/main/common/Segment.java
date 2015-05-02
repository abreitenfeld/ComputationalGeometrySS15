package main.common;


import java.awt.*;

public class Segment implements Drawable {

    private Point _p;
    private Point _q;

    /**
     * Constructor of a seg,ent
     *
     * @param p Start point
     * @param q End point
     */
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

    public Point getNormal() {
        return this._q.subtract(this._p).rotate90CCW().normalize();
    }

    /**
     * Calculates the intersection of two segments.
     *
     * @param s
     * @return Returns a valid point if an intersection exists. Otherwise returns an undefined point.
     */
    public Point intersection(Segment s) {
        Point intersection = OrientedLine.intersectionFromPoints(
                new Point[]{this._p, this._q},
                new Point[]{s._p, s._q}
        );

        if (!intersection.equals(Point.Undefined()) &&
                this.boundsContainsPoint(intersection) &&
                s.boundsContainsPoint(intersection)) {
            return intersection;
        } else {
            return Point.Undefined();
        }

    }

    /**
     * Checks if the given point is inside of the bounding box of segment.
     *
     * @param p Point to check
     * @return True if the point is inside.
     */
    public boolean boundsContainsPoint(Point p) {
        return Math.min(this._p.getX(), this._q.getX()) <= p.getX() &&
                p.getX() <= Math.max(this._p.getX(), this._q.getX()) &&
                Math.min(this._q.getY(), this._p.getY()) <= p.getY() &&
                p.getY() <= Math.max(this._q.getY(), this._p.getY());
    }

    /**
     * Returns the length of the segment
     *
     * @return The length of the segment
     */
    public double length() {
        return this.getP().subtract(this.getQ()).magnitude();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.black);
        g.drawLine((int) this._p.getX(), (int) this._p.getY(), (int) this._q.getX(), (int) this._q.getY());
    }

    @Override
    public void drawCaption(Graphics2D g) {

    }
}
