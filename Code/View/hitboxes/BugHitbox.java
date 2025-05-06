package View.hitboxes;

import Model.Bridge.GameBoard;
import Model.Bug.Bug;
import View.Coordinate;
import View.drawables.Drawable;
import View.drawables.DrawableTexture;

import java.awt.*;
import java.io.File;


public class BugHitbox extends Hitbox{
    Bug bug;
    Point centerPoint;

    public BugHitbox(Bug bug, Point centerPoint) {
        this.bug = bug;
        this.centerPoint = centerPoint;
        this.drawable = new DrawableTexture(centerPoint, File.createTempFile());
    }


    /**
     * Checks if the object was clicked
     * @param point Point of the cursor
     * @return The logical value if it was hit or not
     */
    public boolean isHit(Point point){
        return false;
    }
}
