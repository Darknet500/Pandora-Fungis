package Shroomer;

import Bug.Bug;

import java.util.List;
import Bug.Paralyzed;
import Bug.Strategy;
import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class ParalyzerSpore extends Spore {

    /**
     * Default constructor
     */
    public ParalyzerSpore(Shroomer shroomer) {
        super(shroomer);
    }

    /**
     * @param b 
     * @return
     */
    public int haveEffect(Bug b) {
        SKELETON.printCall(this, List.of(b), "haveEffect");
        Strategy paralyzed = new Paralyzed();
        SKELETON.objectNameMap.put(paralyzed, "paralyzed");
        b.setStrategy(paralyzed);
        SKELETON.printReturn(String.format("%d", 4));
        return 4;
    }
}