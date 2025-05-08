package View;

import Controller.Controller;
import Model.Bridge.GameBoard;

public interface IView {
    void connectObjects(GameBoard gameBoard, Controller controller);
    void displayMessage(String message);
    void setEndOfGame();
    void run();

    int getDrawingSurfaceWidth();
    int getDrawingSurfaceHeight();

    /**
     * kontroller ertesiti a view-t, grafikusban letiltjuk a hatasara a masik jatekosfajta akcio kivalaszto gombjait
     */
    void shroomerNext(String playerName);
    void buggerNext(String playerName);
}
