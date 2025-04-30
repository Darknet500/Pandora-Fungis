package View.drawables;

import View.Coordinate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawableTexture implements Drawable {
    private Coordinate topLeft;
    private Coordinate bottomRight;
    private BufferedImage image;

    public DrawableTexture(Coordinate topLeft, Coordinate bottomRight, BufferedImage image) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.image = image;
    }

    public DrawableTexture(Coordinate topLeft, Coordinate bottomRight, File imageFile) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        try {
            this.image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(BufferedImage target) {
        Graphics2D g2d = target.createGraphics();
        g2d.drawImage(image, (int)topLeft.getX(), (int)topLeft.getY(), (int)(bottomRight.getX() - topLeft.getX()), (int)(bottomRight.getY() - topLeft.getY()),
                null);
        g2d.dispose();
    }

    @Override public Coordinate getP1() {
        return topLeft; }

    @Override public Coordinate getP2() {
        return bottomRight; }

}
