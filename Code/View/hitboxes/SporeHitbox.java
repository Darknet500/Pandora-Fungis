package View.hitboxes;

import Model.Shroomer.Spore;
import View.drawables.Drawable;
import View.drawables.DrawableRect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SporeHitbox extends Hitbox {
    private Point centerPoint;
    private Spore spore;
    private Color color;

    public SporeHitbox(Point centerPoint, Spore spore, Color color) {
        this.centerPoint = centerPoint;
        this.spore = spore;
        this.color = color;

        spore.addObserver(this);
        drawable = new DrawableRect(centerPoint, 15, color);

    }
    @Override
    public boolean isHit(Point point) {
        int dx = point.x - centerPoint.x;
        int dy = point.y - centerPoint.y;

        int squaredDistance = dx * dx + dy * dy;

        return squaredDistance <= 9;
    }


}