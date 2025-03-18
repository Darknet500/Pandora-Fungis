package Shroomer;

import Tekton.Tekton;

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
        SlowerSpore spore = new SlowerSpore(this.shroomer);
        target.storeSpore(spore);

    }
}