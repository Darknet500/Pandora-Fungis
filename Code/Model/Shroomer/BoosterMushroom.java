package Model.Shroomer;

import Model.Bridge.GameBoard;
import Model.Tekton.TektonBase;
import java.util.List;
import java.util.Collections;


/**
 * A BoosterMushroom egy speciális gomba, amely Booster spórákat tud szórni.
 * Egy adott Shroomerhez és egy Tektonhoz tartozik.
 */
public class BoosterMushroom extends Mushroom {

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új BoosterMushroom példányt a megadott Shroomerhez, és a megadott Tektonra.
     *
     * @param shroomer - A gombát létrehozó Shroomer.
     * @param location - A Tekton, ahol a gomba található.
     */
    public BoosterMushroom(Shroomer shroomer, TektonBase location) {
        super(shroomer, location);
        GameBoard.addReferenceToMaps("boostermushroom", this);
    }

    /**
     * Elszór egy Booster spórát a megadott Tektonra.
     * Ha a gomba már öt spórát elszórt, elpusztul.
     *
     * @param target - A cél Tekton, amelyre a spórát elszórja.
     */
    public void sporeThrown(TektonBase target) {
        hitbox.onTextureChanged();
        Spore booster = new BoosterSpore(this.shroomer);
        target.storeSpore(booster);
        sporesThrown++;
        numberOfSpores = -1;
        if(getSporesThrown()==5){
            die();
        }

    }
}