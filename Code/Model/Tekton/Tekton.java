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
 * 
 */
public class Tekton {

    /**
     *
     */
    private Bug bug;

    /**
     *
     */
    private Mushroom mushroom;

    /**
     *
     */
    private List<Spore> storedSpores;

    /**
     *
     */
    private List<Tekton> neighbours;

    /**
     *
     */
    private final List<Hypa> connectedHypas;

    /**
     * Default constructor
     */
    public Tekton() {
        this.bug = null;
        this.mushroom = null;
        this.storedSpores = new ArrayList<>();
        this.neighbours = new ArrayList<>();
        this.connectedHypas = new ArrayList<>();
    }



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
     * 
     */
    public void breakTekton() {
        SKELETON.printCall(this, Collections.emptyList(), "breakTekton");
        Tekton newTekton = new Tekton();

        // Szétosztjuk a szomszédokat 50-50%
        Iterator<Tekton> iterator = neighbours.iterator();
        while (iterator.hasNext()) {
            Tekton neighbour = iterator.next();
            if (Math.random() < 0.5) { // 50%, h áthelyezzük az újhoz
                newTekton.neighbours.add(neighbour);
                iterator.remove();
            }
        }

        // A régi és az új szomszédok lesznek
        this.neighbours.add(newTekton);
        newTekton.neighbours.add(this);

        // A régi Tekton összes fonala elhal
        this.connectedHypas.clear();
        SKELETON.printReturn("");
    }

    /**
     * 
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
     * @param s
     */
    public void storeSpore(Spore s) {
        SKELETON.printCall(this, Collections.singletonList(s), "storeSpore");

        if (s != null) {
            storedSpores.add(s);
        }
        SKELETON.printReturn("");
    }

    /**
     * @param shroomer 
     * @return
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
     * @param s
     */
    public void removeSpore(Spore s) {
        SKELETON.printCall(this, Collections.singletonList(s), "removeSpore");
        if (s != null) {
            storedSpores.remove(s);
        }
        SKELETON.printReturn("");
    }

    /**
     * @param b
     */
    public void tryBug(Bug b) {
        SKELETON.printCall(this, Collections.singletonList(b), "tryBug");
        if(bug == null) {
            bug = b;
        }
        SKELETON.printReturn("");
    }

    /**
     * @return
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
     * @param h
     */
    public void removeHypa(Hypa h) {
        SKELETON.printCall(this, Collections.singletonList(h), "removeHypa");
        if (h != null) {
            connectedHypas.remove(h);
        }
        SKELETON.printReturn("");
    }

    /**
     * @param s 
     * @return
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
            if (hypa.getShroomer() != null && hypa.getShroomer().equals(s)) {
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
            if (spore.getShroomer() != null && spore.getShroomer().equals(s)) {
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
     * @param h
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
     * @param shr
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
                    if (spore != null && spore.getShroomer() != null && spore.getShroomer().equals(shroomer)) {
                        iterator.remove();  // Eltávolítjuk a storedSpores listából
                        removedCount++;
                    }
                }
            }
        }
        SKELETON.printReturn("");
    }

    public List<Hypa> getHypas(){
        SKELETON.printCall(this, Collections.emptyList(), "getHypas");
        SKELETON.printReturn("connectedHypas: List<Hypa>");
        return connectedHypas;
    }

    public List<Tekton> getNeighbours() {
        SKELETON.printCall(this, Collections.emptyList(), "getNeighbours");
        SKELETON.printReturn("neighbours: List<Tekton>");
        return neighbours;
    }

    public void setNeighbours(List<Tekton> neighbours) {
        SKELETON.printCall(this, Collections.singletonList(neighbours), "setNeighbours");
        this.neighbours = neighbours;
        SKELETON.printReturn("");
    }

    public List<Spore> getStoredSpores() {
        SKELETON.printCall(this, Collections.emptyList(), "getStoredSpores");
        SKELETON.printReturn("storedSpores: List<Spore>");
        return storedSpores;
    }

    public void setStoredSpores(List<Spore> storedSpores) {
        SKELETON.printCall(this, Collections.singletonList(storedSpores), "setStoredSpores");
        this.storedSpores = storedSpores;
        SKELETON.printReturn("");
    }

    public Mushroom getMushroom() {
        SKELETON.printCall(this, Collections.emptyList(), "getMushroom");
        SKELETON.printReturn(SKELETON.objectNameMap.get(mushroom)+": Mushroom");
        return mushroom;
    }

    public void setMushroom(Mushroom mushroom) {
        SKELETON.printCall(this, Collections.singletonList(mushroom), "setMushroom");
        this.mushroom = mushroom;
        SKELETON.printReturn("");
    }

    public Bug getBug() {
        SKELETON.printCall(this, Collections.emptyList(), "getBug");
        SKELETON.printReturn(SKELETON.objectNameMap.get(bug)+": Bug");
        return bug;
    }

    public void setBug(Bug bug) {
        SKELETON.printCall(this,Collections.singletonList(bug), "setBug");
        this.bug = bug;
        SKELETON.printReturn("");
    }
}