
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Slowed extends Strategy {

    /**
     * Default constructor
     */
    public Slowed() {
    }

    /**
     * 
     */
    private int movesMade;

    /**
     * @param b 
     * @param s
     */
    public void eat(Bug b, Spore s) {
        // TODO implement here
    }

    /**
     * @param b 
     * @param h
     */
    public void bite(Bug b, Hypa h) {
        // TODO implement here
    }

    /**
     * @param b 
     * @param to
     */
    public void move(Bug b, Tekton to) {
        // TODO implement here
    }

    /**
     * @param b 
     * @param s
     */
    public abstract void eat(Bug b, Spore s);

    /**
     * @param b 
     * @param h
     */
    public abstract void bite(Bug b, Hypa h);

    /**
     * @param b 
     * @param to
     */
    public abstract void move(Bug b, Tekton to);

}