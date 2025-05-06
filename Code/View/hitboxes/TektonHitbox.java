package View.hitboxes;
import Model.Tekton.*;
import View.Coordinate;
import View.drawables.Drawable;
import View.drawables.DrawableLine;
import View.drawables.DrawableTexture;
import java.awt.*;
import java.awt.image.BufferedImage;

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
        BufferedImage image;
        switch (tektonType){
           // case "Tekton" ->

        }


        //drawable = new DrawableTexture(centerPoint, )


    }

    /**
     *
     */
    public boolean isHit(Point point){

        return false;
    }




}
