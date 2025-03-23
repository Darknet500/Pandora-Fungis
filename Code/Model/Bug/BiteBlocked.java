package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;
import static Controll.Skeleton.SKELETON;

import java.util.Collections;
import java.util.List;

/**
 * A BiteBlocked osztály egy speciális stratégia, amely megakadályozza,
 * hogy a Bug harapni vagy enni tudjon. Ez az osztály a Normal osztályból származik,
 * de korlátozza a harapás és evés lehetőségét.
 */
public class BiteBlocked extends Normal {
    
    /**
     * Megakadályozza, hogy a Bug megharapja a megadott Hypa-t.
     *
     * @param b A Bug, amely harapni próbál.
     * @param h A Hypa, amelyet megpróbál megharapni.
     * @return Mindig hamis, mivel a harapás blokkolva van.
     */
    @Override
    public boolean bite(Bug b, Hypa h) {
        SKELETON.printCall(this, List.of(b, h), "bite");
        SKELETON.printReturn("false");
        return false;
    }

    /**
     * Megakadályozza, hogy a Bug egyen.
     *
     * @return Mindig hamis, mivel az evés blokkolva van.
     */
    @Override
    public boolean eat() {
        SKELETON.printCall(this, Collections.emptyList(), "eat");
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
        /** Ha 2 kör óta effect alatt áll átállítja a bug strategy-jét normálra**/
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