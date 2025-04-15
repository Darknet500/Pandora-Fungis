package Model.Shroomer;

import Model.Bridge.GameBoard;
import Model.Tekton.Tekton;
import java.util.List;
import java.util.Collections;


/**
 * A BiteBlockerMushroom egy speciális gombafajta, amely BiteBlocker spórákat tud szórni.
 * Egy adott Shroomerhez és Tektonhoz tartozik.
 */
public class BiteBlockerMushroom extends Mushroom {

    /**
     * statikus számláló, minden konstruktorhíváskor növeljük, ez biztosítja a név egyediséget.
     *  objektum elnevezése: biteblockermushroom[biteBlockerMushroomID aktuális értéke]
     */
    private static int biteBlockerMushroomID = 0;

    /**
     * objektum neve, egyedi az egész modellben
     */
    private String name;

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új BiteBlockerMushroom példányt egy adott Shroomer és Tekton helyszín alapján.
     * elnevezi magát, és beleteszi a gameBoard nameObjectMap-jébe
     *
     * @param shroomer - A gombát létrehozó Shroomer.
     * @param location - A Tekton, ahol a gomba elhelyezkedik.
     */
    public BiteBlockerMushroom(Shroomer shroomer, Tekton location) {
        super(shroomer, location);
        biteBlockerMushroomID++;
        name = "biteblockermushroom"+biteBlockerMushroomID;
        GameBoard.nameObjectMap.put(name, this);
    }

    @Override
    public String getName(){return name;}

    /**
     * Egy új BiteBlockerSpore spórát szór a megadott Tektonra.
     * Ha a gomba már öt spórát szórt, akkor elpusztul.
     *
     * @param target A Tekton, amelyre a spóra kerül.
     */
    public void sporeThrown(Tekton target) {
        Spore biteBloskerSpore = new BiteBlockerSpore(this.shroomer);
        target.storeSpore(biteBloskerSpore);
        sporesThrown++;
        numberOfSpores = -1;
        if(getSporesThrown()==5){
            die();
        }
    }
}