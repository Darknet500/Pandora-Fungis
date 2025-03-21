package Shroomer;

import Tekton.Tekton;

import java.util.Collections;

import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class SlowerMushroom extends Mushroom {

    /**
     * Default constructor
     */
    public SlowerMushroom(Shroomer shroomer, Tekton location) {
        super(shroomer, location);
    }

    /**
     * @param target
     */
    public void sporeThrown(Tekton target) {
        SKELETON.printCall(this, Collections.singletonList(target), "sporeThrown");

        SlowerSpore spore = new SlowerSpore(this.shroomer);
        target.storeSpore(spore);
        SKELETON.printReturn("");

    }
}