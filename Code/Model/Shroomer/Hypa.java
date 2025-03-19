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
        return SKELETON.getNumericInput("Hány köre nem része egy testes hálózatnak ez a hypa?\n-1 - még most is része\n0 - ebben a körben vált le\n1 - egy köre nem csatlakozik már", -1,1);
    }
    public void setIsDyingSince(int isDyingSince){

    }
    public int getAge(){
        return SKELETON.getNumericInput("Milyen idős a hypa (hány köre él)?\n Kérek egy 0 és 20 közötti egész számot.\n", 0,20);
    }

    public Tekton getEnd1() {
        return end1;
    }
    public Tekton getEnd2() {
        return end2;
    }
    public Shroomer getShroomer() {
        return shroomer;
    }


    /**
     * 
     */
    public void die() {
       end1.removeHypa(this);
       end2.removeHypa(this);
       shroomer.removeHypa(this);
       shroomer.traverseHypaNetwork();
    }




    /**
     * 
     */
    public void age() {
        // TODO implement here
    }

}