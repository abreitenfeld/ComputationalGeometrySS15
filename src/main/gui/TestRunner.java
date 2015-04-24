package main.gui;


import main.common.OrientedLine;
import main.common.Point;

public class TestRunner {

    public static void main(String[] args) {
        TestA();
        TestB();
        TestC();
        TestD();
    }

    private static void TestA() {
        Point p = new Point(0, 10);
        Point q = new Point(1, 10);
        OrientedLine line = new OrientedLine(p, q);
        System.out.println(line.toString());
        System.out.println(line.getN().magnitude());
    }

    private static void TestB() {
        Point p = new Point(0, 10);
        Point q = new Point(1, 10);
        OrientedLine line = new OrientedLine(p, q);
        System.out.println(line.toString());
        System.out.println(line.getN().magnitude());
    }

    private static void TestC() {
        Point p = new Point(0, 10);
        Point q = new Point(1, 10);
        OrientedLine line = new OrientedLine(p, q);
        System.out.println(line.toString());
        System.out.println(line.getN().magnitude());
    }

    private static void TestD() {
        Point p = new Point(0, 10);
        Point q = new Point(1, 10);
        OrientedLine line = new OrientedLine(p, q);
        System.out.println(line.toString());
        System.out.println(line.getN().magnitude());
    }


}
