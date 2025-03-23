package Shroomer;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import Controll.Player;
import Controll.Skeleton;
import Tekton.Tekton;

import static Controll.Skeleton.SKELETON;

/**
 * A Shroomer osztály a játékos egyik típusa, amely gombatesteket hozhat létre
 * és fonalakat (Hypa) növeszthet a pályán.
 * <p>
 * A Shroomer felelős a gombatestek és fonalak kezeléséért, valamint a spóraszórásért.
 */
public class Shroomer extends Player {

    //private Function mushroomctor;
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
     * Alapértelmezett konstruktor.
     * Létrehoz egy új Shroomer példányt, amely képes gombatesteket növeszteni.
     *
     * @param mushroomctor - A függvény, amely létrehozza a gombatesteket.
     */
    public Shroomer(BiFunction<Shroomer, Tekton, Mushroom> mushroomctor) {

        mushrooms = new ArrayList<Mushroom>();
        HypaList = new ArrayList<Hypa>();
        this.mushroomctor = mushroomctor;
    }


    /**
     * Egy új fonalat (Hypa) növeszt két adott Tekton között, ha lehetséges.
     * Ha a cél Tekton elfogadja a hipát, a hipát hozzáadja a listához és
     * megpróbál gombát növeszteni a cél Tektonon, ha ott teljesül minden feltétel..
     *
     * @param start - A hipa kiindulási Tektonja.
     * @param target - A hipa cél Tektonja.
     */
    public void growHypa(Tekton start, Tekton target) {
        SKELETON.printCall(this, Arrays.asList(start, target), "growHypa" );

        if(!start.hasSpore()){
           if (target.acceptHypa(this)){
               Hypa hypa= new Hypa(start, target, this);
               SKELETON.objectNameMap.put(hypa, "hypa");
               SKELETON.printCall(hypa, Arrays.asList(start, target, this), "Hypa" );
               SKELETON.printReturn("");
               start.connectHypa(hypa);
               target.connectHypa(hypa);

               HypaList.add(hypa);
               tryGrowMushroom(target);
               traverseHypaNetwork();
           }
        }
        SKELETON.printReturn("");
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
    public void growHypaFar(Tekton start,Tekton middle, Tekton target) {
        SKELETON.printCall(this, Arrays.asList(start,middle, target), "growHypaFar" );

        if(!start.hasSpore())
            if (middle.acceptHypa(this)){
                Hypa hypa1= new Hypa(start, middle, this);
                SKELETON.objectNameMap.put(hypa1, "hypa1");
                SKELETON.printCall(hypa1, Arrays.asList(start, middle, this), "Hypa" );
                SKELETON.printReturn("");
                start.connectHypa(hypa1);
                middle.connectHypa(hypa1);
                HypaList.add(hypa1);
                tryGrowMushroom(middle);
                if (target.acceptHypa(this)) {
                    Hypa hypa2= new Hypa(middle, target, this);
                    SKELETON.objectNameMap.put(hypa2, "hypa2");
                    SKELETON.printCall(hypa2, Arrays.asList(middle, target, this), "Hypa" );
                    SKELETON.printReturn("");
                    start.connectHypa(hypa2);
                    middle.connectHypa(hypa2);
                    HypaList.add(hypa2);
                    tryGrowMushroom(target);
                }
                traverseHypaNetwork();
            }
        SKELETON.printReturn("");
    }


    /**
     * Egy adott gombatestből spórát szór egy Tektonra, ha az elérhető.
     *
     * @param mushroom - A spórát szóró gombatest.
     * @param target - A cél Tekton, amelyre a spóra kerül.
     */
    public void throwSpore(Mushroom mushroom, Tekton target) {
        SKELETON.printCall(this, Arrays.asList(mushroom, target), "throwSpore" );
        if(mushroom.getNumberOfSpores()!=1){
            SKELETON.printReturn("");
            return;
        }
        Tekton location = mushroom.getLocation();
        List<Tekton> neighbours = location.getNeighbours();
        int age = mushroom.getAge();
        if(age<=4) {
            if (!neighbours.contains(target)){
                SKELETON.printReturn("Exception");
                return; //talán egy exception
            }

        } else {
            Set<Tekton> canReach = new HashSet<Tekton>();
            canReach.addAll(neighbours);
            for(Tekton t : canReach){
                canReach.addAll(t.getNeighbours());
            }
            if (!canReach.contains(target)){
                SKELETON.printReturn("Exception");
                return; //talán egy exception
            }
        }

        mushroom.sporeThrown(target);
        tryGrowMushroom(target);
        SKELETON.printReturn("");
    }

    /**
     * Kezeli egy gombatest halálát: eltávolítja a listából és frissíti a hipák hálózatát.
     *
     * @param m - A meghalt gombatest.
     */
    public void mushroomDied(Mushroom m) {
        SKELETON.printCall(this, Collections.singletonList(m), "mushroomDied" );

        m.getLocation().setMushroom(null);
        mushrooms.remove(m);
        traverseHypaNetwork();
        SKELETON.printReturn("");
    }

    /**
     * Megpróbál egy új gombatestet növeszteni egy adott Tektonon.
     *
     * @param target - A Tekton, ahol megpróbál növeszteni.
     */
    public void tryGrowMushroom(Tekton target) {
        SKELETON.printCall(this, Collections.singletonList(target), "tryGrowMushroom" );

        if (target.canMushroomGrow(this)) {
            Mushroom mush = mushroomctor.apply(this, target);
            SKELETON.objectNameMap.put(mush, "mushroom");
            target.setMushroomRemoveSpores(mush);
            traverseHypaNetwork();
        }
        SKELETON.printReturn("");
    }

    /**
     * Eltávolít egy hipát a listából.
     *
     * @param h - Az eltávolítandó hipa.
     */
    public void removeHypa(Hypa h) {
        SKELETON.printCall(this, Collections.singletonList(h), "removeHypa" );
        HypaList.remove(h);
        SKELETON.printReturn("");
    }

    /**
     * A kör végén lefutó adminisztrációs műveletek.
     * Növeli a gombák életkorát és eltávolítja az elhalt hipákat.
     */
    public void endOfRoundAdministration() {
        SKELETON.printCall(this, Collections.emptyList(), "endOfRoundAdministration" );
        for(Mushroom mus: mushrooms){
            mus.age();
        }

        List<Hypa> hypasListCopy = new ArrayList<>();
        hypasListCopy.addAll(HypaList);

        Iterator<Hypa> iterator = hypasListCopy.iterator();
        while(iterator.hasNext()){
            Hypa hyp = iterator.next();
            if(hyp.getIsDyingSince()==1){
                Tekton end1 = hyp.getEnd1();
                end1.removeHypa(hyp);
                Tekton end2 = hyp.getEnd2();
                end2.removeHypa(hyp);
                removeHypa(hyp);
            }else{
                hyp.age();
            }
        }

        SKELETON.printReturn("");


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
        SKELETON.printCall(this, Collections.emptyList(), "traverseHypaNetowrk" );
        Set<Tekton> inNetworkTektons = new HashSet<Tekton>();
        for(Mushroom mus: mushrooms){
            inNetworkTektons.add(mus.getLocation());
        }
        for(Tekton tekton: inNetworkTektons){
            List<Hypa> hypas = tekton.getHypas();
            Iterator<Hypa> iterator = hypas.iterator();
            while (iterator.hasNext()) {
                Hypa hypa = iterator.next();
                if(hypa.getShroomer()==this){
                    inNetworkTektons.add(hypa.getEnd1());
                    inNetworkTektons.add(hypa.getEnd2());
                }
            }
        }

        Iterator<Hypa> iterator = HypaList.iterator();
        while (iterator.hasNext()) {
            Hypa hypa = iterator.next();
            if (inNetworkTektons.contains(hypa.getEnd1())||inNetworkTektons.contains(hypa.getEnd2())) {
                hypa.setIsDyingSince(-1);
            }else{
                if (hypa.getIsDyingSince() == -1)
                    hypa.setIsDyingSince(0);
            }
        }
        SKELETON.printReturn("");
    }

    /**
     * Hozzáad egy új hipát a Shroomer hipalistájához.
     *
     * @param h - A hozzáadandó hipa.
     */
    public void addHypa(Hypa h){
        HypaList.add(h);
    }


}