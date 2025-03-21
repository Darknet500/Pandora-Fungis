package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;
import Controll.Skeleton;
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
        Tekton location = b.getLocation();
        List<Tekton> canReach = location.getNeighboursByHypa();
        return canReach.contains(to)&& Skeleton.SKELETON.getNumericInput(
                "moves made in the prev. 2 rounds = ?", 0, 1)==0;
    }

    public void endOfTurn(Bug b){
        if(b.getUnderEffectSince()==2){
            b.setStrategy(new Normal());
        }
    }
}