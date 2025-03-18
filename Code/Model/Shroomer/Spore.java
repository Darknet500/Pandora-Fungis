package Shroomer;

import Bug.Bug;

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

}