package Controller;
import Model.Bridge.*;
import View.*;
import Model.Bug.*;
import Model.Shroomer.*;
import Model.Tekton.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Controller {
    private int actualPlayer = 0;
    private int round;
    private GameBoard gameBoard;
    private View view;
    private long seed;

    public void connectObjects(View view, GameBoard gameBoard) {
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
            Tekton tekton;
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
            gameBoard.addTekton(tekton);
        }
        for (int k = 1; k <= 25; k++) {
            for (int i = 0; i < 25; i++) {
                for (int j = 0; j < 25; j++) {
                    if (i != j) {
                        if(!gameBoard.getTekton(i).isNeighbour(gameBoard.getTekton(j))) {
                            r = rand.nextDouble();
                            if(r < 0.035) {
                                gameBoard.getTekton(i).addNeighbour(gameBoard.getTekton(j));
                            }

                        }
                    }
                }
            }
        }

        //játékosok kezdő objektumainak elhelyezése
        for (int i = 0; i < gameBoard.getNumberOfPlayers(); i++) {
            while(true){
                int ir = rand.nextInt(25);

                if(!gameBoard.getTektons().get(ir).hasMushroom()&&gameBoard.getTektons().get(ir).getBug()==null&&normalTektonsNumber.contains(ir)) {
                    if(gameBoard.getShroomer().containsKey(i)){
                        gameBoard.getShroomer().get(i).growFirstMushroom(gameBoard.getTektons().get(ir));
                    }else{
                        Bug bug = new Bug(gameBoard.getBugger().get(i));
                        gameBoard.getBugger().get(i).addBug(bug);
                        bug.setLocation(gameBoard.getTektons().get(ir));
                    }

                    break;
                }
            }
        }
    }

    public void gameCycle(){

    }

    private void endOfRound(){
        gameBoard.getTektons().forEach(Tekton::endOfRound);
        gameBoard.getShroomer().values().forEach(Shroomer::endOfRoundAdministration);
        ///random vagy enm random tekton törése
        if(seed!=12345L) {
            Random rand = new Random(seed);
            gameBoard.getTektons().get(rand.nextInt(gameBoard.getTektons().size())).breakTekton(seed);
        }

    }

    public boolean move(Bug bug, Tekton to){
        if (gameBoard.getBugger().containsKey(actualPlayer)){
            if (gameBoard.getBugger().get(actualPlayer).move(bug,to)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean bite(Bug bug, Hypa hypa){
        if (gameBoard.getBugger().containsKey(actualPlayer)) {
            if(gameBoard.getBugger().get(actualPlayer).bite(bug,hypa)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean eat(Bug bug, Spore spore){
        if (gameBoard.getBugger().containsKey(actualPlayer)) {
            if(gameBoard.getBugger().get(actualPlayer).eat(bug,spore)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean growhypa(Tekton start, Tekton target){
        if (gameBoard.getShroomer().containsKey(actualPlayer)) {
            if(gameBoard.getShroomer().get(actualPlayer).growHypa(start,target)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean growhypafar(Tekton start,Tekton middle,  Tekton target){
        if (gameBoard.getShroomer().containsKey(actualPlayer)) {
            if(gameBoard.getShroomer().get(actualPlayer).growHypaFar(start,middle,target)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean throwspore(Mushroom mushroom, Tekton target){
        if (gameBoard.getShroomer().containsKey(actualPlayer)) {
            if(gameBoard.getShroomer().get(actualPlayer).throwSpore(mushroom,target)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean eatbug(Bug bug){
        if (gameBoard.getShroomer().containsKey(actualPlayer)) {
            if(gameBoard.getShroomer().get(actualPlayer).eatBug(bug)){
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

    public void breaktekton(Tekton tekton){
        tekton.breakTekton(seed);
    }


    /**
     * metódus, amit sikeres játékos akciók után kell hívni. Lépteti a kört és az aktuális játékost.
     */
    private void success(){
        if(actualPlayer == gameBoard.getNumberOfPlayers() && round == 20){
            //last player in the round just made a successful action and it is the last round of the game
            view.setEndOfGame();
            endOfRound();
        } else if(actualPlayer == gameBoard.getNumberOfPlayers()-1 && round != 20){
            //last player in the round just made a successful action
            round++;
            actualPlayer = (actualPlayer+1)% gameBoard.getNumberOfPlayers();
            endOfRound();
            view.displayMessage("SUCESS: Round: "+round+", The next player is: "+ actualPlayer);
        } else{
            actualPlayer = (actualPlayer+1)% gameBoard.getNumberOfPlayers();
            view.displayMessage("SUCCESS: Round: "+round+", The next player is: "+ actualPlayer);
        }
    }
}


