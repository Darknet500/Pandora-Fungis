package Tekton;

import Bug.Bug;
import Controll.Skeleton;
import Shroomer.Hypa;
import Shroomer.Mushroom;
import Shroomer.Shroomer;
import Shroomer.Spore;

import java.util.*;

import static Controll.Skeleton.SKELETON;

/**
 * A Tekton osztály egy egységet reprezentál a pályán, amelynek lehetnek szomszédai,
 * tárolhat spórákat, illetve kapcsolódhat hozzá egy gomba vagy egy bogár.
 * A Tektonok egymáshoz kapcsolódhatnak és eltörhetnek.
 */
public class Tekton {

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
    protected List<Tekton> neighbours;

    /**
     * Az adott Tektonhoz kapcsolódó fonalak.
     */
    protected final List<Hypa> connectedHypas;

    /**
     * Alapértelmezett konstruktor, amely inicializálja a Tekton objektumot.
     * Kezdetben nincs hozzá társítva bogár vagy gomba, és üres a szomszédok,
     * spórák és fonalak listája.
     */
    public Tekton() {
        this.bug = null;
        this.mushroom = null;
        this.storedSpores = new ArrayList<>();
        this.neighbours = new ArrayList<>();
        this.connectedHypas = new ArrayList<>();
    }


    /**
     * Ellenőrzi, hogy van-e legalább egy spóra a Tektonon.
     *
     * @return true, ha van spóra, különben false.
     */
    public boolean hasSpore(){
        SKELETON.printCall(this, Collections.emptyList(), "hasSpore");
        if(storedSpores.isEmpty()){
            SKELETON.printReturn("false");
            return false;
        }
        SKELETON.printReturn("true");
        return true;
    }


    /**
     * A Tekton törése: létrehoz egy új Tekton példányt,
     * a szomszédokat véletlenszerűen két részre ostja a régi és az új Tekton között. A szétválasztás
     * után a kapcsolódó fonalak elhalnak.
     */
    public void breakTekton() {
        SKELETON.printCall(this, Collections.emptyList(), "breakTekton");
        Tekton newTekton = new Tekton();
        SKELETON.objectNameMap.put(newTekton, "newTekton");
        SKELETON.printCall(newTekton, Collections.emptyList(), "Tekton");
        SKELETON.printReturn("");

        // Szétosztjuk a szomszédokat 50-50%

        List<Tekton> remain = new ArrayList<>();
        List<Tekton> newNeighbours = new ArrayList<>();

        for (Tekton neighbour: neighbours) {
            if (Math.random() < 0.5) { // 50%, h áthelyezzük az újhoz
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
        SKELETON.printReturn("");
    }

    /**
     * Eltávolítja a megadott szomszédot a Tekton szomszédlistájából.
     *
     * @param neighbour - Az eltávolítandó szomszéd Tekton.
     */
    public void removeNeighbour(Tekton neighbour) {
        SKELETON.printCall(this, Collections.emptyList(), "removeNeighbour");
        this.neighbours.remove(neighbour);
        SKELETON.printReturn("");
    }

    /**
     * Ellenőrzi, hogy van-e gomba a megadott Tektonon.
     *
     * @return true, ha van gomba, különben false.
     */
    public boolean hasMushroom() {
        SKELETON.printCall(this, Collections.emptyList(), "hasMushroom");
        if(mushroom == null){
            SKELETON.printReturn("false");
            return false;
        }else{
            SKELETON.printReturn("true");
            return true;
        }

    }

    /**
     * A spórát elszórás után hozzáadja a Tekton spóralistájához.
     *
     * @param s - A tárolandó spóra.
     */
    public void storeSpore(Spore s) {
        SKELETON.printCall(this, Collections.singletonList(s), "storeSpore");

        if (s != null) {
            storedSpores.add(s);
        }
        SKELETON.printReturn("");
    }

    /**
     * Megvizsgálja, hogy a megadott Shroomer kapcsolhat-e ehhez a Tektonhoz Hypa-t.
     * @param shroomer - A megadott Shroomer.
     * @return Igaz, ha a Shroomer kapcsolhat, egyébként hamis.
     */
    public boolean acceptHypa(Shroomer shroomer) {
        SKELETON.printCall(this, Collections.singletonList(shroomer), "acceptHypa");
        if (shroomer == null) {
            SKELETON.printReturn("false");
            return false;  // Ha a paraméterül kapott Shroomer null, akkor nem fogadjuk el
        }

        if(connectedHypas.size()==0) {
            SKELETON.printReturn("true");

            return true;
        }

        // Ha már van egy Hypa ezen a Tekton-on, akkor ellenőrizzük, hogy a paraméterül kapott Shroomer-e
        for (Hypa hypa : getHypas()) {
            if (hypa.getShroomer() != null && hypa.getShroomer().equals(shroomer)) {
                SKELETON.printReturn("true");

                return true;  // Ha már a paraméterül kapott Shroomer-nek van Hypa-ja, akkor true-t adunk vissza
            }
        }
        SKELETON.printReturn("false");
        return false;
    }

    /**
     * Eltávolítja a megadott spórát a Tektonról.
     * @param s - Az eltávolítandó spóra.
     */
    public void removeSpore(Spore s) {
        SKELETON.printCall(this, Collections.singletonList(s), "removeSpore");
        if (s != null) {
            storedSpores.remove(s);
        }
        SKELETON.printReturn("");
    }

    /**
     * Hozzáadja a Tektonhoz a megadott bogarat, ha még nincs rajta másik bogár.
     * @param b - A hozzáadandó bogár.
     */
    public boolean tryBug(Bug b) {
        SKELETON.printCall(this, Collections.singletonList(b), "tryBug");
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
    public List<Tekton> getNeighboursByHypa() {
        SKELETON.printCall(this, Collections.emptyList(), "getNeighboursByHypa");
        List<Tekton> neighboursByHypa = new ArrayList<>();
        SKELETON.print=false;
        // végigmegyünk az összes hypa-n
        for (Hypa hypa : connectedHypas) {
            Tekton end1 = hypa.getEnd1();
            Tekton end2 = hypa.getEnd2();

            // ha end1 nem maga a Tekton, és még nincs benne a listában, hozzáadjuk
            if (end1 != this && !neighboursByHypa.contains(end1)) {
                neighboursByHypa.add(end1);
            }

            // ha end2 nem maga a Tekton, és még nincs benne a listában, hozzáadjuk
            if (end2 != this && !neighboursByHypa.contains(end2)) {
                neighboursByHypa.add(end2);
            }
        }
        SKELETON.print=true;

        SKELETON.printReturn("neighboursByHypa: List<Tekton>");
        // vissztér az új listával
        return neighboursByHypa;
    }

    /**
     * Eltávolítja a megadott Hypa-t a Tektonhoz kapcsolódó Hypa-k közül.
     * @param h - Az eltávolítandó Hypa.
     */
    public void removeHypa(Hypa h) {
        SKELETON.printCall(this, Collections.singletonList(h), "removeHypa");
        if (h != null) {
            connectedHypas.remove(h);
        }
        SKELETON.printReturn("");
    }

    /**
     * Megvizsgálja, hogy a megadott Shroomer növeszthet-e gombát ezen a Tektonon.
     * @param s - A kérdéses Shroomer.
     * @return - Igaz, ha növeszthet (mert teljesül minden feltétel), egyébként hamis.
     */
    public boolean canMushroomGrow(Shroomer s) {
        SKELETON.printCall(this, Collections.singletonList(s), "canMushroomGrow");
        if (s == null) {
            SKELETON.printReturn("false");
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
            SKELETON.printReturn("false");
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
            SKELETON.printReturn("true");
            return true;
        }else{
            SKELETON.printReturn("false");
            return false;
        }

    }

    /**
     * A megadott Hypa-t hozzákapcsolja a Tektonhoz.
     * @param h - A hozzáadandó Hypa.
     */
    public void connectHypa(Hypa h) {
        SKELETON.printCall(this, Collections.singletonList(h), "connectHypa");
        //if (h != null && !getHypas().contains(h)) {
        //    getHypas().add(h);
        //}
        connectedHypas.add(h);
        SKELETON.printReturn("");
    }

    /**
     * Gombát állít be a Tektonra, és eltávolítja a spórákat.
     * @param shr - A beállítandó gomba.
     */
    public void setMushroomRemoveSpores(Mushroom shr) {
        SKELETON.printCall(this, Collections.singletonList(shr), "setMushroomRemoveSpores");
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
                    }
                }
            }
        }
        SKELETON.printReturn("");
    }

    /**
     * Visszaadja a Tektonhoz kapcsolódó Hypa-k listáját.
     * @return - A Hypa-k listája.
     */
    public List<Hypa> getHypas(){
        SKELETON.printCall(this, Collections.emptyList(), "getHypas");
        SKELETON.printReturn("connectedHypas: List<Hypa>");
        return connectedHypas;
    }

    /**
     * Visszaadja a Tekton szomszédait.
     * @return - A szomszédos Tektonok listája.
     */
    public List<Tekton> getNeighbours() {
        SKELETON.printCall(this, Collections.emptyList(), "getNeighbours");
        SKELETON.printReturn("neighbours: List<Tekton>");
        return neighbours;
    }

    /**
     * Beállítja a Tekton szomszédait a kapott lista alapján.
     * @param neighbours - A szomszédos Tektonok listája.
     */
    public void setNeighbours(List<Tekton> neighbours) {
        SKELETON.printCall(this, Collections.singletonList(neighbours), "setNeighbours");
        this.neighbours = neighbours;
        SKELETON.printReturn("");
    }

    /**
     * Hozzáad egy új szomszédos Tekton-t a jelenlegihez.
     * @param t - A hozzáadandó Tekton.
     */
    public void addNeighbour(Tekton t){
        SKELETON.printCall(this, List.of(t), "addNeighbour");
        neighbours.add(t);
        SKELETON.printReturn("");
    }

    /**
     * Visszaadja a Tektonon lévő spórák listáját.
     * @return - A spórák listája.
     */
    public List<Spore> getStoredSpores() {
        SKELETON.printCall(this, Collections.emptyList(), "getStoredSpores");
        SKELETON.printReturn("storedSpores: List<Spore>");
        return storedSpores;
    }

    /**
     * Beállítja a Tektonon tárolt spórák listáját a kapott lista alapján.
     * @param storedSpores - Az új spóra lista.
     */
    public void setStoredSpores(List<Spore> storedSpores) {
        SKELETON.printCall(this, Collections.singletonList(storedSpores), "setStoredSpores");
        this.storedSpores = storedSpores;
        SKELETON.printReturn("");
    }

    /**
     * Visszaadja a Tektonon lévő gombát.
     * @return - A jelenlegi gomba, ha van.
     */
    public Mushroom getMushroom() {
        SKELETON.printCall(this, Collections.emptyList(), "getMushroom");
        SKELETON.printReturn(SKELETON.objectNameMap.get(mushroom)+": Mushroom");
        return mushroom;
    }

    /**
     * Beállítja a Tektonon lévő gombát.
     * @param mushroom - Az új gomba.
     */
    public void setMushroom(Mushroom mushroom) {
        SKELETON.printCall(this, Collections.singletonList(mushroom), "setMushroom");
        this.mushroom = mushroom;
        SKELETON.printReturn("");
    }

    /**
     * Visszaadja a Tektonon lévő bogarat.
     * @return - A jelenlegi bogár, ha van.
     */
    public Bug getBug() {
        SKELETON.printCall(this, Collections.emptyList(), "getBug");
        SKELETON.printReturn(SKELETON.objectNameMap.get(bug)+": Bug");
        return bug;
    }

    /**
     * Beállítja a Tektonon lévő bogarat.
     * @param bug - Az új bogár.
     */
    public void setBug(Bug bug) {
        SKELETON.printCall(this,Collections.singletonList(bug), "setBug");
        this.bug = bug;
        SKELETON.printReturn("");
    }
}