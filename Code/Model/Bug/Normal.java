package Bug;


import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.Collections;
import java.util.List;


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
        Tekton location = b.getLocation();
        List<Tekton> canReach = location.getNeighboursByHypa();
        boolean canDo = canReach.contains(to);
        return canDo;
    }

    /**
     * A normal bug mindig ehet spórát
     * @return Mindig true
     */
    public boolean eat() {
        return true;
    }

    /**
     * A bogár elharap egy fonalat
     * @param b A Bug, amely harapni próbál.
     * @param h A Hypa, amelyet a Bug meg akar harapni.
     * @return bool érték arról hogy a bogár elharaphatja e a fonalat
     */
    public boolean bite(Bug b, Hypa h) {
        if (h.getIsDyingSinceBitten()!=-1) return false;
        Tekton location = b.getLocation();
        List<Hypa> hypas = location.getHypas();
        boolean canDo = hypas.contains(h);
        return canDo;
    }

    public boolean canBeEaten(){
        return false;
    }

    /**
     * Végrehajtja a Bug körének lezárását, amely tartalmazhat állapotfrissítéseket
     *
     * @param b A Bug, amelynek a köre lezárul.
     */
    public void endOfTurn(Bug b){
    }


}