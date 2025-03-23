package Shroomer;

import Bug.Bug;

import java.util.Collections;
import java.util.List;
import Bug.Strategy;
import Bug.BiteBlocked;
import static Controll.Skeleton.SKELETON;

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
        SKELETON.printCall(this, List.of(b), "haveEffect");
        Strategy biteBlocked = new BiteBlocked();
        SKELETON.objectNameMap.put(biteBlocked, "biteBlocked");
        SKELETON.printCall(biteBlocked, Collections.emptyList(), "BiteBlocked" );
        SKELETON.printReturn("");
        b.setStrategy(biteBlocked);
        SKELETON.printReturn(String.format("%d", 2));
        return 2;
    }
}