package Model.Bug;

import Model.Bridge.GameBoard;
import Model.Shroomer.Spore;
import Model.Tekton.TektonBase;
import java.util.List;


/**
 * A Slowed osztály egy olyan stratégia, amely korlátozza a Bug mozgását,
 * így csak akkor mozoghat, ha az előző két körben nem mozgott.
 */
public class Slowed extends Normal {
    private int movesMade = 0;

    /**
     * Alapértelmezett paraméter nélküli konstruktor
     */
    public Slowed() {
        GameBoard.addReferenceToMaps("slowed", this);
    }

    /**
     * ha más Spóra hatása alatt áll a rovar nem ehet másik spórat
     * @return Mindig false mivel
     */
    @Override
    public boolean eat(Bug b, Spore s) {
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
    public boolean move(Bug b, TektonBase to) {
        if(movesMade>0) return false;
        TektonBase location = b.getLocation();
        List<TektonBase> canReach = location.getNeighboursByHypa();
        boolean canDo = canReach.contains(to);
        if(canDo && to.tryBug(b)){
            movesMade++;
            b.getLocation().setBug(null);
            b.setLocation(to);
            b.getHitbox().onPositionChanged();
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
            GameBoard.removeReferenceFromMaps(b.getStrategy());
            Normal normal = new Normal();
            b.setStrategy(normal);
            b.getHitbox().onStrategyChanged("normal");
        }else
            b.increaseUnderEffectSince();
    }
}