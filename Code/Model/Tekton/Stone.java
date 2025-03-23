package Tekton;

import Shroomer.Shroomer;

import java.util.Collections;

import static Controll.Skeleton.SKELETON;

/**
 * A Stone egy speciális Tekton típus, amelyre nem nőhetnek gombatestek (Mushroom).
 * Ez azt jelenti, hogy a canMushroomGrow metódus mindig hamis értéket ad vissza.
 */
public class Stone extends Tekton {

    /**
     * Alapértelmezett konstruktor, amely meghívja az ősosztály (Tekton) konstruktorát.
     */
    public Stone() {
        super();
    }

    /**
     * Megvizsgálja, hogy nőhet-e gombatest (Mushroom) ezen a Stone típusú Tektonon.
     * Mivel a Stone-on nem nőhet gomba, ezért mindig hamis értéket ad vissza.
     *
     * @param s - A Shroomer, amely gombát szeretne növeszteni.
     * @return - Mindig false, mert Stone-on nem nőhet Mushroom.
     */
    @Override
    public boolean canMushroomGrow(Shroomer s) {
        SKELETON.printCall(this, Collections.singletonList(s), "canMushroomGrow");
        SKELETON.printReturn("false");
        return false;
    }

}