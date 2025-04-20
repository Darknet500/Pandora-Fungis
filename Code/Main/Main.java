package Main;

import Model.Bridge.GameBoard;
import View.View;
import View.GameMode;
import Controller.Controller;

public class Main {
    public static void main(String[] args) {

        //kiprobalaskent autotestall
        args = new String[3];
        args[0] = "-testmode";
        args[1] = "-f";
        args[2] = "-a";

        GameBoard gameBoard = new GameBoard();
        GameMode gm = GameMode.game;
        String tc = null;
        if(args.length ==3){
            if(args[0].equals("-testmode")){
                if(args[1].equals("-f")){
                    if(args[2].equals("-a")){
                        gm=GameMode.autotestall;
                    } else{
                        gm = GameMode.autotestone;
                        tc = args[2].replace("-", "");
                    }
                } else if(args[1].equals("-manual")){
                    gm = GameMode.manualtest;
                }
            }
        } else if(args.length ==0){
            gm = GameMode.game;
        }
        View view = new View(gm, tc);
        Controller controller = new Controller();
        view.connectObjects(gameBoard, controller);

        view.run();
    }
}
