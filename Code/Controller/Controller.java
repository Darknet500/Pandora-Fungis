package Controller;
import Model.Bridge.*;
import View.*;

import java.io.File;


public abstract class Controller {
    int actualPlayer;
    GameBoard gameBoard;
    View view;
    ArrangeSection arrangeSection;


    public Controller() {
        gameBoard = new GameBoard();
        view = new CommandLineView(this);

    }


    public void HandleInput(String input){

    }

    public void run(){
        initMap();
        gameCycle();
    }

    private void initMap(){
        gameBoard.toString();

    }

    private void gameCycle(){

    }

    private void endOfRound(){
        gameBoard.getTektons().forEach(tekton -> tekton.endOfRound());
        gameBoard.getShroomer().values().forEach(sho->sho.endOfRoundAdministration());
    }
}


