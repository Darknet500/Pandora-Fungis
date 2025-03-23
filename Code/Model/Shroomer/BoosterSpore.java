package Shroomer;

import Bug.Bug;

import java.util.List;
import Bug.Strategy;
import Bug.Boosted;
import static Controll.Skeleton.SKELETON;

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
        SKELETON.printCall(this, List.of(b), "haveEffect");
        Strategy boosted = new Boosted();
        SKELETON.objectNameMap.put(boosted, "boosted");
        b.setStrategy(boosted);
        SKELETON.printReturn(String.format("%d", 1));
        return 1;
    }
}