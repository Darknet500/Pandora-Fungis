package Shroomer;

import Bug.Bug;

import java.util.Collections;
import java.util.List;
import Bug.Paralyzed;
import Bug.Strategy;
import static Controll.Skeleton.SKELETON;

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
        SKELETON.printCall(this, List.of(b), "haveEffect");
        Strategy paralyzed = new Paralyzed();
        SKELETON.objectNameMap.put(paralyzed, "paralyzed");
        SKELETON.printCall(paralyzed, Collections.emptyList(), "Paralyzed" );
        SKELETON.printReturn("");
        b.setStrategy(paralyzed);
        SKELETON.printReturn(String.format("%d", 4));
        return 4;
    }
}