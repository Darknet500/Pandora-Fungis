package Tekton;

import Shroomer.Shroomer;

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
        return false;
    }

}