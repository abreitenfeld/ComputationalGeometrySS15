package main.common;

import java.util.*;

public class ConvexPolygon extends Polygon {

    /**
     * Constructs a polygon from a list of points.
     *
     * @param points
     */
    public ConvexPolygon(Point[] points) {
        super(sortVerticesClockwise(points));
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


}
