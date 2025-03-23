package Shroomer;

import Bug.Bug;
import Bug.Strategy;
import Bug.Slowed;
import static Controll.Skeleton.SKELETON;
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
    }

    /**
     * A spóra hatást gyakorol egy Bug objektumra, amelynek hatására a bogár mozgása lassul.
     *
     * @param b - A Bug, amelyre a spóra hatással van.
     * @return - A hatás típusának sorszáma. (jelen esetben 3)
     */
    public int haveEffect(Bug b) {
        SKELETON.printCall(this, List.of(b), "haveEffect");
        Strategy slowed = new Slowed();
        SKELETON.objectNameMap.put(slowed, "slowed");
        b.setStrategy(slowed);
        SKELETON.printReturn(String.format("%d", 3));
        return 3;
    }
}