package Tekton;

import Shroomer.Shroomer;

import java.util.Collections;

import static Controll.Skeleton.SKELETON;

/**
 * A Peat egy speciális Tekton típus, amelyen több fonal (Hypa) is keresztezheti egymást.
 * Ez azt jelenti, hogy az acceptHypa metódus mindig igaz értéket ad vissza.
 */
public class Peat extends Tekton {

    /**
     * Alapértelmezett konstruktor, amely meghívja az ősosztály (Tekton) konstruktorát.
     */
    public Peat() {
        super();
    }

    /**
     * Ellenőrzi, hogy a megadott Shroomer kapcsolhat-e Hypa-t.
     * Mivel a Peat típusú Tekton nem korlátozza a fonalak számát,
     * ezért mindig igaz értéket ad vissza.
     *
     * @param shroomer - A Shroomer, amely Hypa-t szeretne csatlakoztatni.
     * @return - Mindig true, mivel a Peat nem korlátozza a Hypa-k számát.
     */
    @Override
    public boolean acceptHypa(Shroomer shroomer) {
        SKELETON.printCall(this, Collections.singletonList(shroomer), "acceptHypa");
        SKELETON.printReturn("true");
        return true;
    }

}