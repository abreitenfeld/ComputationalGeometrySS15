package main.common;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class GrahamScan extends Polygon {


    public GrahamScan(Point[] points) {
        super(computeGrahamScan(points));

        this._drawNormals = false;
    }

    private static Point[] computeGrahamScan(Point[] points) {
        // step 1: sort points
        Arrays.sort(points, Point.Y_ORDER);
        Arrays.sort(points, 1, points.length, points[0].POLAR_ORDER);

        if (points.length > 2) {
            // step 2 compute convex hull:
            Stack<Point> stack = new Stack<Point>();
            // initialize stack
            stack.push(points[0]);
            stack.push(points[1]);

            for (int i = 2; i < points.length; i++) {
                Point last = stack.pop();

                while (Point.ccw(stack.peek(), last, points[i]) <= 0) {
                    last = stack.pop();
                }

                stack.push(last);
                // push current point
                stack.push(points[i]);
            }
            points = stack.toArray(new Point[stack.size()]);
        }

        return points;
    }

}
