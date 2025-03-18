package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

/**
 * 
 */
public interface  Strategy {

    /**
     * @param b 
     * @param s
     */
    boolean eat(Bug b, Spore s);

    /**
     * @param b 
     * @param h
     */
    boolean bite(Bug b, Hypa h);

    /**
     * @param b 
     * @param to
     */
    boolean move(Bug b, Tekton to);

}