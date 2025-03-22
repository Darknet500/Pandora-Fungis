package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;
import Controll.Skeleton;

import java.util.Arrays;
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
        SKELETON.printCall(this, Arrays.asList(b, to), "move");
        Tekton location = b.getLocation();
        List<Tekton> canReach = location.getNeighboursByHypa();
        int rethalf = Skeleton.SKELETON.getNumericInput(
                "moves made in the prev. 2 rounds = ?\n 0 vagy 1\n", 0, 1);

        boolean ret = canReach.contains(to)&& rethalf ==0;

        SKELETON.printReturn(ret?"true":"false");
        return ret;
    }

    public void endOfTurn(Bug b){
        if(b.getUnderEffectSince()==2){
            b.setStrategy(new Normal());
        }
    }
}