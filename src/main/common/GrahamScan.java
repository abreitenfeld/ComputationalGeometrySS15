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
        // step 1: sort by slope
        points = sortByPolarCoordinates(points);
        if (points.length > 2) {
            // step 2 compute convex hull:
            Stack<Point> stack = new Stack<Point>();
            // initialize stack
            stack.push(points[0]);
            stack.push(points[1]);

            for (int i = 2; i < points.length; i++) {
                OrientedLine line = new OrientedLine(stack.get(stack.size() - 2), stack.get(stack.size() - 1));
                double distance = line.distance(points[i]);
                if (distance == 0) {
                    stack.pop();
                } else if (distance < 0) {
                    stack.pop();
                    int popCount = 0;
                    // find point to pop
                    for (int k = stack.size() - 1; k >= 0; k--) {
                        if (stack.size() > 1) {
                            line = new OrientedLine(stack.get(stack.size() - 2), stack.get(stack.size() - 1));
                            distance = line.distance(points[i]);
                            if (distance <= 0) {
                                stack.pop();
                            } else {
                                break;
                            }
                        }
                    }

                }
                // push current point
                stack.push(points[i]);
            }

            points = new Point[stack.size()];
            for (int i = 0; i < stack.size(); i++) {
                points[i] = stack.get(i);
            }
        }

        return points;
    }

    private static Point[] sortByPolarCoordinates(Point[] points) {
        final Point center = calculateCenter(points);

        // sort points clockwise
        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                a = a.subtract(center);
                b = b.subtract(center);
                int compareResult = 0;
                // steps 1: sort by quadrant
                compareResult = Integer.compare(getQuadrant(a), getQuadrant(b));

                // steps 2: sort by y/x
                if (compareResult == 0) {
                    for (int i = 0; i < getQuadrant(a) - 1; i++) {
                        a = a.rotate90CW();
                        b = b.rotate90CW();
                    }
                    if (a.getX() != 0 && b.getX() != 0) {
                        compareResult = Double.compare(a.getY() / a.getX(), b.getY() / b.getX());
                    } else if (a.getX() == 0 && b.getX() != 0) {
                        compareResult = 1;
                    } else if (a.getX() != 0 && b.getX() == 0) {
                        compareResult = -1;
                    }
                }

                // set 3: square distance to origin
                if (compareResult == 0) {
                    // compare by distance to origin
                    compareResult = Double.compare(a.sqrMagnitude(), b.sqrMagnitude());
                }

                return compareResult;
            }
        });

        return points;
    }

    private static int getQuadrant(Point point) {
        if (point.getX() > 0) {
            if (point.getY() >= 0) {
                return 1;
            } else {
                return 4;
            }
        } else {
            if (point.getY() > 0) {
                return 2;
            } else {
                return 3;
            }
        }
    }

}
