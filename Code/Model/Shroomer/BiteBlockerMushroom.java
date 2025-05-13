package Model.Shroomer;

import Model.Bridge.GameBoard;
import Model.Tekton.TektonBase;
import java.util.List;
import java.util.Collections;


/**
 * A BiteBlockerMushroom egy speciális gombafajta, amely BiteBlocker spórákat tud szórni.
 * Egy adott Shroomerhez és Tektonhoz tartozik.
 */
public class BiteBlockerMushroom extends Mushroom {

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új BiteBlockerMushroom példányt egy adott Shroomer és Tekton helyszín alapján.
     * elnevezi magát, és beleteszi a gameBoard nameObjectMap-jébe
     *
     * @param shroomer - A gombát létrehozó Shroomer.
     * @param location - A Tekton, ahol a gomba elhelyezkedik.
     */
    public BiteBlockerMushroom(Shroomer shroomer, TektonBase location) {
        super(shroomer, location);
        GameBoard.addReferenceToMaps("biteblockermushroom", this);
    }

    /**
     * Egy új BiteBlockerSpore spórát szór a megadott Tektonra.
     * Ha a gomba már öt spórát szórt, akkor elpusztul.
     *
     * @param target A Tekton, amelyre a spóra kerül.
     */
    public void sporeThrown(TektonBase target) {
        Spore biteBloskerSpore = new BiteBlockerSpore(this.shroomer, target);
        target.storeSpore(biteBloskerSpore);
        sporesThrown++;
        numberOfSpores = -1;
        if(getSporesThrown()==5){
            die();
        }else
            hitbox.onTextureChanged();
    }
}