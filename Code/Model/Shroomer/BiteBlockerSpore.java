package Model.Shroomer;

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
     * Alapértelmezett konstruktor
     * Létrehoz egy új BiteBlockerSpore példányt egy adott Shroomerhez kapcsolódva.
     *
     * @param shroomer - A spórát létrehozó Shroomer.
     */
    public BiteBlockerSpore(Shroomer shroomer) {
        super(shroomer);
    }

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