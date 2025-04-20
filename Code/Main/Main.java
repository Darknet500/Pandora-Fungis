package Main;

import Model.Bridge.GameBoard;
import View.View;
import View.GameMode;
import Controller.Controller;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        View view = new View(GameMode.autotestall, null);
        Controller controller = new Controller();

        view.connectObjects(gameBoard, controller);

        view.run();

        System.exit(0);
    }
}
