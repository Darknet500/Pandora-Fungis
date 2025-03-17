
import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class Mushroom {

    /**
     * Default constructor
     */
    public Mushroom() {
    }

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
     * @param to
     */
    public abstract void sporeThrown(Tekton to);

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

}