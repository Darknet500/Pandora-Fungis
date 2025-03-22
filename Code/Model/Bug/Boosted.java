package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class Boosted extends Normal {

    @Override
    public boolean eat(){
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
        SKELETON.printCall(this, List.of(b, to), "move");
        Tekton location = b.getLocation();
        Set<Tekton> canReach = new HashSet<Tekton>();
        canReach.addAll(location.getNeighboursByHypa());
        for(Tekton t : canReach){
            canReach.addAll(t.getNeighboursByHypa());
        }
        boolean canDo = canReach.contains(to);
        SKELETON.printReturn(canDo?"true":"false");
        return canDo;
    }

    @Override
    public void endOfTurn(Bug b){
        SKELETON.printCall(this, List.of(b), "endOfTurn");
        if(b.getUnderEffectSince()==2){
            Normal normal = new Normal();
            SKELETON.objectNameMap.put(normal, "normal");
            b.setStrategy(normal);
        }
        SKELETON.printReturn("");
    }

}