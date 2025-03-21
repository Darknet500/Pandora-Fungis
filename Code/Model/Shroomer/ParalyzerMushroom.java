package Shroomer;

import Tekton.Tekton;

import java.util.Collections;

import static Controll.Skeleton.SKELETON;


/**
 * 
 */
public class ParalyzerMushroom extends Mushroom {

    /**
     * Default constructor
     */
    public ParalyzerMushroom(Shroomer shroomer, Tekton location) {
        super(shroomer, location);
    }

    /**
     * @param target
     */
    public void sporeThrown(Tekton target) {
        SKELETON.printCall(this, Collections.singletonList(target), "sporeThrown");
        ParalyzerSpore spore = new ParalyzerSpore(this.shroomer);
        target.storeSpore(spore);
        SKELETON.printReturn("");

    }
}