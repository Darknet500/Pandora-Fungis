package Model.Bug;
import Model.Shroomer.*;
import Model.Bridge.GameBoard;

/**
 * A BiteBlocked osztály egy speciális stratégia, amely megakadályozza,
 * hogy a Bug harapni vagy enni tudjon. Ez az osztály a Normal osztályból származik,
 * de korlátozza a harapás és evés lehetőségét.
 */
public class BiteBlocked extends Normal {


    /**
     * konstruktorban beleteszi a gameBoard nameObjectMap-jébe magát
     */
    public BiteBlocked() {
        GameBoard.addReferenceToMaps("biteblocked", this);
    }

    /**
     * Megakadályozza, hogy a Bug megharapja a megadott Hypa-t.
     *
     * @param b A Bug, amely harapni próbál.
     * @param h A Hypa, amelyet megpróbál megharapni.
     * @return Mindig hamis, mivel a harapás blokkolva van.
     */
    @Override
    public boolean bite(Bug b, Hypa h) {
        return false;
    }

    /**
     * Megakadályozza, hogy a Bug egyen.
     *
     * @return Mindig hamis, mivel az evés blokkolva van.
     */
    @Override
    public boolean eat(Bug b, Spore s) {
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
            GameBoard.removeReferenceFromMaps(b.getStrategy());
            Normal normal = new Normal();
            b.setStrategy(normal);
        }else
            b.increaseUnderEffectSince();
    }

}