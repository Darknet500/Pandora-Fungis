package Bug;

import Controll.Player;
import Controll.Skeleton;
import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;
import java.util.List;

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
        boolean canDo = strategy.move(this, to);
        if (canDo) {
            to.tryBug(this);
            tekton.setBug(null);
            setLocation(to);
        }
    }

    /**
     * @param h
     */
    public void bite(Hypa h) {
        boolean canDo = strategy.bite(this, h);
        if (canDo) { h.die(); }
    }

    /**
     * @param s
     */
    public void eat(Spore s) {
        boolean canDo = strategy.eat(this, s);
        if (canDo) {
            List<Spore> spores = tekton.getStoredSpores();
            if(spores.contains(s)){
                s.haveEffect(this);
                tekton.removeSpore(s);
            }
        }
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