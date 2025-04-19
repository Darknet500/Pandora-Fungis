package Model.Shroomer;

import Model.Bridge.GameBoard;
import Model.Bug.*;

import java.util.*;
import Model.Tekton.Tekton;

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
        GameBoard.addReferenceToMaps("proliferatingspore", this);
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

        Queue<Tekton> closestTektons = new ArrayDeque<>();
        closestTektons.addAll(b.getLocation().getNeighbours());
        while (!closestTektons.isEmpty()) {
            Tekton tekton = closestTektons.poll();
            if (tekton.tryBug(newbug)) {
                bugger.addBug(newbug);
                tekton.setBug(newbug);
                newbug.setLocation(tekton);
                break;
            } else{
                List<Tekton> currentNeighbours = tekton.getNeighbours();
                for(Tekton neighbour: currentNeighbours) {
                    if(!closestTektons.contains(neighbour)) {
                        closestTektons.add(neighbour);
                    }
                }
            }
        }

        return 0; //ennek a spóratípusnak a tápanyagtartalma (pontok)
    }
}