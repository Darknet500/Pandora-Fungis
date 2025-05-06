package Model.Tekton;

import java.util.*;

import Model.Bridge.GameBoard;
import Model.Observer.EventType;
import Model.Observer.Observable;
import Model.Shroomer.Hypa;
import Model.Shroomer.Shroomer;
import Model.Shroomer.Spore;


/**
 * A Swamp egy speciális Tekton típus, amelyen a fonalak (Hypa) egy idő után felszívódnak.
 * Ez azt jelenti, hogy a Hypa objektumok idővel eltűnnek erről a Tektonról.
 */
public class Swamp extends TektonBase implements Observable {

    /**
     * Alapértelmezett konstruktor, amely létrehoz egy Swamp példányt.
     * Meghívja a szülőosztály (Tekton) konstruktorát.
     */
    public Swamp() {
        super();
        GameBoard.addReferenceToMaps("swamp", this);
    }

    /**
     * Megvizsgálja, hogy a megadott Shroomer kapcsolhat-e ehhez a Tektonhoz Hypa-t.
     * @param shroomer - A megadott Shroomer.
     * @return Igaz, ha a Shroomer kapcsolhat, egyébként hamis.
     */
    @Override
    public boolean acceptHypa(Shroomer shroomer) {
        if (shroomer == null) {
            return false;  // Ha a paraméterül kapott Shroomer null, akkor nem fogadjuk el
        }

        if(connectedHypas.size()==0) {

            return true;
        }

        // Ha már van egy Hypa ezen a Tekton-on, akkor ellenőrizzük, hogy a paraméterül kapott Shroomer-e
        for (Hypa hypa : getHypas()) {
            if (hypa.getShroomer() != null && hypa.getShroomer().equals(shroomer)) {

                return true;  // Ha már a paraméterül kapott Shroomer-nek van Hypa-ja, akkor true-t adunk vissza
            }
        }
        return false;
    }

    @Override
    public void endOfRound() {
        checkForDeleteHypa();
    }

    /**
     * Megvizsgálja, hogy a megadott Shroomer növeszthet-e gombát ezen a Tektonon.
     * @param s - A kérdéses Shroomer.
     * @return - Igaz, ha növeszthet (mert teljesül minden feltétel), egyébként hamis.
     */
    @Override
    public boolean canMushroomGrow(Shroomer s) {
        if (hasMushroom())
            return false;
        if (s == null) {
            return false;  // Ha a Shroomer null, akkor nem tud nőni gomba
        }

        // 1. Ellenőrizzük, hogy van-e csatlakozó Hypa-ja a Tekton-hoz
        boolean hasHypa = false;
        for (Hypa hypa : connectedHypas) {
            if (hypa.getShroomer().equals(s)) {
                hasHypa = true;
                break;  // Ha van csatlakozó Hypa, akkor továbblépünk
            }
        }

        if (!hasHypa) {
            return false;  // Ha nincs csatlakozó Hypa, akkor nem nőhet gomba
        }

        // 2. Ellenőrizzük, hogy van-e legalább három spórája a Tekton-on a Shroomer-nek
        int sporeCount = 0;
        for (Spore spore : this.storedSpores) {
            if (spore.getShroomer().equals(s)) {
                sporeCount++;
            }
        }

        // Ha legalább három spóra van, akkor visszaadjuk, hogy nőhet gomba
        if(sporeCount>=3) {
            return true;
        }else{
            return false;
        }

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

    /**
     * A Tekton törése: létrehoz egy új Tekton példányt,
     * a szomszédokat véletlenszerűen két részre ostja a régi és az új Tekton között. A szétválasztás
     * után a kapcsolódó fonalak elhalnak.
     */
    @Override
    public void breakTekton(long seed) {
        Swamp newTekton = new Swamp();
        notifyObservers(EventType.TEKTON_BROKEN, newTekton);

        // Szétosztjuk a szomszédokat 50-50%
        Random rnd = new Random(seed);

        List<TektonBase> remain = new ArrayList<>();
        List<TektonBase> newNeighbours = new ArrayList<>();

        for (TektonBase neighbour: neighbours) {
            if (rnd.nextInt(2)==0) { // 50%, h áthelyezzük az újhoz
                newNeighbours.add(neighbour);
                notifyObservers(EventType.NEIGHBOUR_REMOVED, neighbour);
            } else{
                remain.add(neighbour);
            }
        }

        this.setNeighbours(remain);
        newTekton.setNeighbours(newNeighbours);

        // A régi és az új szomszédok lesznek
        addNeighbour(newTekton);
        newTekton.addNeighbour(this);
        notifyObservers(EventType.NEIGHBOUR_ADDED, newTekton);
        // A régi Tekton összes fonala elhal
        List<Hypa> hypasList = new ArrayList<Hypa>();
        hypasList.addAll(connectedHypas);
        for(Hypa h : hypasList){
            h.die();
            notifyObservers(EventType.HYPA_REMOVED, h);
        }
    }

}