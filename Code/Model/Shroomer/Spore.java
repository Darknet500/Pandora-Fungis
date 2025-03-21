package Shroomer;

import Bug.Bug;

import java.util.Collections;

import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public abstract class Spore {

    /**
     *
     */
    private Shroomer shroomer;

    /**
     *
     */


    /**
     * Default constructor
     */
    public Spore(Shroomer shroomer) {
        this.shroomer = shroomer;
    }



    /**
     * @param b 
     * @return
     */
    public abstract int haveEffect(Bug b);

    public Shroomer getShroomer() {
        SKELETON.printCall(this, Collections.emptyList(), "getShroomer");
        SKELETON.printReturn(SKELETON.objectNameMap.get(shroomer)+": Shroomer");
        return shroomer;
    }

}