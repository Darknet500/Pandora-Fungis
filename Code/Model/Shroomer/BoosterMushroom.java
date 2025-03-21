package Shroomer;

import Tekton.Tekton;

import java.util.Collections;

import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class BoosterMushroom extends Mushroom {

    /**
     * Default constructor
     */
    public BoosterMushroom(Shroomer shroomer, Tekton location) {
        super(shroomer, location);
    }

    /**
     * @param target
     */
    public void sporeThrown(Tekton target) {
        SKELETON.printCall(this, Collections.singletonList(target), "sporeThrown");
        BoosterSpore spore = new BoosterSpore(this.shroomer);
        target.storeSpore(spore);
        SKELETON.printReturn("");

    }
}