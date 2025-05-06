package View.hitboxes;
import Model.Tekton.*;
import View.drawables.DrawableTexture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TektonHitbox extends Hitbox{
    TektonBase tekton;
    Point centerPoint;
    /**
     * TektonHitbox konstruktor
     * @param
     *
     */
    public TektonHitbox(Point centerPoint, TektonBase tekton, String tektonType ) {
        this.centerPoint = centerPoint;
        this.tekton = tekton;
        BufferedImage image = null;
        try {
                    image=ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/Tektons/"+tektonType+".png")));

        }catch (IOException e){
            e.printStackTrace();
        }

        //drawable=new DrawableTexture(centerPoint, image);
    }

    /**
     *
     */
    public boolean isHit(Point point){
        if(point.x>=centerPoint.x-16&&point.x<=centerPoint.x+16&&point.y>=centerPoint.y-16&&point.y<=centerPoint.y+16)
            return true;
        return false;
    }




}
