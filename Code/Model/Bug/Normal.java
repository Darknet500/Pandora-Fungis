package Bug;


import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.Collections;
import java.util.List;

import static Controll.Skeleton.SKELETON;

/**
 * Hatás alatt nem álló bogár stratégiája.
 */
public class Normal implements Strategy {

    /**
     * Meghatározza, hogy a Bug át tud-e mozogni egy másik Tektonra.
     *
     * @param b  A Bug, amely mozogni próbál.
     * @param to A cél Tekton helyszín.
     * @return Igaz, ha a mozgást el lehet végezni, hamis egyébként.
     */
    public boolean move(Bug b, Tekton to) {
        SKELETON.printCall(this, List.of(b, to), "move");
        Tekton location = b.getLocation();
        List<Tekton> canReach = location.getNeighboursByHypa();
        boolean canDo = canReach.contains(to);
        SKELETON.printReturn(canDo?"true":"false");
        return canDo;
    }

    /**
     * A normal bug mindig ehet spórát
     * @return Mindig true
     */
    public boolean eat() {
        SKELETON.printCall(this, Collections.emptyList(), "eat");
        SKELETON.printReturn("true");
        return true;
    }

    /**
     * A bogár elharap egy fonalat
     * @param b A Bug, amely harapni próbál.
     * @param h A Hypa, amelyet a Bug meg akar harapni.
     * @return bool érték arról hogy a bogár elharaphatja e a fonalat
     */
    public boolean bite(Bug b, Hypa h) {
        SKELETON.printCall(this, List.of(b, h), "bite");
        Tekton location = b.getLocation();
        List<Hypa> hypas = location.getHypas();
        boolean canDo = hypas.contains(h);
        SKELETON.printReturn(canDo?"true":"false");
        return canDo;
    }

    /**
     * Végrehajtja a Bug körének lezárását, amely tartalmazhat állapotfrissítéseket
     *
     * @param b A Bug, amelynek a köre lezárul.
     */
    public void endOfTurn(Bug b){
        SKELETON.printCall(this, List.of(b), "endOfTurn");
        SKELETON.printReturn("");

    }
}