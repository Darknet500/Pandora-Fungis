package Shroomer;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import Controll.Player;
import Controll.Skeleton;
import Tekton.Tekton;

import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class Shroomer extends Player {

    /**
     *
    private Function mushroomctor;
     */
    private BiFunction<Shroomer, Tekton, Mushroom> mushroomctor;


    /**
     *
     *
     */
    private List<Mushroom> mushrooms;

    /**
     *
     */
    private List<Hypa> HypaList;

    /**
     * Default constructor
     */
    public Shroomer(BiFunction<Shroomer, Tekton, Mushroom> mushroomctor) {

        mushrooms = new ArrayList<Mushroom>();
        HypaList = new ArrayList<Hypa>();
        this.mushroomctor = mushroomctor;
    }


    /**
     * @param start
     * @param target
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

    public void addMushroom(Mushroom mushroom){
        mushrooms.add(mushroom);
    }

/**
 * @param start
 * @param middle
 * @param target
 */
public void growHypaFar(Tekton start,Tekton middle, Tekton target) {
    SKELETON.printCall(this, Arrays.asList(start,middle, target), "growHypaFar" );

    if(!start.hasSpore())
        if (middle.acceptHypa(this)){
            Hypa hypa1= new Hypa(start, middle, this);
            SKELETON.objectNameMap.put(hypa1, "hypa1");
            SKELETON.printCall(hypa1, Arrays.asList(start, target, this), "Hypa" );
            SKELETON.printReturn("");
            start.connectHypa(hypa1);
            middle.connectHypa(hypa1);
            HypaList.add(hypa1);
            tryGrowMushroom(middle);
            if (target.acceptHypa(this)) {
                Hypa hypa2= new Hypa(middle, target, this);
                SKELETON.objectNameMap.put(hypa2, "hypa2");
                SKELETON.printCall(hypa2, Arrays.asList(start, target, this), "Hypa" );
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
     * @param mushroom
     * @param target
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
     * @param m
     */
    public void mushroomDied(Mushroom m) {
        SKELETON.printCall(this, Collections.singletonList(m), "mushroomDied" );

        m.getLocation().setMushroom(null);
        mushrooms.remove(m);
        traverseHypaNetwork();
        SKELETON.printReturn("");
    }

    /**
     *
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
     * @param mushroomctor
    public Shroomer(Function mushroomctor) {
        // TODO implement here
    }
     */

    /**
     * @param h
     */
    public void removeHypa(Hypa h) {
        SKELETON.printCall(this, Collections.singletonList(h), "removeHypa" );
        HypaList.remove(h);
        SKELETON.printReturn("");
    }

    /**
     * 
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
     * 
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

    public void addHypa(Hypa h){
        HypaList.add(h);
    }


}