package Bug;

import Controll.Player;
import Controll.Skeleton;
import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;

/**
 * 
 */
public class Bug extends Player {

    /**
     *
     */
    private Tekton tekton;

    /**
     *
     */
    private Strategy strategy;

    /**
     * Default constructor
     */
    public Bug() {}

    /**
     * @param to
     */
    public void move(Tekton to) {
        Skeleton.SKELETON.callStackDepth++;
        Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(this)+"->"+Skeleton.SKELETON.objectNameMap.get(strategy)+": move("+
                Skeleton.SKELETON.objectNameMap.get(this)+", "+Skeleton.SKELETON.objectNameMap.get(strategy)+")");

        boolean canDo = strategy.move(this, to);

        Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(strategy)+"-->>"+Skeleton.SKELETON.objectNameMap.get(this)+": "+canDo);

        if (canDo) {
            Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(this)+"->"+Skeleton.SKELETON.objectNameMap.get(to)+": tryBug("+
                    Skeleton.SKELETON.objectNameMap.get(this)+")");
            to.tryBug(this);
            Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(to)+"-->>"+Skeleton.SKELETON.objectNameMap.get(this));

            Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(this)+"->"+Skeleton.SKELETON.objectNameMap.get(tekton)+": setBug(null)");
            tekton.setBug(null);
            Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(tekton)+"-->>"+Skeleton.SKELETON.objectNameMap.get(this));

            Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(this)+"->"+Skeleton.SKELETON.objectNameMap.get(this)+": setLocation("+
                    Skeleton.SKELETON.objectNameMap.get(to)+")");
            setLocation(to);
            Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(this)+"-->>"+Skeleton.SKELETON.objectNameMap.get(this));
        }

        Skeleton.SKELETON.callStackDepth--;
    }

    /**
     * @param h
     */
    public void bite(Hypa h) {
        Skeleton.SKELETON.callStackDepth++;

        Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(this)+"->"+Skeleton.SKELETON.objectNameMap.get(strategy)+": bite("+
                Skeleton.SKELETON.objectNameMap.get(this)+", "+Skeleton.SKELETON.objectNameMap.get(h)+")");

        boolean canDo = strategy.bite(this, h);

        Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(strategy)+"-->>"+Skeleton.SKELETON.objectNameMap.get(this)+": "+canDo);

        if (canDo) {
            Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(this)+"->"+Skeleton.SKELETON.objectNameMap.get(h)+": die()");

            h.die();

            Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(h)+"->"+Skeleton.SKELETON.objectNameMap.get(this));
        }

        Skeleton.SKELETON.callStackDepth--;
    }

    /**
     * @param s
     */
    public void eat(Spore s) {
        Skeleton.SKELETON.callStackDepth++;

        Skeleton.SKELETON.printCall(Skeleton.SKELETON.objectNameMap.get(this)+"->"+Skeleton.SKELETON.objectNameMap.get(strategy)+": eat("+
                Skeleton.SKELETON.objectNameMap.get(this)+", "+Skeleton.SKELETON.objectNameMap.get(s)+")");


    }

    /**
     *
     * @return
     */

    public Tekton getLocation(){
        return tekton;
    }

    /**
     * @param newLoc
     */

    public void setLocation(Tekton newLoc){
        this.tekton = newLoc;
    }

    /**
     * @param s
     */
    public void setStrategy(Strategy s) {
        this.strategy = s;
    }
}