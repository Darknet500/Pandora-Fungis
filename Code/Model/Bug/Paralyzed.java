package Bug;

import Controll.Skeleton;
import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.Collections;
import java.util.List;

import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class Paralyzed extends Normal {

    public Paralyzed(){}

    /**
     * @param b 
     * @param to
     */
    @Override
    public boolean move(Bug b, Tekton to) {
        SKELETON.printCall(this, List.of(b, to), "move");
        SKELETON.printReturn("false");
        return false;
    }

    @Override
    public boolean eat() {
        SKELETON.printCall(this, Collections.emptyList(), "eat");
        SKELETON.printReturn("false");
        return false;
    }

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