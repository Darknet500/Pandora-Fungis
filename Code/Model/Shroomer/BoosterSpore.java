package Shroomer;

import Bug.Bug;

import java.util.Collections;
import java.util.List;
import Bug.Strategy;
import Bug.Boosted;

/**
 * A BoosterSpore egy speciális spóra, amely hatást gyakorol a Bug mozgására.
 */
public class BoosterSpore extends Spore {

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új BoosterSpore példányt a megadott Shroomerhez kapcsolódva.
     *
     * @param shroomer - A spórát létrehozó Shroomer.
     */
    public BoosterSpore(Shroomer shroomer) {
        super(shroomer);
    }

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