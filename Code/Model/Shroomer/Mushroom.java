package Shroomer;

import Tekton.Tekton;

import java.util.Collections;

import static Controll.Skeleton.SKELETON;
import static Controll.Skeleton.user;

/**
 * 
 */
public abstract class Mushroom {

    /**
     *
     */
    protected Tekton location;

    /**
     *
     */
    protected Shroomer shroomer;



    /**
     * @param s
     * @param pos
     */
    public Mushroom(Shroomer s, Tekton pos) {
        shroomer = s;
        location = pos;
    }

    Tekton getLocation() {
        SKELETON.printCall(this, Collections.emptyList(), "getLocation");
        SKELETON.printReturn(SKELETON.objectNameMap.get(location)+": Tekton");
        return location;
    }

    /**
     *
     */
    public void die() {
        // TODO implement here
    }

    /**
     *
     */
    public void age() {
        // TODO implement here
    }

    public Shroomer getShroomer() {
        SKELETON.printCall(this, Collections.emptyList(), "getShroomer");
        SKELETON.printReturn(SKELETON.objectNameMap.get(shroomer)+": Shroomer");
        return shroomer;
    }


    public int getAge(){
        SKELETON.printCall(this, Collections.emptyList(), "getAge");
        SKELETON.printCall(user, Collections.emptyList(), "getNumericInput");
        int ni = SKELETON.getNumericInput("Milyen idős a gombatest (hány köre él)?\n Kérek egy 0 és 20 közötti egész számot.\n", 0,20);
        SKELETON.printReturn(String.format("%d", ni));
        SKELETON.printReturn(String.format("%d", ni));
        return ni;

    }

    public int getNumberOfSpores(){

        SKELETON.printCall(this, Collections.emptyList(), "getNumberOfSpores");
        SKELETON.printCall(user, Collections.emptyList(), "getNumericInput");
        int ni = SKELETON.getNumericInput("Készen áll-e a gombatest egy spóra szórásra?\n -1 - most szórt spórát\n 0 - az előző körben szór spórát\n 1 - régebben szórt spórát, újra tud szórni\n", -1,1);
        SKELETON.printReturn(String.format("%d", ni));
        SKELETON.printReturn(String.format("%d", ni));
        return ni;
    }

    public int getSporesThrown(){
        SKELETON.printCall(this, Collections.emptyList(), "getSporesThrown");
        SKELETON.printCall(user, Collections.emptyList(), "getNumericInput");
        int ni = SKELETON.getNumericInput("Hány spórát szórt már a gombatest?\n Kérek egy 0 és 5 közötti egész számot.\n", 0,5);
        SKELETON.printReturn(String.format("%d", ni));
        SKELETON.printReturn(String.format("%d", ni));
        return ni;
    }

    /**
     * @param to
     */
    public abstract void sporeThrown(Tekton to);

}