package View.drawables;

import View.Coordinate;

import java.awt.image.BufferedImage;

public interface Drawable {
        void draw(BufferedImage target);

        Coordinate getP1();

        Coordinate getP2();

    }
