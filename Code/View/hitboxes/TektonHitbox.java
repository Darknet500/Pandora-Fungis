package View.hitboxes;
import Model.Tekton.*;
import View.drawables.DrawableTexture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class TektonHitbox extends Hitbox{
    private TektonBase tekton;
    private Point centerPoint;
    /**
     * TektonHitbox konstruktor
     * @param
     */
    public TektonHitbox(Point centerPoint, TektonBase tekton, String tektonType ) {
        this.centerPoint = centerPoint;
        this.tekton = tekton;
        BufferedImage image = null;

        try {
            image=ImageIO.read(new File(System.getProperty("user.dir"), "\\Assets\\Tektons\\"+tektonType+".png"));

        }catch (IOException e){
            e.printStackTrace();
        }
        if (image == null) {
            throw new IllegalArgumentException("Image could not be loaded for type: " + tektonType);
        }

        drawable=new DrawableTexture(centerPoint, image);
    }

    /**
     *
     */
    @Override
    public boolean isHit(Point point){
        if(point.distance(centerPoint)<=64){
            System.out.println("Tektonhitbox Hit!");
            return true;
        }
        return false;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }



}
