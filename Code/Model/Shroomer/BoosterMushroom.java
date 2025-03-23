package Shroomer;

import Tekton.Tekton;
import java.util.List;
import java.util.Collections;

import static Controll.Skeleton.SKELETON;

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
    public BoosterMushroom(Shroomer shroomer, Tekton location) {
        super(shroomer, location);
    }

    /**
     * Elszór egy Booster spórát a megadott Tektonra.
     * Ha a gomba már öt spórát elszórt, elpusztul.
     *
     * @param target - A cél Tekton, amelyre a spórát elszórja.
     */
    public void sporeThrown(Tekton target) {
        SKELETON.printCall(this, Collections.singletonList(target), "sporeThrown");
        Spore booster = new BoosterSpore(this.shroomer);
        SKELETON.objectNameMap.put(booster, "booster");
        SKELETON.printCall(booster, List.of(shroomer), "BoosterSpore" );
        SKELETON.printReturn("");
        target.storeSpore(booster);
        if(getSporesThrown()==5){
            die();
        }
        SKELETON.printReturn("");

    }
}