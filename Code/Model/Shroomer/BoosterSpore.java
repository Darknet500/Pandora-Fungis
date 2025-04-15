package Model.Shroomer;

import Model.Bridge.GameBoard;
import Model.Bug.Bug;

import java.util.Collections;
import java.util.List;
import Model.Bug.Strategy;
import Model.Bug.Boosted;

/**
 * A BoosterSpore egy speciális spóra, amely hatást gyakorol a Bug mozgására.
 */
public class BoosterSpore extends Spore {

    /**
     * statikus számláló, minden konstruktorhíváskor növeljük, ez biztosítja a név egyediséget.
     *  objektum elnevezése: boosterspore[boosterSporeID aktuális értéke]
     */
    private static int boosterSporeID = 0;

    /**
     * objektum neve, egyedi az egész modellben
     */
    private String name;

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új BoosterSpore példányt a megadott Shroomerhez kapcsolódva.
     *
     * @param shroomer - A spórát létrehozó Shroomer.
     */
    public BoosterSpore(Shroomer shroomer) {
        super(shroomer);
        boosterSporeID++;
        name = "boosterspore" + boosterSporeID;
        GameBoard.nameObjectMap.put(name, this);
    }

    @Override
    public String getName() {return name;}

    /**
     * A spóra hatást gyakorol egy megadott Bug objektumra, és így a mozgása gyorsul.
     *
     * @param b - A Bug példány, amelyre a spóra hat.
     * @return - A hatás típusának sorszáma. (jelen esetben 1)
     */
    public int haveEffect(Bug b) {
        Strategy boosted = new Boosted();
        b.setStrategy(boosted);
        return 1;  //ennek a spóratípusnak a tápanyagtartalma (pontok)
    }
}