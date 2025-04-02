package Tekton;

import java.util.*;
import Shroomer.Hypa;


/**
 * A Swamp egy speciális Tekton típus, amelyen a fonalak (Hypa) egy idő után felszívódnak.
 * Ez azt jelenti, hogy a Hypa objektumok idővel eltűnnek erről a Tektonról.
 */
public class Swamp extends Tekton {

    /**
     * Alapértelmezett konstruktor, amely létrehoz egy Swamp példányt.
     * Meghívja a szülőosztály (Tekton) konstruktorát.
     */
    public Swamp() {
        super();
    }
    
    public void endOfRound() {
        checkForDeleteHypa();
    }

    /**
     * Ellenőrzi, hogy vannak-e olyan Hypa objektumok, amelyeknek fel kell szívódniuk (koruk elérte a 3-at),
     * és ha igen, eltávolítja őket.
     * <p>
     * Mivel a `connectedHypas` lista módosulhat az iteráció során, először másolatot készítünk róla,
     * majd ezen a másolaton végigiterálva eltávolítjuk az idősebb Hypa objektumokat az eredeti listából.
     */
    public void checkForDeleteHypa() {
        // Mivel a lista közben változni fog, biztonságosabb, ha először készítünk egy másolatot.
        List<Hypa> hypasList = new ArrayList<>();
        hypasList.addAll(connectedHypas);
        Iterator<Hypa> iterator = hypasList.iterator();

        // Iterálunk az összes Hypa-n
        while (iterator.hasNext()) {
            Hypa hypa = iterator.next();

            // Ellenőrizzük a Hypa korát
            if (hypa.getAge() >= 3) {
                // Ha a Hypa kora legalább 3, akkor eltávolítjuk
                hypa.die();
            }
        }
    }

}