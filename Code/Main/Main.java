package Main;

import Model.Bridge.GameBoard;
import View.*;
import Gamemode.GameMode;
import Controller.Controller;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        GameMode gm = null;
        IView view = null;

        /*args = new String[3];
        args[0] = "-testmode";
        args[1] = "-f";
        args[2] = "-a";*/

        if(args.length ==3){
            if(args[0].equals("-testmode")){
                if(args[1].equals("-f")){
                    if(args[2].equals("-a")){
                        view = new ConsoleView(GameMode.autotestall, null);
                        gm = GameMode.autotestall;
                    } else{
                        view = new ConsoleView(GameMode.autotestone, args[2].replace("-", ""));
                        gm = GameMode.autotestone;
                    }
                }
            }
        } else if(args.length==0){
            /**
             * running in game mode, graphic display
             */
            gm=GameMode.game;
            view = new GraphicView();
        }
        if(view==null || gm==null){
            System.out.println("Invalid arguments");
            System.exit(1);
        }
        Controller controller = new Controller(gm);
        view.connectObjects(gameBoard, controller);

        view.run();
    }
}
