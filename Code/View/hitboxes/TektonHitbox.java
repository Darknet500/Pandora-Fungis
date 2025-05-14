package View.hitboxes;
import Model.Shroomer.Hypa;
import Model.Shroomer.Spore;
import Model.Tekton.*;
import View.drawables.DrawableTexture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.Random;

public class TektonHitbox extends Hitbox{
    private TektonBase tekton;
    private Point centerPoint;
    private int width;
    private double weight;
    /**
     * TektonHitbox konstruktor
     * @param
     */
    public TektonHitbox(Point centerPoint, TektonBase tekton, String tektonType, int width) {
        this.centerPoint = centerPoint;
        this.tekton = tekton;
        this.width = width;

        Random rand = new Random();
        weight = 25*(3+Math.max(-3,Math.min(3,rand.nextGaussian())));
        //weight = 300;


        tekton.addObserver(this);
        BufferedImage image = null;

        try {
            image=ImageIO.read(new File(System.getProperty("user.dir"), "\\Assets\\Tektons\\"+tektonType+".png"));

        }catch (IOException e){
            e.printStackTrace();
        }
        if (image == null) {
            throw new IllegalArgumentException("Image could not be loaded for type: " + tektonType);
        }

        drawable=new DrawableTexture(centerPoint, image, width);
    }


    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     *
     */
    @Override
    public boolean isHit(Point point){
        if(point.distance(centerPoint)<=width*0.5){
            return true;
        }
        return false;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public void setWidth(int width){
        this.width = width;
    }
    public int getWidth(){
        return width;
    }

    public void refreshCenterPoint(Point newCenterPoint){

        Point movedVector = new Point(newCenterPoint.x-centerPoint.x,newCenterPoint.y-centerPoint.y );
        centerPoint = newCenterPoint;
        ((DrawableTexture)drawable).setPosition(centerPoint);
        if(tekton.hasMushroom())
            tekton.getMushroom().getHitbox().onPositionChanged();
        if(tekton.getBug() != null)
            tekton.getBug().getHitbox().onPositionChanged();
        for(Spore s: tekton.getStoredSpores()){
            s.getHitbox().onPositionChanged(movedVector);
        }
        for(Hypa h: tekton.getHypas()){
            h.getHitbox().onPositionChanged();
        }
    }


}
