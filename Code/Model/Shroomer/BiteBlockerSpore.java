package Model.Shroomer;

import Model.Bridge.GameBoard;
import Model.Bug.Bug;

import java.util.Collections;
import java.util.List;
import Model.Bug.Strategy;
import Model.Bug.BiteBlocked;

/**
 * A BiteBlockerSpore egy speciális spóra, amely megakadályozza a Bug harapását.
 */
public class BiteBlockerSpore extends Spore {

    /**
     * statikus számláló, minden konstruktorhíváskor növeljük, ez biztosítja a név egyediséget.
     *  objektum elnevezése: biteblockerspore[biteBlockerSporeID aktuális értéke]
     */
    private static int biteBlockerSporeID = 0;

    /**
     * objektum neve, egyedi az egész modellben
     */
    private String name;

    /**
     * Alapértelmezett konstruktor
     * Létrehoz egy új BiteBlockerSpore példányt egy adott Shroomerhez kapcsolódva.
     * elnevezi magát és beleteszi a gameBoard nameObjectMap-jébe
     *
     * @param shroomer - A spórát létrehozó Shroomer.
     */
    public BiteBlockerSpore(Shroomer shroomer) {
        super(shroomer);
        biteBlockerSporeID++;
        name = "biteblockerspore"+biteBlockerSporeID;
        GameBoard.nameObjectMap.put(name, this);
    }

    @Override
    public String getName(){return name;}

    /**
     * A spóra hatást gyakorol egy Bug objektumra, amelynek hatására a harapási képessége blokkolódik.
     *
     * @param b - A Bug, amelyre a spóra hatással van.
     * @return - A hatás típusának sorszáma. (jelen esetben 2)
     */
    public int haveEffect(Bug b) {
        Strategy biteBlocked = new BiteBlocked();
        b.setStrategy(biteBlocked);
        return 2; //ennek a spóratípusnak a tápanyagtartalma (pontok)
    }
}