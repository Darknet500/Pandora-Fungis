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
    public static HashMap<String, Object> nameObjectMap;

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

    public String getObjectNameByReference(Object ref){
        return objectNameMap.get(ref);
    }



}


