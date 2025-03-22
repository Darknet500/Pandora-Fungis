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
 * Bogárt megvalósító osztály, amit a játékos írányít.
 */
public class Bug extends Player {

    /**
     * A Bug jelenlegi helyét reprezentáló Tekton példány.
     */
    private Tekton tekton;

    /**
     * A Bug által használt stratégia a mozgáshoz, harapáshoz és evéshez. Ez jelenti azt, hogy milyen hatás alatt áll.
     */
    private Strategy strategy;

    /**
     * Alapértelmezett paraméter nélküli konstruktor, amely a Bug objektumot hely nélküli állapotban
     * és egy normál stratégiával inicializálja.
     */
    public Bug() {
        tekton = null;
        strategy = new Normal();
    }

    /**
     * Beállítja a Bug stratégiáját.
     *
     * @param s Az új stratégia.
     */
    public void setStrategy(Strategy s) {
        SKELETON.printCall(this, List.of(s), "setStrategy");
        strategy = s;
        SKELETON.printReturn("");
    }

     /**
     * A Bug új Tekton helyre mozog.
     * Ha a mozgás sikeres, a Bug interakcióba lép az új Tektonnal,
     * frissíti a helyét, és végrehajtja a kör végi logikát.
     *
     * @param to A cél Tekton példány.
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
     * A Bug megharap egy Hypa-t.
     * Ha a stratégia engedélyezi, a Hypa elpusztul,
     * és a Bug köre véget ér.
     *
     * @param h A megharapandó Hypa példány.
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
     * A Bug megeszik egy Spore-t.
     * Ha a stratégia engedélyezi az evést, és a spóra elérhető az aktuális Tektonon,
     * annak hatása érvényesül a Bug-ra, növeli a pontszámát, és a spóra eltávolításra kerül.
     *
     * @param s A megevendő Spore példány.
     */
    public void eat(Spore s) {
        SKELETON.printCall(this, List.of(s), "eat");
        if (strategy.eat()) {
                if(tekton.getStoredSpores().contains(s)) {
                    int value = s.haveEffect(this);
                    increaseScore(value);
                    tekton.removeSpore(s);
                    strategy.endOfTurn(this);
                }
        }
        SKELETON.printReturn("");
    }

    /**
     * Visszaadja a Bug jelenlegi Tekton helyzetét.
     *
     * @return A Bug helyzetét reprezentáló Tekton példány.
     */
    public Tekton getLocation(){
        SKELETON.printCall(this, Collections.emptyList(), "getLocation");
        SKELETON.printReturn(SKELETON.objectNameMap.get(tekton));
        return tekton;
    }

     /**
     * Beállítja a Bug helyzetét egy új Tektonra.
     *
     * @param t Az új Tekton helyszín.
     */
    public void setLocation(Tekton t){
        SKELETON.printCall(this, List.of(t), "setLocation");
        tekton = t;
        SKELETON.printReturn("");
    }

    /**
     * Visszaadja, hogy a Bug hány kör óta van hatás alatt.
     * Az érték egy 0 és 2 közötti egész szám, amelyet a SKELETON rendszer kér be.
     *
     * @return A körök száma, amióta a Bug hatás alatt áll.
     */
    public int getUnderEffectSince(){
        SKELETON.printCall(this, Collections.emptyList(), "getUnderEffectSince");
        int ret = SKELETON.getNumericInput("under Effect since =\n Kérek egy 0 és 2 közötti egész számot\n", 0, 2);
        SKELETON.printReturn(String.format("%d", ret));
        return ret;
    }
}