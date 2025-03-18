package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 */
public class Boosted implements Strategy {
        /**
     * @param b 
     * @param s
     */
    public boolean eat(Bug b, Spore s) {
        return false;
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

    /**
     * @param b 
     * @param to
     */
    public boolean move(Bug b, Tekton to) {
        Tekton location = b.getLocation();
        Set<Tekton> canReach = new HashSet<Tekton>();
        canReach.addAll(location.getNeighboursByHypa());
        for(Tekton t : canReach){
            canReach.addAll(t.getNeighboursByHypa());
        }
        return canReach.contains(to);
    }

}