package Shroomer;

import Bug.Bug;

import java.util.Collections;


/**
 * A Spore egy absztrakt osztály, amely a különböző spórák alapvető tulajdonságait és működését definiálja.
 * Minden spóra egy adott Shroomerhez tartozik (A Mushroom-jai hozzák létre), és hatást gyakorolhat egy Bug-ra.
 */
public abstract class Spore {

    /**
     * A Spore tulajdonosa, az a Shroomer, aki létrehozta a gombájával.
     */
    private Shroomer shroomer;

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új Spore példányt egy adott Shroomer számára.
     *
     * @param shroomer - Az a Shroomer, aki létrehozza a spórát.
     */
    public Spore(Shroomer shroomer) {
        this.shroomer = shroomer;
    }

    /**
     * Meghatározza, milyen hatást gyakorol a spóra egy Bug-ra.
     * Az implementáló osztályok határozzák meg a konkrét hatásokat.
     *
     * @param b - A Bug, amelyre a spóra hatással lesz.
     * @return - Egy egész szám, amely a hatás típusát jelzi.
     */
    public abstract int haveEffect(Bug b);

    /**
     * Visszaadja a Spore-hoz tartozó Shroomert.
     *
     * @return - A spórát létrehozó Shroomer.
     */
    public Shroomer getShroomer() {
        return shroomer;
    }

}