package View.drawables;

import View.Coordinate;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Drawable {

    /**
     * Draws the drawable object to the target window
     * @param target The target window where the object is drawed
     */
    void draw(BufferedImage target);

    /**
     * Gets Position of the Drawable object
     * @return
     */
    Point getPosition();

   /* void addPositionListener(PositionListener listener);
    void removePositionListener(PositionListener listener);

    interface PositionListener {
        void onPositionChanged(Drawable source, Point newPosition);
        void onSizeChanged(Drawable source, int newSize);
    } */
}
