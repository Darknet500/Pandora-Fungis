package Bug;


import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.List;

/**
 * 
 */
public class BiteBlocked implements Strategy {
    /**
     * @param b 
     * @param h
     */
    public boolean bite(Bug b, Hypa h) {
        return false;
    }

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
        return false;
    }

}