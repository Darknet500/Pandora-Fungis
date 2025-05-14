package Model.Tekton;

import Model.Bridge.GameBoard;
import Model.Shroomer.Hypa;
import Model.Shroomer.Shroomer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * A Stone egy speciális Tekton típus, amelyre nem nőhetnek gombatestek (Mushroom).
 * Ez azt jelenti, hogy a canMushroomGrow metódus mindig hamis értéket ad vissza.
 */
public class Stone extends TektonBase {

    /**
     * Alapértelmezett konstruktor, amely meghívja az ősosztály (Tekton) konstruktorát.
     */
    public Stone() {
        super();
        GameBoard.addReferenceToMaps("stone", this);
    }

    @Override
    public boolean canMushroomGrow(){
        return false;
    }

    @Override
    public void endOfRound(){}

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
    /**
     * Megvizsgálja, hogy nőhet-e gombatest (Mushroom) ezen a Stone típusú Tektonon.
     * Mivel a Stone-on nem nőhet gomba, ezért mindig hamis értéket ad vissza.
     *
     * @param s - A Shroomer, amely gombát szeretne növeszteni.
     * @return - Mindig false, mert Stone-on nem nőhet Mushroom.
     */
    @Override
    public boolean canMushroomGrow(Shroomer s) {
        return false;
    }

    /**
     * A Tekton törése: létrehoz egy új Tekton példányt,
     * a szomszédokat véletlenszerűen két részre ostja a régi és az új Tekton között. A szétválasztás
     * után a kapcsolódó fonalak elhalnak.
     */
    @Override
    public void breakTekton(long seed) {
        Stone newTekton = new Stone();

        if(seed == 42) {
            for(TektonBase neighbour: this.getNeighbours()){
                neighbour.removeNeighbour(this);
            }
            this.setNeighbours(new ArrayList<>());
            List<Hypa> hypasList = new ArrayList<Hypa>();
            hypasList.addAll(connectedHypas);
            for(Hypa h : hypasList){
                h.die();

            }
            return;
        }        // Szétosztjuk a szomszédokat 50-50%
        Random rnd = new Random(seed);

        List<TektonBase> remain = new ArrayList<>();
        List<TektonBase> newNeighbours = new ArrayList<>();

        for (TektonBase neighbour: neighbours) {
            if (rnd.nextInt(2)==0) { // 50%, h áthelyezzük az újhoz
                newNeighbours.add(neighbour);
            } else{
                remain.add(neighbour);
            }
        }

        this.setNeighbours(remain);
        newTekton.setNeighbours(newNeighbours);
        // A régi és az új szomszédok lesznek
        addNeighbour(newTekton);
        newTekton.addNeighbour(this);

        // A régi Tekton összes fonala elhal
        List<Hypa> hypasList = new ArrayList<Hypa>();
        hypasList.addAll(connectedHypas);
        for(Hypa h : hypasList){
            h.die();
        }
    }

}