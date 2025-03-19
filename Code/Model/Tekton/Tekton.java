package Tekton;

import Bug.Bug;
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
    private List<Hypa> connectedHypas;

    /**
     * Default constructor
     */
    public Tekton() {
    }

    /**
     * 
     */
    public void breakTekton() {
        // TODO implement here
    }

    /**
     * 
     */
    public void hasMushroom() {
        // TODO implement here
    }

    /**
     * @param s
     */
    public void storeSpore(Spore s) {
        // TODO implement here
    }

    /**
     * @param shroomer 
     * @return
     */
    public boolean acceptHypa(Shroomer shroomer) {
        // TODO implement here
        return false;
    }

    /**
     * @param s
     */
    public void removeSpore(Spore s) {
        // TODO implement here
    }

    /**
     * @param b
     */
    public void tryBug(Bug b) {
        // TODO implement here
    }

    /**
     * @return
     */
    public List<Tekton> getNeighboursByHypa() {
        // TODO implement here
        return null;
    }

    /**
     * @param h
     */
    public void removeHypa(Hypa h) {
        // TODO implement here
    }

    /**
     * 
     */
    public void checkOwnedHypas() {
        // TODO implement here
    }

    /**
     * @param s 
     * @return
     */
    public boolean canMushroomGrow(Shroomer s) {
        // TODO implement here
        return false;
    }

    /**
     * @param h
     */
    public void connectHypa(Hypa h) {
        // TODO implement here
    }

    /**
     * @param shr
     */
    public void setMushroomRemoveSpores(Mushroom shr) {
        // TODO implement here
    }

    /**
     * @param b
     */
    public void setBug(Bug b) {
        // TODO implement here
    }

    public List<Hypa> getHypas(){return connectedHypas;}

}