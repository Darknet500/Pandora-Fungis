package Main;

import Model.Bridge.GameBoard;
import View.View;
import View.GameMode;
import Controller.Controller;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        GameMode gm = null;
        String tc = null;
/*
        args = new String[3];
        args[0] = "-testmode";
        args[1] = "-f";
        args[2] = "-a";
*/

        if(args.length ==3){
            if(args[0].equals("-testmode")){
                if(args[1].equals("-f")){
                    if(args[2].equals("-a")){
                        gm=GameMode.autotestall;
                    } else{
                        gm = GameMode.autotestone;
                        tc = args[2].replace("-", "");
                    }
                }
            }
        } else if(args.length==2){
            if(args[1].equals("-manual")){
                gm = GameMode.manualtest;
            }
        }else if(args.length ==0){
            gm = GameMode.game;
        } else{
            System.out.println("Invalid arguments.");
            System.exit(1);
        }
        System.out.println("running in.. "+ gm);
        View view = new View(gm, tc);
        Controller controller = new Controller();
        view.connectObjects(gameBoard, controller);

        view.run();
    }
}
