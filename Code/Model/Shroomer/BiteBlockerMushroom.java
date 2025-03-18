package Shroomer;

import Tekton.Tekton;

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
        BiteBlockerSpore spore = new BiteBlockerSpore(this.shroomer);
        target.storeSpore(spore);

    }
}