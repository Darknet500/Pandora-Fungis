package Model.Shroomer;


import Model.Bridge.GameBoard;
import Model.Bug.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A SlowerSpore egy speciális spóra, amely lassítja a Bug mozgását.
 */
public class SlowerSpore extends Spore {

    /**
     * Alapértelmezett konstruktor
     * Létrehoz egy új SlowerSpore példányt egy adott Shroomerhez kapcsolódva.
     *
     * @param shroomer - A spórát létrehozó Shroomer.
     */
    public SlowerSpore(Shroomer shroomer) {
        super(shroomer);
        GameBoard.addReferenceToMaps("slowerspore", this);
    }

    /**
     * A spóra hatást gyakorol egy Bug objektumra, amelynek hatására a bogár mozgása lassul.
     *
     * @param b - A Bug, amelyre a spóra hatással van.
     * @return - A hatás típusának sorszáma. (jelen esetben 3)
     */
    public int haveEffect(Bug b) {
        Strategy slowed = new Slowed();
        b.setStrategy(slowed);
        return 3;  //ennek a spóratípusnak a tápanyagtartalma (pontok)
    }
}