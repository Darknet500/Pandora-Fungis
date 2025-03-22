package Shroomer;

import Bug.Bug;
import Bug.Strategy;
import Bug.Slowed;
import static Controll.Skeleton.SKELETON;
import java.util.List;

/**
 * 
 */
public class SlowerSpore extends Spore {

    /**
     * Default constructor
     */
    public SlowerSpore(Shroomer shroomer) {
        super(shroomer);
    }

    /**
     * @param b 
     * @return
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