package Tekton;

import Shroomer.Shroomer;

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
        return shroomer != null;
    }

}