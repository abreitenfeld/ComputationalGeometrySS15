package main.common;


public class Segment {

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

    /**
     * Calculates the intersection of two segments.
     *
     * @param s
     * @return
     */
    public Point intersection(Segment s) {
        Point intersection = OrientedLine.intersectionFromPoints(
                new Point[]{this._p, this._q},
                new Point[]{s._p, s._q}
        );

        if (!intersection.equals(Point.UndefinedPoint()) && this.boundsContainsPoint(intersection)) {
            return intersection;
        } else {
            return Point.UndefinedPoint();
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


}
