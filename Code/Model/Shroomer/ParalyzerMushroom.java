package Shroomer;

import Tekton.Tekton;

import java.util.Collections;
import java.util.List;

import static Controll.Skeleton.SKELETON;


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
    public ParalyzerMushroom(Shroomer shroomer, Tekton location) {
        super(shroomer, location);
    }

    /**
     * Egy ParalyzerSpore-t dob ki egy adott Tektonra.
     * Ha a gomba már 5 spórát dobott ki, elpusztul.
     *
     * @param target - A cél Tekton, amelyre a spóra kerül.
     */
    public void sporeThrown(Tekton target) {
        SKELETON.printCall(this, Collections.singletonList(target), "sporeThrown");
        Spore spore = new ParalyzerSpore(this.shroomer);
        SKELETON.objectNameMap.put(spore, "paralyzerSpore");
        SKELETON.printCall(spore, List.of(shroomer), "ParalyzerSpore" );
        SKELETON.printReturn("");
        target.storeSpore(spore);
        if(getSporesThrown()==5){
            die();
        }
        SKELETON.printReturn("");

    }
}