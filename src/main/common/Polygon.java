package main.common;


import java.awt.*;

public abstract class Polygon implements Drawable, Handle {

    protected final Point[] _points;
    protected final Segment[] _segments;
    private boolean _drawNormals = true;

    private static final Color DEFAULT_COLOR = new Color(135, 206, 250, 100);
    private static final float NORMAL_LENGTH = 15;

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

    public Point getCenter() {
        return calculateCenter(this._points);
    }

    public int getPointsCount(){
        return this._points.length;
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

    @Override
    public Point[] getHandles() {
        return this._points;
    }

    /**
     * Calculates the interpolated normals on the vertices.
     *
     * @return Array with points
     */
    public Point[] getVertexNormals() {
        Point[] normals = new Point[this._segments.length];
        for (int i = 0; i < this._segments.length; i++) {
            Point normalA = this._segments[(this._segments.length + (i - 1)) % this._segments.length].getNormal();
            Point normalB = this._segments[i].getNormal();
            normals[i] = normalA.add(normalB).multiply(0.5).normalize();
        }
        return normals;
    }

    public Point[] getNormals() {
        Point[] normals = new Point[this._segments.length];
        for (int i = 0; i < this._segments.length; i++) {
            normals[i] = this._segments[i].getNormal();
        }
        return normals;
    }

    @Override
    public void draw(Graphics2D g) {
        int[] xPoints = new int[this._points.length], yPoints = new int[this._points.length];

        if (this._drawNormals) {
            // draw vertex normals
            g.setColor(Color.blue);
            Point[] vertexNormals = this.getVertexNormals();
            for (int i = 0; i < this.getPointsCount(); i++) {
                this.drawLine(g, this._points[i], this._points[i].subtract(vertexNormals[i].multiply(NORMAL_LENGTH)));
            }

            // draw normals
            g.setColor(Color.red);
            Point[] normals = this.getNormals();
            for (int i = 0; i < this.getPointsCount(); i++) {
                Point segmentMid = this._segments[i].getP().add(this._segments[i].getQ().subtract(this._segments[i].getP()).multiply(0.5));
                this.drawLine(g, segmentMid, segmentMid.subtract(normals[i].multiply(NORMAL_LENGTH)));
            }
        }

        for (int i = 0; i < this._points.length; i++) {
            xPoints[i] = (int) this._points[i].getX();
            yPoints[i] = (int) this._points[i].getY();
        }

        g.setColor(DEFAULT_COLOR);
        g.fillPolygon(xPoints, yPoints, this._points.length);
        g.setColor(Color.black);
        g.drawPolygon(xPoints, yPoints, this._points.length);

        for (Point point : this._points) {
            point.draw(g);
        }
    }

    @Override
    public void drawCaption(Graphics2D g) {

    }

    protected void drawLine(Graphics2D g, Point p, Point q) {
        g.drawLine((int) p.getX(), (int) p.getY(), (int) q.getX(), (int) q.getY());
    }


}
