package View.drawables;

import View.Coordinate;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class DrawableLine implements Drawable {

    /**
     * The Line which will be drawed
     */
    private Line2D.Double line;

    /**
     * First end of the Line
     */
    private Point End1;

    /**
     * Second end of the Line
     */
    private Point End2;

    /**
     * Color of the Line
     */
    private Color Color;
    /**
     * Constructor of the drawableRect object
     * @param x1 The first x position of the Line
     * @param y1 The first y position of the Line
     * @param x2 The second x position of the Line
     * @param y2 The second y position of the Line
     * @param color Color of the Line
     */
    public DrawableLine(int x1, int y1, int x2, int y2, Color color) {
        this.End1 = new Point(x1, y1);
        this.End2 = new Point(x2, y2);
        this.Color = color;
        this.line = new Line2D.Double(x1, y1, x2, y2);

    }
    /**
     * Constructor of the drawableRect object
     * @param End1 The first position of the Line
     * @param End2 The Second position of the Line
     * @param color Color of the Line
     */
    public DrawableLine(Point End1, Point End2, Color color) {
        this.End1 = End1;
        this.End2 = End2;
        this.Color = color;
        this.line = new Line2D.Double(End1, End2);

    }

    @Override
    public void draw(BufferedImage target) {
        Graphics2D g2d = target.createGraphics();
        g2d.setColor(Color);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(line);
        g2d.dispose();
    }

    /**
     * Gets the middle position of the Line
     * Its kinda useless but we use Middle POinTs
     * @return Middle Position of the Line
     */
    @Override
    public Point getPosition() {
        return new Point((End1.x + End2.x)/2, (End1.y + End2.y)/2);
    }

    /**
     * Gets the first end point of the line
     * @return first end point of line
     */
    public Point getEnd1() {
        return End1;
    }

    /**
     * Gets the second end point of the line
     * @return second end point of line
     */
    public Point getEnd2() {
        return End2;
    }


}
