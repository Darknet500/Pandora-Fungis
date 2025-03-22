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
        Spore spore = new ParalyzerSpore(this.shroomer);
        SKELETON.objectNameMap.put(spore, "paralyzerSpore");
        target.storeSpore(spore);
        SKELETON.printReturn("");

    }
}