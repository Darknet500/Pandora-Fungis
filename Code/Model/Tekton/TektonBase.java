package Model.Tekton;

import Model.Bridge.GameBoard;
import Model.Bug.Bug;
import Model.Shroomer.Hypa;
import Model.Shroomer.Mushroom;
import Model.Shroomer.Shroomer;
import Model.Shroomer.Spore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class TektonBase {
    /**
     * Az adott Tektonhoz kapcsolódó bogár.
     */
    protected Bug bug;

    /**
     * Az adott Tektonhoz kapcsolódó gomba.
     */
    protected Mushroom mushroom;

    /**
     * A Tektonon lévő spórák listája.
     */
    protected List<Spore> storedSpores;

    /**
     * Az adott Tekton szomszédai.
     */
    protected List<TektonBase> neighbours;

    /**
     * Az adott Tektonhoz kapcsolódó fonalak.
     */
    protected List<Hypa> connectedHypas;

    public TektonBase() {
        this.bug = null;
        this.mushroom = null;
        this.storedSpores = new ArrayList<>();
        this.neighbours = new ArrayList<>();
        this.connectedHypas = new ArrayList<>();
    }

    /**
     * Ezt utólag írtam hozzá.
     * ellenőrzi, hogy a megadott tekton szomszédja ennek a tektonnak
     * @param t2 a másik tekton
     * @return true ha szomszédok, false ha nem
     */
    public boolean isNeighbour(TektonBase t2) {
        if(t2==null) return false;
        if(this==t2) return false;
        return this.neighbours.contains(t2);
    }
    /**
     * Ellenőrzi, hogy van-e legalább egy spóra a Tektonon.
     *
     * @return true, ha van spóra, különben false.
     */
    public boolean hasSpore(){
        if(storedSpores.isEmpty()){
            return false;
        }
        return true;
    }

    public abstract void endOfRound();

    public abstract boolean acceptHypa(Shroomer shroomer);

    public abstract boolean canMushroomGrow(Shroomer s);

    public abstract void breakTekton(long seed);

    /**
     * Eltávolítja a megadott szomszédot a Tekton szomszédlistájából.
     *
     * @param neighbour - Az eltávolítandó szomszéd Tekton.
     */
    public void removeNeighbour(TektonBase neighbour) {
        this.neighbours.remove(neighbour);
    }

    /**
     * Ellenőrzi, hogy van-e gomba a megadott Tektonon.
     *
     * @return true, ha van gomba, különben false.
     */
    public boolean hasMushroom() {
        if(mushroom == null){
            return false;
        }else{
            return true;
        }

    }

    /**
     * A spórát elszórás után hozzáadja a Tekton spóralistájához.
     *
     * @param s - A tárolandó spóra.
     */
    public void storeSpore(Spore s) {

        if (s != null) {
            storedSpores.add(s);
        }
    }

    /**
     * Eltávolítja a megadott spórát a Tektonról.
     * @param s - Az eltávolítandó spóra.
     */
    public void removeSpore(Spore s) {
        if (s != null) {
            storedSpores.remove(s);
        }
    }

    /**
     * Hozzáadja a Tektonhoz a megadott bogarat, ha még nincs rajta másik bogár.
     * @param b - A hozzáadandó bogár.
     */
    public boolean tryBug(Bug b) {
        if(bug == null) {
            bug = b;
            return true;
        }
        return false;
    }

    /**
     * Visszaadja a Hypa-kon keresztül elérhető szomszédos Tektonokat.
     * @return - A Hypa-kon keresztül elérhető Tektonok listája.
     */
    public List<TektonBase> getNeighboursByHypa() {
        List<TektonBase> neighboursByHypa = new ArrayList<>();
        // végigmegyünk az összes hypa-n
        for (Hypa hypa : connectedHypas) {
            TektonBase end1 = hypa.getEnd1();
            TektonBase end2 = hypa.getEnd2();

            // ha end1 nem maga a Tekton, és még nincs benne a listában, hozzáadjuk
            if (end1 != this && !neighboursByHypa.contains(end1)) {
                neighboursByHypa.add(end1);
            }

            // ha end2 nem maga a Tekton, és még nincs benne a listában, hozzáadjuk
            if (end2 != this && !neighboursByHypa.contains(end2)) {
                neighboursByHypa.add(end2);
            }
        }

        // vissztér az új listával
        return neighboursByHypa;
    }

    /**
     * Eltávolítja a megadott Hypa-t a Tektonhoz kapcsolódó Hypa-k közül.
     * @param h - Az eltávolítandó Hypa.
     */
    public void removeHypa(Hypa h) {
        if (h != null) {
            connectedHypas.remove(h);
        }
    }

    /**
     * A megadott Hypa-t hozzákapcsolja a Tektonhoz.
     * @param h - A hozzáadandó Hypa.
     */
    public void connectHypa(Hypa h) {
        //if (h != null && !getHypas().contains(h)) {
        //    getHypas().add(h);
        //}
        connectedHypas.add(h);
    }

    /**
     * Gombát állít be a Tektonra, és eltávolítja a spórákat.
     * @param shr - A beállítandó gomba.
     */
    public void setMushroomRemoveSpores(Mushroom shr) {
        if(mushroom == null && shr != null) {
            //gomba beállítása
            mushroom = shr;

            //shroomer lekérése
            Shroomer shroomer = shr.getShroomer();

            if (shroomer != null) {
                int removedCount = 0;

                // Ciklus a Tekton storedSpores listáján
                Iterator<Spore> iterator = storedSpores.iterator();
                while (iterator.hasNext() && removedCount < 3) {
                    Spore spore = iterator.next();

                    // Ha a spóra a Shroomer-hez tartozik, eltávolítjuk
                    if (spore.getShroomer().equals(shroomer)) {
                        iterator.remove();  // Eltávolítjuk a storedSpores listából
                        removedCount++;
                        GameBoard.removeReferenceFromMaps(spore);
                    }
                }
            }
        }
    }

    /**
     * Visszaadja a Tektonhoz kapcsolódó Hypa-k listáját.
     * @return - A Hypa-k listája.
     */
    public List<Hypa> getHypas(){
        return connectedHypas;
    }

    /**
     * Visszaadja a Tekton szomszédait.
     * @return - A szomszédos Tektonok listája.
     */
    public List<TektonBase> getNeighbours() {
        return neighbours;
    }

    /**
     * Beállítja a Tekton szomszédait a kapott lista alapján.
     * @param neighbours - A szomszédos Tektonok listája.
     */
    public void setNeighbours(List<TektonBase> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Hozzáad egy új szomszédos Tekton-t a jelenlegihez.
     * @param t - A hozzáadandó Tekton.
     */
    public void addNeighbour(TektonBase t){
        neighbours.add(t);
    }

    /**
     * Visszaadja a Tektonon lévő spórák listáját.
     * @return - A spórák listája.
     */
    public List<Spore> getStoredSpores() {
        return storedSpores;
    }

    /**
     * Beállítja a Tektonon tárolt spórák listáját a kapott lista alapján.
     * @param storedSpores - Az új spóra lista.
     */
    public void setStoredSpores(List<Spore> storedSpores) {
        this.storedSpores = storedSpores;
    }

    /**
     * Visszaadja a Tektonon lévő gombát.
     * @return - A jelenlegi gomba, ha van.
     */
    public Mushroom getMushroom() {
        return mushroom;
    }

    /**
     * Beállítja a Tektonon lévő gombát.
     * @param mushroom - Az új gomba.
     */
    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    /**
     * Visszaadja a Tektonon lévő bogarat.
     * @return - A jelenlegi bogár, ha van.
     */
    public Bug getBug() {
        return bug;
    }

    /**
     * Beállítja a Tektonon lévő bogarat.
     * @param bug - Az új bogár.
     */
    public void setBug(Bug bug) {
        this.bug = bug;
    }
}
