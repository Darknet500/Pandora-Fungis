package View.hitboxes;

import Model.Shroomer.Spore;
import View.drawables.DrawableRect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SporeHitbox extends Hitbox {
    private Point centerPoint;
    private Spore spore;
    private String sporeType;

    public SporeHitbox(Point centerPoint, Spore spore, String sporeType) {
        this.centerPoint = centerPoint;
        this.spore = spore;
        this.sporeType = sporeType;

        spore.addObserver(this);

        switch (sporeType) {
            case "boosterspore":
                drawable = new DrawableRect(centerPoint.x + 20, centerPoint.y, 3, new Color(2,43,226));
                break;
            case "slowerspore":
                drawable = new DrawableRect(centerPoint.x + 20, centerPoint.y, 3, new Color(250,163,0));
                break;
            case "proliferatingspore":
                drawable = new DrawableRect(centerPoint.x + 20, centerPoint.y, 3, new Color(255,45,198));
                break;
            case "biteblockerspore":
                drawable = new DrawableRect(centerPoint.x + 20, centerPoint.y, 3, new Color(240,232,82));
                break;
            case "paralyzerspore":
                drawable = new DrawableRect(centerPoint.x + 20, centerPoint.y, 3, new Color(93,215,82));
                break;
            default:
                throw new IllegalArgumentException("ERROR: unknown spore type: " + sporeType);

        }
    }
    @Override
    public boolean isHit(Point point) {
        int dx = point.x - centerPoint.x;
        int dy = point.y - centerPoint.y;

        int squaredDistance = dx * dx + dy * dy;

        return squaredDistance <= 9;
    }
}