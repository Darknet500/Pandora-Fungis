package Shroomer;

import Bug.Bug;

import java.util.Collections;
import java.util.List;
import Bug.Paralyzed;
import Bug.Strategy;

/**
 * A ParalyzerSpore egy speciális spóra, amely megbénítja a Bug-ot.
 */
public class ParalyzerSpore extends Spore {

    /**
     * Alapértelmezett konstruktor
     * Létrehoz egy új ParalyzerSpore példányt egy adott Shroomerhez kapcsolódva.
     *
     * @param shroomer - A spórát létrehozó Shroomer.
     */
    public ParalyzerSpore(Shroomer shroomer) {
        super(shroomer);
    }

    /**
     * A spóra hatást gyakorol egy Bug objektumra, amelynek hatására a bogár megbénul.
     *
     * @param b - A Bug, amelyre a spóra hatással van.
     * @return - A hatás típusának sorszáma. (jelen esetben 4)
     */
    public int haveEffect(Bug b) {
        Strategy paralyzed = new Paralyzed();
        b.setStrategy(paralyzed);
        return 4;  //ennek a spóratípusnak a tápanyagtartalma (pontok)
    }
}