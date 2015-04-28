package main.common;


import java.awt.*;

public abstract class Polygon implements Drawable {

    protected final Point[] _points;
    protected final Segment[] _segments;

    private static final Color DEFAULT_COLOR = new Color(135, 206, 250, 100);

    /**
     * Base constructor
     */
    protected Polygon(Point[] points) {
        this._points = points;
        this._segments = new Segment[this._points.length];

        // create segments from points
        for (int i = 0; i < this._points.length; i++) {
            this._segments[i] = new Segment(this._points[i], this._points[(i + 1) % this._points.length]);
        }
    }

    /**
     * Calculates the center of scatter plot.
     *
     * @param points
     * @return Center point
     */
    protected static Point calculateCenter(Point[] points) {
        Point center = Point.Zero();
        for (Point pt : points) {
            center = center.add(pt);
        }
        return center.divide(points.length);
    }

    /**
     * Calculates the interpolated normals on the vertices.
     *
     * @return Array with points
     */
    public Point[] getNormals() {
        Point[] normals = new Point[this._segments.length];
        for (int i = 0; i < this._segments.length; i++) {
            Point normalA = this._segments[(i - 1) % this._segments.length].getNormal();
            Point normalB = this._segments[i].getNormal();
            normals[i] = normalA.add(normalB).divide(2).normalize();
        }
        return normals;
    }

    @Override
    public void draw(Graphics2D g) {
        int[] xPoints = new int[this._points.length], yPoints = new int[this._points.length];

        for (int i = 0; i < this._points.length; i++) {
            xPoints[i] = (int) this._points[i].getX();
            yPoints[i] = (int) this._points[i].getY();
        }

        g.setColor(DEFAULT_COLOR);
        g.fillPolygon(xPoints, yPoints, this._points.length);
        g.setColor(Color.black);
        g.drawPolygon(xPoints, yPoints, this._points.length);
    }

}
