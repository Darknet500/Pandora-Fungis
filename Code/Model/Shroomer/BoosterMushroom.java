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
        Spore booster = new BoosterSpore(this.shroomer);
        SKELETON.objectNameMap.put(booster, "booster");
        target.storeSpore(booster);
        if(getSporesThrown()==5){
            die();
        }
        SKELETON.printReturn("");

    }
}