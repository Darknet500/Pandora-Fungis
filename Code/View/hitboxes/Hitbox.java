package View.hitboxes;

import View.Coordinate;
import View.drawables.Drawable;

public abstract class Hitbox {
    protected Drawable drawable;
    protected Coordinate position;

    public Drawable getDrawable() {
        return drawable;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void updatePosition(Coordinate newPosition) {
        this.position = newPosition;
        if (drawable != null) {
            updateDrawablePosition();
        }
    }

    protected abstract void updateDrawablePosition();

    public abstract boolean isHit(double x, double y);

}

