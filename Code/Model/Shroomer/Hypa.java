package Shroomer;

import java.util.*;
import Tekton.Tekton;

import static Controll.Skeleton.SKELETON;

/**
 * 
 */
public class Hypa {
    /**
     *
     */
    private Tekton end1;
    private Tekton end2;

    /**
     *
     */
    private Shroomer shroomer;

    /**
     * Default constructor
     * @param end2
     * @param shroomer
     */
    public Hypa(Tekton end1, Tekton end2, Shroomer shroomer) {
        this.end1 = end1;
        this.end2 = end2;
        this.shroomer = shroomer;

    }



    public int getIsDyingSince(){
        SKELETON.printCall(this, Collections.emptyList(), "getIsDyingSince");
        int ni = SKELETON.getNumericInput("Hány köre nem része egy testes hálózatnak ez a hypa?\n-1 - még most is része\n0 - ebben a körben vált le\n1 - egy köre nem csatlakozik már", -1,1);
        SKELETON.printReturn(String.format("%d", ni));
        return ni;
    }
    public void setIsDyingSince(int isDyingSince){

    }

    public int getAge(){
        SKELETON.printCall(this, Collections.emptyList(), "getAge");
        int ni = SKELETON.getNumericInput("Milyen idős a hypa (hány köre él)?\n Kérek egy 0 és 20 közötti egész számot.\n", 0,20);
        SKELETON.printReturn(String.format("%d", ni));
        return ni;
    }

    public Tekton getEnd1() {
        SKELETON.printCall(this, Collections.emptyList(), "getEnd1");
        SKELETON.printReturn(SKELETON.objectNameMap.get(end1)+": Tekton");
        return end1;
    }
    public Tekton getEnd2() {
        SKELETON.printCall(this, Collections.emptyList(), "getEnd2");
        SKELETON.printReturn(SKELETON.objectNameMap.get(end2)+": Tekton");
        return end2;
    }
    public Shroomer getShroomer() {
        SKELETON.printCall(this, Collections.emptyList(), "getShroomer");
        SKELETON.printReturn(SKELETON.objectNameMap.get(shroomer)+": Shroomer");
        return shroomer;
    }


    /**
     * 
     */
    public void die() {
        SKELETON.printCall(this, Collections.emptyList(), "die");
       end1.removeHypa(this);
       end2.removeHypa(this);
       shroomer.removeHypa(this);
       shroomer.traverseHypaNetwork();
       SKELETON.printReturn("");
    }

    /**
     * 
     */
    public void age() {
        SKELETON.printCall(this, Collections.emptyList(), "age");
        SKELETON.printReturn("");
    }

}