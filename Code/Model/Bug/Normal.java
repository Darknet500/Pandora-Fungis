package Bug;


import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.List;

/**
 * 
 */
public class Normal implements Strategy {

    /**
     * @param b 
     * @param to
     */
    public boolean move(Bug b, Tekton to) {
        Tekton location = b.getLocation();
        List<Tekton> canReach = location.getNeighboursByHypa();
        return canReach.contains(to);
    }

    /**
     * @param b 
     * @param s
     */
    public boolean eat(Bug b, Spore s) {
        return true;
    }

    /**
     * @param b 
     * @param h
     */
    public boolean bite(Bug b, Hypa h) {
        Tekton location = b.getLocation();
        List<Hypa> hypas = location.getHypas();
        return hypas.contains(h);
    }

    public void endOfTurn(Bug b){
        return;
    }
}