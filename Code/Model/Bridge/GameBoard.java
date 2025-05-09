package Model.Bridge;

import Model.Tekton.*;
import Model.Shroomer.*;
import Model.Bug.*;
import View.IView;
import View.hitboxes.*;

import java.awt.*;
import java.util.*;
import java.util.HashMap;
import java.util.List;


public class GameBoard {
    static private IView view;

    static private List<TektonBase> allTektons;

    private HashMap<Integer, Shroomer> shroomers;
    private HashMap<Integer, Bugger> buggers;

    private static HashMap<String, Object> nameObjectMap;
    private static HashMap<Object, String> objectNameMap;
    private static HashMap<Player, String> playerDisplayNameMap;
    private static HashMap<Player, String> playersTypeMap;
    private static HashMap<Object, Hitbox> objectHitboxMap;
    private static HashMap<Hitbox, Object> hitboxObjectMap;
    private static HashMap<Bugger, Color> buggerColorMap;


    /**
     * ID számlálók minden modell osztályhoz
     */
    private static int biteBlockedID = 1;
    private static int boostedID = 1;
    private static int bugID = 1;
    private static int buggerID = 1;
    private static int normalID = 1;
    private static int paralyzedID = 1;
    private static int slowedID = 1;
    private static int biteBlockerMushroomID = 1;
    private static int biteBlockerSporeID = 1;
    private static int boosterMushroomID = 1;
    private static int boosterSporeID = 1;
    private static int hypaID = 1;
    private static int paralyzerMushroomID = 1;
    private static int paralyzerSporeID = 1;
    private static int proliferatingMmushroomID =1;
    private static int proliferatingSporeID = 1;
    private static int shroomerID = 1;
    private static int slowerMushroomID = 1;
    private static int slowerSporeID = 1;
    private static int peatID = 1;
    private static int soilID = 1;
    private static int stoneID = 1;
    private static int swampID = 1;
    private static int tektonID = 1;
    private static final Random rand = new Random();

    public GameBoard(){
        view = null;

        allTektons = new ArrayList<>();
        shroomers = new HashMap<>();
        buggers = new HashMap<>();
        nameObjectMap = new HashMap<>();
        objectNameMap = new HashMap<>();
        playerDisplayNameMap = new HashMap<>();
        objectHitboxMap = new HashMap<>();
        hitboxObjectMap = new HashMap<>();
        playersTypeMap = new HashMap<>();
        buggerColorMap = new HashMap<>();
    }

    public void connectToView(IView view){
        this.view = view;
    }

    public void addShroomer(Shroomer shroomer, String name){
        if (name.isEmpty()) name = "anonymous";
        int lastplayer = 0;
        while (shroomers.containsKey(lastplayer)||buggers.containsKey(lastplayer)){
            lastplayer++;
        }
        shroomers.put(lastplayer, shroomer);
        playerDisplayNameMap.put(shroomer, name);
    }

    public void addBugger(Bugger bugger, String name, Color c){
        if (name.isEmpty()) name = "anonymous";
        int lastplayer = 0;
        while (shroomers.containsKey(lastplayer)||buggers.containsKey(lastplayer)){
            lastplayer++;
        }
        buggers.put(lastplayer, bugger);
        playerDisplayNameMap.put(bugger, name);
        buggerColorMap.put(bugger, c);
    }

    public String getPlayerName(Player player){
        return playerDisplayNameMap.get(player);
    }

    public Hitbox getObjectHitbox(Object o){
        if(objectHitboxMap.containsKey(o)){
            return objectHitboxMap.get(o);
        }
        return null;
    }

    public HashMap<Integer, Shroomer> getShroomers(){
        return shroomers;
    }

    public HashMap<Integer, Bugger> getBuggers(){
        return buggers;
    }

    //public void addTekton(TektonBase tekton){
    //    allTektons.add(tekton);
    //}

    public List<TektonBase> getTektons(){
        return allTektons;
    }

    /**Utólag adtam hozzá templ8ban nincs benne. A kövi doksiba bele kell írni változásként
     * Ehhez az arrange file-ban a különböző típusú Tektonoknak (Soil, Swamp, Stone, stb...) a számozást
     * Folytatni kell a nevében "tekton1;swamp1" helyett "tekton1;swamp2" a helyes megadás
     *
     * @param idx a lekért tekton indexe
     * @return A tekton a idx-edik helyen a listában
     */
    public TektonBase getTekton(int idx){
        return allTektons.get(idx);
    }

    public Object getReferenceByObjectName(String name){
        return nameObjectMap.get(name);
    }
    public String getObjectNameByReference(Object ref){return objectNameMap.get(ref);}

    static public void addReferenceToMaps(String type, Object refe ){
        String name = null;
        //tektonokhoz tartozó előre beállítáűsok
        int tektonsCount = allTektons.size();
        int ystep = view.getDrawingSurfaceHeight()/6;
        int xstep = view.getDrawingSurfaceWidth()/6;
        double randmovx = (rand.nextDouble()-1)/4;
        double randmovy = (rand.nextDouble()-1)/4;
        Point point = new Point(xstep+tektonsCount%5*xstep+(int)(randmovx*xstep),ystep+(int)Math.floor((double)tektonsCount/5)*ystep+(int)(randmovy*ystep));



        //spórákhoz tartozó előre beállítások
        Point sporepoint = new Point(0,0);
        if(type.equals("boosterspore")||type=="paralyzerspore"||type=="proliferatingspore"||type=="slowerspore") {
            TektonBase sporelocation=null;
            for (TektonBase tektonBase : allTektons) {
                List<Spore> tektonsspore = tektonBase.getStoredSpores();
                for (Spore spore: tektonsspore){
                    if(spore==(Spore)refe){
                        sporelocation=tektonBase;
                        break;
                    }
                }
            }
            if(sporelocation!=null){
                /**
                 * Megnézem hogy mennyi spóra van létrehozva és aszerint 1/12-ed arréb mozgatom a tekton origójú 50 usgarú körön
                 */

                int sporecount = sporelocation.getStoredSpores().size()-1;
                double angle =  sporecount*Math.PI/6;
                Point locationTektonCenterPoint =((TektonHitbox)objectHitboxMap.get(sporelocation)).getCenterPoint();
                int x = locationTektonCenterPoint.x + (int)(Math.sin(angle)*50);
                int y = locationTektonCenterPoint.y + (int)(Math.cos(angle)*50);
                sporepoint.setLocation(x, y);
            }
        }

        ///mushroom létrehozásához közös dolgok beállítása
        Point tektoncenterpoint = new Point(0,0);
        if(type.equals("biteblockermushroom")||type.equals("paralyzermushroom")||type.equals("boostermushroom")||type.equals("slowermushroom")||type.equals("proliferatingmushroom")) {
            TektonBase location = ((Mushroom) refe).getLocation();
            TektonHitbox locationhitbox = (TektonHitbox) objectHitboxMap.get(location);
            tektoncenterpoint = locationhitbox.getDrawable().getPosition();
        }
        switch (type){
            case "biteblocked":{
                name = type + biteBlockedID++;

                break;
            }
            case "boosted":{
                name = type + boostedID++;

                break;
            }
            case "bug":{
                name = type + bugID++;
                TektonBase location = ((Bug)refe).getLocation();
                TektonHitbox locationhitbox = (TektonHitbox) objectHitboxMap.get(location);
                new BugHitbox((Bug)refe, locationhitbox.getDrawable().getPosition(), GameBoard.buggerColorMap.get((Bugger)refe));

                break;
            }
            case "bugger":{
                name = type + buggerID++;

                break;
            }
            case "normal":{
                name = type + normalID++;

                break;
            }
            case "paralyzed":{
                name = type + paralyzedID++;

                break;
            }
            case "slowed":{
                name = type + slowedID++;

                break;
            }
            case "biteblockermushroom":{
                name = type + biteBlockerMushroomID++;
                int tektonsize = (int)(ystep*0.8);
                MushroomHitbox hitbox = new MushroomHitbox((Mushroom)refe, new Point(tektoncenterpoint.x-(int)(tektonsize*0.25), tektoncenterpoint.y-(int)(tektonsize/10)), "biteblocker",(int)(tektonsize*0.45));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "biteblockerspore":{
                name = type + biteBlockerSporeID++;
                SporeHitbox hitbox = new SporeHitbox(sporepoint, (Spore)refe, "biteblockerspore");
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "boostermushroom":{
                int tektonsize = (int)(ystep*0.8);
                name = type + boosterMushroomID++;
                MushroomHitbox hitbox = new MushroomHitbox((Mushroom)refe, new Point(tektoncenterpoint.x-(int)(tektonsize*0.25), tektoncenterpoint.y-(int)(tektonsize/10)), "booster",(int)(tektonsize*0.45));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "boosterspore":{
                name = type + boosterSporeID++;
                SporeHitbox hitbox = new SporeHitbox(sporepoint, (Spore)refe, "boosterspore");
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "hypa":{
                name = type + hypaID++;

                break;
            }
            case "paralyzermushroom":{
                int tektonsize = (int)(ystep*0.8);
                name = type + paralyzerMushroomID++;
                MushroomHitbox hitbox = new MushroomHitbox((Mushroom)refe, new Point(tektoncenterpoint.x-(int)(tektonsize*0.25), tektoncenterpoint.y-(int)(tektonsize/10)), "paralyzer",(int)(tektonsize*0.45));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "paralyzerspore":{
                name = type + paralyzerSporeID++;
                SporeHitbox hitbox = new SporeHitbox(sporepoint, (Spore)refe, "paralyzerspore");
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "proliferatingmushroom":{
                int tektonsize = (int)(ystep*0.8);
                name = type + proliferatingMmushroomID++;
                MushroomHitbox hitbox = new MushroomHitbox((Mushroom)refe, new Point(tektoncenterpoint.x-(int)(tektonsize*0.25), tektoncenterpoint.y-(int)(tektonsize/10)), "proliferating",(int)(tektonsize*0.45));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "proliferatingspore":{
                name = type + proliferatingSporeID++;
                SporeHitbox hitbox = new SporeHitbox(sporepoint, (Spore)refe, "proliferatingspore");
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "shroomer":{
                name = type + shroomerID++;

                break;
            }
            case "slowermushroom":{
                int tektonsize = (int)(ystep*0.8);
                name = type + slowerMushroomID++;
                MushroomHitbox hitbox = new MushroomHitbox((Mushroom)refe, new Point(tektoncenterpoint.x-(int)(tektonsize*0.25), tektoncenterpoint.y-(int)(tektonsize/10)), "slower",(int)(tektonsize*0.45));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "slowerspore":{
                name = type + slowerSporeID++;
                SporeHitbox hitbox = new SporeHitbox(sporepoint, (Spore)refe, "slowerspore");
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "peat":{
                name = type + peatID++;
                allTektons.add((TektonBase)refe);
                TektonHitbox tektonHitbox = new TektonHitbox(point, (TektonBase)refe, "peat", (int)(ystep*0.8));
                objectHitboxMap.put(refe, tektonHitbox);
                hitboxObjectMap.put(tektonHitbox, refe);

                break;
            }
            case "soil":{
                name = type + soilID++;
                allTektons.add((TektonBase)refe);
                TektonHitbox tektonHitbox = new TektonHitbox(point, (TektonBase)refe, "soil", (int)(ystep*0.8));
                objectHitboxMap.put(refe, tektonHitbox);
                hitboxObjectMap.put(tektonHitbox, refe);
                break;
            }
            case "stone":{
                name = type + stoneID++;
                allTektons.add((TektonBase)refe);
                TektonHitbox tektonHitbox = new TektonHitbox(point, (TektonBase)refe, "stone", (int)(ystep*0.8));
                objectHitboxMap.put(refe, tektonHitbox);
                hitboxObjectMap.put(tektonHitbox, refe);
                break;
            }
            case "swamp":{
                name = type + swampID++;
                allTektons.add((TektonBase)refe);
                TektonHitbox tektonHitbox = new TektonHitbox(point, (TektonBase)refe, "swamp", (int)(ystep*0.8));
                objectHitboxMap.put(refe, tektonHitbox);
                hitboxObjectMap.put(tektonHitbox, refe);
                break;
            }
            case "tekton":{
                name = type + tektonID++;

                allTektons.add((TektonBase)refe);
                TektonHitbox tektonHitbox = new TektonHitbox(point, (TektonBase)refe, "tekton", (int)(ystep*0.8));
                objectHitboxMap.put(refe, tektonHitbox);
                hitboxObjectMap.put(tektonHitbox, refe);
                break;
            }
        }
        if(name!=null){
            objectNameMap.put(refe, name);
            nameObjectMap.put(name, refe);
        }
    }

    static public void removeReferenceFromMaps(Object refe){
        if (objectNameMap.containsKey(refe)) {
            nameObjectMap.remove(objectNameMap.get(refe));
            objectNameMap.remove(refe);
        }
    }

    public void clear(){
        System.out.println("clearing game board..");
        shroomers.clear();
        buggers.clear();
        allTektons.clear();
        nameObjectMap.clear();
        objectNameMap.clear();
        playerDisplayNameMap.clear();

        biteBlockedID = 1;
        boostedID = 1;
        bugID = 1;
        buggerID = 1;
        normalID = 1;
        paralyzedID = 1;
        slowedID = 1;
        biteBlockerMushroomID = 1;
        biteBlockerSporeID = 1;
        boosterMushroomID = 1;
        boosterSporeID = 1;
        hypaID = 1;
        paralyzerMushroomID = 1;
        paralyzerSporeID = 1;
        proliferatingMmushroomID = 1;
        proliferatingSporeID = 1;
        shroomerID = 1;
        slowerMushroomID = 1;
        slowerSporeID = 1;
        peatID = 1;
        soilID = 1;
        stoneID = 1;
        swampID = 1;
        tektonID = 1;
    }

    public int getNumberOfPlayers(){return shroomers.size() + buggers.size();}

}


