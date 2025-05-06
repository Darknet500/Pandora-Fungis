package View;

import Controller.Controller;
import Model.Bridge.GameBoard;

public interface IView {
    void connectObjects(GameBoard gameBoard, Controller controller);
    void displayMessage(String message);
    void setEndOfGame();
    void run();
}
