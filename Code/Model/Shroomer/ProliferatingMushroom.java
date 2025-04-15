package Model.Shroomer;

import Model.Bridge.GameBoard;
import Model.Tekton.Tekton;

import java.util.Collections;
import java.util.List;



/**
 * A ProliferatingMushroom egy speciális gomba, amely Proliferating spórát tud szórni.
 * Egy adott Shroomerhez kapcsolódik és egy Tektonon található.
 */
public class ProliferatingMushroom extends Mushroom {

    private static int proliferatingMmushroomID =0;

    private String name;

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új ProliferatingMushroom példányt a megadott Shroomerhez és Tektonra.
     *
     * @param shroomer - A gombát létrehozó Shroomer.
     * @param location - A Tekton, ahol a gomba található.
     */
    public ProliferatingMushroom(Shroomer shroomer, Tekton location) {
        super(shroomer, location);
        proliferatingMmushroomID++;
        name = "proliferatingmushroom" + proliferatingMmushroomID;
        GameBoard.nameObjectMap.put(name, this);
    }

    @Override
    public String getName(){return name;}

    /**
     * Egy ProliferatingSpore-t dob ki egy adott Tektonra.
     * Ha a gomba már 5 spórát dobott ki, elpusztul.
     *
     * @param target - A cél Tekton, amelyre a spóra kerül.
     */
    public void sporeThrown(Tekton target) {
        Spore spore = new ProliferatingSpore(this.shroomer);
        target.storeSpore(spore);
        sporesThrown++;
        numberOfSpores = -1;
        if(sporesThrown==5){
            die();
        }

    }
}