package main.gui;

import main.common.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;

public class CanvasPanel extends JPanel implements ActionListener, MouseWheelListener, MouseMotionListener {

    private final List<Drawable> _drawables = new LinkedList<Drawable>();

    private double _scale = 3;

    private static final double MIN_SCALE = 1;
    private static final double MAX_SCALE = 10;

    public CanvasPanel() {
        this.setOpaque(true);
        this.setBackground(Color.white);

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

    public CanvasPanel removeDrawable(Drawable drawable) {
        this._drawables.remove(drawable);
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this._scale = Math.min(Math.max(this._scale + e.getWheelRotation() * 0.1f, MIN_SCALE), MAX_SCALE);
        this.repaint();
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
        g2d.scale(this._scale, -this._scale);

        // draw center
        g.setColor(Color.lightGray);
        g.drawLine(-halfWidth, 0, halfWidth, 0);
        g.drawLine(0, -halfHeight, 0, halfHeight);

        // draw objects
        for (Drawable drawable : this._drawables) {
            drawable.draw(g2d);
        }
    }

}
