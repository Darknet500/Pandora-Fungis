package View.drawables;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawableTexture implements Drawable {

    /**
     * Position of the textures middle point
     */
    private Point Position;
    /**
     * Textures image file
     */
    private BufferedImage image;

    /**
     * WIDTH and HEIGHT of the texture (Because every texture is a square image)
     */
    private int Width;

    /**
     * Constructor of the drawableTexture object
     * @param x The x position of the textures middle point
     * @param y The y position of the textures middle point
     * @param image Textures image file
     */
    public DrawableTexture(int x, int y, BufferedImage image) {
        this.Position = new Point(x, y);
        this.image = image;
        this.Width = image.getWidth();
    }
    /**
     * Constructor of the drawableTexture object
     * @param pos Middle position of the texture
     * @param image Textures image file
     */
    public DrawableTexture(Point pos, BufferedImage image, int width) {
        this.Position = pos;
        this.image = image;
        this.Width = width;
    }

    /**
     * Frissíti az image-t egy új képre
     */
    public void refreshImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Constructor of the drawableTexture object
     * @param x The x position of the textures middle point
     * @param y The y position of the textures middle point
     * @param imageFile Textures image file
     */
    public DrawableTexture(int x, int y, File imageFile) {
        this.Position = new Point(x, y);
        try {
            this.image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Constructor of the drawableTexture object
     * @param pos Middle position of the texture
     * @param imageFile Textures image file
     */
    public DrawableTexture(Point pos, File imageFile) {
        this.Position = pos;
        try {
            this.image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the texture to a window
     * @param target The target window where to draw
     */
    @Override
    public void draw(BufferedImage target) {
        Graphics2D g2d = target.createGraphics();

        /** Getting the left top corners coordinates to draw **/
        int drawX = Position.x - Width / 2;
        int drawY = Position.y - Width / 2;

        g2d.drawImage(image, drawX, drawY,Width,Width, null);
        g2d.dispose();
    }

    /**
     * Gets the middle position of the texture
     * @return Position of the texture
     */
    @Override
    public Point getPosition() {
        return Position;
    }

    public void setPosition(Point position) {
        this.Position = position;
    }

    /**
     * Gets the width of the texture
     * @return width of the texture
     */
    public int getWidth() {
        return Width;
    }
    public void setWidth(int width) {
        Width = width;
    }

    public BufferedImage getImage() {
        return this.image;
    }

}
