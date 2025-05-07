package View;

import Model.Bridge.GameBoard;
import Model.Tekton.TektonBase;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawingSurface extends JPanel {
    private BufferedImage canvas;
    private BufferedImage bg = null;
    private GameBoard gameBoard;

    public DrawingSurface(int width, int height, GameBoard gameboard) {
        super();
        this.gameBoard = gameboard;
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        setPreferredSize(new Dimension(width, height));
        try {
            bg = ImageIO.read(new File(System.getProperty("user.dir"), "\\Assets\\BACKGROUND.jpg"));
        } catch (IOException e) {
            System.out.println("ERROR: cannot load gameboard background image");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = canvas.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        if(bg!=null){
            g2d.drawImage(bg, 0, 0, canvas.getWidth(), canvas.getHeight(),null);
        }
        for(TektonBase t: gameBoard.getTektons()){
            //get tekton hitboxes, draw their drawables,
            //get bugs on the tektons, get their hitboxes, get the drawables from the hitbox, draw them
            //get mushrooms ...
            //get spores ...
            //get Hypas ...
        }
        g2d.dispose();
        g.drawImage(canvas, 0, 0, null);

    }
}
