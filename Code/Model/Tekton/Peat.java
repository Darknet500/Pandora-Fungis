package Tekton;

import Shroomer.Shroomer;

import java.util.Collections;

import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class Peat extends Tekton {

    /**
     * Default constructor
     */
    public Peat() {
        super();
    }

    /**
     * @param shroomer
     */
    @Override
    public boolean acceptHypa(Shroomer shroomer) {
        SKELETON.printCall(this, Collections.singletonList(shroomer), "acceptHypa");
        SKELETON.printReturn("true");
        return true;
    }

}