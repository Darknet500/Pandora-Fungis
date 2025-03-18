package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.List;

/**
 * 
 */
public class Slowed implements Strategy {
    /**
     * Default constructor
     */
    public Slowed() {}

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
        
    }
}