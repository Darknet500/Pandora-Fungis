package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

/**
 * 
 */
public class Paralyzed implements Strategy {

    public Paralyzed(){}

    /**
     * @param b 
     * @param to
     */
    public boolean move(Bug b, Tekton to) {
        return false;
    }

    /**
     * @param b 
     * @param s
     */
    public boolean eat(Bug b, Spore s) {
        return false;
    }

    /**
     * @param b 
     * @param h
     */
    public boolean bite(Bug b, Hypa h) {
        return false;
    }

    public void endOfTurn(Bug b){
        if(b.getUnderEffectSince()==2){
            b.setStrategy(new Normal());
        }
    }

}