package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.Collections;
import java.util.List;


/**
 * A Paralyzed osztály olyan stratégia, amely teljesen megbénítja a Bugot,
 * így nem tud mozogni, harapni vagy enni.
 */
public class Paralyzed extends Normal {
    
     /**
     * Alapértelmezett paraméter nélküli konstruktor a stratégiára
     */
    public Paralyzed(){}

    /**
     * Minden művelet le van tiltva ebben az állapotban.
     * @return Mindig hamis.
     */
    @Override
    public boolean move(Bug b, Tekton to) {
        return false;
    }

    /**
     * Minden művelet le van tiltva ebben az állapotban.
     * @return Mindig hamis.
     */
    @Override
    public boolean eat(Bug b, Spore s) {
        return false;
    }

    /**
     * Minden művelet le van tiltva ebben az állapotban.
     * @return Mindig hamis.
     */
    @Override
    public boolean bite(Bug b, Hypa h) {
        return false;
    }

    @Override
    public boolean eatenByHypa(Bug b, Hypa h){
        b.getBugger().removeBug(b);
        b.getLocation().setBug(null);
        return true;
    }

    /**
     * Végrehajtja a Bug körének lezárását, amely tartalmazhat állapotfrissítéseket
     *
     * @param b A Bug, amelynek a köre lezárul.
     */
    @Override
    public void endOfTurn(Bug b){
        /* Ha 2 kör óta effect alatt áll átállítja a bug strategy-jét normálra */
        if(b.getUnderEffectSince()==2){
            Normal normal = new Normal();
            b.setStrategy(normal);
        }else
            b.increaseUnderEffectSince();
    }

}