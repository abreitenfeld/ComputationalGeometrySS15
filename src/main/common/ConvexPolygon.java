package main.common;

import java.awt.*;
import java.util.*;

public class ConvexPolygon extends Polygon {

    protected boolean _drawAntipodalPairs = true;

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
                if (i != k) {
                    OrientedLine line = new OrientedLine(vertexNormals[i].multiply(-1).rotate90CCW(), 0);
                    double distA = line.distance(normals[k]);
                    double distB = line.distance(normals[(k + 1) % getPointsCount()]);
                    if (distA >= 0 && distB <= 0) {
                        antipodalPoints[i][0] = _points[i];
                        antipodalPoints[i][1] = _points[(k + 1) % getPointsCount()];
                    }
                }
            }
        }
        return antipodalPoints;
    }

    public double getDiameter(){
        double diameter = 0;
        Point[][] antipodalPairs = getAntipodalPairs();
        for (int i=0;i<getPointsCount();i++){
            double dist = Point.distance(antipodalPairs[i][0],antipodalPairs[i][1]);
            if (dist > diameter){diameter = dist;}
        }
        return diameter;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        if (this._drawAntipodalPairs) {
            Point[][] pairs = getAntipodalPairs();
            g.setColor(Color.gray);
            for (int r = 0; r < pairs.length; r++) {
                this.drawLine(g, pairs[r][0], pairs[r][1]);
            }
        }
    }
}
