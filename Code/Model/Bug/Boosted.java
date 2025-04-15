package Model.Bug;

import Model.Bridge.GameBoard;
import Model.Shroomer.*;
import Model.Tekton.*;
import java.util.*;


/**
 * A Boosted osztály egy olyan stratégia, amely lehetővé teszi, hogy a Bug
 * gyorsabban mozogjon.
 */
public class Boosted extends Normal {

    /**
     * statikus számláló, minden konstruktorhíváskor növeljük, ez biztosítja a név egyediséget.
     *  objektum elnevezése: boosted[boostedID aktuális értéke]
     */
    private static int boostedID = 0;

    /**
     * objektum neve, egyedi az egész modellben
     */
    private String name;

    /**
     * konstruktorban elnevezi magát, és beleteszi a gameBoard nameObjectMap-jébe
     */
    public Boosted() {
        boostedID++;
        name = "boosted" + boostedID;
        GameBoard.nameObjectMap.put(name, this);
    }

    public String getName() {return name;}

    /**
     * ha más Spóra hatása alatt áll a rovar nem ehet másik spórat
     * @return Mindig false mivel
     */
    @Override
    public boolean eat(Bug b, Spore s){
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
        if(canDo && to.tryBug(b)){
            b.getLocation().setBug(null);
            b.setLocation(to);
            return true;
        }
        return false;
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
            GameBoard.nameObjectMap.remove(b.getStrategy().getName());
            Normal normal = new Normal();
            b.setStrategy(normal);
        }else
            b.increaseUnderEffectSince();
    }

}