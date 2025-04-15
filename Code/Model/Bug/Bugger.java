package Model.Bug;

import Model.Bridge.GameBoard;
import Model.Bridge.Player;
import java.util.*;
import Model.Shroomer.Hypa;
import Model.Shroomer.Spore;
import Model.Tekton.Tekton;

public class Bugger extends Player {

    /**
     * statikus számláló, minden konstruktorhíváskor növeljük, ez biztosítja a név egyediséget.
     *  objektum elnevezése: bugger[buggerID aktuális értéke]
     */
    private static int buggerID = 0;

    private String name;

    private List<Bug> bugs;

    public Bugger(){
        buggerID++;
        name = "bugger"+buggerID;
        GameBoard.nameObjectMap.put(name, this);
        bugs = new ArrayList<Bug>();
    }

    public String getName(){return name;}

    public List<Bug> getBugs(){
        return bugs;
    }

    public void addBug(Bug b){bugs.add(b); }

    public void removeBug(Bug b){

        if(bugs.contains(b)){
            bugs.remove(b);
            GameBoard.nameObjectMap.remove(b.getName());
        }
    }

    public boolean move(Bug b, Tekton to){
        if(bugs.contains(b)){
            return b.move(to);
        }
        return false;
    }

    public boolean bite(Bug b, Hypa h){
        if(bugs.contains(b)){
            return b.bite(h);
        }
        return false;
    }

    public boolean eat(Bug b, Spore s){
        if(bugs.contains(b)){
            return b.eat(s);
        }
        return false;
    }

    /**
     * game felelossege hogy hivja korok vegen
     */
    public void endOfTurn(){
        for (Bug b : bugs){
            b.endOfTurn();
        }
    }




}
