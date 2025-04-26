package Model.Bridge;

import Model.Tekton.*;
import Model.Shroomer.*;
import Model.Bug.*;
import java.util.*;
import java.util.HashMap;


public class GameBoard {
    private List<Tekton> allTektons;
    private HashMap<Integer, Shroomer> shroomers;
    private HashMap<Integer, Bugger> buggers;
    private static HashMap<String, Object> nameObjectMap;
    private static HashMap<Object, String> objectNameMap;

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

    public GameBoard(){
        allTektons = new ArrayList<>();
        shroomers = new HashMap<>();
        buggers = new HashMap<>();
        nameObjectMap = new HashMap<>();
        objectNameMap = new HashMap<>();
    }

    public void addShroomer(Shroomer shroomer){
        int lastplayer = 0;
        while (shroomers.containsKey(lastplayer)||buggers.containsKey(lastplayer)){
            lastplayer++;
        }
        shroomers.put(lastplayer, shroomer);
    }

    public void addBugger(Bugger bugger){
        int lastplayer = 0;
        while (shroomers.containsKey(lastplayer)||buggers.containsKey(lastplayer)){
            lastplayer++;
        }
        buggers.put(lastplayer, bugger);
    }

    public HashMap<Integer, Shroomer> getShroomers(){
        return shroomers;
    }

    public HashMap<Integer, Bugger> getBuggers(){
        return buggers;
    }

    public void addTekton(Tekton tekton){
        allTektons.add(tekton);
    }

    public List<Tekton> getTektons(){
        return allTektons;
    }

    /**Utólag adtam hozzá templ8ban nincs benne. A kövi doksiba bele kell írni változásként
     * Ehhez az arrange file-ban a különböző típusú Tektonoknak (Soil, Swamp, Stone, stb...) a számozást
     * Folytatni kell a nevében "tekton1;swamp1" helyett "tekton1;swamp2" a helyes megadás
     *
     * @param idx a lekért tekton indexe
     * @return A tekton a idx-edik helyen a listában
     */
    public Tekton getTekton(int idx){
        return allTektons.get(idx);
    }

    public Object getReferenceByObjectName(String name){
        return nameObjectMap.get(name);
    }
    public String getObjectNameByReference(Object ref){return objectNameMap.get(ref);}

    static public void addReferenceToMaps(String type, Object refe ){
        String name = null;
        switch (type){
            case "biteblocked" -> name = type + biteBlockedID++;
            case "boosted" -> name = type + boostedID++;
            case "bug" -> name = type + bugID++;
            case "bugger" -> name = type + buggerID++;
            case "normal" -> name = type + normalID++;
            case "paralyzed" -> name = type + paralyzedID++;
            case "slowed" -> name = type + slowedID++;
            case "biteblockermushroom" -> name = type + biteBlockerMushroomID++;
            case "biteblockerspore" -> name = type + biteBlockerSporeID++;
            case "boostermushroom" -> name = type + boosterMushroomID++;
            case "boosterspore" -> name = type + boosterSporeID++;
            case "hypa" -> name = type + hypaID++;
            case "paralyzermushroom" -> name = type + paralyzerMushroomID++;
            case "paralyzerspore" -> name = type + paralyzerSporeID++;
            case "proliferatingmushroom" -> name = type + proliferatingMmushroomID++;
            case "proliferatingspore" -> name = type + proliferatingSporeID++;
            case "shroomer" -> name = type + shroomerID++;
            case "slower" -> name = type + slowerMushroomID++;
            case "slowerspore" -> name = type + slowerSporeID++;
            case "peat" -> name = type + peatID++;
            case "soil" -> name = type + soilID++;
            case "stone" -> name = type + stoneID++;
            case "swamp" -> name = type + swampID++;
            case "tekton" -> name = type + tektonID++;
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


