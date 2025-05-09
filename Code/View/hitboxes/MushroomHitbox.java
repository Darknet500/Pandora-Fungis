package View.hitboxes;
import Model.Shroomer.*;
import View.drawables.DrawableTexture;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class MushroomHitbox extends Hitbox{
    private Mushroom mushroom;
    private Point centerPoint;
    private String mushroomType;
    private int width;
    public MushroomHitbox(Mushroom mushroom,Point centerPoint, String mushroomType, int width) {
        this.mushroom = mushroom;
        this.centerPoint = centerPoint;
        this.mushroomType = mushroomType;
        this.width = width;

        mushroom.addObserver(this);

        BufferedImage image = null;
        try {
            Path imagePath = Path.of(System.getProperty("user.dir"), "Assets", "Mushrooms", "young"+ mushroomType,"5Y.png");
            //System.out.println(imagePath);
            image=ImageIO.read(imagePath.toFile());
        }catch (IOException e){
            e.printStackTrace();
        }
        if (image == null) {
            throw new IllegalArgumentException("Image could not be loaded");
        }

       drawable=new DrawableTexture(centerPoint, image, width);


    }
    public boolean isHit(Point point){
        if(point.x>=centerPoint.x-8&&point.x<=centerPoint.x+8&&point.y>=centerPoint.y-8&&point.y<=centerPoint.y+8)
            return true;
        return false;
    }

    /**
     * a hozzá tartozó Mushroom objektum hívja le, ha változik a
     *  spóra dobási képessége
     *  kora
     *  spóráinak száma
     */
    public void onTextureChanged(){
        boolean isyoung = mushroom.getAge()<=4;
        int spores = 5-mushroom.getSporesThrown();
        boolean abletothrow = mushroom.getNumberOfSpores()==1;
        BufferedImage image = null;
        try {
            image=ImageIO.read(new File(System.getProperty("user.dir"), "\\Assets\\Mushrooms\\"+(isyoung?"young":"old")+mushroomType+"\\"+spores+(abletothrow?"Y":"N")+".png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        if (image == null) {
            throw new IllegalArgumentException("Image could not be loaded");
        }

        ((DrawableTexture)drawable).refreshImage(image);
    }



}
