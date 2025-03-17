package Shroomer;

import Tekton.Tekton;

/**
 * 
 */
public abstract class Mushroom {
    /**
     *
     */
    private int age;

    /**
     *
     */
    private int numeberOfSpores;

    /**
     *
     */
    private int sporesThrown;

    /**
     *
     */
    private Tekton tekton;

    /**
     *
     */
    private Shroomer shroomer;

    /**
     * Default constructor
     */
    public Mushroom() {
    }

    /**
     * @param s
     * @param pos
     */
    public Mushroom(Shroomer s, Tekton pos) {
        // TODO implement here
    }

    /**
     *
     */
    public void die() {
        // TODO implement here
    }

    /**
     *
     */
    public void age() {
        // TODO implement here
    }

    /**
     * @param to
     */
    public abstract void sporeThrown(Tekton to);

}