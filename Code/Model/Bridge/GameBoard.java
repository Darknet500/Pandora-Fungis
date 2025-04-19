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
    private static int biteBlockedID = 0;
    private static int boostedID = 0;
    private static int bugID = 0;
    private static int buggerID = 0;
    private static int normalID = 0;
    private static int paralyzedID = 0;
    private static int slowedID = 0;
    private static int biteBlockerMushroomID = 0;
    private static int biteBlockerSporeID = 0;
    private static int boosterMushroomID = 0;
    private static int boosterSporeID = 0;
    private static int hypaID = 0;



    public GameBoard(){
        allTektons = new ArrayList<>();
        shroomers = new HashMap<>();
        buggers = new HashMap<>();
        nameObjectMap = new HashMap<>();
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

    public HashMap<Integer, Shroomer> getShroomer(){
        return shroomers;
    }

    public HashMap<Integer, Bugger> getBugger(){
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

    static public void addReferenceToMaps(String type, Object refe ){
        String name = null;
        switch (type){
            case "biteblocked" -> {
                biteBlockedID++;
                name = type + biteBlockedID;
            }
            case "boosted" -> {
                boostedID++;
                name = type + boostedID;

            }
            case "bug" -> {
                bugID++;
                name = type + bugID;
            }
            case "bugger" -> {
                buggerID++;
                name = type + buggerID;
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
        shroomers.clear();
        buggers.clear();
        allTektons.clear();
        nameObjectMap.clear();
        objectNameMap.clear();

        biteBlockedID = 0;
        boostedID = 0;
        bugID = 0;
        buggerID = 0;
        normalID = 0;
        paralyzedID = 0;
        slowedID = 0;

    }



}


