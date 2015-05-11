package main.gui;

import main.common.*;
import main.common.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CanvasPanel extends JPanel implements MouseListener, MouseWheelListener, MouseMotionListener {

    private final List<Drawable> _drawables = new LinkedList<Drawable>();

    private boolean _drawCaption = true;
    private double _scale = 1.5;
    private Point _selectedHandle = null;
    private Handle _handleOwner = null;

    private static final double MIN_SCALE = 1;
    private static final double MAX_SCALE = 10;

    /**
     * Constructor of the panel.
     */
    public CanvasPanel() {
        this.setOpaque(true);
        this.setBackground(Color.white);

        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        this.addMouseMotionListener(this);
    }

    public static CanvasPanel createFrame() {
        //Create and set up the window.
        JFrame frame = new JFrame("Canvas Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        final CanvasPanel panel = new CanvasPanel();

        frame.getContentPane().add(panel);
        //Display the window.
        frame.setVisible(true);
        return panel;
    }

    public List<Drawable> getDrawables() {
        return this._drawables;
    }

    public CanvasPanel addDrawable(Drawable drawable) {
        this._drawables.add(drawable);
        return this;
    }

    public CanvasPanel addDrawable(Drawable[] drawable) {
        this._drawables.addAll(Arrays.asList(drawable));
        return this;
    }

    public CanvasPanel removeDrawable(Drawable drawable) {
        this._drawables.remove(drawable);
        return this;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Point mousePos = this.transformMousePosition(e.getX(), e.getY());
            for (Drawable drawable : this._drawables) {
                if (drawable instanceof Handle) {
                    Point[] handles = ((Handle) drawable).getHandles();
                    for (Point handle : handles) {
                        if (Point.distance(handle, mousePos) <= Point.CIRCLE_DIAMETER) {
                            this._selectedHandle = handle;
                            this._handleOwner = (Handle) drawable;
                            this.repaint();
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this._selectedHandle != null) {
            Point mousePos = this.transformMousePosition(e.getX(), e.getY());
            this._selectedHandle.setX(mousePos.getX());
            this._selectedHandle.setY(mousePos.getY());
            this._handleOwner.handleMoved(this._selectedHandle);
            this.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this._selectedHandle = null;
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this._scale = Math.min(Math.max(this._scale + e.getWheelRotation() * 0.1f, MIN_SCALE), MAX_SCALE);
        this.repaint();
    }

    /**
     * Transform the screen position to coordinate space.
     *
     * @param x
     * @param y
     * @return
     */
    protected Point transformMousePosition(int x, int y) {
        x = Math.max(Math.min(x, this.getWidth()), 0);
        y = Math.max(Math.min(y, this.getHeight()), 0);
        x -= (int) (this.getWidth() * 0.5f);
        y -= (int) (this.getHeight() * 0.5f);
        //y *= -1;
        return new Point(x, y).divide(this._scale);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // translate origin
        int halfWidth = (int) (this.getWidth() * 0.5f);
        int halfHeight = (int) (this.getHeight() * 0.5f);
        g2d.translate(halfWidth, halfHeight);

        // scale transform
        g2d.scale(this._scale, this._scale);

        // draw center
        g.setColor(Color.lightGray);
        g.drawLine(-halfWidth, 0, halfWidth, 0);
        g.drawLine(0, -halfHeight, 0, halfHeight);

        // draw objects
        for (Drawable drawable : this._drawables) {
            drawable.draw(g2d);
        }

        // draw caption
        if (this._drawCaption) {
            for (Drawable drawable : this._drawables) {
                drawable.drawCaption(g2d);
            }
        }

        // draw selected handle
        if (this._selectedHandle != null) {
            this._selectedHandle.draw(g2d, Point.CIRCLE_DIAMETER * 2, Color.red);
        }
    }

}
