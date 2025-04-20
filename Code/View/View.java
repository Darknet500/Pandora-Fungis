package View;

import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

import Controller.Controller;
import Model.Bridge.*;
import Model.Bug.*;
import Model.Shroomer.*;
import Model.Tekton.*;

public class View {
    private GameBoard gameBoard;
    private Controller controller;
    private ArrangeSection arrangeSection;
    private String testCase;
    private GameMode gameMode;
    private boolean endOfGame = false;

    public View(GameMode gameMode, String testCase) {
        this.gameMode = gameMode;
        this.testCase = testCase;
    }

    public void connectObjects(GameBoard gameBoard, Controller controller) {
        this.gameBoard = gameBoard;
        this.controller = controller;
        controller.connectObjects(this, gameBoard);
    }

    public void setEndOfGame(){
        this.endOfGame = true;
    }


    public void run(){
        ///ha minden tesztfájlt le kell hogy futtasson
        if(gameMode == GameMode.autotestall){
            File testsDir = new File("./test");
            if (!testsDir.exists()||!testsDir.isDirectory()) return;
            File[] testCases = testsDir.listFiles(File::isDirectory);
            if(testCases == null||testCases.length==0) return;
            ///inputsource az fájlból, végig megy az összes test eset mappán, és egymás után meghívja az arrange, act, assert metódust
            for (File tc: testCases) {
                try {
                    gameBoard.clear();
                    File arrangefile = new File(tc + "arrange.txt");
                    InputSource arrangesource = new FileInputSource(arrangefile);
                    arrangeMethod(arrangefile, arrangesource);

                    File actfile = new File(tc + "act.txt");
                    InputSource actsource = new FileInputSource(actfile);
                    actMethod(actfile, actsource);
                    File assertfile = new File(tc + "assert.txt");
                    InputSource assertsource = new FileInputSource(assertfile);
                    assertMethod(assertfile, assertsource);

                } catch (Exception _) {

                }
            }
        }
        ///ha egy adott tesztesetet vizsgálunk, csak azon az adott mappán megyünk végig
        else if(gameMode == GameMode.autotestone) {
            File tc = new File("./test/" + testCase);
            try {
                gameBoard.clear();
                File arrangefile = new File(tc + "arrange.txt");
                InputSource arrangesource = new FileInputSource(arrangefile);
                arrangeMethod(arrangefile, arrangesource);

                File actfile = new File(tc + "act.txt");
                InputSource actsource = new FileInputSource(actfile);
                actMethod(actfile, actsource);
                File assertfile = new File(tc + "assert.txt");
                InputSource assertsource = new FileInputSource(assertfile);
                assertMethod(assertfile, assertsource);
            } catch (Exception _) {}
        }else  {
            ///InputSource konzolról, nullt adunk át fájl helyett, mert nincs szükségfájlra, a gameMode alapján fognak másképp viselkedni a metódusok
            try {
                gameBoard.clear();
                InputSource arrangesource = new ConsoleInputSource();
                arrangeMethod(null, arrangesource);
                InputSource actsource = new ConsoleInputSource();
                actMethod(null, actsource);
                InputSource assertsource = new ConsoleInputSource();
                assertMethod(null, assertsource);
            }catch (Exception _) {}
        }

    }

    public void arrangeMethod(File tc, InputSource source) throws IOException {
        ///éles módban nincs arrange nyelvű pálya, hanem a controller csinálja meg a kiindulópálya generálását
        if (gameMode.equals(GameMode.game)) {
            for (int i=0;i<8;i++){
                Scanner scanner = new Scanner(System.in);
                System.out.println("Adjon hozzá egy új gombászt (shroomer paranccsal), \n Egy új bogarászt (bugger paranccsal)\n vagy indítsa el a játékot (start paranccsal). Még "+(8-i)+" játékost adhat hozzá.");
                String input = scanner.nextLine().trim();;
                while (true){
                    if (input.equalsIgnoreCase("shroomer")||input.equalsIgnoreCase("bugger")||input.equalsIgnoreCase("start"))
                        break;
                    System.out.println("nem valid parancs");
                    input = scanner.nextLine().trim();
                }

                switch (input){
                    case "shroomer" -> {
                        System.out.println("Válasszon gombász fajtát(booster/ slower/ paralyzer/ biteblocker/ prolferating)");
                        String shroomertype = scanner.nextLine().trim();;
                        while (true){
                            if (input.equalsIgnoreCase("booster")||input.equalsIgnoreCase("slower")||input.equalsIgnoreCase("paralyzer")||input.equalsIgnoreCase("biteblocker")||input.equalsIgnoreCase("proliferating"))
                                break;
                            System.out.println("nem valid gombásztípus");
                            input = scanner.nextLine().trim();
                        }
                        BiFunction<Shroomer, Tekton, Mushroom> mushroomctor=(x, y)->new BoosterMushroom(x, y);
                        int hypaDieAfter=5;
                        switch (shroomertype) {
                            case "booster" -> {
                                mushroomctor = (x, y)->new BoosterMushroom(x, y);
                                hypaDieAfter=4;
                            }
                            case "slower" -> {
                                mushroomctor = (x, y)->new SlowerMushroom(x, y);
                                hypaDieAfter=3;
                            }
                            case "paralyzer" -> {
                                mushroomctor = (x, y)->new ParalyzerMushroom(x, y);
                                hypaDieAfter=2;
                            }
                            case "biteblocker" -> {
                                mushroomctor = (x, y)->new BiteBlockerMushroom(x, y);
                                hypaDieAfter=3;
                            }
                            case "proliferating" -> {
                                mushroomctor = (x, y)->new ProliferatingMushroom(x, y);
                                hypaDieAfter=5;
                            }
                        }
                        gameBoard.addShroomer(new Shroomer(mushroomctor,hypaDieAfter));

                    }
                    case "bugger" -> gameBoard.addBugger(new Bugger());
                    case "start" -> i=8;
                }
            }

            controller.initMap();
            return;
        }
        try {
            while (source.hasNextLine()) {
                String line = source.readLine();
                /// a valódi arrange fájl veolvasás logikája
                try {
                    arrangeSection = ArrangeSection.valueOf(line.toUpperCase());
                    return;
                } catch (IllegalArgumentException ignored) {}

                String[] parts = line.split(";");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                switch (arrangeSection) {
                    case TEKTONS -> {
                        switch (parts[0]) {
                            case "Tekton" -> {
                                gameBoard.addTekton(new Tekton());

                            }
                            case "Peat" -> {
                                gameBoard.addTekton(new Peat());
                            }
                            case "Soil" -> {
                                gameBoard.addTekton(new Soil());
                            }
                            case "Stone" -> {
                                gameBoard.addTekton(new Stone());
                            }
                            case "Swamp" -> {
                                gameBoard.addTekton(new Swamp());
                            }
                        }
                    }
                    case NEIGHBOURS -> {
                        Tekton first = (Tekton) gameBoard.getReferenceByObjectName(parts[0]);
                        for (int i = 1; i < parts.length; i++) {
                            Tekton neighbour = (Tekton) gameBoard.getReferenceByObjectName(parts[i]);
                            first.addNeighbour(neighbour);
                            neighbour.addNeighbour(first);
                        }

                    }
                    case SHROOMERS -> {
                        BiFunction<Shroomer, Tekton, Mushroom> mushroomctor;
                        int hypaDieAfter;
                        switch (parts[0].toLowerCase()) {
                            case "booster" -> {
                                mushroomctor = (x, y) -> new BoosterMushroom(x, y);
                                hypaDieAfter = 4;
                            }
                            case "slower" -> {
                                mushroomctor = (x, y) -> new SlowerMushroom(x, y);
                                hypaDieAfter = 3;
                            }
                            case "paralyzer" -> {
                                mushroomctor = (x, y) -> new ParalyzerMushroom(x, y);
                                hypaDieAfter = 2;
                            }
                            case "biteblocker" -> {
                                mushroomctor = (x, y) -> new BiteBlockerMushroom(x, y);
                                hypaDieAfter = 3;
                            }
                            case "proliferating" -> {
                                mushroomctor = (x, y) -> new ProliferatingMushroom(x, y);
                                hypaDieAfter = 5;
                            }

                            default -> {
                                mushroomctor = (x, y) -> new BoosterMushroom(x, y);
                                hypaDieAfter = 4;
                            }
                        }


                        gameBoard.addShroomer(new Shroomer(mushroomctor, hypaDieAfter));

                    }
                    case MUSHROOMS -> {
                        switch (parts[0].toLowerCase()) {
                            case "boostermushroom" -> {
                                new BoosterMushroom((Shroomer) gameBoard.getReferenceByObjectName(parts[1]), (Tekton) gameBoard.getReferenceByObjectName(parts[2]));
                            }
                            case "slowermushroom" -> {
                                new SlowerMushroom((Shroomer) gameBoard.getReferenceByObjectName(parts[1]), (Tekton) gameBoard.getReferenceByObjectName(parts[2]));
                            }
                            case "paralyzermushroom" -> {
                                new ParalyzerMushroom((Shroomer) gameBoard.getReferenceByObjectName(parts[1]), (Tekton) gameBoard.getReferenceByObjectName(parts[2]));
                            }
                            case "biteblockermushroom" -> {
                                new BiteBlockerMushroom((Shroomer) gameBoard.getReferenceByObjectName(parts[1]), (Tekton) gameBoard.getReferenceByObjectName(parts[2]));
                            }
                            case "proliferatingmushroom" -> {
                                new ProliferatingMushroom((Shroomer) gameBoard.getReferenceByObjectName(parts[1]), (Tekton) gameBoard.getReferenceByObjectName(parts[2]));
                            }


                        }

                    }
                    case BUGGERS -> {
                        gameBoard.addBugger(new Bugger());
                    }
                    case STRATEGIES -> {
                        switch (parts[0].toLowerCase()) {
                            case "normal" -> new Normal();

                            case "boosted" -> new Boosted();

                            case "slowed" -> new Slowed();

                            case "paralyzed" -> new Paralyzed();

                            case "biteblocked" -> new BiteBlocked();

                        }
                    }
                    case BUGS -> {
                        Bug bug = new Bug((Bugger) gameBoard.getReferenceByObjectName(parts[1]));
                        bug.setStrategy((Strategy) gameBoard.getReferenceByObjectName(parts[0]));
                        bug.setLocation((Tekton) gameBoard.getReferenceByObjectName(parts[2]));
                        ((Bugger) gameBoard.getReferenceByObjectName(parts[1])).addBug(bug);
                    }
                    case HYPAS -> {
                        Hypa hypa = new Hypa((Tekton) gameBoard.getReferenceByObjectName(parts[0]),
                                (Tekton) gameBoard.getReferenceByObjectName(parts[1]), (Shroomer) gameBoard.getReferenceByObjectName(parts[2]));
                        ((Tekton) gameBoard.getReferenceByObjectName(parts[0])).connectHypa(hypa);
                        ((Tekton) gameBoard.getReferenceByObjectName(parts[1])).connectHypa(hypa);
                    }
                    case SPORES -> {
                        Spore spore;
                        Shroomer shroomer = (Shroomer) gameBoard.getReferenceByObjectName(parts[1]);
                        switch (parts[0].toLowerCase()) {
                            case "boosterspore" -> spore = new BoosterSpore(shroomer);
                            case "slowerspore" -> spore = new SlowerSpore(shroomer);
                            case "paralyzerspore" -> spore = new ParalyzerSpore(shroomer);
                            case "biteblockerspore" -> spore = new BiteBlockerSpore(shroomer);
                            case "proliferatingspore" -> spore = new ProliferatingSpore(shroomer);
                            default -> spore = null;

                        }
                        ((Tekton) gameBoard.getReferenceByObjectName(parts[2])).storeSpore(spore);

                    }
                }


                /// csak manual módban, act paranccsal lehet lezárni az arrange fázist (persze Cntr+Z enter mellett)
                if(line.equalsIgnoreCase("act")) break;
            }
        } finally {
            source.close();
        }



    }

    public void actMethod(File tc, InputSource source) throws IOException {
        try {
            while (source.hasNextLine() && !endOfGame) {
                String line = source.readLine();
                ///valódi act parancsok beolvasása értelmezése
                String[] parts = line.split(";");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                switch (parts[0].toLowerCase()){
                    case "move" -> {
                        if(parts.length<3){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if(controller.move((Bug)gameBoard.getReferenceByObjectName(parts[1]),
                                (Tekton)gameBoard.getReferenceByObjectName(parts[2])))
                            System.out.println("action performed");
                        else
                            System.out.println("action failed");
                    }
                    case "bite" -> {
                        if(parts.length<3){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if(controller.bite((Bug)gameBoard.getReferenceByObjectName(parts[1]),
                                (Hypa)gameBoard.getReferenceByObjectName(parts[2])))
                            System.out.println("action performed");
                        else
                            System.out.println("action failed");
                    }
                    case "eat" -> {
                        if(parts.length<3){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if(controller.eat((Bug)gameBoard.getReferenceByObjectName(parts[1]),
                                (Spore)gameBoard.getReferenceByObjectName(parts[2])))
                            System.out.println("action performed");
                        else
                            System.out.println("action failed");

                    }
                    case "growhypa" -> {
                        if(parts.length<3){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if(controller.growhypa((Tekton)gameBoard.getReferenceByObjectName(parts[1]),
                                (Tekton)gameBoard.getReferenceByObjectName(parts[2])))
                            System.out.println("action performed");
                        else
                            System.out.println("action failed");

                    }
                    case "growhypafar" -> {
                        if(parts.length<4){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if (controller.growhypafar((Tekton)gameBoard.getReferenceByObjectName(parts[1]),
                                (Tekton)gameBoard.getReferenceByObjectName(parts[2]),
                                (Tekton)gameBoard.getReferenceByObjectName(parts[3])))
                            System.out.println("action performed");
                        else
                            System.out.println("action failed");
                    }
                    case "throwspore" -> {
                        if(parts.length<3){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if(controller.throwspore((Mushroom)gameBoard.getReferenceByObjectName(parts[1]),
                                (Tekton)gameBoard.getReferenceByObjectName(parts[2])))
                            System.out.println("action performed");
                        else
                            System.out.println("action failed");
                    }
                    case "eatbug" -> {
                        if(parts.length<2){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if(controller.eatbug((Bug)gameBoard.getReferenceByObjectName(parts[1])))
                            System.out.println("action performed");
                        else
                            System.out.println("action failed");
                    }
                    case "endturn" -> {
                        controller.endturn();
                        System.out.println("action performed");
                    }

                    case "break" -> {
                        if (gameMode!=GameMode.game) {
                            if (parts.length < 2) {
                                System.out.println("not enough parameters");
                                break;
                            }
                            controller.breaktekton((Tekton) gameBoard.getReferenceByObjectName(parts[1]));
                        }
                        System.out.println("action failed");
                    }
                    default -> System.out.println("action failed");
                }



                ///csak manual módban lesz ilyen, hogy standar inputon bejön egy assert parancs, ilyenkor act parancson belül
                /// hívja le az assert metódust, ily módon utána ACT parancs hatására visszatér ide és folytathatja az act parancsokkal
                if(line.equalsIgnoreCase("assert")){
                    ConsoleInputSource assertsource = new ConsoleInputSource();
                    assertMethod(tc, assertsource);
                }
            }
        } finally {
            source.close();
        }

    }

    public void assertMethod(File tc, InputSource source) throws IOException {
        try {
            while (source.hasNextLine()) {
                String line = source.readLine();
                String[] parts = line.split(";");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                switch (parts[1].toLowerCase()){
                    case "bugs" -> {
                        if (gameBoard.getBugger().containsValue((Bugger)gameBoard.getReferenceByObjectName(parts[0]))) {
                            List<Bug> bugs;
                            bugs = ((Bugger) gameBoard.getReferenceByObjectName(parts[0])).getBugs();
                            List<Bug> assertbugs = new ArrayList<>();
                            for (int i = 0; i < bugs.size(); i++) {
                                assertbugs.add((Bug)gameBoard.getReferenceByObjectName(parts[2 + i]));
                            }
                            if (areListsIdentical(bugs, assertbugs))
                                System.out.println("SUCCESS");
                            else
                                System.out.println("FAIL");
                        }
                        else
                            System.out.println("ERROR");
                    }
                    case "mushroomlocation" ->{
                        Tekton location;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null)
                            location = ((Mushroom)gameBoard.getReferenceByObjectName(parts[0])).getLocation();
                        else {
                            System.out.println("ERROR");
                            break;
                        }
                        if(location==gameBoard.getReferenceByObjectName(parts[2]))
                            System.out.println("SUCCESS");
                        else System.out.println("FAIL");
                    }
                    case "buglocation" ->{
                        Tekton location;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null)
                            location = ((Bug)gameBoard.getReferenceByObjectName(parts[0])).getLocation();
                        else {
                            System.out.println("ERROR");
                            break;
                        }
                        if(location==gameBoard.getReferenceByObjectName(parts[2]))
                            System.out.println("SUCCESS");
                        else System.out.println("FAIL");
                    }
                    case "strategy" -> {
                        Strategy strategy;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            strategy = ((Bug)gameBoard.getReferenceByObjectName(parts[0])).getStrategy();
                            if (strategy == ((Strategy)gameBoard.getReferenceByObjectName(parts[2])))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }
                        else
                            System.out.println("ERROR");

                    }
                    case "undereffectsince" -> {
                        int underEffectSince;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            underEffectSince = ((Bug)gameBoard.getReferenceByObjectName(parts[0])).getUnderEffectSince();
                            if (underEffectSince == Integer.parseInt(parts[2]))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }
                        else
                            System.out.println("ERROR");
                    }
                    case "mushrooms" ->{

                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            List<Mushroom> mushrooms;
                            mushrooms = ((Shroomer) gameBoard.getReferenceByObjectName(parts[0])).getMushrooms();
                            List<Mushroom> assertmushrooms = new ArrayList<>();
                            for (int i = 0; i < mushrooms.size(); i++) {
                                assertmushrooms.add((Mushroom) gameBoard.getReferenceByObjectName(parts[2 + i]));
                            }
                            if (areListsIdentical(mushrooms, assertmushrooms))
                                System.out.println("SUCCESS");
                            else
                                System.out.println("FAIL");
                        }
                        else
                            System.out.println("ERROR");
                    }
                    case "shroomerhypas" -> {
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            List<Hypa> hypas;
                            hypas = ((Shroomer) gameBoard.getReferenceByObjectName(parts[0])).getHypaList();
                            List<Hypa> asserthypas = new ArrayList<>();
                            for (int i = 0; i < hypas.size(); i++) {
                                asserthypas.add((Hypa) gameBoard.getReferenceByObjectName(parts[2 + i]));
                            }
                            if (areListsIdentical(hypas, asserthypas))
                                System.out.println("SUCCESS");
                            else
                                System.out.println("FAIL");
                        }
                        else
                            System.out.println("ERROR");
                    }
                    case "tektonhypas" -> {
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            List<Hypa> hypas;
                            hypas = ((Tekton) gameBoard.getReferenceByObjectName(parts[0])).getHypas();
                            List<Hypa> asserthypas = new ArrayList<>();
                            for (int i = 0; i < hypas.size(); i++) {
                                asserthypas.add((Hypa) gameBoard.getReferenceByObjectName(parts[2 + i]));
                            }
                            if (areListsIdentical(hypas, asserthypas))
                                System.out.println("SUCCESS");
                            else
                                System.out.println("FAIL");
                        }
                        else
                            System.out.println("ERROR");
                    }
                    case "mushroomage" -> {
                        int age;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            age = ((Mushroom) gameBoard.getReferenceByObjectName(parts[0])).getAge();
                            if (age == Integer.parseInt(parts[2]))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }else
                            System.out.println("ERROR");
                    }
                    case "hypaage" -> {
                        int age;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            age = ((Hypa) gameBoard.getReferenceByObjectName(parts[0])).getAge();
                            if (age == Integer.parseInt(parts[2]))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }else
                            System.out.println("ERROR");
                    }


                    case "numberofspores" ->{
                        int numberOfSpores;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            numberOfSpores = ((Mushroom)gameBoard.getReferenceByObjectName(parts[0])).getNumberOfSpores();
                            if (numberOfSpores == Integer.parseInt(parts[2]))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }
                        else System.out.println("ERROR");
                    }
                    case "sporesthrown" ->{
                        int sporesThrown;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            sporesThrown = ((Mushroom)gameBoard.getReferenceByObjectName(parts[0])).getSporesThrown();
                            if (sporesThrown == Integer.parseInt(parts[2]))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }
                        else System.out.println("ERROR");


                    }
                    case "mushroomshroomer" ->{
                        Shroomer shroomer;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            shroomer = ((Mushroom) gameBoard.getReferenceByObjectName(parts[0])).getShroomer();
                            if(shroomer==gameBoard.getReferenceByObjectName(parts[2]))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }
                        else {
                            System.out.println("ERROR");
                        }
                    }
                    case "sporeshroomer" ->{
                        Shroomer shroomer;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            shroomer = ((Spore) gameBoard.getReferenceByObjectName(parts[0])).getShroomer();
                            if(shroomer==gameBoard.getReferenceByObjectName(parts[2]))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }
                        else {
                            System.out.println("ERROR");
                        }
                    }
                    case "hypashroomer" ->{
                        Shroomer shroomer;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            shroomer = ((Hypa) gameBoard.getReferenceByObjectName(parts[0])).getShroomer();
                            if(shroomer==gameBoard.getReferenceByObjectName(parts[2]))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }
                        else {
                            System.out.println("ERROR");
                        }
                    }
                    case "ends" ->{
                        Tekton end1;
                        Tekton end2;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            end1 = ((Hypa)gameBoard.getReferenceByObjectName(parts[0])).getEnd1();
                            end2 = ((Hypa)gameBoard.getReferenceByObjectName(parts[0])).getEnd1();
                            if (end1 == gameBoard.getReferenceByObjectName(parts[2])&&end2 == gameBoard.getReferenceByObjectName(parts[3])
                            ||end1 == gameBoard.getReferenceByObjectName(parts[3])&&end2 == gameBoard.getReferenceByObjectName(parts[2]))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }
                        else System.out.println("ERROR");


                    }
                    case "isdyingsincedisconnected" -> {
                        int isDyingSinceDisconnected;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            isDyingSinceDisconnected = ((Hypa)gameBoard.getReferenceByObjectName(parts[0])).getIsDyingSinceDisconnected();

                            if(isDyingSinceDisconnected==Integer.parseInt(parts[2]))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }else System.out.println("ERROR");
                    }
                    case "isdyingsincebitten" ->{
                        int isDyingSinceBitten;
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            isDyingSinceBitten = ((Hypa)gameBoard.getReferenceByObjectName(parts[0])).getIsDyingSinceBitten();
                            if(isDyingSinceBitten==Integer.parseInt(parts[2]))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }
                        else System.out.println("ERROR");
                    }
                    case "neighbours" ->{
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            List<Tekton> neighbours;
                            neighbours = ((Tekton)gameBoard.getReferenceByObjectName(parts[0])).getNeighbours();
                            List<Tekton> assertneighbour = new ArrayList<>();
                            for (int i = 0; i < neighbours.size(); i++) {
                                assertneighbour.add((Tekton) gameBoard.getReferenceByObjectName(parts[2 + i]));
                            }
                            if (areListsIdentical(neighbours, assertneighbour))
                                System.out.println("SUCCESS");
                            else
                                System.out.println("FAIL");
                        }
                        else
                            System.out.println("ERROR");
                    }
                    case "spores" ->{
                        if(gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            List<Spore> spores = new ArrayList<>();
                            spores = ((Tekton)gameBoard.getReferenceByObjectName(parts[0])).getStoredSpores();
                            List<Tekton> assertspores = new ArrayList<>();
                            for (int i = 0; i < spores.size(); i++) {
                                assertspores.add((Tekton) gameBoard.getReferenceByObjectName(parts[2 + i]));
                            }
                            if (areListsIdentical(spores, assertspores))
                                System.out.println("SUCCESS");
                            else
                                System.out.println("FAIL");
                        }
                        else
                            System.out.println("ERROR");
                    }
                    case "bug" ->{
                        if (gameBoard.getReferenceByObjectName(parts[0])!=null) {
                            if(((Tekton)gameBoard.getReferenceByObjectName(parts[0])).getBug()==gameBoard.getReferenceByObjectName(parts[2]))
                                System.out.println("SUCCESS");
                            else System.out.println("FAIL");
                        }
                        else System.out.println("ERROR");
                    }

                }



                if(line.equalsIgnoreCase("act")) break;
            }
        } finally {
            source.close();
        }

    }

    public void displayMessage(String message){
        System.out.println(message);
    }

    private boolean areListsIdentical(List<?> list1, List<?> list2) {
        Set<?> set1 = new HashSet<>(list1);
        Set<?> set2 = new HashSet<>(list2);
        return set1.equals(set2);
    }



}
