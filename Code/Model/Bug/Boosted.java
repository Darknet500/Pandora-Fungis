package Bug;

import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Controll.Skeleton.SKELETON;

/**
 * A Boosted osztály egy olyan stratégia, amely lehetővé teszi, hogy a Bug
 * gyorsabban mozogjon.
 */
public class Boosted extends Normal {

    @Override
    public boolean eat(){
        SKELETON.printCall(this, Collections.emptyList(), "eat");
        SKELETON.printReturn("false");
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
        SKELETON.printCall(this, List.of(b, to), "move");
        Tekton location = b.getLocation();
        Set<Tekton> canReach = new HashSet<Tekton>();
        canReach.addAll(location.getNeighboursByHypa());
        for(Tekton t : canReach){
            canReach.addAll(t.getNeighboursByHypa());
        }
        boolean canDo = canReach.contains(to);
        SKELETON.printReturn(canDo?"true":"false");
        return canDo;
    }

    @Override
    public void endOfTurn(Bug b){
        SKELETON.printCall(this, List.of(b), "endOfTurn");
        if(b.getUnderEffectSince()==2){
            Normal normal = new Normal();
            SKELETON.objectNameMap.put(normal, "normal");
            b.setStrategy(normal);
        }
        SKELETON.printReturn("");
    }

}