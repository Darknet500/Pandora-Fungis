package View.Hitbox;

import Model.Shroomer.Spore;
import View.Drawable.DrawableRect;

import java.awt.*;

public class SporeHitbox extends Hitbox {
    private Point centerPoint;
    private Spore spore;
    private Color color;

    public SporeHitbox(Point centerPoint, Spore spore, Color color) {
        this.centerPoint = centerPoint;
        this.spore = spore;
        this.color = color;

        spore.addObserver(this);
        drawable = new DrawableRect(centerPoint, 10, color);

    }

    public Point getCenterPoint() {
        return centerPoint;
    }
    @Override
    public boolean isHit(Point point) {
        int dx = point.x - centerPoint.x;
        int dy = point.y - centerPoint.y;
        return (-5<dx&&dx<5&&-5<dy&&dy<5);
    }

    public void onPositionChanged(Point movedVec){
        centerPoint = new Point(centerPoint.x+movedVec.x, centerPoint.y+movedVec.y);
        ((DrawableRect)drawable).refreshPosition(centerPoint);
    }


}