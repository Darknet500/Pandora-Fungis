package Model.Shroomer;

import Model.Bridge.GameBoard;
import Model.Tekton.*;

import java.util.Collections;
import java.util.List;


/**
 * A SlowerMushroom egy speciális gomba, amely Slower spórát tud szórni.
 * Egy adott Shroomerhez kapcsolódik és egy Tektonon található.
 */
public class SlowerMushroom extends Mushroom {

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új SlowerMushroom példányt.
     * A konstruktor a szülőosztály konstruktorát hívja meg a gomba inicializálásához,
     * a megfelelő Shroomer és Tekton paraméterekkel.
     *
     * @param shroomer - A Shroomer objektum, amely a gombát birtokolja.
     * @param location - A Tekton objektum, amely meghatározza a gomba helyét.
     */
    public SlowerMushroom(Shroomer shroomer, Tekton location) {
        super(shroomer, location);
        GameBoard.addReferenceToMaps("slowermushroom", this);
    }

    /**
     * Egy SlowerSpore-t dob ki egy adott Tektonra.
     * Ha a gomba már 5 spórát dobott ki, elpusztul.
     *
     * @param target - A cél Tekton, amelyre a spóra kerül.
     */
    public void sporeThrown(Tekton target) {
        SlowerSpore spore = new SlowerSpore(this.shroomer);
        target.storeSpore(spore);
        sporesThrown++;
        numberOfSpores = -1;
        if(getSporesThrown()==5){
            die();
        }

    }
}