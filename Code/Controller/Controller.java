package Controller;
import Model.Bridge.*;
import View.*;
import Model.Bug.*;
import Model.Shroomer.*;
import Model.Tekton.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.HashMap;


public class Controller {
    private int actualPlayer = 0;
    private int round;
    private GameBoard gameBoard;
    private IView view;
    private long seed;

    public void connectObjects(IView view, GameBoard gameBoard) {
        this.view = view;
        this.gameBoard = gameBoard;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void resetActualPlayerandRound() {
        actualPlayer = 0;
        round = 0;
    }

    public void initMap(){
        Random rand = new Random();
        double r;
        List<Integer> normalTektonsNumber = new ArrayList<>();
        for (int k = 1; k <= 25; k++) {
            r = rand.nextDouble();
            TektonBase tekton;
            if (r < 0.48) {
                tekton = new Tekton();
                normalTektonsNumber.add(k-1);

            } else if (r < 0.61) {
                tekton = new Peat();
            } else if (r < 0.74) {
                tekton = new Stone();
            } else if (r < 0.87) {
                tekton = new Swamp();
            } else {
                tekton = new Soil();
            }
        }

        gameBoard.getTekton(0).isNeighbour(gameBoard.getTekton(1));
        gameBoard.getTekton(1).isNeighbour(gameBoard.getTekton(0));


        List<Point> triangulationEdges = List.of(
                new Point(6, 15),
                new Point(7, 20),
                new Point(12, 16),
                new Point(4, 12),
                new Point(3, 16),
                new Point(22, 23),
                new Point(12, 22),
                new Point(5, 16),
                //new Point(0, 5),
                new Point(3, 22),
                new Point(9, 11),
                //new Point(8, 21),
                new Point(0, 17),
                new Point(10, 15),
                new Point(11, 23),
                new Point(9, 23),
                new Point(11, 20),
                new Point(10, 24),
                new Point(13, 23),
                new Point(2, 20),
                new Point(13, 20),
                //new Point(6, 14),
                new Point(15, 23),
                new Point(16, 22),
                new Point(6, 17),
                new Point(14, 18),
                new Point(4, 17),
                new Point(3, 21),
                new Point(9, 10),
                new Point(14, 24),
                new Point(5, 21),
                new Point(4, 23),
                //new Point(0, 4),
                new Point(0, 16),
                new Point(10, 14),
                new Point(2, 7),
                new Point(19, 23),
                new Point(8, 20),
                new Point(11, 13),
                new Point(1, 17),
                new Point(1, 23),
                new Point(16, 21),
                new Point(6, 10),
                new Point(15, 19),
                new Point(13, 22),
                new Point(6, 19),
                new Point(7, 18),
                new Point(18, 24),
                new Point(7, 24),
                //new Point(20, 21),
                new Point(3, 8),
                new Point(4, 22),
                //new Point(17, 19),
                new Point(1, 4),
                new Point(7, 11),
                new Point(0, 12),
                new Point(8, 13),
                new Point(9, 15),
                new Point(9, 24),
                new Point(8, 22),
                new Point(2, 18),
                new Point(11, 24),
                new Point(1, 19)
        );

        for (int k = 1; k <= 25; k++) {
            for (int i = 0; i < 25; i++) {
                for (int j = 0; j < 25; j++) {
                    if (i != j) {
                        if(!gameBoard.getTekton(i).isNeighbour(gameBoard.getTekton(j))) {

                            r = rand.nextDouble();
                            //if(true){
                            //if(r < 0.007) {
                            if(triangulationEdges.contains(new Point(i, j))) {
                                gameBoard.getTekton(i).addNeighbour(gameBoard.getTekton(j));
                                gameBoard.getTekton(j).addNeighbour(gameBoard.getTekton(i));
                            }

                        }
                    }
                }
            }
        }



        //játékosok kezdő objektumainak elhelyezése

        for(Shroomer s: gameBoard.getShroomers().values()){
            for (int z=0;z<100;z++) {
                int ir = rand.nextInt(25);
                TektonBase tekton = gameBoard.getTektons().get(ir);
                if(tekton.canMushroomGrow()&&!tekton.hasMushroom()){
                    s.growFirstMushroom(tekton);
                    break;
                }
                if(z==99) System.out.println("nem sikerült megfelelő tektont találni");
            }
        }

            /**
             * TO DO : buggersek hozzáadása, megfelelő tektonra
             */
        for(Bugger b: gameBoard.getBuggers().values()){
            Bug newbug = new Bug(b, gameBoard.getTektons().get(1) );
            b.addBug(newbug);
            for (int z=0;z<100;z++) {
                int ir = rand.nextInt(25);
                TektonBase tekton = gameBoard.getTektons().get(ir);
                if(!tekton.hasMushroom()&&tekton.tryBug(newbug)){
                    newbug.setLocation(tekton);
                    break;
                }
                if(z==99) System.out.println("nem sikerült megfelelő tektont találni");
            }
        }


    }

    public void gameCycle(){
        notifyViewNextPlayer();
    }

    private void endOfRound(){
        gameBoard.getTektons().forEach(TektonBase::endOfRound);
        gameBoard.getShroomers().values().forEach(Shroomer::endOfRoundAdministration);
        ///random vagy enm random tekton törése
        if(seed!=12345L) {
            Random rand = new Random(seed);
            gameBoard.getTektons().get(rand.nextInt(gameBoard.getTektons().size())).breakTekton(seed);
        }

    }

    public boolean move(Bug bug, TektonBase to){
        if (gameBoard.getBuggers().containsKey(actualPlayer)){
            if (gameBoard.getBuggers().get(actualPlayer).move(bug,to)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean bite(Bug bug, Hypa hypa){
        if (gameBoard.getBuggers().containsKey(actualPlayer)) {
            if(gameBoard.getBuggers().get(actualPlayer).bite(bug,hypa)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean eat(Bug bug, Spore spore){
        if (gameBoard.getBuggers().containsKey(actualPlayer)) {
            if(gameBoard.getBuggers().get(actualPlayer).eat(bug,spore)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean growhypa(TektonBase start, TektonBase target){
        if (gameBoard.getShroomers().containsKey(actualPlayer)) {
            if(gameBoard.getShroomers().get(actualPlayer).growHypa(start,target)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean growhypafar(TektonBase start,TektonBase middle,  TektonBase target){
        if (gameBoard.getShroomers().containsKey(actualPlayer)) {
            if(gameBoard.getShroomers().get(actualPlayer).growHypaFar(start,middle,target)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean throwspore(Mushroom mushroom, TektonBase target){
        if (gameBoard.getShroomers().containsKey(actualPlayer)) {
            if(gameBoard.getShroomers().get(actualPlayer).throwSpore(mushroom,target)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean eatbug(Bug bug){
        if (gameBoard.getShroomers().containsKey(actualPlayer)) {
            if(gameBoard.getShroomers().get(actualPlayer).eatBug(bug)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean endturn(){
        success();
        return true;
    }

    public void breaktekton(TektonBase tekton){
        tekton.breakTekton(seed);
    }


    /**
     * metódus, amit sikeres játékos akciók után kell hívni. Lépteti a kört és az aktuális játékost.
     */
    private void success(){
        if(actualPlayer == gameBoard.getNumberOfPlayers()-1 && round == 20){
            /**
             * a korben utolso jatekos sikeres akciot hajtott vegre es ez volt az utolso kor
             */
            view.setEndOfGame();
            endOfRound();
        } else if(actualPlayer == gameBoard.getNumberOfPlayers()-1 && round != 20){
            /**
             * a korben utolso jatekos sikeres akciot hajtott vegre
             */
            round++;
            actualPlayer = (actualPlayer+1)% gameBoard.getNumberOfPlayers();
            notifyViewNextPlayer();
            endOfRound();
            view.displayMessage("SUCESS: Round: "+round+", The next player is: "+ actualPlayer);
        } else{
            /**
             * a jatekos sikeres akciot hajtott vegre, kovetkezo jatekos jon
             */
            actualPlayer = (actualPlayer+1)% gameBoard.getNumberOfPlayers();
            notifyViewNextPlayer();
            view.displayMessage("SUCCESS: Round: "+round+", The next player is: "+ actualPlayer);
        }
    }
    private void notifyViewNextPlayer(){
        /**
         * jelzes a view-nak, grafikus esetben van ertelme, konzolos esetben no-op
         */
        Player next;
        if(gameBoard.getShroomers().containsKey(actualPlayer)){
            next = gameBoard.getShroomers().get(actualPlayer);
            view.shroomerNext(gameBoard.getPlayerName(next));
        }else if(gameBoard.getBuggers().containsKey(actualPlayer)){
            next = gameBoard.getBuggers().get(actualPlayer);
            view.buggerNext(gameBoard.getPlayerName(next));
        }
    }

}


