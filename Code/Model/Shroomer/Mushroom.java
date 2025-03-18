package Shroomer;

import Tekton.Tekton;

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


    public int getAge(){
        return SKELETON.getNUmericInput("Milyen idős a gombatest (hány köre él)?\n Kérek egy 0 és 20 közötti egész számot.\n", 0,20);
    }

    public int getNumberOfSpores(){
        return SKELETON.getNUmericInput("Készen áll-e a gombatest egy spóra szórásra?\n -1 - most szórt spórát\n 0 - az előző körben szór spórát\n 1 - régebben szórt spórát, újra tud szórni\n", -1,1);

    }

    public int getSporesThrown(){
        return SKELETON.getNUmericInput("Hány spórát szórt már a gombatest?\n Kérek egy 0 és 5 közötti egész számot.\n", 0,5);

    }

    /**
     * @param to
     */
    public abstract void sporeThrown(Tekton to);

}