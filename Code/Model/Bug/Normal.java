package Model.Bug;


import Model.Bridge.GameBoard;
import Model.Shroomer.*;
import Model.Tekton.*;
import java.util.List;


/**
 * Hatás alatt nem álló bogár stratégiája.
 */
public class Normal implements Strategy {

    /**
     * statikus számláló, minden konstruktorhíváskor növeljük, ez biztosítja a név egyediséget.
     *  objektum elnevezése: normal[normalID aktuális értéke]
     */
    private static int normalID = 0;

    /**
     * objektum neve, egyedi az egész modellben
     */
    private String name;

    /**
     * konstruktorban elnevezi magát, és beleteszi a gameBoard nameObjectMap-jébe
     */
    public Normal() {
        normalID++;
        name = "normal" + normalID;
        GameBoard.nameObjectMap.put(name, this);
    }

    public String getName(){return name;}

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
        if(canDo && to.tryBug(b)){
            b.getLocation().setBug(null);
            b.setLocation(to);
            return true;
        }
        return false;
    }

    /**
     * A normal bug mindig ehet spórát
     * @return Mindig true
     */
    public boolean eat(Bug b, Spore s) {
        if(b.getLocation().getStoredSpores().contains(s)) {
            int value = s.haveEffect(b);
            b.getBugger().increaseScore(value);
            b.getLocation().removeSpore(s);
            GameBoard.nameObjectMap.remove(s.getName());
            b.resetUnderEffectSince();
            return true;
        }
        return false;
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
        if(hypas.contains(h)){
            h.setIsDyingSinceBitten(0);
            return true;
        }
        return false;
    }

    public boolean eatenByHypa(Bug b, Hypa h){
        return false;
    }

    /**
     * Végrehajtja a Bug körének lezárását, amely tartalmazhat állapotfrissítéseket
     *
     * @param b A Bug, amelynek a köre lezárul.
     */
    public void endOfTurn(Bug b){
        b.increaseUnderEffectSince();
    }


}