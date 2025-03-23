package Shroomer;

import java.util.*;
import Tekton.Tekton;

import static Controll.Skeleton.SKELETON;

/**
 * A Hypa egy kapcsolatot alkot két Tekton között, amelyet egy Shroomer hoz létre és kezel.
 * A Hypa az idő múlásával elöregedhet, és ha nem része egy gombatest-hálózatnak, elhalhat.
 */
public class Hypa {
    /**
     * A Hypa egyik végpontja.
     */
    private Tekton end1;

    /**
     * A Hypa másik végpontja.
     */
    private Tekton end2;

    /**
     * A Shroomer, aki létrehozta és kezeli ezt a Hypa-t.
     */
    private Shroomer shroomer;

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új Hypa kapcsolatot két Tekton között egy adott Shroomer számára.
     *
     * @param end1 - A Hypa egyik végpontja.
     * @param end2 - A Hypa másik végpontja.
     * @param shroomer - A Shroomer, aki létrehozza a Hypa-t.
     */
    public Hypa(Tekton end1, Tekton end2, Shroomer shroomer) {
        this.end1 = end1;
        this.end2 = end2;
        this.shroomer = shroomer;

    }

    /**
     * Megadja, hogy hány kör óta nem része a Hypa egy gombatest-hálózatnak.
     *
     * @return -1, ha még része a hálózatnak, 0, ha ebben a körben vált le, 1, ha egy kör óta nincs kapcsolatban.
     */
    public int getIsDyingSince(){
        SKELETON.printCall(this, Collections.emptyList(), "getIsDyingSince");
        int ni = SKELETON.getNumericInput("Hány köre nem része egy testes hálózatnak ez a hypa?\n-1 - még most is része\n0 - ebben a körben vált le\n1 - egy köre nem csatlakozik már", -1,1);
        SKELETON.printReturn(String.format("%d", ni));
        return ni;
    }

    /**
     * Beállítja, hogy hány kör óta nem része a Hypa egy gombatest-hálózatnak.
     *
     * @param isDyingSince - Az új érték, amely jelzi a leválás óta eltelt köröket.
     */
    public void setIsDyingSince(int isDyingSince){

    }

    /**
     * Megadja, hogy hány kör óta létezik a Hypa.
     *
     * @return - A Hypa életkora (0 és 20 közötti érték).
     */
    public int getAge(){
        SKELETON.printCall(this, Collections.emptyList(), "getAge");
        int ni = SKELETON.getNumericInput("Milyen idős a hypa (hány köre él)?\n Kérek egy 0 és 20 közötti egész számot.\n", 0,20);
        SKELETON.printReturn(String.format("%d", ni));
        return ni;
    }

    /**
     * Visszaadja a Hypa egyik végpontját.
     *
     * @return - A Hypa egyik végpontját reprezentáló Tekton.
     */
    public Tekton getEnd1() {
        SKELETON.printCall(this, Collections.emptyList(), "getEnd1");
        SKELETON.printReturn(SKELETON.objectNameMap.get(end1)+": Tekton");
        return end1;
    }

    /**
     * Visszaadja a Hypa másik végpontját.
     *
     * @return - A Hypa másik végpontját reprezentáló Tekton.
     */
    public Tekton getEnd2() {
        SKELETON.printCall(this, Collections.emptyList(), "getEnd2");
        SKELETON.printReturn(SKELETON.objectNameMap.get(end2)+": Tekton");
        return end2;
    }

    /**
     * Visszaadja a Shroomert, aki létrehozta ezt a Hypa-t.
     *
     * @return - A Hypát létrehozó Shroomer.
     */
    public Shroomer getShroomer() {
        SKELETON.printCall(this, Collections.emptyList(), "getShroomer");
        SKELETON.printReturn(SKELETON.objectNameMap.get(shroomer)+": Shroomer");
        return shroomer;
    }


    /**
     * Megszünteti a Hypa-t, eltávolítja azt a Tektonoktól és a Shroomertől.
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
     * Növeli a Hypa életkorát minden körben.
     */
    public void age() {
        SKELETON.printCall(this, Collections.emptyList(), "age");
        SKELETON.printReturn("");
    }

}