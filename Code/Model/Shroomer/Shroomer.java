package Model.Shroomer;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import Model.Bridge.GameBoard;
import Model.Bridge.Player;
import Model.Tekton.Tekton;
import Model.Bug.Bug;


/**
 * A Shroomer osztály a játékos egyik típusa, amely gombatesteket hozhat létre
 * és fonalakat (Hypa) növeszthet a pályán.
 * <p>
 * A Shroomer felelős a gombatestek és fonalak kezeléséért, valamint a spóraszórásért.
 */
public class Shroomer extends Player {
    /**
     * Egy függvény, amely egy új Mushroom példányt hoz létre a megadott helyen.
     */
    private BiFunction<Shroomer, Tekton, Mushroom> mushroomctor;

    /**
     * A Shroomer által létrehozott gombatestek listája.
     */
    private List<Mushroom> mushrooms;

    /**
     * A Shroomer által létrehozott fonalak listája.
     */
    private List<Hypa> HypaList;

    /**
     * Az érték amely kör után halnak meg a gombásznak a gombafonalai miután elharapja egy bogár.
     */
    private final int hypaDieAfterBite;

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új Shroomer példányt, amely képes gombatesteket növeszteni.
     *
     * @param mushroomctor - A függvény, amely létrehozza a gombatesteket.
     */
    public Shroomer(BiFunction<Shroomer, Tekton, Mushroom> mushroomctor, int hypaDieAfterBite) {
        mushrooms = new ArrayList<Mushroom>();
        HypaList = new ArrayList<Hypa>();
        this.mushroomctor = mushroomctor;
        this.hypaDieAfterBite=hypaDieAfterBite;
        GameBoard.addReferenceToMaps("shroomer", this);
    }

    public List<Mushroom> getMushrooms() {
        return mushrooms;
    }

    public List<Hypa> getHypaList() {
        return HypaList;
    }

    /**
     *
     * @return az érték, amennyi körig élnek a gombafonalai harapás után
     */
    public int getHypaDieAfterBite(){
        return hypaDieAfterBite;
    }

    /**
     * Egy új fonalat (Hypa) növeszt két adott Tekton között, ha lehetséges.
     * Ha a cél Tekton elfogadja a hipát, a hipát hozzáadja a listához és
     * megpróbál gombát növeszteni a cél Tektonon, ha ott teljesül minden feltétel..
     *
     * @param start - A hipa kiindulási Tektonja.
     * @param target - A hipa cél Tektonja.
     */
    public boolean growHypa(Tekton start, Tekton target) {
        boolean validStart = false;
        for(Mushroom mushroom : mushrooms) {
            if(mushroom.getLocation()==start){
                validStart=true;
            }
        }

        for(Hypa h : HypaList) {
            if(h.getEnd1()==start && h.getEnd2()!=target || h.getEnd2()==start && h.getEnd1()!=target){
                validStart=true;
            } else if(h.getEnd1()==start && h.getEnd2()==target || h.getEnd2()==start && h.getEnd1()==target){
                validStart=false;
                break;
            }
        }

        if(!target.getNeighbours().contains(start) || start==target){
            validStart=false;
        }

        if(validStart){
           if (target.acceptHypa(this)){
               Hypa hypa= new Hypa(start, target, this);
               start.connectHypa(hypa);
               target.connectHypa(hypa);

               HypaList.add(hypa);
               tryGrowMushroom(target);
               traverseHypaNetwork();
               return true;
           }
        }
        return false;
    }

    /**
     * Hozzáad egy új gombatestet a Shroomer gombalistájához.
     *
     * @param mushroom - Az új gombatest.
     */
    public void addMushroom(Mushroom mushroom){
        mushrooms.add(mushroom);
    }

    /**
     * Egy távolabbi célponthoz növeszt hipát egy közbülső Tekonon keresztül.
     *
     * @param start - A hipa kezdő Tektonja.
     * @param middle - A köztes Tekton, amelyen keresztül halad a hipa.
     * @param target - A végső cél Tektonja.
     */
    public boolean growHypaFar(Tekton start,Tekton middle, Tekton target) {
        if(start.hasSpore()){
            if(growHypa(start,middle)){
                if(target!=null){
                    growHypa(middle,target);
                }
                return true;
            }
        }
        return false;

    }


    /**
     * Egy adott gombatestből spórát szór egy Tektonra, ha az elérhető.
     *
     * @param mushroom - A spórát szóró gombatest.
     * @param target - A cél Tekton, amelyre a spóra kerül.
     */
    public boolean throwSpore(Mushroom mushroom, Tekton target) {
        if(!mushrooms.contains(mushroom)){
            return false;
        }

        if(mushroom.getNumberOfSpores()!=1){
            return false;
        }
        Tekton location = mushroom.getLocation();
        List<Tekton> neighbours = location.getNeighbours();
        int age = mushroom.getAge();
        if(age<=4) {
            if (!neighbours.contains(target)){
                return false;
            }

        } else {
            Set<Tekton> canReach = new HashSet<Tekton>();
            canReach.addAll(neighbours);
            for(Tekton t : neighbours){
                canReach.addAll(t.getNeighbours());
            }
            if (!canReach.contains(target)){
                return false;
            }
        }

        mushroom.sporeThrown(target);
        tryGrowMushroom(target);
        return true;
    }

    /**
     * Kezeli egy gombatest halálát: eltávolítja a listából és frissíti a hipák hálózatát.
     *
     * @param m - A meghalt gombatest.
     */
    public void mushroomDied(Mushroom m) {
        GameBoard.removeReferenceFromMaps(m);
        m.getLocation().setMushroom(null);
        mushrooms.remove(m);
        traverseHypaNetwork();
    }


    /**
     * ÚJ!!! Gombászok első gombatestének lerakásához szükséges
     */
    public void growFirstMushroom(Tekton target){
        Mushroom mush = mushroomctor.apply(this, target);
        mushrooms.add(mush);
        target.setMushroom(mush);
    }


    /**
     * Megpróbál egy új gombatestet növeszteni egy adott Tektonon.
     *
     * @param target - A Tekton, ahol megpróbál növeszteni.
     */
    public void tryGrowMushroom(Tekton target) {

        if (target.canMushroomGrow(this)) {
            Mushroom mush = mushroomctor.apply(this, target);
            mushrooms.add(mush);
            target.setMushroomRemoveSpores(mush);
            traverseHypaNetwork();
        }
    }

    /**
     * Eltávolít egy hipát a listából.
     *
     * @param h - Az eltávolítandó hipa.
     */
    public void removeHypa(Hypa h) {
        GameBoard.removeReferenceFromMaps(h);
        HypaList.remove(h);
    }

    /**
     * A kör végén lefutó adminisztrációs műveletek.
     * Növeli a gombák életkorát és eltávolítja az elhalt hipákat.
     */
    public void endOfRoundAdministration() {
        for(Mushroom mus: mushrooms){
            mus.age();
            if(mus.getNumberOfSpores()!=1){
                mus.increaseNumberofSpores();
            }
        }

        List<Hypa> hypasListCopy = new ArrayList<>();
        hypasListCopy.addAll(HypaList);

        Iterator<Hypa> iterator = hypasListCopy.iterator();
        while(iterator.hasNext()){
            Hypa hyp = iterator.next();
            if (hyp.getIsDyingSinceDisconnected()!=1&&hyp.getIsDyingSinceBitten()!=hypaDieAfterBite)
                hyp.age();

            else {
                if (hyp.getIsDyingSinceDisconnected() == 1) {
                    Tekton end1 = hyp.getEnd1();
                    end1.removeHypa(hyp);
                    Tekton end2 = hyp.getEnd2();
                    end2.removeHypa(hyp);
                    this.removeHypa(hyp);
                }
                if (hyp.getIsDyingSinceBitten() == hypaDieAfterBite) {
                    hyp.die();
                }
            }
        }


    }

    /**
     * Bejárja a Shroomer hipahálózatát, és meghatározza, hogy mely hipák tartoznak még
     * a hálózatba, valamint melyek kezdenek elhalni.
     *
     * Az eljárás a meglévő gombatestekhez kapcsolódó Tektonokat összeszedi, majd
     * ezekhez hozzáveszi az összes olyan Tektont, amely egy hipán keresztül elérhető.
     * Ez alapján frissíti a hipák "isDyingSince" állapotát.
     */
    public void traverseHypaNetwork() {
        Set<Tekton> inNetworkTektons = new HashSet<Tekton>();
        Queue<Tekton> queue = new ArrayDeque<>();

        for(Mushroom mus: mushrooms){
            Tekton loc = mus.getLocation();
            if(inNetworkTektons.add(loc)){
                queue.add(loc);
            }
        }

        while(!queue.isEmpty()){
            Tekton tekton = queue.poll();
            for (Hypa hypa : tekton.getHypas()) {
                if (hypa.getShroomer() == this) {
                    if (inNetworkTektons.add(hypa.getEnd1())) {
                        queue.add(hypa.getEnd1());
                    }
                    if (inNetworkTektons.add(hypa.getEnd2())) {
                        queue.add(hypa.getEnd2());
                    }
                }
            }
        }

        Iterator<Hypa> iterator = HypaList.iterator();
        while (iterator.hasNext()) {
            Hypa hypa = iterator.next();
            if (inNetworkTektons.contains(hypa.getEnd1())||inNetworkTektons.contains(hypa.getEnd2())) {
                hypa.setIsDyingSinceDisconnected(-1);
            }else{
                if (hypa.getIsDyingSinceDisconnected() == -1)
                    hypa.setIsDyingSinceDisconnected(0);
            }
        }
    }

    /**
     * Hozzáad egy új hipát a Shroomer hipalistájához.
     *
     * @param h - A hozzáadandó hipa.
     */
    public void addHypa(Hypa h){
        HypaList.add(h);
    }

    /**
     * egy kiválasztott bogarat tud megenni vele a gombász, ha a bogár
     * pozíciójához (tekton) csatlakozik egy fonala a gombásznak. Ha meg tuja enni, akkor hívja a bogár
     * gazdájának removeBug(b) metódusát, ezzel kivetetve azt a klistájából és ha nincs gomba a
     * tektonon, növeszt oda egy gombatestet.
     */

    public boolean eatBug(Bug b){
        Tekton location = b.getLocation();
        List<Hypa> hypasaround = location.getHypas();

        for (Hypa h: hypasaround) {
            if (h.getShroomer()==this){
                if (b.beEaten(h)) {
                    if (!location.hasMushroom()){
                        Mushroom mush = mushroomctor.apply(this, location);
                        mushrooms.add(mush);
                        location.setMushroom(mush);
                        traverseHypaNetwork();
                    }
                    return true;
                }
            }
        }
        return false;

    }


}