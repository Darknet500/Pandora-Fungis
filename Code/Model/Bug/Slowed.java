package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * A Slowed osztály egy olyan stratégia, amely korlátozza a Bug mozgását,
 * így csak akkor mozoghat, ha az előző két körben nem mozgott.
 */
public class Slowed extends Normal {
    private int movesMade;

    /**
     * Alapértelmezett paraméter nélküli konstruktor
     */
    public Slowed() {
        movesMade = 0;
    }

    /**
     * ha más Spóra hatása alatt áll a rovar nem ehet másik spórat
     * @return Mindig false mivel
     */
    @Override
    public boolean eat() {
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
        if(movesMade>0) return false;
        Tekton location = b.getLocation();
        List<Tekton> canReach = location.getNeighboursByHypa();
        if(canReach.contains(to)){
            movesMade++;
            return true;
        }

        return false;
    }

    /**
     * Végrehajtja a Bug körének lezárását, amely tartalmazhat állapotfrissítéseket
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