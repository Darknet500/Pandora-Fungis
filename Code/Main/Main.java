package Main;

import Model.Bridge.GameBoard;
import View.*;
import View.GameMode;
import Controller.Controller;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        IView view = null;

        if(args.length ==3){
            if(args[0].equals("-testmode")){
                if(args[1].equals("-f")){
                    if(args[2].equals("-a")){
                        view = new ConsoleView(GameMode.autotestall, null);
                    } else{
                        view = new ConsoleView(GameMode.autotestone, args[2].replace("-", ""));
                    }
                }
            }
        } else if(args.length==0){
            /**
             * running in game mode, graphic display
             */
            view = new GraphicView();
        }
        if(view==null){
            System.out.println("Invalid arguments");
            System.exit(1);
        }
        Controller controller = new Controller();
        view.connectObjects(gameBoard, controller);

        view.run();
    }
}
