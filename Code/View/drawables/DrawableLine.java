package View.drawables;

import View.Coordinate;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class DrawableLine implements Drawable {
    private Line2D.Double line;
    private Coordinate p1;
    private Coordinate p2;

    public DrawableLine(Coordinate end1, Coordinate end2) {
        this.p1 = end1;
        this.p2 = end2;
        this.line = new Line2D.Double(end1.getX(), end1.getY(), end2.getX(), end2.getY()
        );
    }

    @Override
    public void draw(BufferedImage target) {
        Graphics2D g2d = target.createGraphics();
        g2d.draw(line);
        g2d.dispose();
    }

    @Override
    public Coordinate getP1() {
        return null;
    }

    @Override
    public Coordinate getP2() {
        return null;
    }
}
