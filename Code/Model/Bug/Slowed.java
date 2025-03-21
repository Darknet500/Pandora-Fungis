package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;
import Controll.Skeleton;

import java.util.Collections;
import java.util.List;

import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class Slowed extends Normal {
    /**
     * Default constructor
     */
    public Slowed() {}

    @Override
    public boolean eat() {
        SKELETON.printCall(this, Collections.emptyList(), "eat");
        SKELETON.printReturn("false");
        return false;
    }

    /**
     * @param b 
     * @param to
     */
    @Override
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