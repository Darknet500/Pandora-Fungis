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
        Spore biteBloskerSpore = new BiteBlockerSpore(this.shroomer);
        SKELETON.objectNameMap.put(biteBloskerSpore, "biteBloskerSpore");
        target.storeSpore(biteBloskerSpore);
        if(getSporesThrown()==5){
            die();
        }
        SKELETON.printReturn("");

    }
}