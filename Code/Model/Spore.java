
import java.io.*;
import java.util.*;

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
    public Spore() {
    }

    /**
     * @param s
     */
    public Spore(Shroomer s) {
        // TODO implement here
    }

    /**
     * @param b 
     * @return
     */
    public abstract int haveEffect(Bug b);

}