package Shroomer;

import Bug.Bug;
import Bug.Bugger;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Bug.Strategy;
import Bug.BiteBlocked;
import Tekton.Tekton;

/**
 * A BiteBlockerSpore egy speciális spóra, amely megakadályozza a Bug harapását.
 */
public class ProliferatingSpore extends Spore {

    /**
     * Alapértelmezett konstruktor
     * Létrehoz egy új BiteBlockerSpore példányt egy adott Shroomerhez kapcsolódva.
     *
     * @param shroomer - A spórát létrehozó Shroomer.
     */
    public ProliferatingSpore(Shroomer shroomer) {
        super(shroomer);
    }

    /**
     * A spóra hatást gyakorol egy Bug objektumra, amelynek hatására a harapási képessége blokkolódik.
     *
     * @param b - A Bug, amelyre a spóra hatással van.
     * @return - A hatás típusának sorszáma. (jelen esetben 2)
     */
    public int haveEffect(Bug b) {
        Bugger bugger = b.getBugger();
        Bug newbug = new Bug(bugger);
        bugger.addBug(newbug);

        Set<Tekton> closestTektons = new HashSet<Tekton>();
        closestTektons.addAll(b.getLocation().getNeighbours());
        for (Tekton tekton: closestTektons) {
            if (tekton.tryBug(newbug))
                break;
        }

        return 0; //ennek a spóratípusnak a tápanyagtartalma (pontok)
    }
}