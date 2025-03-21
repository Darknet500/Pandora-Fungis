package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;
import static Controll.Skeleton.SKELETON;

import java.util.Collections;
import java.util.List;

/**
 * 
 */
public class BiteBlocked extends Normal {
    /**
     * @param b 
     * @param h
     */
    @Override
    public boolean bite(Bug b, Hypa h) {
        SKELETON.printCall(this, List.of(b, h), "bite");
        SKELETON.printReturn("false");
        return false;
    }

    @Override
    public boolean eat() {
        SKELETON.printCall(this, Collections.emptyList(), "eat");
        SKELETON.printReturn("false");
        return false;
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