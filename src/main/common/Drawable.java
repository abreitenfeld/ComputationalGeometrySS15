package main.common;

import java.awt.*;

public interface Drawable {

    /**
     * Draws the shape of the geometric object
     *
     * @param g Graphics object
     */
    void draw(Graphics2D g);

    /**
     * Draw the caption of the geometric object.
     *
     * @param g Graphics object
     */
    void drawCaption(Graphics2D g);

}
