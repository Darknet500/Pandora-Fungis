package Shroomer;

import Tekton.Tekton;

import java.util.Collections;

import static Controll.Skeleton.SKELETON;

/**
 * A BiteBlockerMushroom egy speciális gombafajta, amely BiteBlocker spórákat tud szórni.
 * Egy adott Shroomerhez és Tektonhoz tartozik.
 */
public class BiteBlockerMushroom extends Mushroom {

    /**
     * Alapértelmezett konstruktor.
     * Létrehoz egy új BiteBlockerMushroom példányt egy adott Shroomer és Tekton helyszín alapján.
     *
     * @param shroomer - A gombát létrehozó Shroomer.
     * @param location - A Tekton, ahol a gomba elhelyezkedik.
     */
    public BiteBlockerMushroom(Shroomer shroomer, Tekton location) {
        super(shroomer, location);
    }

    /**
     * Egy új BiteBlockerSpore spórát szór a megadott Tektonra.
     * Ha a gomba már öt spórát szórt, akkor elpusztul.
     *
     * @param target A Tekton, amelyre a spóra kerül.
     */
    public void sporeThrown(Tekton target) {
        SKELETON.printCall(this, Collections.singletonList(target), "sporeThrown");
        Spore biteBloskerSpore = new BiteBlockerSpore(this.shroomer);
        SKELETON.objectNameMap.put(biteBloskerSpore, "biteBloskerSpore");
        target.storeSpore(biteBloskerSpore);
        if(getSporesThrown()==5){
            die();
        }
        SKELETON.printReturn("");

    }
}