package Model.Bug;

import Model.Bridge.GameBoard;
import Model.Shroomer.Hypa;
import Model.Shroomer.Spore;
import Model.Tekton.TektonBase;

/**
 * A Paralyzed osztály olyan stratégia, amely teljesen megbénítja a Bugot,
 * így nem tud mozogni, harapni vagy enni.
 */
public class Paralyzed extends Normal {

    /**
     * konstruktorban elnevezi magát, és beleteszi a gameBoard nameObjectMap-jébe
     */
    public Paralyzed(){
        GameBoard.addReferenceToMaps("paralyzed", this);
    }

    /**
     * Minden művelet le van tiltva ebben az állapotban.
     * @return Mindig hamis.
     */
    @Override
    public boolean move(Bug b, TektonBase to) {
        return false;
    }

    /**
     * Minden művelet le van tiltva ebben az állapotban.
     * @return Mindig hamis.
     */
    @Override
    public boolean eat(Bug b, Spore s) {
        return false;
    }

    /**
     * Minden művelet le van tiltva ebben az állapotban.
     * @return Mindig hamis.
     */
    @Override
    public boolean bite(Bug b, Hypa h) {
        return false;
    }

    @Override
    public boolean eatenByHypa(Bug b, Hypa h){
        b.getBugger().removeBug(b);
        b.getLocation().setBug(null);
        return true;
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
            GameBoard.removeReferenceFromMaps(b.getStrategy());
            Normal normal = new Normal();
            b.setStrategy(normal);
        }else
            b.increaseUnderEffectSince();
    }

}