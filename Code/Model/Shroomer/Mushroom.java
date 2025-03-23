package Shroomer;

import Tekton.Tekton;

import java.util.Collections;

import static Controll.Skeleton.SKELETON;

/**
 * A Mushroom osztály egy absztrakt osztály, amely a gombatesteket reprezentálja a játékban.
 * Minden Mushroom egy adott Tektonon található és egy Shroomer-hez tartozik.
 * A gombatestek megöregedhetnek, elhalhatnak és spórákat szórhatnak, valamint fonalakat (Hypa) növeszthetnek.
 */
public abstract class Mushroom {

    /**
     * A gombatest aktuális pozíciója.
     */
    protected Tekton location;

    /**
     * A Shroomer, amely ezt a gombatestet létrehozta.
     */
    protected Shroomer shroomer;



    /**
     * Létrehoz egy új gombatestet a megadott Shroomer-rel és Tekton pozícióval.
     *
     * @param s - A gombatestet létrehozó Shroomer.
     * @param pos - A Tekton, amelyen a gombatest található.
     */
    public Mushroom(Shroomer s, Tekton pos) {
        shroomer = s;
        location = pos;
    }

    /**
     * Lekérdezi a gombatest aktuális helyzetét a pályán.
     *
     * @return - A Tekton objektum, amin a gombatest van.
     */
    Tekton getLocation() {
        SKELETON.printCall(this, Collections.emptyList(), "getLocation");
        SKELETON.printReturn(SKELETON.objectNameMap.get(location)+": Tekton");
        return location;
    }

    /**
     * A gombatest elhalását kezeli.
     * Értesíti a Shroomer-t a gombatest elhalásáról.
     */
    public void die() {
        SKELETON.printCall(this, Collections.emptyList(), "die");
        shroomer.mushroomDied(this);
        SKELETON.printReturn("");
    }

    /**
     * A gombatest életkorának növelése minden kör után.
     */
    public void age() {
        SKELETON.printCall(this, Collections.emptyList(), "age");
        SKELETON.printReturn("");
    }

    /**
     * Visszaadja a gombatestet létrehozó Shroomer objektumot.
     *
     * @return - A Shroomer, amelyhez a gombatest tartozik.
     */
    public Shroomer getShroomer() {
        SKELETON.printCall(this, Collections.emptyList(), "getShroomer");
        SKELETON.printReturn(SKELETON.objectNameMap.get(shroomer)+": Shroomer");
        return shroomer;
    }

    /**
     * Visszaadja a gombatest életkorát.
     * Az életkor befolyásolja a spóraszórást.
     *
     * @return - A gombatest életkora (0 és 20 közötti egész szám).
     */
    public int getAge(){
        SKELETON.printCall(this, Collections.emptyList(), "getAge");
        int ni = SKELETON.getNumericInput("Milyen idős a gombatest (hány köre él)?\n Kérek egy 0 és 20 közötti egész számot.\n", 0,20);
        SKELETON.printReturn(String.format("%d", ni));
        return ni;

    }

    /**
     * Megadja, hogy a gombatest készen áll-e a spóraszórásra.
     *
     * @return -1, ha most szórt spórát; 0, ha az előző körben szórt spórát;
     *         1, ha régebben szórt spórát és újra tud spórázni.
     */
    public int getNumberOfSpores(){

        SKELETON.printCall(this, Collections.emptyList(), "getNumberOfSpores");
        int ni = SKELETON.getNumericInput("Készen áll-e a gombatest egy spóra szórásra?\n -1 - most szórt spórát\n 0 - az előző körben szór spórát\n 1 - régebben szórt spórát, újra tud szórni\n", -1,1);
        SKELETON.printReturn(String.format("%d", ni));
        return ni;
    }

    /**
     * Visszaadja, hogy a gombatest eddig hány spórát szórt el.
     *
     * @return - A spóraszórások száma (0 és 5 közötti egész szám).
     */
    public int getSporesThrown(){
        SKELETON.printCall(this, Collections.emptyList(), "getSporesThrown");
        int ni = SKELETON.getNumericInput("Hány spórát szórt már a gombatest?\n Kérek egy 0 és 5 közötti egész számot.\n", 0,5);
        SKELETON.printReturn(String.format("%d", ni));
        return ni;
    }

    /**
     * Egy adott Tekton-ra spórát szór.
     * Az implementációt a konkrét gombatest típusok határozzák meg.
     *
     * @param to - A Tekton, amelyre a spóra kerül.
     */
    public abstract void sporeThrown(Tekton to);

}