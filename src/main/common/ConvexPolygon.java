package main.common;

import java.util.*;

public class ConvexPolygon {

    private final Point[] _points;
    private final Segment[] _segments;

    /**
     * Constructs a polygon from a list of points.
     *
     * @param points
     */
    public ConvexPolygon(Point[] points) {
        this._points = sortVerticesClockwise(points);
        this._segments = new Segment[this._points.length];

        // create segments from points
        for (int i = 0; i < this._points.length; i++) {
            this._segments[i] = new Segment(this._points[i], this._points[(i + 1) % this._points.length]);
        }
    }

    /**
     * Sorts the given array of points clockwise
     * (http://en.wikipedia.org/wiki/Atan2)
     *
     * @param points
     * @return Sorted array
     */
    private static Point[] sortVerticesClockwise(Point[] points) {
        final Point center = calculateCenter(points);

        // sort points clockwise
        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                double atanA = Math.atan2(a.getY() - center.getY(), a.getX() - center.getX());
                double atanB = Math.atan2(b.getY() - center.getY(), b.getX() - center.getX());
                return Double.compare(atanA, atanB);
            }
        });

        return points;
    }

    /**
     * Calculates the center of scatter plot.
     *
     * @param points
     * @return Center point
     */
    private static Point calculateCenter(Point[] points) {
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


}
