package View.Drawable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Point;

public class DrawableRect implements Drawable {

    /**
     * The rectangle which will be drawed
     */
    private Rectangle2D.Double rect;

    /**
     * Position of the rectangles middle point
     */
    private Point Position;

    /**
     * WIDTH of the rectangle
     */
    private int Width;

    /**
     * Color of the rectangle
     */
    private Color Color;

    /**
     * Constructor of the drawableRect object
     * @param x The x position of the textures middle point
     * @param y The y position of the textures middle point
     * @param width width of the rectangle
     */
    public DrawableRect(int x, int y, int width, Color color) {
        Position = new Point(x, y);
        this.Color = color;
        Width = width;
        rect = new Rectangle2D.Double(x, y, width, width);
    }
    /**
     * Constructor of the drawableRect object
     * @param pos Middle position of the rect
     * @param width width of the rectangle
     */
    public DrawableRect(Point pos, int width, Color color) {
        Position = pos;
        this.Color = color;
        Width = width;
        rect = new Rectangle2D.Double(pos.x, pos.y, width, width);
    }

    /**
     * Draws the rectangle to a window
     * @param target The target window where to draw
     */
    @Override
    public void draw(BufferedImage target) {
        Graphics2D g2d = target.createGraphics();

        /** Getting the left top corners coordinates to draw **/
        int drawX = Position.x - Width / 2;
        int drawY = Position.y - Width / 2;
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(Color.BLACK);
        g2d.draw(rect);
        g2d.setColor(Color);
        g2d.fill(rect);
        g2d.dispose();
    }

    /**
     * Gets the middle position of the rectangle
     * @return Position of the rectangle
     */
    @Override
    public Point getPosition() {
        return Position;
    }


    /**
     * Gets the width of the rectangle
     * @return width of the rectangle
     */
    public int getWidth() {
        return Width;
    }

    public void refreshPosition(Point point){
        Position = point;
        rect.setRect(Position.x , Position.y , Width, Width);

    }
}

