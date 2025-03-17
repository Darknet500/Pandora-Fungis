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