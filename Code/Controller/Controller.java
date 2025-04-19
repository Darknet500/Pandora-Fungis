package Controller;
import Model.Bridge.*;
import View.*;


public abstract class Controller {
    int actualPlayer;
    GameBoard gameBoard;
    View view;
    ArrangeSection arrangeSection;
    State state;


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


