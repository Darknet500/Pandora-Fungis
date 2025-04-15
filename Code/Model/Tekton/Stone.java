package Model.Tekton;

import Model.Bridge.GameBoard;
import Model.Shroomer.Shroomer;

import java.util.Collections;


/**
 * A Stone egy speciális Tekton típus, amelyre nem nőhetnek gombatestek (Mushroom).
 * Ez azt jelenti, hogy a canMushroomGrow metódus mindig hamis értéket ad vissza.
 */
public class Stone extends Tekton {

    private static int stoneID = 0;
    private String name;

    /**
     * Alapértelmezett konstruktor, amely meghívja az ősosztály (Tekton) konstruktorát.
     */
    public Stone() {
        super();
        stoneID++;
        name = "stone" + stoneID;
        GameBoard.nameObjectMap.put(name, this);
    }

    @Override
    public String getName(){return name;}

    /**
     * Megvizsgálja, hogy nőhet-e gombatest (Mushroom) ezen a Stone típusú Tektonon.
     * Mivel a Stone-on nem nőhet gomba, ezért mindig hamis értéket ad vissza.
     *
     * @param s - A Shroomer, amely gombát szeretne növeszteni.
     * @return - Mindig false, mert Stone-on nem nőhet Mushroom.
     */
    @Override
    public boolean canMushroomGrow(Shroomer s) {
        return false;
    }

}