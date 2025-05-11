package View;

import Model.Bridge.GameBoard;
import Model.Bug.Bugger;
import Model.Shroomer.Shroomer;
import Model.Shroomer.Spore;
import Model.Tekton.Tekton;
import Model.Tekton.TektonBase;
import View.drawables.DrawableLine;
import View.hitboxes.TektonHitbox;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
        //  TODO: draw everything




        if(gameBoard.getTektons().size()>0){
            for (TektonBase tektoni: gameBoard.getTektons()) {
                for (TektonBase tektonj: gameBoard.getTektons()) {
                    if(tektoni.isNeighbour(tektonj)){

                        DrawableLine line = new DrawableLine(((TektonHitbox)gameBoard.getObjectHitbox(tektoni)).getCenterPoint(),((TektonHitbox)gameBoard.getObjectHitbox(tektonj)).getCenterPoint(),Color.black);
                        line.draw(canvas);
                    }
                }
            }


            for(TektonBase tekton:gameBoard.getTektons()){
                gameBoard.getObjectHitbox(tekton).getDrawable().draw(canvas);
                if (!tekton.getHypas().isEmpty()){
                    for (int i = 0; i < tekton.getHypas().size(); i++) {
                        tekton.getHypas().get(i).getHitbox().getDrawable().draw(canvas);
                    }
                }
                if(!tekton.getStoredSpores().isEmpty()){
                    for (int i = 0; i < tekton.getStoredSpores().size(); i++) {
                        tekton.getStoredSpores().get(i).getHitbox().getDrawable().draw(canvas);
                    }
                }
            }
        }





        for (Bugger b : gameBoard.getBuggers().values()){
            for (int i = 0; i < b.getBugs().size(); i++){
                b.getBugs().get(i).getHitbox().getDrawable().draw(canvas);
            }
        }

        for (Shroomer s: gameBoard.getShroomers().values()){
            for (int i = 0; i < s.getMushrooms().size(); i++){
                s.getMushrooms().get(i).getHitbox().getDrawable().draw(canvas);
            }
        }



        g2d.dispose();
        g.drawImage(canvas, 0, 0, null);
    }
}
