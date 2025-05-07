package View.hitboxes;

import Model.Bug.Bug;
import View.drawables.DrawableTexture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class BugHitbox extends Hitbox{
    Bug bug;
    Point centerPoint;

    public BugHitbox(Bug bug, Point centerPoint, String bugTexture) {
        this.bug = bug;
        this.centerPoint = centerPoint;
        bug.addObserver(this);

        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/BUGS/"+ image.getColorModel().toString() +"Normal.png"))); // itt nem tudom hogy kellene lekérdezni a színét :D

        }catch (IOException e){
            e.printStackTrace();
        }

        drawable=new DrawableTexture(centerPoint, image);
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

    public void onStrategyChanged() {
        String strategyName = bug.getStrategy().toString();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource(
                    "/Assets/BUGS/" + image.getColorModel().toString() + strategyName + ".png"
            )));
        } catch (IOException e) {
            e.printStackTrace();
        }

        drawable = new DrawableTexture(centerPoint, image);
    }

    public void onPositionChanged() {
        String strategyName = bug.getStrategy().toString();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource(
                    "/Assets/BUGS/" + image.getColorModel().toString() + strategyName + ".png"
            )));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (drawable != null) {
           // drawable = new DrawableTexture(bug.getLocation()., image); // Hogy lehet lekérdezni egy tekton pozícióját? Tektonnak nincs referenciája a hitboxára
        }
    }
}
