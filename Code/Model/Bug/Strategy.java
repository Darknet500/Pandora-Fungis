package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

/**
 * 
 */
public interface Strategy {

    boolean eat();

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

    void endOfTurn(Bug b);

}