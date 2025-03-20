package Bug;

import Controll.Player;
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
    private int underEffectSince;
    private Tekton tekton;
    private Strategy strategy;

    /**
     * Default constructor
     */
    public Bug() {
        underEffectSince = 0;
        tekton = null;
        strategy = new Normal();
    }

    /**
     * @param s
     */
    public void setStrategy(Strategy s) {
        strategy = s;
    }

    /**
     * @param to
     */
    public void move(Tekton to) {
        if (strategy.move(this, to)) {
            to.tryBug(this);
            tekton.setBug(null);
            this.tekton = to;
            strategy.endOfTurn(this);
        }
    }

    /**
     * @param h
     */
    public void bite(Hypa h) {

        if (strategy.bite(this, h)) {
            h.die();
            strategy.endOfTurn(this);
        }
    }

    /**
     * @param s
     */
    public void eat(Spore s) {
        if (strategy.eat(this, s)) {
                if(tekton.getStoredSpores().contains(s)) {
                    s.haveEffect(this);
                    strategy.endOfTurn(this);
                }
        }
    }

    public Tekton getLocation(){return tekton;}

    public int getUnderEffectSince(){return underEffectSince;}
}