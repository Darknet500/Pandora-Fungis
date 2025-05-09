package View.hitboxes;

import Model.Bridge.GameBoard;
import Model.Bug.Bug;
import View.drawables.DrawableTexture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class BugHitbox extends Hitbox{
    private Bug bug;
    private Point centerPoint;
    private String bugColor;
    private int width;

    public BugHitbox(Bug bug, Point centerPoint, Color Color, int width) {
        this.bug = bug;
        this.centerPoint = centerPoint;
        this.bugColor = setColor(Color);
        this.width = width;


        bug.addObserver(this);

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir"),
                    "\\Assets\\BUGS\\"+ bugColor + "Normal.png"));

        }catch (IOException e){
            e.printStackTrace();
        }

        drawable = new DrawableTexture(centerPoint, image, width);
    }

    private String setColor(Color color){
        if(color == Color.BLUE){
            return "blue";
        }
        else if(color == Color.RED){
            return "red";
        }
        else if(color == Color.GREEN){
            return "green";
        }
        else if(color == Color.MAGENTA){
            return "purple";
        }
        return "A kurva anyád";
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

    public void onStrategyChanged(String strategy) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource(
                    "/Assets/BUGS/" + bugColor + strategy +".png"
            )));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((DrawableTexture)drawable).refreshImage(image);
    }


    public void onPositionChanged() {
        if (drawable != null) {
            centerPoint = new Point(bug.getLocation().getHitbox().getCenterPoint().x+(int)(width*0.5555), bug.getLocation().getHitbox().getCenterPoint().y);

            ((DrawableTexture)drawable).setPosition(centerPoint);
        }
    }
}
