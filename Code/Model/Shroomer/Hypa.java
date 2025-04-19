package Model.Shroomer;

import java.util.*;

import Model.Bridge.GameBoard;
import Model.Tekton.*;


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
     * Hány köre él az adott hypa
     */
    private int age = 0;
    /**
     * Értéke 0 lesz, amikor a hypa nem csatlakozik közvetve, vagy közvetlenül a birtokló gombász gombatestéhez
     * Ha nem -1 az értéke, akkor körönként nő az értéke eggyel. Ha eléri az 1-et, akkor meghal.
     */
    private int isDyingSinceDisconnected = -1;

    /**
     * Értéke 0 lesz, amikor az adott hypát elharapja egy bogár
     * Ha nem -1 az értkéke, akkor körönként nő eggyel. Ha eléri a gombásznál eltárolt értéket (shroomer.hypaDieAfterBite) akkor meghal
     */
    private int isDyingSinceBitten = -1;

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új Hypa kapcsolatot két Tekton között egy adott Shroomer számára.
     *
     * @param end1 - A Hypa egyik végpontja.
     * @param end2 - A Hypa másik végpontja.
     * @param shroomer - A Shroomer, aki létrehozza a Hypa-t.
     */
    public Hypa(Tekton end1, Tekton end2, Shroomer shroomer) {
        hypaID++;
        name = "hypa"+hypaID;
        GameBoard.nameObjectMap.put(name, this);
        this.end1 = end1;
        this.end2 = end2;
        this.shroomer = shroomer;

    }

    public String getName(){return name;}

    /**
     * Megadja, hogy hány kör óta nem része a Hypa egy gombatest-hálózatnak.
     *
     * @return -1, ha még része a hálózatnak, 0, ha ebben a körben vált le, 1, ha egy kör óta nincs kapcsolatban.
     */
    public int getIsDyingSinceDisconnected(){
        return isDyingSinceDisconnected;
    }

    /**
     * Beállítja, hogy hány kör óta nem része a Hypa egy gombatest-hálózatnak.
     *
     * @param isDyingSinceDisconnected - Az új érték, amely jelzi a leválás óta eltelt köröket.
     */
    public void setIsDyingSinceDisconnected(int isDyingSinceDisconnected) {
        this.isDyingSinceDisconnected = isDyingSinceDisconnected;
    }

    /**
     * Megadja, hogy hány kör óta nem lett elharapha.
     *
     * @return -1, ha még nem lett elharpava, 0, ha ebben a körben harapták el.
     */
    public int getIsDyingSinceBitten(){
        return isDyingSinceBitten;
    }

    /**
     * Beállítja, hogy hány körrel ezelőtt harapták el.
     *
     * @param isDyingSinceBitten - Az új érték, amely jelzi az elharapás óta eltelt köröket.
     */
    public void setIsDyingSinceBitten(int isDyingSinceBitten) {
        this.isDyingSinceBitten = isDyingSinceBitten;
    }



    /**
     * Megadja, hogy hány kör óta létezik a Hypa.
     *
     * @return - A Hypa életkora (0 és 20 közötti érték).
     */
    public int getAge(){
        return this.age;
    }

    /**
     * Visszaadja a Hypa egyik végpontját.
     *
     * @return - A Hypa egyik végpontját reprezentáló Tekton.
     */
    public Tekton getEnd1() {
        return end1;
    }

    /**
     * Visszaadja a Hypa másik végpontját.
     *
     * @return - A Hypa másik végpontját reprezentáló Tekton.
     */
    public Tekton getEnd2() {
        return end2;
    }

    /**
     * Visszaadja a Shroomert, aki létrehozta ezt a Hypa-t.
     *
     * @return - A Hypát létrehozó Shroomer.
     */
    public Shroomer getShroomer() {
        return shroomer;
    }


    /**
     * Megszünteti a Hypa-t, eltávolítja azt a Tektonoktól és a Shroomertől.
     */
    public void die() {
       end1.removeHypa(this);
       end2.removeHypa(this);
       shroomer.removeHypa(this);
       shroomer.traverseHypaNetwork();
       GameBoard.nameObjectMap.remove(name);
    }

    /**
     * Növeli a Hypa életkorát minden körben.
     */
    public void age() {
        age++;
        if (isDyingSinceBitten!=-1) isDyingSinceBitten++;
        if (isDyingSinceDisconnected!=-1) isDyingSinceDisconnected++;
    }

}