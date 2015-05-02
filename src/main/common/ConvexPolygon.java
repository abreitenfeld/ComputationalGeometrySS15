package main.common;

import java.awt.*;
import java.text.DecimalFormat;
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
                // compare by polar coordinates
                int compareResult = Double.compare(atanA, atanB);

                if (compareResult == 0) {
                    // compare by distance to origin
                    return Double.compare(Point.distance(a, center), Point.distance(b, center));
                }
                return compareResult;
            }
        });

        return points;
    }

    /**
     * Calculates the antipodal pairs.
     *
     * @return
     */
    public Point[][] getAntipodalPairs() {
        Point [][] antipodalPoints = new Point[getPointsCount()][2]; //das Array wo die antipodalen Punkten reinsollen
        Point[] vertexNormals = this.getVertexNormals(); //Array mit den Normalen als Durschnittswert an den Ecken
        Point[] normals = this.getNormals();

        for (int i = 0; i < this.getPointsCount(); i++) {
            OrientedLine line = new OrientedLine(vertexNormals[i].multiply(-1).rotate90CCW(), 0);
            for (int k = 0; k < this.getPointsCount(); k++) {
                if (i != (k + 1) % this.getPointsCount()) {
                    double distA = line.distance(normals[k]);
                    double distB = line.distance(normals[(k + 1) % this.getPointsCount()]);
                    if (distA <= 0 && distB >= 0) {
                        antipodalPoints[i][0] = _points[i];
                        antipodalPoints[i][1] = _points[(k + 1) % this.getPointsCount()];
                    }
                }
            }
        }
        return antipodalPoints;
    }

    /**
     * Calculates the diameter of the polygon.
     * @return
     */
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

    @Override
    public void drawCaption(Graphics2D g) {
        super.drawCaption(g);

        DecimalFormat df = new DecimalFormat("0.0#");
        Point center = this.getCenter();
        double diameter = this.getDiameter();
        g.setColor(Color.black);
        g.drawString(String.valueOf(df.format(diameter)), (int) Math.floor(center.getX()), (int) Math.floor(center.getY()));
    }
}
