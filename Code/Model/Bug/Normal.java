package Bug;


import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.Collections;
import java.util.List;

import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class Normal implements Strategy {

    /**
     * @param b 
     * @param to
     */
    public boolean move(Bug b, Tekton to) {
        SKELETON.printCall(this, List.of(b, to), "move");
        Tekton location = b.getLocation();
        List<Tekton> canReach = location.getNeighboursByHypa();
        boolean canDo = canReach.contains(to);
        SKELETON.printReturn(canDo?"true":"false");
        return canDo;
    }

    public boolean eat() {
        SKELETON.printCall(this, Collections.emptyList(), "move");
        SKELETON.printReturn("true");
        return true;
    }

    /**
     * @param b 
     * @param h
     */
    public boolean bite(Bug b, Hypa h) {
        SKELETON.printCall(this, List.of(b, h), "bite");
        Tekton location = b.getLocation();
        List<Hypa> hypas = location.getHypas();
        boolean canDo = hypas.contains(h);
        SKELETON.printReturn(canDo?"true":"false");
        return canDo;
    }

    public void endOfTurn(Bug b){
        SKELETON.printCall(this, List.of(b), "endOfTurn");
        SKELETON.printReturn("");

    }
}