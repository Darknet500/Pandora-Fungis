package Shroomer;

import java.util.*;
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
    private Supplier<Mushroom> mushroomctor;


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
    public Shroomer(Supplier<Mushroom> mushroomctor) {
        mushrooms = new ArrayList<Mushroom>();
        HypaList = new ArrayList<Hypa>();
        this.mushroomctor = mushroomctor;
    }


    /**
     * @param start
     * @param target
     */
    public void growHypa(Tekton start, Tekton target) {
        if(!start.hasSpore()){
           if (target.acceptHypa(this)){
               Hypa hypa= new Hypa(start, target, this);
               start.connectHypa(hypa);
               target.connectHypa(hypa);
               HypaList.add(hypa);
               traverseHypaNetwork();
           }
        }
    }

/**
 * @param start
 * @param middle
 * @param target
 */
public void growHypaFar(Tekton start,Tekton middle, Tekton target) {
    if(start.hasSpore())
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
}


    /**
     * @param mushroom
     * @param target
     */
    public void throwSpore(Mushroom mushroom, Tekton target) {
        Tekton location = mushroom.getLocation();
        List<Tekton> neighbours = location.getNeighbours();
        int age = mushroom.getAge();
        if(age<=4) {
            if (!neighbours.contains(target)) return; //talán egy exception

        }else {
            Set<Tekton> canReach = new HashSet<Tekton>();
            canReach.addAll(neighbours);
            for(Tekton t : canReach){
                canReach.addAll(t.getNeighbours());
            }
            if (!canReach.contains(target)) return; //talán egy exception
        }

        mushroom.sporeThrown(target);
    }

    /**
     * @param m
     */
    public void mushroomDied(Mushroom m) {
        m.getLocation().removeMushroom(m);
        mushrooms.remove(m);
        traverseHypaNetwork();
    }

    /**
     *
     */
    public void growMushroom(Tekton target) {
        if (target.canMushroomGrow(this)) {
            Mushroom mush = mushroomctor.get();
            target.setMushroomRemoveSpores(mush);
            traverseHypaNetwork();
        }
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

        HypaList.remove(h);
    }

    /**
     * 
     */
    public void endOfRoundAdministration() {
        SKELETON.callStackDepth++;
        for(Mushroom mus: mushrooms){
            SKELETON.printCall(SKELETON.objectNameMap.get(this) + " -> " + SKELETON.objectNameMap.get(mus) + ": age()");
            mus.age();
            SKELETON.printCall(SKELETON.objectNameMap.get(mus) + " -->> " + SKELETON.objectNameMap.get(this));
        }
        for(Hypa hyp: HypaList){
            if(hyp.getIsDyingSince()==1){
                SKELETON.printCall(SKELETON.objectNameMap.get(this) + " -> "+SKELETON.objectNameMap.get(hyp) + ": getEnd1()");
                Tekton end1 = hyp.getEnd1();
                SKELETON.printCall(SKELETON.objectNameMap.get(hyp) + " -->> "+SKELETON.objectNameMap.get(this) + ": end1: Tekton");
                SKELETON.printCall(SKELETON.objectNameMap.get(this) + " -> "+SKELETON.objectNameMap.get(end1) + ": removeHypa("+SKELETON.objectNameMap.get(hyp)+")");
                end1.removeHypa(hyp);
                SKELETON.printCall(SKELETON.objectNameMap.get(end1) + " -->> "+SKELETON.objectNameMap.get(this));
                SKELETON.printCall(SKELETON.objectNameMap.get(this) + " -> "+SKELETON.objectNameMap.get(hyp) + ": getEnd2()");
                Tekton end2 = hyp.getEnd2();
                SKELETON.printCall(SKELETON.objectNameMap.get(hyp) + " -->> "+SKELETON.objectNameMap.get(this) + ": end2: Tekton");
                SKELETON.printCall(SKELETON.objectNameMap.get(this) + " -> "+SKELETON.objectNameMap.get(end1) + ": removeHypa("+SKELETON.objectNameMap.get(hyp)+")");
                end2.removeHypa(hyp);
                SKELETON.printCall(SKELETON.objectNameMap.get(end1) + " -->> "+SKELETON.objectNameMap.get(this));
                SKELETON.printCall(SKELETON.objectNameMap.get(this) + " ->> "+SKELETON.objectNameMap.get(this) + ": removeHypa("+SKELETON.objectNameMap.get(hyp)+")");
                removeHypa(hyp);
            }else{
                SKELETON.printCall(SKELETON.objectNameMap.get(this) + " -> "+SKELETON.objectNameMap.get(hyp) + ": age()");
                hyp.age();
                SKELETON.printCall(SKELETON.objectNameMap.get(hyp) + " -->> "+SKELETON.objectNameMap.get(this) );
            }
        }
        SKELETON.callStackDepth--;


    }

    /**
     * 
     */
    public void traverseHypaNetwork() {

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



    }


}