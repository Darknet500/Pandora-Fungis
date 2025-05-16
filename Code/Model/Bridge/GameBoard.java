package Model.Bridge;

import Model.Tekton.*;
import Model.Shroomer.*;
import Model.Bug.*;
import View.IView;
import View.Hitbox.*;

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
    private static HashMap<Shroomer, Color> shroomerColorMap;
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
        shroomerColorMap = new HashMap<>();
        buggerColorMap = new HashMap<>();
    }

    public void connectToView(IView view){
        this.view = view;
    }

    public void addShroomer(Shroomer shroomer, String name, Color c){
        if (name.isEmpty()) name = "anonymous";
        int lastplayer = 0;
        while (shroomers.containsKey(lastplayer)||buggers.containsKey(lastplayer)){
            lastplayer++;
        }
        shroomers.put(lastplayer, shroomer);
        playerDisplayNameMap.put(shroomer, name);
        shroomerColorMap.put(shroomer, c);
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
        int xborder = view.getDrawingSurfaceWidth()/12;
        int yborder = view.getDrawingSurfaceHeight()/12;

        int drawingSurfaceWidth = view.getDrawingSurfaceWidth()-xborder*2;
        int drawingSurfaceHeight = view.getDrawingSurfaceHeight()-yborder*2;
        double randmovx = (rand.nextDouble()-1)/4;
        double randmovy = (rand.nextDouble()-1)/4;

        List<Point> tektonpoints = Arrays.asList(
                new Point(xborder + (int)(0.95635 * drawingSurfaceWidth), yborder + (int)(0.5133 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.5844 * drawingSurfaceWidth), yborder + (int)(0.7483 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.1152 * drawingSurfaceWidth), yborder + (int)(0.0606 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.7456 * drawingSurfaceWidth), yborder + (int)(0.1124 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.7058 * drawingSurfaceWidth), yborder + (int)(0.6409 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.99965 * drawingSurfaceWidth), yborder + (int)(0.0143 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.2214 * drawingSurfaceWidth), yborder + (int)(0.9854 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.2385 * drawingSurfaceWidth), yborder + (int)(0.1840 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.6134 * drawingSurfaceWidth), yborder + (int)(0.0884 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.3007 * drawingSurfaceWidth), yborder + (int)(0.4651 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.1205 * drawingSurfaceWidth), yborder + (int)(0.6750 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.3709 * drawingSurfaceWidth), yborder + (int)(0.2387 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.82365 * drawingSurfaceWidth), yborder + (int)(0.4887 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.50355 * drawingSurfaceWidth), yborder + (int)(0.2557 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.0000 * drawingSurfaceWidth), yborder + (int)(0.5503 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.25255 * drawingSurfaceWidth), yborder + (int)(0.7153 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.9300 * drawingSurfaceWidth), yborder + (int)(0.2413 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.8084 * drawingSurfaceWidth), yborder + (int)(0.9656 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.04965 * drawingSurfaceWidth), yborder + (int)(0.3038 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.4417 * drawingSurfaceWidth), yborder + (int)(0.7554 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.4644 * drawingSurfaceWidth), yborder + (int)(0.0002 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.86705 * drawingSurfaceWidth), yborder + (int)(0.0002 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.70225 * drawingSurfaceWidth), yborder + (int)(0.3733 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.4335 * drawingSurfaceWidth), yborder + (int)(0.4839 * drawingSurfaceHeight)),
                new Point(xborder + (int)(0.2099 * drawingSurfaceWidth), yborder + (int)(0.4198 * drawingSurfaceHeight))
        );



        Point tektonpoint = new Point(0,0);
        if (tektonsCount>=0&&tektonsCount<25)
            tektonpoint = tektonpoints.get(tektonsCount);
        //tektonpoint = new Point(xstep+tektonsCount%5*xstep+(int)(randmovx*xstep),ystep+(int)Math.floor((double)tektonsCount/5)*ystep+(int)(randmovy*ystep));


        int tektonsize = (int)(ystep*0.8);



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
                BugHitbox hitbox = new BugHitbox((Bug)refe,new Point(10, 10), GameBoard.buggerColorMap.get(((Bug)refe).getBugger()),(int)(tektonsize*0.45));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);

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
                MushroomHitbox hitbox = new MushroomHitbox((Mushroom)refe, new Point(tektoncenterpoint.x-(int)(tektonsize*0.25), tektoncenterpoint.y), "biteblocker",(int)(tektonsize*0.45));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "biteblockerspore":{
                name = type + biteBlockerSporeID++;
                SporeHitbox hitbox = new SporeHitbox(getSporeHitboxPoint(((Spore)refe).getTekton()), (Spore)refe, shroomerColorMap.get(((Spore)refe).getShroomer()));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "boostermushroom":{
                name = type + boosterMushroomID++;
                MushroomHitbox hitbox = new MushroomHitbox((Mushroom)refe, new Point(tektoncenterpoint.x-(int)(tektonsize*0.25), tektoncenterpoint.y), "booster",(int)(tektonsize*0.45));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "boosterspore":{
                name = type + boosterSporeID++;
                SporeHitbox hitbox = new SporeHitbox(getSporeHitboxPoint(((Spore)refe).getTekton()), (Spore)refe, shroomerColorMap.get(((Spore)refe).getShroomer()));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "hypa":{
                name = type + hypaID++;
                HypaHitbox hitbox = new HypaHitbox((Hypa)refe, shroomerColorMap.get(((Hypa)refe).getShroomer()));
                ((Hypa)refe).addObserver(hitbox);
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "paralyzermushroom":{
                name = type + paralyzerMushroomID++;
                MushroomHitbox hitbox = new MushroomHitbox((Mushroom)refe, new Point(tektoncenterpoint.x-(int)(tektonsize*0.25), tektoncenterpoint.y), "paralyzer",(int)(tektonsize*0.45));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "paralyzerspore":{
                name = type + paralyzerSporeID++;
                SporeHitbox hitbox = new SporeHitbox(getSporeHitboxPoint(((Spore)refe).getTekton()), (Spore)refe, shroomerColorMap.get(((Spore)refe).getShroomer()));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "proliferatingmushroom":{
                name = type + proliferatingMmushroomID++;
                MushroomHitbox hitbox = new MushroomHitbox((Mushroom)refe, new Point(tektoncenterpoint.x-(int)(tektonsize*0.25), tektoncenterpoint.y), "proliferating",(int)(tektonsize*0.45));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "proliferatingspore":{
                name = type + proliferatingSporeID++;
                SporeHitbox hitbox = new SporeHitbox(getSporeHitboxPoint(((Spore)refe).getTekton()), (Spore)refe, shroomerColorMap.get(((Spore)refe).getShroomer()));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "shroomer":{
                name = type + shroomerID++;
                break;
            }
            case "slowermushroom":{
                name = type + slowerMushroomID++;
                MushroomHitbox hitbox = new MushroomHitbox((Mushroom)refe, new Point(tektoncenterpoint.x-(int)(tektonsize*0.25), tektoncenterpoint.y), "slower",(int)(tektonsize*0.45));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "slowerspore":{
                name = type + slowerSporeID++;
                SporeHitbox hitbox = new SporeHitbox(getSporeHitboxPoint(((Spore)refe).getTekton()), (Spore)refe, shroomerColorMap.get(((Spore)refe).getShroomer()));
                objectHitboxMap.put(refe, hitbox);
                hitboxObjectMap.put(hitbox, refe);
                break;
            }
            case "peat":{
                name = type + peatID++;
                allTektons.add((TektonBase)refe);
                TektonHitbox tektonHitbox = new TektonHitbox(tektonpoint, (TektonBase)refe, "peat", (int)(ystep*0.8));
                objectHitboxMap.put(refe, tektonHitbox);
                hitboxObjectMap.put(tektonHitbox, refe);
                break;
            }
            case "soil":{
                name = type + soilID++;
                allTektons.add((TektonBase)refe);
                TektonHitbox tektonHitbox = new TektonHitbox(tektonpoint, (TektonBase)refe, "soil", (int)(ystep*0.8));
                objectHitboxMap.put(refe, tektonHitbox);
                hitboxObjectMap.put(tektonHitbox, refe);
                break;
            }
            case "stone":{
                name = type + stoneID++;
                allTektons.add((TektonBase)refe);
                TektonHitbox tektonHitbox = new TektonHitbox(tektonpoint, (TektonBase)refe, "stone", (int)(ystep*0.8));
                objectHitboxMap.put(refe, tektonHitbox);
                hitboxObjectMap.put(tektonHitbox, refe);
                break;
            }
            case "swamp":{
                name = type + swampID++;
                allTektons.add((TektonBase)refe);
                TektonHitbox tektonHitbox = new TektonHitbox(tektonpoint, (TektonBase)refe, "swamp", (int)(ystep*0.8));
                objectHitboxMap.put(refe, tektonHitbox);
                hitboxObjectMap.put(tektonHitbox, refe);
                break;
            }
            case "tekton":{
                name = type + tektonID++;
                allTektons.add((TektonBase)refe);
                TektonHitbox tektonHitbox = new TektonHitbox(tektonpoint, (TektonBase)refe, "tekton", (int)(ystep*0.8));
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

    static private Point getSporeHitboxPoint(TektonBase sporelocation){
        int tektonsize=view.getDrawingSurfaceHeight()*2/15;
        int sporecount = sporelocation.getStoredSpores().size()-1;
        int i=0;
        while(true){
            boolean isPlaceFree = true;
            double angle =  i*Math.PI/5.05;
            Point locationTektonCenterPoint =((TektonHitbox)objectHitboxMap.get(sporelocation)).getCenterPoint();
            int x = locationTektonCenterPoint.x + (int)(Math.sin(angle)*tektonsize*0.35*Math.pow(0.8,i/10));
            int y = locationTektonCenterPoint.y + (int)(Math.cos(angle)*tektonsize*0.35*Math.pow(0.8,i/10));
            Point tmpPoint = new Point (x,y);
            for(Spore s: sporelocation.getStoredSpores()){
                if(s.getHitbox().getCenterPoint().getX() == tmpPoint.getX()&&s.getHitbox().getCenterPoint().getY()==tmpPoint.getY()){
                    isPlaceFree = false;
                }
            }

            if(isPlaceFree){
                return tmpPoint;
            }
            i++;

        }
    }

    /**
     * Antigravitációs erőt szimulálva mozgatja a tektonokat, azaz, hagyja hogy egymástól távolodjanak
     * Maguk a TektonBase objektumok hívják meg tekton törés után
     */
    static public void tektonSpreading(){
        double[][] movedCenterpoint=new double[2][allTektons.size()];
        for (int i=0;i<10000;i++){
            int j=0;
            Arrays.fill(movedCenterpoint[0],0);
            Arrays.fill(movedCenterpoint[1],0);
            for(TektonBase tek: allTektons){
                double fx = 0;
                double fy = 0;
                for(TektonBase otherTek: allTektons){
                    if(tek!=otherTek){
                        int dx = tek.getCenterPoint().x - otherTek.getCenterPoint().x;
                        int dy = tek.getCenterPoint().y - otherTek.getCenterPoint().y;
                        double distance = Math.max(10,Math.sqrt(dx*dx+dy*dy));
                        double graviForce = tek.getWeight()*otherTek.getWeight()*500/(distance*distance*distance);
                        fx+=(dx/distance)*graviForce;
                        fy+=(dy/distance)*graviForce;
                    }
                }
                //négy fal is taszítja őt, vagyis minden falon a hozzá legközellbi pont
                //felső fal
                int dy = Math.max(10,tek.getCenterPoint().y);
                fy+=tek.getWeight()*100/(dy*dy);
                //alsó fal
                dy =Math.max(10, (view.getDrawingSurfaceHeight()-tek.getCenterPoint().y));
                fy+=tek.getWeight()*100/(dy*dy);
                //bal fal
                int dx = Math.max(10,tek.getCenterPoint().x);
                fx+=tek.getWeight()*100/(dx*dx);
                //jobb fal
                dx = Math.max(10,(view.getDrawingSurfaceWidth()-tek.getCenterPoint().x));
                fx+=tek.getWeight()*100/(dx*dx);

                //csekkoljuk hogy ne legyen túl nagy ez az erő
                while(true){
                    double forceStrength = Math.sqrt(dx*dx+dy*dy);
                    if(forceStrength>20){
                        dx=dx/3;
                        dy=dy/3;
                    }else
                        break;
                }


                movedCenterpoint[0][j]=fx/tek.getWeight()*50;
                movedCenterpoint[1][j]=fy/tek.getWeight()*50;
                j++;
            }
            j=0;
            for(TektonBase tek: allTektons){
                int movedpointX = (int)(Math.min(view.getDrawingSurfaceWidth()*11/12,Math.max(view.getDrawingSurfaceWidth()/12,tek.getCenterPoint().x+movedCenterpoint[0][j])));
                int movedpointY = (int)(Math.min(view.getDrawingSurfaceHeight()*11/12,Math.max(view.getDrawingSurfaceHeight()/12,tek.getCenterPoint().y+movedCenterpoint[1][j])));
                tek.setCenterPoint(new Point(movedpointX,movedpointY));
                j++;
            }
            //lecsekkoljuk hogy eltávolodtak-e már a tektonok amennyire kell
            boolean spreadEnough = true;
            for(TektonBase tek: allTektons){
                for(TektonBase otherTek: allTektons){
                    if(tek!=otherTek){
                        int dx = tek.getCenterPoint().x - otherTek.getCenterPoint().x;
                        int dy = tek.getCenterPoint().y - otherTek.getCenterPoint().y;
                        double distance = Math.sqrt(dx*dx+dy*dy);
                        if(distance< (double) (view.getDrawingSurfaceHeight() * 2) /15)
                            spreadEnough = false;
                    }
                }
            }
            if (spreadEnough){
                break;
            }
        }
    }
}


