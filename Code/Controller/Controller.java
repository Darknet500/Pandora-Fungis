package Controller;
import Model.Bridge.*;
import View.*;
import Model.Bug.*;
import Model.Shroomer.*;
import Model.Tekton.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Controller {
    private int actualPlayer = 0;
    private int round;
    private GameBoard gameBoard;
    private IView view;
    private long seed;

    public void connectObjects(IView view, GameBoard gameBoard) {
        this.view = view;
        this.gameBoard = gameBoard;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void resetActualPlayerandRound() {
        actualPlayer = 0;
        round = 0;
    }

    public void initMap(){
        Random rand = new Random();
        double r;
        List<Integer> normalTektonsNumber = new ArrayList<>();
        for (int k = 1; k <= 25; k++) {
            r = rand.nextDouble();
            TektonBase tekton;
            if (r < 0.48) {
                tekton = new Tekton();
                normalTektonsNumber.add(k-1);

            } else if (r < 0.61) {
                tekton = new Peat();
            } else if (r < 0.74) {
                tekton = new Stone();
            } else if (r < 0.87) {
                tekton = new Swamp();
            } else {
                tekton = new Soil();
            }
            gameBoard.addTekton(tekton);
        }
        for (int k = 1; k <= 25; k++) {
            for (int i = 0; i < 25; i++) {
                for (int j = 0; j < 25; j++) {
                    if (i != j) {
                        if(!gameBoard.getTekton(i).isNeighbour(gameBoard.getTekton(j))) {
                            r = rand.nextDouble();
                            if(r < 0.007) {
                                gameBoard.getTekton(i).addNeighbour(gameBoard.getTekton(j));
                                gameBoard.getTekton(j).addNeighbour(gameBoard.getTekton(i));
                            }

                        }
                    }
                }
            }
        }

        //játékosok kezdő objektumainak elhelyezése
        for (int i = 0; i < gameBoard.getNumberOfPlayers(); i++) {
            while(true){
                int ir = rand.nextInt(25);

                if(!gameBoard.getTektons().get(ir).hasMushroom()&&gameBoard.getTektons().get(ir).getBug()==null&&normalTektonsNumber.contains(ir)) {
                    if(gameBoard.getShroomers().containsKey(i)){
                        gameBoard.getShroomers().get(i).growFirstMushroom(gameBoard.getTektons().get(ir));
                    }else{
                        Bug bug = new Bug(gameBoard.getBuggers().get(i));
                        gameBoard.getBuggers().get(i).addBug(bug);
                        bug.setLocation(gameBoard.getTektons().get(ir));
                    }

                    break;
                }
            }
        }
    }

    public void gameCycle(){

    }

    private void endOfRound(){
        gameBoard.getTektons().forEach(TektonBase::endOfRound);
        gameBoard.getShroomers().values().forEach(Shroomer::endOfRoundAdministration);
        ///random vagy enm random tekton törése
        if(seed!=12345L) {
            Random rand = new Random(seed);
            gameBoard.getTektons().get(rand.nextInt(gameBoard.getTektons().size())).breakTekton(seed);
        }

    }

    public boolean move(Bug bug, TektonBase to){
        if (gameBoard.getBuggers().containsKey(actualPlayer)){
            if (gameBoard.getBuggers().get(actualPlayer).move(bug,to)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean bite(Bug bug, Hypa hypa){
        if (gameBoard.getBuggers().containsKey(actualPlayer)) {
            if(gameBoard.getBuggers().get(actualPlayer).bite(bug,hypa)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean eat(Bug bug, Spore spore){
        if (gameBoard.getBuggers().containsKey(actualPlayer)) {
            if(gameBoard.getBuggers().get(actualPlayer).eat(bug,spore)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean growhypa(TektonBase start, TektonBase target){
        if (gameBoard.getShroomers().containsKey(actualPlayer)) {
            if(gameBoard.getShroomers().get(actualPlayer).growHypa(start,target)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean growhypafar(TektonBase start,TektonBase middle,  TektonBase target){
        if (gameBoard.getShroomers().containsKey(actualPlayer)) {
            if(gameBoard.getShroomers().get(actualPlayer).growHypaFar(start,middle,target)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean throwspore(Mushroom mushroom, TektonBase target){
        if (gameBoard.getShroomers().containsKey(actualPlayer)) {
            if(gameBoard.getShroomers().get(actualPlayer).throwSpore(mushroom,target)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean eatbug(Bug bug){
        if (gameBoard.getShroomers().containsKey(actualPlayer)) {
            if(gameBoard.getShroomers().get(actualPlayer).eatBug(bug)){
                success();
                return true;
            }
        }
        return false;
    }

    public boolean endturn(){
        success();
        return true;
    }

    public void breaktekton(TektonBase tekton){
        tekton.breakTekton(seed);
    }


    /**
     * metódus, amit sikeres játékos akciók után kell hívni. Lépteti a kört és az aktuális játékost.
     */
    private void success(){
        if(actualPlayer == gameBoard.getNumberOfPlayers()-1 && round == 20){
            /**
             * a korben utolso jatekos sikeres akciot hajtott vegre es ez volt az utolso kor
             */
            view.setEndOfGame();
            endOfRound();
        } else if(actualPlayer == gameBoard.getNumberOfPlayers()-1 && round != 20){
            /**
             * a korben utolso jatekos sikeres akciot hajtott vegre
             */
            round++;
            actualPlayer = (actualPlayer+1)% gameBoard.getNumberOfPlayers();
            /**
             * jelzes a view-nak, grafikus esetben van ertelme, konzolos esetben no-op
             */
            Player next;
            if(gameBoard.getShroomers().containsKey(actualPlayer)){
                next = gameBoard.getShroomers().get(actualPlayer);
                view.shroomerNext(gameBoard.getPlayerName(next));
            }else if(gameBoard.getBuggers().containsKey(actualPlayer)){
                next = gameBoard.getBuggers().get(actualPlayer);
                view.buggerNext(gameBoard.getPlayerName(next));
            }
            endOfRound();
            view.displayMessage("SUCESS: Round: "+round+", The next player is: "+ actualPlayer);
        } else{
            /**
             * a jatekos sikeres akciot hajtott vegre, kovetkezo jatekos jon
             */
            actualPlayer = (actualPlayer+1)% gameBoard.getNumberOfPlayers();
            /**
             * jelzes a view-nak, grafikus esetben van ertelme, konzolos esetben no-op
             */
            Player next;
            if(gameBoard.getShroomers().containsKey(actualPlayer)){
                next = gameBoard.getShroomers().get(actualPlayer);
                view.shroomerNext(gameBoard.getPlayerName(next));
            }else if(gameBoard.getBuggers().containsKey(actualPlayer)){
                next = gameBoard.getBuggers().get(actualPlayer);
                view.buggerNext(gameBoard.getPlayerName(next));
            }
            view.displayMessage("SUCCESS: Round: "+round+", The next player is: "+ actualPlayer);
        }
    }
}


