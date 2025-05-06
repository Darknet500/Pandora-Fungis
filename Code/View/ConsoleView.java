package View;

import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

import Controller.Controller;
import Model.Bridge.*;
import Model.Bug.*;
import Model.Shroomer.*;
import Model.Tekton.*;

public class ConsoleView implements IView{
    private GameBoard gameBoard;
    private Controller controller;
    private ArrangeSection arrangeSection;
    private String testCase;
    private GameMode gameMode;
    private boolean endOfGame = false;


    public ConsoleView(GameMode gameMode, String testCase) {
        this.gameMode = gameMode;
        this.testCase = testCase;
    }

    @Override
    public void connectObjects(GameBoard gameBoard, Controller controller) {
        this.gameBoard = gameBoard;
        this.controller = controller;
        controller.connectObjects(this, gameBoard);
        controller.setSeed(12345L);

    }

    @Override
    public void setEndOfGame(){
        this.endOfGame = true;
    }


    public void run(){
        ///ha minden tesztfájlt le kell hogy futtasson
        if(gameMode == GameMode.autotestall){
            File testsDir = new File(System.getProperty("user.dir"),"test");
            if (!testsDir.exists()||!testsDir.isDirectory()) return;
            File[] testCases = testsDir.listFiles(File::isDirectory);
            if(testCases == null||testCases.length==0) return;
            ///inputsource az fájlból, végig megy az összes test eset mappán, és egymás után meghívja az arrange, act, assert metódust
            for (File tc: testCases) {
                try {
                    System.out.println("\ntc: "+tc.getName());
                    gameBoard.clear();
                    controller.resetActualPlayerandRound();
                    File arrangefile = new File(tc , "arrange.txt");
                    FileInputSource arrangesource = new FileInputSource(arrangefile);
                    arrangeMethod(arrangefile, arrangesource);

                    File actfile = new File(tc , "act.txt");
                    FileInputSource actsource = new FileInputSource(actfile);
                    actMethod(actfile, actsource);
                    File assertfile = new File(tc , "assert.txt");
                    FileInputSource assertsource = new FileInputSource(assertfile);
                    assertMethod(assertfile, assertsource);

                } catch (Exception ignored) {

                }
            }
        }
        ///ha egy adott tesztesetet vizsgálunk, csak azon az adott mappán megyünk végig
        else if(gameMode == GameMode.autotestone) {
            File tc = new File("./test/" + testCase);
            if(!tc.exists()){
                System.out.println("file not found");
                System.exit(0);
            }
            System.out.println("\ntc: "+tc.getName());
            try {
                gameBoard.clear();
                controller.resetActualPlayerandRound();
                File arrangefile = new File(tc , "arrange.txt");
                FileInputSource arrangesource = new FileInputSource(arrangefile);
                arrangeMethod(arrangefile, arrangesource);

                File actfile = new File(tc , "act.txt");
                FileInputSource actsource = new FileInputSource(actfile);
                actMethod(actfile, actsource);
                File assertfile = new File(tc , "assert.txt");
                FileInputSource assertsource = new FileInputSource(assertfile);
                assertMethod(assertfile, assertsource);
            } catch (Exception ignored) {

            }
        }
    }

    public void arrangeMethod(File tc, FileInputSource source) throws IOException {
        System.out.println("ARRANGE");
        try {
            while (source.hasNextLine()) {
                String line = source.readLine();
                /// a valódi arrange fájl veolvasás logikája
                try {
                    arrangeSection = ArrangeSection.valueOf(line.trim().toUpperCase());
                    continue;
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
                        TektonBase first = (TektonBase) gameBoard.getReferenceByObjectName(parts[0]);
                        for (int i = 1; i < parts.length; i++) {
                            TektonBase neighbour = (TektonBase) gameBoard.getReferenceByObjectName(parts[i]);
                            first.addNeighbour(neighbour);
                            neighbour.addNeighbour(first);
                        }

                    }
                    case SHROOMERS -> {
                        BiFunction<Shroomer, TektonBase, Mushroom> mushroomctor;
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


                        gameBoard.addShroomer(new Shroomer(mushroomctor, hypaDieAfter), "");

                    }
                    case MUSHROOMS -> {
                        Mushroom mushroom=null;
                        switch (parts[0].toLowerCase()) {
                            case "boostermushroom" -> {
                                mushroom = new BoosterMushroom((Shroomer) gameBoard.getReferenceByObjectName(parts[1]), (TektonBase) gameBoard.getReferenceByObjectName(parts[2]));
                            }
                            case "slowermushroom" -> {
                                mushroom = new SlowerMushroom((Shroomer) gameBoard.getReferenceByObjectName(parts[1]), (TektonBase) gameBoard.getReferenceByObjectName(parts[2]));
                            }
                            case "paralyzermushroom" -> {
                                mushroom = new ParalyzerMushroom((Shroomer) gameBoard.getReferenceByObjectName(parts[1]), (TektonBase) gameBoard.getReferenceByObjectName(parts[2]));
                            }
                            case "biteblockermushroom" -> {
                                mushroom = new BiteBlockerMushroom((Shroomer) gameBoard.getReferenceByObjectName(parts[1]), (TektonBase) gameBoard.getReferenceByObjectName(parts[2]));
                            }
                            case "proliferatingmushroom" -> {
                                mushroom = new ProliferatingMushroom((Shroomer) gameBoard.getReferenceByObjectName(parts[1]), (TektonBase) gameBoard.getReferenceByObjectName(parts[2]));
                            }

                        }
                        ((Shroomer) gameBoard.getReferenceByObjectName(parts[1])).addMushroom(mushroom);
                        ((TektonBase) gameBoard.getReferenceByObjectName(parts[2])).setMushroom(mushroom);
                    }
                    case BUGGERS -> {
                        gameBoard.addBugger(new Bugger(), "");
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
                        bug.setLocation((TektonBase) gameBoard.getReferenceByObjectName(parts[2]));
                        ((TektonBase) gameBoard.getReferenceByObjectName(parts[2])).setBug(bug);
                        ((Bugger) gameBoard.getReferenceByObjectName(parts[1])).addBug(bug);
                    }
                    case HYPAS -> {
                        Hypa hypa = new Hypa((TektonBase) gameBoard.getReferenceByObjectName(parts[0]),
                                (TektonBase) gameBoard.getReferenceByObjectName(parts[1]), (Shroomer) gameBoard.getReferenceByObjectName(parts[2]));
                        ((TektonBase) gameBoard.getReferenceByObjectName(parts[0])).connectHypa(hypa);
                        ((TektonBase) gameBoard.getReferenceByObjectName(parts[1])).connectHypa(hypa);
                        ((Shroomer) gameBoard.getReferenceByObjectName(parts[2])).addHypa(hypa);
                    }
                    case SPORES -> {
                        Spore spore;
                        Shroomer shroomer = ((Shroomer) gameBoard.getReferenceByObjectName(parts[1]));
                        switch (parts[0].toLowerCase()) {
                            case "boosterspore" -> spore = new BoosterSpore(shroomer);
                            case "slowerspore" -> spore = new SlowerSpore(shroomer);
                            case "paralyzerspore" -> spore = new ParalyzerSpore(shroomer);
                            case "biteblockerspore" -> spore = new BiteBlockerSpore(shroomer);
                            case "proliferatingspore" -> spore = new ProliferatingSpore(shroomer);
                            default -> spore = null;
                        }
                        ((TektonBase) gameBoard.getReferenceByObjectName(parts[2])).storeSpore(spore);
                    }
                }
            }
        } finally {
            source.close();
        }
    }

    public void actMethod(File tc, FileInputSource source) throws IOException {
        System.out.println("ACT");
        try {
            while (source.hasNextLine() && !endOfGame) {
                String line = source.readLine();
                /**
                 * valódi act parancsok beolvasása értelmezése
                 * a sikeres akciókról a controller visszajelez, csak a sikertelenek kiírását hagytam itt
                 */
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
                        if(!controller.move((Bug)gameBoard.getReferenceByObjectName(parts[1]),
                                (TektonBase)gameBoard.getReferenceByObjectName(parts[2])))
                            System.out.println("action failed");
                    }
                    case "bite" -> {
                        if(parts.length<3){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if(!controller.bite((Bug)gameBoard.getReferenceByObjectName(parts[1]),
                                (Hypa)gameBoard.getReferenceByObjectName(parts[2])))
                            System.out.println("action failed");
                    }
                    case "eat" -> {
                        if(parts.length<3){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if(!controller.eat((Bug)gameBoard.getReferenceByObjectName(parts[1]),
                                (Spore)gameBoard.getReferenceByObjectName(parts[2])))
                            System.out.println("action failed");
                    }
                    case "growhypa" -> {
                        if(parts.length<3){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if(!controller.growhypa((TektonBase)gameBoard.getReferenceByObjectName(parts[1]),
                                (TektonBase)gameBoard.getReferenceByObjectName(parts[2])))
                            System.out.println("action failed");
                    }
                    case "growhypafar" -> {
                        if(parts.length<4){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if (!controller.growhypafar((TektonBase)gameBoard.getReferenceByObjectName(parts[1]),
                                (TektonBase)gameBoard.getReferenceByObjectName(parts[2]),
                                (TektonBase)gameBoard.getReferenceByObjectName(parts[3])))
                            System.out.println("action failed");
                    }
                    case "throwspore" -> {
                        if(parts.length<3){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if(!controller.throwspore((Mushroom)gameBoard.getReferenceByObjectName(parts[1]), (TektonBase)gameBoard.getReferenceByObjectName(parts[2])))
                            System.out.println("action failed");
                    }
                    case "eatbug" -> {
                        if(parts.length<2){
                            System.out.println("not enough parameters");
                            break;
                        }
                        if(!controller.eatbug((Bug)gameBoard.getReferenceByObjectName(parts[1])))
                            System.out.println("action failed");
                    }
                    case "skip" -> {
                        controller.endturn();
                    }
                    default -> System.out.println("action failed because of invalid command or assert command");
                }
                if(line.equalsIgnoreCase("esc")){
                    return;
                }
                if(endOfGame){
                    endOfGameTable();
                    return;
                }
            }
        } finally {
            source.close();
        }
    }

    public void assertMethod(File tc, FileInputSource source) throws IOException {
        System.out.println("ASSERT");
        try {
            while (source.hasNextLine()) {
                String line = source.readLine();
                String[] parts = line.split(";");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                if (parts.length > 1) {
                    switch (parts[1].toLowerCase()) {
                        case "bugs" -> {
                            if (gameBoard.getBuggers().containsValue((Bugger) gameBoard.getReferenceByObjectName(parts[0]))) {
                                List<Bug> bugs;
                                bugs = ((Bugger) gameBoard.getReferenceByObjectName(parts[0])).getBugs();

                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    for (int i = 0; i < bugs.size(); i++) {
                                        System.out.print(gameBoard.getObjectNameByReference(bugs.get(i)) + (i != bugs.size() - 1 ? ";" : ""));
                                    }
                                    System.out.println();
                                    break;
                                }


                                ///ha a megadott paraméter null, akkor az üres lista esetén success
                                if (bugs.isEmpty() && parts[2].equalsIgnoreCase("null")) {
                                    assertSuccess(line);
                                    break;
                                }
                                List<Bug> assertbugs = new ArrayList<>();
                                for (int i = 2; i < parts.length; i++) {
                                    assertbugs.add((Bug) gameBoard.getReferenceByObjectName(parts[i]));
                                }
                                if (areListsIdentical(bugs, assertbugs))
                                    assertSuccess(line);
                                else {
                                    assertFail(line);
                                    System.out.print("The actual value(s): ");
                                    for (int i = 0; i < bugs.size(); i++) {
                                        System.out.print(gameBoard.getObjectNameByReference(bugs.get(i)) + ";");
                                    }
                                    System.out.println();

                                }
                            } else
                                assertError(line);
                        }

                        case "mushroomlocation" -> {
                            TektonBase location;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                location = ((Mushroom) gameBoard.getReferenceByObjectName(parts[0])).getLocation();

                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(gameBoard.getObjectNameByReference(location));
                                    break;
                                }

                            } else {
                                assertError(line);
                                break;
                            }
                            if (location == gameBoard.getReferenceByObjectName(parts[2]))
                                assertSuccess(line);
                            else
                                assertFail(line);
                        }

                        case "buglocation" -> {
                            TektonBase location;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                location = ((Bug) gameBoard.getReferenceByObjectName(parts[0])).getLocation();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(gameBoard.getObjectNameByReference(location));
                                    break;
                                }
                            } else {
                                assertError(line);
                                break;
                            }
                            if (location == gameBoard.getReferenceByObjectName(parts[2]))
                                assertSuccess(line);
                            else
                                assertFail(line);
                        }

                        case "strategy" -> {
                            Strategy strategy;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                strategy = ((Bug) gameBoard.getReferenceByObjectName(parts[0])).getStrategy();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(gameBoard.getObjectNameByReference(strategy));
                                    break;
                                }

                                if (strategy == ((Strategy) gameBoard.getReferenceByObjectName(parts[2])))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else
                                assertError(line);

                        }

                        case "undereffectsince" -> {
                            int underEffectSince;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                underEffectSince = ((Bug) gameBoard.getReferenceByObjectName(parts[0])).getUnderEffectSince();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(underEffectSince);
                                    break;
                                }
                                if (underEffectSince == Integer.parseInt(parts[2]))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else
                                assertError(line);
                        }

                        case "mushrooms" -> {
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                List<Mushroom> mushrooms;
                                mushrooms = ((Shroomer) gameBoard.getReferenceByObjectName(parts[0])).getMushrooms();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    for (int i = 0; i < mushrooms.size(); i++) {
                                        System.out.print(gameBoard.getObjectNameByReference(mushrooms.get(i)) + (i != mushrooms.size() - 1 ? ";" : ""));
                                    }
                                    System.out.println();
                                    break;
                                }


                                ///ha a megadott paraméter null, akkor az üres lista esetén success
                                if (mushrooms.isEmpty() && parts[2].equalsIgnoreCase("null")) {
                                    assertSuccess(line);
                                    break;
                                }
                                List<Mushroom> assertmushrooms = new ArrayList<>();
                                for (int i = 2; i < parts.length; i++) {
                                    assertmushrooms.add((Mushroom) gameBoard.getReferenceByObjectName(parts[i]));
                                }
                                if (areListsIdentical(mushrooms, assertmushrooms))
                                    assertSuccess(line);
                                else {
                                    assertFail(line);
                                    System.out.print("The actual value(s): ");
                                    for (int i = 0; i < mushrooms.size(); i++) {
                                        System.out.print(gameBoard.getObjectNameByReference(mushrooms.get(i)) + ";");
                                    }
                                    System.out.println();
                                }
                            } else
                                assertError(line);
                        }

                        case "shroomerhypas" -> {
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                List<Hypa> hypas;
                                hypas = ((Shroomer) gameBoard.getReferenceByObjectName(parts[0])).getHypaList();

                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    for (int i = 0; i < hypas.size(); i++) {
                                        System.out.print(gameBoard.getObjectNameByReference(hypas.get(i)) + (i != hypas.size() - 1 ? ";" : ""));
                                    }
                                    System.out.println();
                                    break;
                                }


                                ///ha a megadott paraméter null, akkor az üres lista esetén success
                                if (hypas.isEmpty() && parts[2].equalsIgnoreCase("null")) {
                                    assertSuccess(line);
                                    break;
                                }
                                List<Hypa> asserthypas = new ArrayList<>();
                                for (int i = 2; i < parts.length; i++) {
                                    asserthypas.add((Hypa) gameBoard.getReferenceByObjectName(parts[i]));
                                }
                                if (areListsIdentical(hypas, asserthypas))
                                    assertSuccess(line);
                                else {
                                    assertFail(line);

                                    System.out.print("The actual value(s): ");
                                    for (int i = 0; i < hypas.size(); i++) {
                                        System.out.print(gameBoard.getObjectNameByReference(hypas.get(i)) + ";");
                                    }
                                    System.out.println();
                                }
                            } else
                                assertError(line);
                        }

                        case "tektonhypas" -> {
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                List<Hypa> hypas;
                                hypas = ((TektonBase) gameBoard.getReferenceByObjectName(parts[0])).getHypas();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    for (int i = 0; i < hypas.size(); i++) {
                                        System.out.print(gameBoard.getObjectNameByReference(hypas.get(i)) + (i != hypas.size() - 1 ? ";" : ""));
                                    }
                                    System.out.println();
                                    break;
                                }


                                ///ha a megadott paraméter null, akkor az üres lista esetén success
                                if (hypas.isEmpty() && parts[2].equalsIgnoreCase("null")) {
                                    assertSuccess(line);
                                    break;
                                }
                                List<Hypa> asserthypas = new ArrayList<>();
                                for (int i = 2; i < parts.length; i++) {
                                    asserthypas.add((Hypa) gameBoard.getReferenceByObjectName(parts[i]));
                                }
                                if (areListsIdentical(hypas, asserthypas))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else
                                assertError(line);
                        }

                        case "mushroomage" -> {
                            int age;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                age = ((Mushroom) gameBoard.getReferenceByObjectName(parts[0])).getAge();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(age);
                                    break;
                                }
                                if (age == Integer.parseInt(parts[2]))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else
                                assertError(line);
                        }

                        case "hypaage" -> {
                            int age;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                age = ((Hypa) gameBoard.getReferenceByObjectName(parts[0])).getAge();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(age);
                                    break;
                                }
                                if (age == Integer.parseInt(parts[2]))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else
                                assertError(line);
                        }


                        case "numberofspores" -> {
                            int numberOfSpores;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                numberOfSpores = ((Mushroom) gameBoard.getReferenceByObjectName(parts[0])).getNumberOfSpores();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(numberOfSpores);
                                    break;
                                }
                                if (numberOfSpores == Integer.parseInt(parts[2]))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else
                                assertError(line);
                        }

                        case "sporesthrown" -> {
                            int sporesThrown;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                sporesThrown = ((Mushroom) gameBoard.getReferenceByObjectName(parts[0])).getSporesThrown();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(sporesThrown);
                                    break;
                                }
                                if (sporesThrown == Integer.parseInt(parts[2]))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else
                                assertError(line);
                        }
                        case "mushroomshroomer" -> {
                            Shroomer shroomer;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                shroomer = ((Mushroom) gameBoard.getReferenceByObjectName(parts[0])).getShroomer();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(gameBoard.getObjectNameByReference(shroomer));
                                    break;
                                }
                                if (shroomer == gameBoard.getReferenceByObjectName(parts[2]))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else
                                assertError(line);
                        }

                        case "sporeshroomer" -> {
                            Shroomer shroomer;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                shroomer = ((Spore) gameBoard.getReferenceByObjectName(parts[0])).getShroomer();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(gameBoard.getObjectNameByReference(shroomer));
                                    break;
                                }
                                if (shroomer == gameBoard.getReferenceByObjectName(parts[2]))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else
                                assertError(line);
                        }

                        case "hypashroomer" -> {
                            Shroomer shroomer;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                shroomer = ((Hypa) gameBoard.getReferenceByObjectName(parts[0])).getShroomer();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(gameBoard.getObjectNameByReference(shroomer));
                                    break;
                                }
                                if (shroomer == gameBoard.getReferenceByObjectName(parts[2]))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else {
                                assertError(line);
                            }
                        }

                        case "ends" -> {
                            TektonBase end1;
                            TektonBase end2;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {


                                end1 = ((Hypa) gameBoard.getReferenceByObjectName(parts[0])).getEnd1();
                                end2 = ((Hypa) gameBoard.getReferenceByObjectName(parts[0])).getEnd2();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(gameBoard.getObjectNameByReference(end1) + "; " + gameBoard.getObjectNameByReference(end2));
                                    break;
                                }
                                if (end1 == gameBoard.getReferenceByObjectName(parts[2]) && end2 == gameBoard.getReferenceByObjectName(parts[3])
                                        || end1 == gameBoard.getReferenceByObjectName(parts[3]) && end2 == gameBoard.getReferenceByObjectName(parts[2]))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else
                                assertError(line);
                        }

                        case "isdyingsincedisconnected" -> {
                            int isDyingSinceDisconnected;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                isDyingSinceDisconnected = ((Hypa) gameBoard.getReferenceByObjectName(parts[0])).getIsDyingSinceDisconnected();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(isDyingSinceDisconnected);
                                    break;
                                }
                                if (isDyingSinceDisconnected == Integer.parseInt(parts[2]))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else
                                assertError(line);
                        }

                        case "isdyingsincebitten" -> {
                            int isDyingSinceBitten;
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                isDyingSinceBitten = ((Hypa) gameBoard.getReferenceByObjectName(parts[0])).getIsDyingSinceBitten();
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(isDyingSinceBitten);
                                    break;
                                }
                                if (isDyingSinceBitten == Integer.parseInt(parts[2]))
                                    assertSuccess(line);
                                else
                                    assertFail(line);
                            } else
                                assertError(line);
                        }

                        case "neighbours" -> {
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                List<TektonBase> neighbours;
                                neighbours = ((TektonBase) gameBoard.getReferenceByObjectName(parts[0])).getNeighbours();


                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    for (int i = 0; i < neighbours.size(); i++) {
                                        System.out.print(gameBoard.getObjectNameByReference(neighbours.get(i)) + (i != neighbours.size() - 1 ? ";" : ""));
                                    }
                                    System.out.println();
                                    break;
                                }

                                ///ha a megadott paraméter null, akkor az üres lista esetén success
                                if (neighbours.isEmpty() && parts[2].equalsIgnoreCase("null")) {
                                    assertSuccess(line);
                                    break;
                                }
                                List<TektonBase> assertneighbour = new ArrayList<>();
                                for (int i = 2; i < parts.length; i++) {
                                    assertneighbour.add((TektonBase) gameBoard.getReferenceByObjectName(parts[i]));
                                }
                                if (areListsIdentical(neighbours, assertneighbour))
                                    assertSuccess(line);
                                else {
                                    assertFail(line);
                                    System.out.print("The actual value(s): ");
                                    for (int i = 0; i < neighbours.size(); i++) {
                                        System.out.print(gameBoard.getObjectNameByReference(neighbours.get(i)) + ";");
                                    }
                                    System.out.println();
                                }
                            } else
                                assertError(line);
                        }

                        case "spores" -> {
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                List<Spore> spores;
                                spores = ((TektonBase) gameBoard.getReferenceByObjectName(parts[0])).getStoredSpores();

                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    for (int i = 0; i < spores.size(); i++) {
                                        System.out.print(gameBoard.getObjectNameByReference(spores.get(i)) + (i != spores.size() - 1 ? ";" : ""));
                                    }
                                    System.out.println();
                                    break;
                                }

                                ///ha a megadott paraméter null, akkor az üres lista esetén success
                                if (spores.isEmpty() && parts[2].equalsIgnoreCase("null")) {
                                    assertSuccess(line);
                                    break;
                                }

                                List<Spore> assertspores = new ArrayList<>();
                                for (int i = 2; i < parts.length; i++) {
                                    assertspores.add((Spore) gameBoard.getReferenceByObjectName(parts[i]));
                                }


                                if (areListsIdentical(spores, assertspores))
                                    assertSuccess(line);
                                else {
                                    assertFail(line);
                                    System.out.print("The actual value(s): ");
                                    for (int i = 0; i < spores.size(); i++) {
                                        System.out.print(gameBoard.getObjectNameByReference(spores.get(i)) + ";");
                                    }
                                    System.out.println();
                                }
                            } else
                                assertError(line);
                        }

                        case "bug" -> {
                            if (gameBoard.getReferenceByObjectName(parts[0]) != null) {
                                ///paraméter nélkül nem összehasonlít, hanme lekérdez
                                if (parts.length == 2) {
                                    System.out.println(gameBoard.getObjectNameByReference(((TektonBase) gameBoard.getReferenceByObjectName(parts[0])).getBug()));
                                    break;
                                }


                                if (((TektonBase) gameBoard.getReferenceByObjectName(parts[0])).getBug() == gameBoard.getReferenceByObjectName(parts[2]))
                                    assertSuccess(line);
                                else {
                                    assertFail(line);
                                    System.out.println("The actual value: " + gameBoard.getObjectNameByReference(((TektonBase) gameBoard.getReferenceByObjectName(parts[0])).getBug()));
                                }
                            } else
                                assertError(line);
                        }
                        default -> {
                            System.out.println("not a valid command");
                        }
                    }
                } else if(line.equalsIgnoreCase("esc")){
                    source.close();
                    return;
                }else{
                    System.out.println("not enough parameters");
                }
            }
        } finally {
            source.close();
        }
    }

    @Override
    public void displayMessage(String message){
        System.out.println(message);
    }

    private boolean areListsIdentical(List<?> list1, List<?> list2) {
        Set<?> set1 = new HashSet<>(list1);
        Set<?> set2 = new HashSet<>(list2);
        return set1.equals(set2);
    }

    private void assertSuccess(String assertLine){
        System.out.println(assertLine+": SUCCESS");
    }

    private void assertFail(String assertLine){
        System.out.println(assertLine+": FAIL");
    }

    private void assertError(String assertLine){
        System.out.println(assertLine+": ERROR");
    }

    private boolean checkIfObjectExists(String oName){
        if(gameBoard.getReferenceByObjectName(oName) == null){
            System.out.println("The object "+oName + " doesn't exist");
            return false;
        }
        return true;
    }
    private void endOfGameTable(){
        System.out.println("Score board");
        List<Integer> shroomerSortedKeys = new ArrayList<>(gameBoard.getShroomers().keySet());
        Collections.sort(shroomerSortedKeys);
        List<Integer> buggerSortedKeys = new ArrayList<>(gameBoard.getBuggers().keySet());
        Collections.sort(buggerSortedKeys);
        List<Integer> shroomerScores = new ArrayList<>();
        List<Integer> buggerScores = new ArrayList<>();

        int maxScoreBugger=0;
        List<Integer> maxScoreBuggerIds = new ArrayList<>();
        for(int i=0;i<gameBoard.getBuggers().size();i++){
              int score = gameBoard.getBuggers().get(buggerSortedKeys.get(i)).getScore();
              if(score>=maxScoreBugger){
                  maxScoreBugger=score;
                  maxScoreBuggerIds.add(i);
              }
            buggerScores.add(score);
        }
        int maxScoreShroomer=0;
        List<Integer> maxScoreShroomerIds=new ArrayList<>();
        for(int i=0;i<gameBoard.getShroomers().size();i++){
            int score = gameBoard.getShroomers().get(shroomerSortedKeys.get(i)).getScore();
            if(score>=maxScoreShroomer){
                maxScoreShroomer=score;
                maxScoreShroomerIds.add(i);
            }
            shroomerScores.add(score);
        }

        System.out.print("The winner bugger(s) is ");
        for(int i=0;i<maxScoreBuggerIds.size();i++){
            System.out.print(gameBoard.getObjectNameByReference(gameBoard.getBuggers().get(buggerSortedKeys.get(maxScoreBuggerIds.get(i))))
            + (i==maxScoreBuggerIds.size()-1?" ":"; ") );
        }
        System.out.println("with " + maxScoreBugger + " points");

        System.out.print("The winner shroomer(s) is ");
        for(int i=0;i<maxScoreShroomerIds.size();i++){
            System.out.print(gameBoard.getObjectNameByReference(gameBoard.getShroomers().get(shroomerSortedKeys.get(maxScoreShroomerIds.get(i))))
                    + (i==maxScoreShroomerIds.size()-1?" ":"; ") );
        }
        System.out.println("with " + maxScoreShroomer + " points");

        System.out.println("The other players with the points they have achieved");
        for (int i=0;i< gameBoard.getBuggers().size();i++){
            if(!maxScoreBuggerIds.contains(i)) {
                System.out.println(gameBoard.getObjectNameByReference(gameBoard.getBuggers().get(buggerSortedKeys.get(i)))+
                        " - " + gameBoard.getBuggers().get(buggerSortedKeys.get(i)).getScore()+ " points");
            }
        }
        for (int i=0;i< gameBoard.getShroomers().size();i++){
            if(maxScoreShroomerIds.contains(i)) {
                System.out.println(gameBoard.getObjectNameByReference(gameBoard.getShroomers().get(shroomerSortedKeys.get(i))) +
                        " - " + gameBoard.getShroomers().get(shroomerSortedKeys.get(i)).getScore() + " points");
            }
        }
    }
}
