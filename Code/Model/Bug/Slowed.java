package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;
import Controll.Skeleton;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static Controll.Skeleton.SKELETON;

/**
 * A Slowed osztály egy olyan stratégia, amely korlátozza a Bug mozgását,
 * így csak akkor mozoghat, ha az előző két körben nem mozgott.
 */
public class Slowed extends Normal {
    /**
     * Alapértelmezett paraméter nélküli konstruktor
     */
    public Slowed() {}

    @Override
    public boolean eat() {
        SKELETON.printCall(this, Collections.emptyList(), "eat");
        SKELETON.printReturn("false");
        return false;
    }

    /**
     * Meghatározza, hogy a Bug képes-e mozogni az adott körben.
     * A felhasználótól megkérdezi, hogy mozgott-e a bogár az előző két körben, ez alapján dönt.
     *
     * @param b  A Bug, amely mozogni próbál.
     * @param to A cél Tekton helyszín.
     * @return Igaz, ha a mozgás engedélyezett, hamis egyébként.
     */
    @Override
    public boolean move(Bug b, Tekton to) {
        SKELETON.printCall(this, Arrays.asList(b, to), "move");
        Tekton location = b.getLocation();
        List<Tekton> canReach = location.getNeighboursByHypa();
        int rethalf = Skeleton.SKELETON.getNumericInput(
                "moves made in the prev. 2 rounds = ?\n 0 vagy 1\n", 0, 1);

        boolean ret = canReach.contains(to)&& rethalf ==0;

        SKELETON.printReturn(ret?"true":"false");
        return ret;
    }

    public void endOfTurn(Bug b){
        SKELETON.printCall(this, List.of(b), "endOfTurn");
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