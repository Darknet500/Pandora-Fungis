package Shroomer;

import Tekton.Tekton;

import java.util.Collections;

import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class BiteBlockerMushroom extends Mushroom {

    /**
     * Default constructor
     */
    public BiteBlockerMushroom(Shroomer shroomer, Tekton location) {
        super(shroomer, location);
    }

    /**
     * @param target
     */
    public void sporeThrown(Tekton target) {
        SKELETON.printCall(this, Collections.singletonList(target), "sporeThrown");
        BiteBlockerSpore spore = new BiteBlockerSpore(this.shroomer);
        target.storeSpore(spore);
        SKELETON.printReturn("");

    }
}