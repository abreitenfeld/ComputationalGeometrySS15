package main.common;

public interface Handle {

    /**
     * Returns the handles of the geometric object.
     *
     * @return Array with points.
     */
    Point[] getHandles();

    void handleMoved(Point handle);
}
