package Shroomer;

import Tekton.Tekton;

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
        BoosterSpore spore = new BoosterSpore(this.shroomer);
        target.storeSpore(spore);

    }
}