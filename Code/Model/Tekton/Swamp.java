package Tekton;

import java.util.*;
import Shroomer.Hypa;

/**
 * 
 */
public class Swamp extends Tekton {

    /**
     * Default constructor
     */
    public Swamp() {
        super();
    }

    /**
     * 
     */
    public void checkForDeleteHypa() {
        // Mivel a lista közben változni fog, biztonságosabb, ha először készítünk egy másolatot.
        Iterator<Hypa> iterator = getHypas().iterator();

        // Iterálunk az összes Hypa-n
        while (iterator.hasNext()) {
            Hypa hypa = iterator.next();

            // Ellenőrizzük a Hypa korát
            if (hypa.getAge() >= 3) {
                // Ha a Hypa kora legalább 3, akkor eltávolítjuk
                iterator.remove();
            }
        }
    }

}