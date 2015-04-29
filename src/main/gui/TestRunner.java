package main.gui;

import main.common.*;

public class TestRunner {

    private static Point ptA, ptB;
    private static OrientedLine lineA, lineB, lineC, lineD;
    private static Segment segmentA, segmentB, segmentC;
    private static Polygon polygonA;

    public static void main(String[] args) {
        setUp();
        /*TestA();
        TestB();
        TestC();
        TestD();*/

        TestUI();
    }

    private static void setUp() {
        ptA = new Point(0, 10);
        ptB = new Point(1, 10);

        lineA = new OrientedLine(new Point(0, 0), new Point(1, 1));
        lineB = new OrientedLine(new Point(0, 0), new Point(0, 1));
        lineC = new OrientedLine(new Point(0, 1), new Point(1, 0));
        lineD = new OrientedLine(new Point(-1, 0), new Point(0, 1));

        segmentA = new Segment(new Point(-2, 0), new Point(2, 4));
        segmentB = new Segment(new Point(-2, 4), new Point(2, 0));
        segmentC = new Segment(new Point(0, 5), new Point(0, 10));

        polygonA = new ConvexPolygon(new Point[]{
                new Point(0, 50), new Point(-40, 5), new Point(60, 6),
                new Point(-30, -40), new Point(20, -30)
        });
    }

    /**
     * Test exercise (a)
     */
    private static void TestA() {
        System.out.println("\nRun tests for exercise (a)");
        OrientedLine line = new OrientedLine(ptA, ptB);
        System.out.println(line.toString());
    }

    /**
     * Test exercise (b)
     */
    private static void TestB() {
        System.out.println("\nRun tests for exercise (b)");
        // case 1: there is an intersection
        Point intersectionC = lineA.intersection(lineC);
        System.out.println(intersectionC.toString());

        // case 1: there is no intersection
        Point intersectionD = lineA.intersection(lineD);
        System.out.println(intersectionD.toString());
    }

    /**
     * Test exercise (c)
     */
    private static void TestC() {
        System.out.println("\nRun tests for exercise (c)");
        // case 1: point lies left
        double distanceA = lineB.distance(new Point(-1, 1));
        System.out.println((distanceA > 0) + " (" + distanceA + ")");
        // case 2: point lies right
        double distanceB = lineB.distance(new Point(1, 1));
        System.out.println((distanceB < 0) + " (" + distanceB + ")");
    }

    /**
     * Test exercise (d)
     */
    private static void TestD() {
        System.out.println("\nRun tests for exercise (d)");
        // case 1: there is an intersection
        Point intersectionB = segmentA.intersection(segmentB);
        System.out.println(intersectionB.toString());
        // case 2: there is no intersection
        Point intersectionC = segmentA.intersection(segmentC);
        System.out.println(intersectionC.toString());
    }

    private static void TestUI() {
        CanvasPanel panel = CanvasPanel.createFrame();

        panel.addDrawable(polygonA);
        /*panel.addDrawable(new Point(10, 10));
        panel.addDrawable(new Point(0, -10));
        panel.addDrawable(segmentA);
        panel.addDrawable(segmentB);
        panel.addDrawable(segmentC);*/

        //System.out.println(Point.angle(new Point(0, 10), new Point(-5, 10)));
        //System.out.println(Point.angle(new Point(-5, 10), new Point(0, 10)));

        //System.out.println(new Point(0, 10).normalize().dotProduct(new Point(-5, 10).normalize()));
        //System.out.println(new Point(-5, 10).normalize().dotProduct(new Point(0, 10).normalize()));

    }

}
