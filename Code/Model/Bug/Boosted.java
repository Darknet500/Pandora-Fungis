package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.*;


/**
 * A Boosted osztály egy olyan stratégia, amely lehetővé teszi, hogy a Bug
 * gyorsabban mozogjon.
 */
public class Boosted extends Normal {


    /**
     * ha más Spóra hatása alatt áll a rovar nem ehet másik spórat
     * @return Mindig false mivel
     */
    @Override
    public boolean eat(){
        return false;
    }

    /**
     * Lehetővé teszi, hogy a Bug két Hypa távolságra mozogjon.
     * Ez azt jelenti, hogy a Bug nemcsak a közvetlen szomszédos helyekre léphet, hanem egy közvetítő Hypa segítségével tovább is haladhat.
     * A függvény megnézi, hogy a jelenlegi Tekton szomszédjainak szomszédjai között van-e a a cél Tekton.
     * @param b  A Bug, amely mozogni próbál.
     * @param to A cél Tekton.
     * @return Igaz, ha a mozgás lehetséges, hamis egyébként.
     */
    @Override
    public boolean move(Bug b, Tekton to) {
        Tekton location = b.getLocation();
        Set<Tekton> canReach = new HashSet<Tekton>();
        canReach.addAll(location.getNeighboursByHypa());
        Queue<Tekton> queue = new ArrayDeque<>();
        queue.addAll(canReach);
        while (!queue.isEmpty()) {
            Tekton current = queue.poll();
            canReach.addAll(current.getNeighboursByHypa());
        }

        boolean canDo = canReach.contains(to);
        return canDo;
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