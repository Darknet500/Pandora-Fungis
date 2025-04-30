package View.drawables;

import View.Coordinate;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class DrawableRect implements Drawable {
    private Rectangle2D.Double rect;
    private Coordinate p1;
    private Coordinate p2;

    public DrawableRect(Rectangle2D rect) {
        this.rect = new Rectangle2D.Double(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()
        );
        this.p1 = new Coordinate(rect.getX(), rect.getY());
        this.p2 = new Coordinate(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
    }

    @Override
    public void draw(BufferedImage target) {
        Graphics2D g2d = target.createGraphics();
        g2d.draw(rect);
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
