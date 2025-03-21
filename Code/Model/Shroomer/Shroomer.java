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

        if(start.getStoredSpores().isEmpty()){
           if (target.acceptHypa(this)){
               Hypa hypa= new Hypa(start, target, this);
               start.connectHypa(hypa);
               target.connectHypa(hypa);
               HypaList.add(hypa);
               traverseHypaNetwork();
           }
        }
        SKELETON.printReturn("asdasd");
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
    SKELETON.printCall(SKELETON.objectNameMap.get(this), Arrays.asList(start,middle, target), "growHypaFar" );

    if(!start.getStoredSpores().isEmpty())
        if (middle.acceptHypa(this)){
            Hypa hypa1= new Hypa(start, middle, this);
            start.connectHypa(hypa1);
            middle.connectHypa(hypa1);
            HypaList.add(hypa1);
            if (target.acceptHypa(this)) {
                Hypa hypa2= new Hypa(middle, target, this);
                start.connectHypa(hypa2);
                middle.connectHypa(hypa2);
                HypaList.add(hypa2);
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
        SKELETON.printCall(SKELETON.objectNameMap.get(this), Arrays.asList(mushroom, target), "throwSpore" );

        Tekton location = mushroom.getLocation();
        List<Tekton> neighbours = location.getNeighbours();
        int age = mushroom.getAge();
        if(age<=4) {
            if (!neighbours.contains(target)){
                SKELETON.printReturn("Exception");
                return; //talán egy exception
            }

        }else {
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
        SKELETON.printReturn("");
    }

    /**
     * @param m
     */
    public void mushroomDied(Mushroom m) {
        SKELETON.printCall(SKELETON.objectNameMap.get(this), Collections.singletonList(m), "mushroomDied" );

        m.getLocation().setMushroom(null);
        mushrooms.remove(m);
        traverseHypaNetwork();
        SKELETON.printReturn("");
    }

    /**
     *
     */
    public void growMushroom(Tekton target) {
        SKELETON.printCall(SKELETON.objectNameMap.get(this), Collections.singletonList(target), "growMushroom" );

        if (target.canMushroomGrow(this)) {
            Mushroom mush = mushroomctor.apply(this, target);
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
        SKELETON.printCall(SKELETON.objectNameMap.get(this), Collections.singletonList(h), "removeHypa" );
        HypaList.remove(h);
        SKELETON.printReturn("");
    }

    /**
     * 
     */
    public void endOfRoundAdministration() {
        SKELETON.printCall(SKELETON.objectNameMap.get(this), Collections.emptyList(), "endOfRoundAdministration" );
        for(Mushroom mus: mushrooms){
            mus.age();
        }
        for(Hypa hyp: HypaList){
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
            for (Hypa hypa : hypas) {
                if(hypa.getShroomer()==this){
                    inNetworkTektons.add(hypa.getEnd1());
                    inNetworkTektons.add(hypa.getEnd2());
                }
            }
        }

        for(Hypa hypa: HypaList){
            if (inNetworkTektons.contains(hypa.getEnd1())||inNetworkTektons.contains(hypa.getEnd2())) {
                hypa.setIsDyingSince(-1);
            }else{
                if (hypa.getIsDyingSince() == -1)
                    hypa.setIsDyingSince(0);
            }
        }
        SKELETON.printReturn("");
    }


}