package Shroomer;

import Bug.Bug;

import java.util.List;
import Bug.Strategy;
import Bug.Boosted;
import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class BoosterSpore extends Spore {

    /**
     * Default constructor
     */
    public BoosterSpore(Shroomer shroomer) {
        super(shroomer);
    }

    /**
     * @param b 
     * @return
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