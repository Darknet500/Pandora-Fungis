package Tekton;

import Shroomer.Shroomer;

import java.util.Collections;

import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class Stone extends Tekton {

    /**
     * Default constructor
     */
    public Stone() {
        super();
    }

    /**
     * @return
     */
    @Override
    public boolean canMushroomGrow(Shroomer s) {
        SKELETON.printCall(this, Collections.singletonList(s), "canMushroomGrow");
        SKELETON.printReturn("false");
        return false;
    }

}