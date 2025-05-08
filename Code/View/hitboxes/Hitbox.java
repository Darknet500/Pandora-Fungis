package View.hitboxes;

import View.drawables.Drawable;
import java.awt.*;

public abstract class Hitbox {

    /**
     * The graphical Drawable object of the Hitbox
     */
    protected Drawable drawable;

    /**
     *  Returns the drawable object of the Hitbox
     * @return Drawable object
     */
    public Drawable getDrawable() {
        return drawable;
    }


    /**
     * Checks if the object was clicked
     * @param point Point of the cursor
     * @return The logical value if it was hit or not
     */
    public abstract boolean isHit(Point point);



}

