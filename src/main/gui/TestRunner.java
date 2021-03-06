package main.gui;

import main.common.*;
import java.util.Random;

public class TestRunner {

    private static Point ptA, ptB;
    private static OrientedLine lineA, lineB, lineC, lineD;
    private static Segment segmentA, segmentB, segmentC;

    public static void main(String[] args) {
        setUp();
        //TestA();
        //TestB();
        //TestC();
        //TestD();

        //TestUI();
        TestGrahamScan();
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

        Polygon polygonA = new ConvexPolygon(new Point[]{
                new Point(0, 50),
                new Point(-40, 5),
                new Point(60, 6),
                new Point(-30, -40),
                new Point(20, -30),
                new Point(100, 80)
        });

        panel.addDrawable(polygonA);
        panel.repaint();
    }

    private static void TestGrahamScan() {
        final double spread = 500;
        CanvasPanel panel = CanvasPanel.createFrame();

        Point[] randomPoints = new Point[100];
        Random rnd = new Random();
        for (int i = 0; i < randomPoints.length; i++) {
            randomPoints[i] = new Point(rnd.nextDouble(), rnd.nextDouble()).multiply(spread).subtract(Point.One().multiply(spread * 0.5f));
        }

        GrahamScan polygonGraham = new GrahamScan(randomPoints);
        panel.addDrawable(polygonGraham);
        panel.repaint();
    }

}
