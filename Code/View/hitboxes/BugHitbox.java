package View.hitboxes;

import Model.Bridge.GameBoard;
import Model.Bug.BiteBlocked;
import Model.Bug.Bug;
import View.Coordinate;
import View.drawables.Drawable;
import View.drawables.DrawableTexture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class BugHitbox extends Hitbox{
    Bug bug;
    Point centerPoint;

    public BugHitbox(Bug bug, Point centerPoint, String bugTexture) {
        this.bug = bug;
        this.centerPoint = centerPoint;
    }


    /**
     * Checks if the object was clicked
     * @param point Point of the cursor
     * @return The logical value if it was hit or not
     */
    public boolean isHit(Point point){
        if(point.x>=centerPoint.x-8&&point.x<=centerPoint.x+8&&point.y>=centerPoint.y-8&&point.y<=centerPoint.y+8)
            return true;
        return false;
    }

    public void onStrategyChanged(){

    }

    public void onPositionChanged(){

    }


}
