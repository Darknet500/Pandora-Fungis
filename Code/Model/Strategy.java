
import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class Strategy {

    /**
     * Default constructor
     */
    public Strategy() {
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