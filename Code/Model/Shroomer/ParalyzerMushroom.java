package Shroomer;

import Tekton.Tekton;


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
        ParalyzerSpore spore = new ParalyzerSpore(this.shroomer);
        target.storeSpore(spore);

    }
}