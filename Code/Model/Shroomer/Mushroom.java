package Model.Shroomer;

import Model.Tekton.*;
import View.hitboxes.MushroomHitbox;

/**
 * A Mushroom osztály egy absztrakt osztály, amely a gombatesteket reprezentálja a játékban.
 * Minden Mushroom egy adott Tektonon található és egy Shroomer-hez tartozik.
 * A gombatestek megöregedhetnek, elhalhatnak és spórákat szórhatnak, valamint fonalakat (Hypa) növeszthetnek.
 */
public abstract class Mushroom {

    /**
     * eltárolja a hozzá kapcsolódó hitbox-ot, hogy tudja azt értesíteni az őt ért változásokról
     */
    protected MushroomHitbox hitbox;

    /**
     * A gombatest aktuális pozíciója.
     */
    protected TektonBase location;

    /**
     * A Shroomer, amely ezt a gombatestet létrehozta.
     */
    protected Shroomer shroomer;


    /**
     *
     */
    protected int age=0;

    /**
     *
     */
    protected int numberOfSpores = 1;

    /**
     *
     */
    protected int sporesThrown = 0;

    /**
     * Létrehoz egy új gombatestet a megadott Shroomer-rel és Tekton pozícióval.
     *
     * @param s - A gombatestet létrehozó Shroomer.
     * @param pos - A Tekton, amelyen a gombatest található.
     */
    public Mushroom(Shroomer s, TektonBase pos) {
        shroomer = s;
        location = pos;
    }

    /**
     * Lekérdezi a gombatest aktuális helyzetét a pályán.
     *
     * @return - A Tekton objektum, amin a gombatest van.
     */
    public TektonBase getLocation() {
        return location;
    }

    /**
     * A gombatest elhalását kezeli.
     * Értesíti a Shroomer-t a gombatest elhalásáról.
     */
    public void die() {
        shroomer.mushroomDied(this);
    }

    /**
     * A gombatest életkorának növelése minden kör után.
     */
    public void age() {
        System.out.println(age);
        age++;
        if(age==5)
            hitbox.onTextureChanged();
    }

    /**
     * Visszaadja a gombatestet létrehozó Shroomer objektumot.
     *
     * @return - A Shroomer, amelyhez a gombatest tartozik.
     */
    public Shroomer getShroomer() {
        return shroomer;
    }

    /**
     * Visszaadja a gombatest életkorát.
     * Az életkor befolyásolja a spóraszórást.
     *
     * @return - A gombatest életkora (0 és 20 közötti egész szám).
     */
    public int getAge(){
        return age;

    }

    /**
     * Megadja, hogy a gombatest készen áll-e a spóraszórásra.
     *
     * @return -1, ha most szórt spórát; 0, ha az előző körben szórt spórát;
     *         1, ha régebben szórt spórát és újra tud spórázni.
     */
    public int getNumberOfSpores(){
        return numberOfSpores;
    }

    public void increaseNumberofSpores() {

        numberOfSpores++;
        //if(numberOfSpores==1)
            hitbox.onTextureChanged();
    }

    /**
     * Visszaadja, hogy a gombatest eddig hány spórát szórt el.
     *
     * @return - A spóraszórások száma (0 és 5 közötti egész szám).
     */
    public int getSporesThrown(){
        return sporesThrown;
    }

    /**
     * Egy adott Tekton-ra spórát szór.
     * Az implementációt a konkrét gombatest típusok határozzák meg.
     *
     * @param to - A Tekton, amelyre a spóra kerül.
     */
    public abstract void sporeThrown(TektonBase to);

    public void addObserver(MushroomHitbox hitbox){
        this.hitbox = hitbox;
    }

    public MushroomHitbox getHitbox(){
        return hitbox;
    }
}