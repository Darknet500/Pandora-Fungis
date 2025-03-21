package Bug;

import Controll.Player;
import Controll.Skeleton;
import Shroomer.Hypa;
import Shroomer.Spore;
import Tekton.Tekton;
import static Controll.Skeleton.SKELETON;

import java.util.Collections;
import java.util.List;
/**
 * 
 */
public class Bug extends Player {

    /**
     *
     */
    private Tekton tekton;
    private Strategy strategy;

    /**
     * Default constructor
     */
    public Bug() {
        tekton = null;
        strategy = new Normal();
    }

    /**
     * @param s
     */
    public void setStrategy(Strategy s) {
        SKELETON.printCall(this, List.of(s), "setStrategy");
        strategy = s;
        SKELETON.printReturn("");
    }

    /**
     * @param to
     */
    public void move(Tekton to) {
        SKELETON.printCall(this, List.of(to), "move");
        if (strategy.move(this, to)) {
            to.tryBug(this);
            tekton.setBug(null);
            setLocation(to);
            strategy.endOfTurn(this);
        }
        SKELETON.printReturn("");
    }

    /**
     * @param h
     */
    public void bite(Hypa h) {
        SKELETON.printCall(this, List.of(h), "bite");
        if (strategy.bite(this, h)) {
            h.die();
            strategy.endOfTurn(this);
        }
        SKELETON.printReturn("");
    }

    /**
     * @param s
     */
    public void eat(Spore s) {
        SKELETON.printCall(this, List.of(s), "eat");
        if (strategy.eat()) {
                if(tekton.getStoredSpores().contains(s)) {
                    int value = s.haveEffect(this);
                    increaseScore(value);
                    strategy.endOfTurn(this);
                }
        }
        SKELETON.printReturn("");
    }

    public Tekton getLocation(){
        SKELETON.printCall(this, Collections.emptyList(), "getLocation");
        SKELETON.printReturn(SKELETON.objectNameMap.get(tekton));
        return tekton;
    }

    public void setLocation(Tekton t){
        SKELETON.printCall(this, List.of(t), "setLocation");
        tekton = t;
        SKELETON.printReturn("");
    }

    public int getUnderEffectSince(){
        SKELETON.printCall(this, Collections.emptyList(), "getUnderEffectSince");
        int ret = SKELETON.getNumericInput("under Effect since =", 0, 20);
        SKELETON.printReturn(String.format("%d", ret));
        return ret;
    }
}