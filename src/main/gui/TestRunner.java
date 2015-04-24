package main.gui;


import main.common.OrientedLine;
import main.common.Point;

public class TestRunner {

    public static void main(String[] args) {
        TestA();
    }

    private static void TestA() {
        Point p = new Point(0, 10);
        Point q = new Point(1, 10);
        OrientedLine line = new OrientedLine(p, q);
        System.out.println(line.toString());
        System.out.println(line.getN().magnitude());
    }

}
