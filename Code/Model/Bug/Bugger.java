package Bug;

import Controll.Player;
import java.util.*;
import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

public class Bugger extends Player {

    private List<Bug> bugs;

    public Bugger(){
        bugs = new ArrayList<Bug>();
    }

    public void addBug(Bug b){
        bugs.add(b);
    }

    public void removeBug(Bug b){
        bugs.remove(b);
    }

    public void move(Bug b, Tekton to){
        b.move(to);
    }

    public void bite(Bug b, Hypa h){
        b.bite(h);
    }

    public void eat(Bug b, Spore s){
        b.eat(s);
    }

    public void endOfTurn(){
        for (Bug b : bugs){
            b.endOfTurn();
        }
    }




}
