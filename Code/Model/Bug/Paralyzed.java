package Bug;

import Controll.Skeleton;
import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.Collections;
import java.util.List;

import static Controll.Skeleton.SKELETON;

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
        SKELETON.printCall(this, List.of(b, to), "move");
        SKELETON.printReturn("false");
        return false;
    }

    /**
     * Minden művelet le van tiltva ebben az állapotban.
     * @return Mindig hamis.
     */
    @Override
    public boolean eat() {
        SKELETON.printCall(this, Collections.emptyList(), "eat");
        SKELETON.printReturn("false");
        return false;
    }

    /**
     * Minden művelet le van tiltva ebben az állapotban.
     * @return Mindig hamis.
     */
    @Override
    public boolean bite(Bug b, Hypa h) {
        SKELETON.printCall(this, List.of(b, h), "bite");
        SKELETON.printReturn("false");
        return false;
    }

    /**
     * Végrehajtja a Bug körének lezárását, amely tartalmazhat állapotfrissítéseket
     *
     * @param b A Bug, amelynek a köre lezárul.
     */
    @Override
    public void endOfTurn(Bug b){
        SKELETON.printCall(this, List.of(b), "endOfTurn");
        /** Ha 2 kör óta effect alatt áll átállítja a bug strategy-jét normálra **/
        if(b.getUnderEffectSince()==2){
            Normal normal = new Normal();
            SKELETON.objectNameMap.put(normal, "normal");
            SKELETON.printCall(normal, Collections.emptyList(), "Normal" );
            SKELETON.printReturn("");
            b.setStrategy(normal);
        }
        SKELETON.printReturn("");
    }

}