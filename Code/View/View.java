package View;

import java.io.*;
import java.util.Scanner;
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
                System.out.println("Adjon hozzá egy új gombászt (shroomer paranccsal), \n Egy új bogarászt (bugger paranccsal)\n vagy indítsa el a játékot (start paranccsal)");
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

                if(line.equalsIgnoreCase("act")) break;
            }
        } finally {
            source.close();
        }

    }

    public void displayMessage(String message){
        System.out.println(message);
    }



}
