package Model.Shroomer;

import Model.Bridge.GameBoard;
import Model.Tekton.*;

/**
 * A ParalyzerMushroom egy speciális gomba, amely Paralyzer spórát tud szórni.
 * Egy adott Shroomerhez kapcsolódik és egy Tektonon található.
 */
public class ParalyzerMushroom extends Mushroom {

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új ParalyzerMushroom példányt a megadott Shroomerhez és Tektonra.
     *
     * @param shroomer - A gombát létrehozó Shroomer.
     * @param location - A Tekton, ahol a gomba található.
     */
    public ParalyzerMushroom(Shroomer shroomer, TektonBase location) {
        super(shroomer, location);
        GameBoard.addReferenceToMaps("paralyzermushroom", this);
    }

    /**
     * Egy ParalyzerSpore-t dob ki egy adott Tektonra.
     * Ha a gomba már 5 spórát dobott ki, elpusztul.
     *
     * @param target - A cél Tekton, amelyre a spóra kerül.
     */
    public void sporeThrown(TektonBase target) {
        hitbox.onSporeThrown();
        hitbox.onSporeThrowableChanged();
        Spore spore = new ParalyzerSpore(this.shroomer);
        target.storeSpore(spore);
        sporesThrown++;
        numberOfSpores = -1;
        if(getSporesThrown()==5){
            die();
        }

    }
}