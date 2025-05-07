package View.hitboxes;
import Model.Shroomer.*;
import View.drawables.DrawableTexture;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MushroomHitbox extends Hitbox{
    Mushroom mushroom;
    Point centerPoint;
    String mushroomType;
    public MushroomHitbox(Mushroom mushroom,Point centerPoint, String mushroomType) {
        this.mushroom = mushroom;
        this.centerPoint = centerPoint;
        this.mushroomType = mushroomType;

        mushroom.addObserver(this);

        BufferedImage image = null;
        try {
            image=ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/Mushrooms/young"+mushroomType+"/5Y.png")));

        }catch (IOException e){
            e.printStackTrace();
        }

       drawable=new DrawableTexture(centerPoint, image);


    }
    public boolean isHit(Point point){
        if(point.x>=centerPoint.x-8&&point.x<=centerPoint.x+8&&point.y>=centerPoint.y-8&&point.y<=centerPoint.y+8)
            return true;
        return false;
    }

    /**
     * a hozzá tartozó Mushroom objektum hívja le, ha változik a spóra szórási képessége
     */
    public void onSporeThrowableChanged(){
        boolean isyoung = mushroom.getAge()<=4;
        int spores = 5-mushroom.getSporesThrown();
        boolean abletothrow = mushroom.getNumberOfSpores()==1;
        BufferedImage image = null;
        try {
            image=ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/Mushrooms/"+(isyoung?"young":"old")+mushroomType+"/"+spores+(abletothrow?"Y":"N")+".png")));

        }catch (IOException e){
            e.printStackTrace();
        }

        drawable=new DrawableTexture(centerPoint, image);
    }

    /**
     * a hozzá tartozó Mushroom objektum hívja le, ha dobott spórát
     */
    public void onSporeThrown(){
        boolean isyoung = mushroom.getAge()<=4;
        int spores = 5-mushroom.getSporesThrown();
        boolean abletothrow = mushroom.getNumberOfSpores()==1;
        BufferedImage image = null;
        try {
            image=ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/Mushrooms/"+(isyoung?"young":"old")+mushroomType+"/"+spores+(abletothrow?"Y":"N")+".png")));

        }catch (IOException e){
            e.printStackTrace();
        }

        drawable=new DrawableTexture(centerPoint, image);
    }

    /**
     * a hozzá tartozó Mushroom objektum hívja le, ha öreggé változott
     */
    public void onBecameOld(){
        boolean isyoung = mushroom.getAge()<=4;
        int spores = 5-mushroom.getSporesThrown();
        boolean abletothrow = mushroom.getNumberOfSpores()==1;
        BufferedImage image = null;
        try {
            image=ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/Mushrooms/"+(isyoung?"young":"old")+mushroomType+"/"+spores+(abletothrow?"Y":"N")+".png")));

        }catch (IOException e){
            e.printStackTrace();
        }

        drawable=new DrawableTexture(centerPoint, image);
    }

}
