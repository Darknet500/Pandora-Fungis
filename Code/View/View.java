package View;

import java.io.File;
import Model.Bridge.*;

public interface View {
    void getInput();

    void readInputFile(File f);

    void serveResult(String msg);

    void displayGameBoard(GameBoard gameBoard);
}
