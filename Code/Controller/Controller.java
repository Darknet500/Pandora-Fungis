package Controller;
import Model.Bridge.*;
import View.*;
import Model.Bug.*;
import Model.Shroomer.*;
import Model.Tekton.*;

import java.util.Random;


public class Controller {
    private int actualPlayer = 0;
    private int round;
    private GameBoard gameBoard;
    private View view;

    public void connectObjects(View view, GameBoard gameBoard) {
        this.view = view;
        this.gameBoard = gameBoard;
    }

    public void initMap(){
        Random rand = new Random();
        for (int k = 1; k <= 25; k++) {
            double r = rand.nextDouble();
            Tekton tekton;
            if (r < 0.48) {
                tekton = new Tekton();
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
    }

    public void gameCycle(){

    }

    private void endOfRound(){
        gameBoard.getTektons().forEach(Tekton::endOfRound);
        gameBoard.getShroomer().values().forEach(Shroomer::endOfRoundAdministration);
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
        tekton.breakTekton();
    }


    /**
     * metódus, amit sikeres játékos akciók után kell hívni. Lépteti a kört és az aktuális játékost.
     */
    private void success(){
        if(actualPlayer == gameBoard.getNumberOfPlayers() && round == 20){
            //last player in the round just made a successful action and it is the last round of the game
            view.setEndOfGame();
            endOfRound();
        } else if(actualPlayer == gameBoard.getNumberOfPlayers() && round != 20){
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


