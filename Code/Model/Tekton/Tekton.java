package Tekton;

import Bug.Bug;
import Controll.Skeleton;
import Shroomer.Hypa;
import Shroomer.Mushroom;
import Shroomer.Shroomer;
import Shroomer.Spore;

import java.util.*;

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

    /**
     * 
     */
    public void breakTekton() {
        Tekton newTekton = new Tekton();

        // Szétosztjuk a szomszédokat 50-50%
        Iterator<Tekton> iterator = getNeighbours().iterator();
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
    }

    /**
     * 
     */
    public boolean hasMushroom() {
        return getMushroom() != null;
    }

    /**
     * @param s
     */
    public void storeSpore(Spore s) {
        if (s != null) {
            getStoredSpores().add(s);
        }
    }

    /**
     * @param shroomer 
     * @return
     */
    public boolean acceptHypa(Shroomer shroomer) {

        if (shroomer == null) {
            return false;  // Ha a paraméterül kapott Shroomer null, akkor nem fogadjuk el
        }

        if(connectedHypas.size()==0) return true;

        // Ha már van egy Hypa ezen a Tekton-on, akkor ellenőrizzük, hogy a paraméterül kapott Shroomer-e
        for (Hypa hypa : getHypas()) {
            if (hypa.getShroomer() != null && hypa.getShroomer().equals(shroomer)) {
                return true;  // Ha már a paraméterül kapott Shroomer-nek van Hypa-ja, akkor true-t adunk vissza
            }
        }

        return false;
    }

    /**
     * @param s
     */
    public void removeSpore(Spore s) {
        if (s != null) {
            getStoredSpores().remove(s);
        }
    }

    /**
     * @param b
     */
    public void tryBug(Bug b) {
        if(getBug() == null) {
            setBug(b);
        }
    }

    /**
     * @return
     */
    public List<Tekton> getNeighboursByHypa() {
        List<Tekton> neighboursByHypa = new ArrayList<>();

        // végigmegyünk az összes hypa-n
        for (Hypa hypa : getHypas()) {
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
        // vissztér az új listával
        return neighboursByHypa;
    }

    /**
     * @param h
     */
    public void removeHypa(Hypa h) {
        if (h != null) {
            getHypas().remove(h);
        }
    }

    /**
     * @param s 
     * @return
     */
    public boolean canMushroomGrow(Shroomer s) {
        if (s == null) {
            return false;  // Ha a Shroomer null, akkor nem tud nőni gomba
        }

        // 1. Ellenőrizzük, hogy van-e csatlakozó Hypa-ja a Tekton-hoz
        boolean hasHypa = false;
        for (Hypa hypa : getHypas()) {
            if (hypa.getShroomer() != null && hypa.getShroomer().equals(s)) {
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
            if (spore.getShroomer() != null && spore.getShroomer().equals(s)) {
                sporeCount++;
            }
        }

        // Ha legalább három spóra van, akkor visszaadjuk, hogy nőhet gomba
        return sporeCount >= 3;
    }

    /**
     * @param h
     */
    public void connectHypa(Hypa h) {
        if (h != null && !getHypas().contains(h)) {
            getHypas().add(h);
        }
    }

    /**
     * @param shr
     */
    public void setMushroomRemoveSpores(Mushroom shr) {
        if(getMushroom() == null && shr != null) {
            //gomba beállítása
            setMushroom(shr);

            //shroomer lekérése
            Shroomer shroomer = shr.getShroomer();

            if (shroomer != null) {
                int removedCount = 0;

                // Ciklus a Tekton storedSpores listáján
                Iterator<Spore> iterator = getStoredSpores().iterator();
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
    }

    public List<Hypa> getHypas(){return connectedHypas;}

    public List<Tekton> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Tekton> neighbours) {
        this.neighbours = neighbours;
    }

    public List<Spore> getStoredSpores() {
        return storedSpores;
    }

    public void setStoredSpores(List<Spore> storedSpores) {
        this.storedSpores = storedSpores;
    }

    public Mushroom getMushroom() {
        return mushroom;
    }

    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    public Bug getBug() {
        return bug;
    }

    public void setBug(Bug bug) {
        this.bug = bug;
    }
}