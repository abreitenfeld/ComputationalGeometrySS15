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

    public Point[][] getAntipodalPairs() {
        Point[] vertexNormals = getVertexNormals(); //Array mit den Normalen als Durschnittswert an den Ecken
        Point [][] antipodalPoints = new Point[getPointsCount()][2]; //das Array wo die antipodalen Punkten reinsollen
        Point[] normals = getNormals();
        for (int i=0;i<getPointsCount();i++){
            for (int k=0;k<getPointsCount();k++){
                OrientedLine line = new OrientedLine(vertexNormals[i].multiply(-1).rotate90CCW(),0);
                double distA = line.distance(normals[k]);
                double distB = line.distance(normals[(k+1) % getPointsCount()]);
                if (distA >= 0 && distB <= 0){
                    antipodalPoints[i][0] = _points[i];
                    antipodalPoints[i][1] = _points[(k + 1) % getPointsCount()];
                }
            }
        }
        return antipodalPoints;
    }


}
