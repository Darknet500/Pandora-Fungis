package Model.Tekton;

import Model.Bridge.GameBoard;
import Model.Bug.Bug;
import Model.Shroomer.Hypa;
import Model.Shroomer.Mushroom;
import Model.Shroomer.Shroomer;
import Model.Shroomer.Spore;
import View.Hitbox.TektonHitbox;

import java.awt.*;
import java.util.*;
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

    protected TektonHitbox hitbox;

    protected double weight;

    protected Point centerPoint;

    public TektonBase() {
        this.bug = null;
        this.mushroom = null;
        this.storedSpores = new ArrayList<>();
        this.neighbours = new ArrayList<>();
        this.connectedHypas = new ArrayList<>();
        this.hitbox = null;

        Random rand = new Random();
        weight = 25*(3+Math.max(-3,Math.min(3,rand.nextGaussian())));
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setCenterPoint(Point centerPoint) {
        this.centerPoint = centerPoint;
        if(hitbox != null) {
            hitbox.refreshCenterPoint(centerPoint);
        }
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    /**
     * Sets the Hitbox of the tekton
     * @param h Hitbox wich will be set
     */
    public void addObserver(TektonHitbox h){
        this.hitbox = h;
    }

    public TektonHitbox getHitbox(){
        return this.hitbox;
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

    /**
     * az első gombatest növesztésének engedélyezése, vagy elutasítása, cask a stone tekton utasítja el
     * @return
     */
    public boolean canMushroomGrow(){ return true; }

    public abstract void breakTekton(long seed);

    /**
     * Eltávolítja a megadott szomszédot a Tekton szomszédlistájából.
     *
     * @param neighbour - Az eltávolítandó szomszéd Tekton.
     */
    public void removeNeighbour(TektonBase neighbour) {
        neighbours.remove(neighbour);
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
        if (s != null && storedSpores.remove(s)) {
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
        if (h != null && connectedHypas.remove(h)) {
        }
    }

    /**
     * A megadott Hypa-t hozzákapcsolja a Tektonhoz.
     * @param h - A hozzáadandó Hypa.
     */
    public void connectHypa(Hypa h) {
        if (h != null && !connectedHypas.contains(h)) {
            connectedHypas.add(h);
        }
    }

    /**
     * Gombát állít be a Tektonra, és eltávolítja a spórákat.
     * @param shr - A beállítandó gomba.
     */
    public void setMushroomRemoveSpores(Mushroom shr) {
        if(mushroom == null && shr != null) {
            //gomba beállítása
            Mushroom oldMushroom = this.mushroom;
            this.mushroom = shr;

            if (oldMushroom != null) {
            }

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
        if (t != null && !neighbours.contains(t)) {
            neighbours.add(t);
        }
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
        Bug oldBug = this.bug;
        this.bug = bug;
    }

    protected Point findBestPointAround(){

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

        HashSet<TektonBase> allTektons = new HashSet<>();
        Set<TektonBase> visited = new HashSet<>();

        Queue<TektonBase> bfs = new ArrayDeque<>();
        bfs.addAll(neighbours);
        visited.addAll(neighbours);

        while (!bfs.isEmpty()) {
            TektonBase t = bfs.poll();
            allTektons.add(t);

            for (TektonBase tneighbour : t.getNeighbours()) {
                if (!visited.contains(tneighbour)) {
                    bfs.add(tneighbour);
                    visited.add(tneighbour);
                }
            }
        }

        // megyünk egy kört a régi tekton körül, és oda helyezzük el az újat, ahol a maximális a távolság a többitől
        int circleSteps = 32;
        double maxdistance=0;
        Point bestPoint = new Point(0,0);
        for (int i=0;i<circleSteps;i++){
            Point tmppoint = new Point(centerPoint.x+(int)(Math.cos(i*Math.PI*2/circleSteps)*((double)((screensize.getHeight()-75)*2/15)+5)),centerPoint.y+(int)(Math.sin(i*Math.PI*2/circleSteps)*((double)((screensize.getHeight()-75)*2/15)+5)));
            double mindistance = 100000;
            for(TektonBase tekt: allTektons){
                double dist = Math.sqrt(Math.pow(tmppoint.x-tekt.getCenterPoint().getX(),2)+Math.pow(tmppoint.y-tekt.getCenterPoint().getY(),2));
                if (dist<mindistance)
                    mindistance=dist;
            }
            if (mindistance>maxdistance&&(tmppoint.x>(screensize.getHeight()-75)/15)&&tmppoint.y>(screensize.getHeight()-75)*2/15&&tmppoint.x<(screensize.getWidth()-((screensize.getHeight()-75)*2/15))&&tmppoint.y<((screensize.getHeight()-75)-(screensize.getHeight()-75)*2/15)){
                maxdistance=mindistance;
                bestPoint= new Point(tmppoint);
            }

        }
        return bestPoint;
    }

    protected void distributeNeighbours(TektonBase newTekton){
        newTekton.setCenterPoint(findBestPointAround());
        newTekton.setWeight(weight*2);
        weight *=2;
        List<TektonBase> oldneighbours = new ArrayList<>();
        oldneighbours.addAll(neighbours);


        for(TektonBase neighbour: this.getNeighbours()){
            neighbour.removeNeighbour(this);
        }
        this.setNeighbours(new ArrayList<>());
        List<Hypa> hypasList = new ArrayList<Hypa>();
        hypasList.addAll(connectedHypas);
        for(Hypa h : hypasList){
            h.die();
        }
        newTekton.addNeighbour(this);
        addNeighbour(newTekton);

        for(TektonBase neighbour: oldneighbours){
            if(neighbour.getCenterPoint().distance(centerPoint)<
                    neighbour.getCenterPoint().distance(newTekton.getCenterPoint())){
                this.addNeighbour(neighbour);
                neighbour.addNeighbour(this);
            }else{
                newTekton.addNeighbour(neighbour);
                neighbour.addNeighbour(newTekton);
            }
        }
        GameBoard.tektonSpreading();
    }

}
