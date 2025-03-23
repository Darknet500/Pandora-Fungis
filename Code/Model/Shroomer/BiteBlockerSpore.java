package Shroomer;

import Bug.Bug;

import java.util.List;
import Bug.Strategy;
import Bug.BiteBlocked;
import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class BiteBlockerSpore extends Spore {

    /**
     * Default constructor
     */
    public BiteBlockerSpore(Shroomer shroomer) {
        super(shroomer);
    }

    /**
     * @param b 
     * @return
     */
    public int haveEffect(Bug b) {
        SKELETON.printCall(this, List.of(b), "haveEffect");
        Strategy biteBlocked = new BiteBlocked();
        SKELETON.objectNameMap.put(biteBlocked, "biteBlocked");
        b.setStrategy(biteBlocked);
        SKELETON.printReturn(String.format("%d", 2));
        return 2;
    }
}