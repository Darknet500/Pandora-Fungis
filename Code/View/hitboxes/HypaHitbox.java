    package View.hitboxes;

    import Model.Shroomer.Hypa;
    import View.drawables.Drawable;
    import View.drawables.DrawableLine;

    import javax.imageio.ImageIO;
    import java.awt.*;
    import java.awt.image.BufferedImage;
    import java.io.File;
    import java.io.IOException;

    public class HypaHitbox extends Hitbox {
        private Hypa hypa;
        private Point end1;
        private Point end2;
        private Color color;


        public HypaHitbox(Hypa hypa, Color mushroomtype) {
            this.hypa = hypa;
            this.end1 = hypa.getEnd1().getHitbox().getCenterPoint();
            this.end2 = hypa.getEnd2().getHitbox().getCenterPoint();
            this.color = mushroomtype;

            hypa.addObserver(this);

            drawable = new DrawableLine(end1, end2, color);
        }

        public boolean isHit(Point point) {
            return point.distance(end1) + point.distance(end2) <= end1.distance(end2) + 5;
        }


        public void onHypaChanged(int number) {
            if(number==1) ((DrawableLine)drawable).changecolorA(127);
            else ((DrawableLine)drawable).changecolorA(255);
        }


    }
