package View;

import java.io.File;

import Controller.Controller;
import Model.Bridge.*;

public class View {
    private GameBoard gameBoard;
    private Controller controller;
    private ArrangeSection arrangeSection;
    private String testCase;
    private GameMode gameMode;

    public View(GameMode gameMode, String testCase) {
        this.gameMode = gameMode;
        this.testCase = testCase;
    }

    public void connectObjects(GameBoard gameBoard, Controller controller) {
        this.gameBoard = gameBoard;
        this.controller = controller;
        controller.connectObjects(this);
    }

    public void run(){

        if(gameMode.equals("autotest")){

        }


        arrange(testCase);
        while (true){
            act(testCase);
            if (!gameMode.equals("game")) break;
        }
        assert(testCase);

    }

    public void arrange(String tc){
        switch ()
    }

    void getInput();

    void readInputFile(File f);

    void serveResult(String msg);

    void displayGameBoard(GameBoard gameBoard);
}
