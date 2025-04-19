package Controller;
import Model.Bridge.*;
import View.*;
import Model.Bug.*;
import Model.Shroomer.*;
import Model.Tekton.*;

import java.util.Random;


public class Controller {
    int actualPlayer=1;
    GameBoard gameBoard;
    View view;


    public Controller() {

    }

    public void connectObjects(View view, GameBoard gameBoard) {
        this.view = view;
        this.gameBoard = gameBoard;
    }


    public void HandleInput(String input){

    }

    public void run(){
        initMap();
        gameCycle();
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

                actualPlayer++;

                return true;
            }
        }
        return false;
    }
    public boolean bite(Bug bug, Hypa hypa){
        if (gameBoard.getBugger().containsKey(actualPlayer)) {
            if(gameBoard.getBugger().get(actualPlayer).bite(bug,hypa)){
                actualPlayer++;
                return true;
            }
        }
        return false;
    }
    public boolean eat(Bug bug, Spore spore){
        return false;
    }
    public boolean growhypa(Tekton start, Tekton target){
        return false;
    }
    public boolean growhypafar(Tekton start,Tekton middle,  Tekton target){
        return false;
    }
    public boolean throwspore(Mushroom mushroom, Tekton target){
        return false;
    }
    public boolean eatbug(Bug bug){
        return false;
    }
    public boolean endturn(){
        return true;
    }
    public void breaktekton(Tekton tekton){
        tekton.breakTekton();
    }

}


