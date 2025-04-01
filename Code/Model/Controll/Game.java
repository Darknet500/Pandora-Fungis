package Controll;

import java.util.*;
import java.util.function.BiFunction;

import Tekton.*;
import Bug.*;
import Shroomer.*;

enum Section {
    TEKTONS, NEIGHBOURS, SHROOMERS, MUSHROOMS, BUGGERS, STRATEGIES, BUGS, HYPAS, SPORES
}

enum Phase {
    ARRANGE, ACT, ASSERT
}

public class Game {


    private int turncount=0;
    Scanner scanner = new Scanner(System.in);
    Map<String, Bug> bugObjectNames = new HashMap<>();
    Map<String, Strategy> strategyObjectNames = new HashMap<>();
    Map<String, Mushroom> mushroomObjectNames = new HashMap<>();
    Map<String, Hypa> hypaObjectNames = new HashMap<>();
    Map<String, Spore> sporeObjectNames = new HashMap<>();
    Map<String, Tekton> tektonObjectNames = new HashMap<>();
    Map<String, Integer> objectCount = new HashMap<>();
    Map<String, Player> playerObjectNames = new HashMap<>();
    Section section=null;
    Phase phase=null;

    public Game(){


        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            try {
                phase = Phase.valueOf(line.toUpperCase());
                continue;
            } catch (IllegalArgumentException ignored) {}

            switch (phase) {
                case ARRANGE-> getArrangeLine(line);
                case ACT -> getActLine(line);
                case ASSERT -> getAssertLine(line);

            }




        }
        scanner.close();

    }

    private static String generateName(String className, Map<String, Integer> objectCount) {
        String classNamename = className.toLowerCase();
        if (objectCount.containsKey(classNamename)) {
            int count = objectCount.get(classNamename);
            objectCount.replace(classNamename, ++count);
        }else {
            objectCount.put(classNamename,1);
        }
        return className + objectCount.get(classNamename);
    }


    private static Tekton createTekton(String className){
        return switch (className){
            case "Tekton" -> new Tekton();
            case "Peat" -> new Peat();
            case "Soil" -> new Soil();
            case "Stone" -> new Stone();
            case "Swamp" -> new Swamp();
            default -> null;
        };
    }

    private static Mushroom createMushroom(String className, Shroomer sh, Tekton loc){
        return switch (className){
            case "BiteBlockerMushroom" -> new BiteBlockerMushroom(sh, loc);
            case "BoosterMushroom" -> new BoosterMushroom(sh, loc);
            case "ParalyzerMushroom" -> new ParalyzerMushroom(sh, loc);
            case "ProliferatingMushroom" -> new ProliferatingMushroom(sh, loc);
            case "SlowerMushroom" -> new SlowerMushroom(sh, loc);
            default -> null;
        };
    }

    private static Strategy createStrategy(String className){
        return switch (className){
            case "Normal" -> new Normal();
            case "BiteBlocked" -> new BiteBlocked();
            case "Boosted" -> new Boosted();
            case "Paralyzed" -> new Paralyzed();
            case "Slowed" -> new Slowed();
            default -> null;
        };
    }

    private static Spore createSpore(String className, Shroomer sh){
        return switch (className){
            case "BiteBlockerSpore" -> new BiteBlockerSpore(sh);
            case "BoosterSpore" -> new BoosterSpore(sh);
            case "ParalyzerSpore" -> new ParalyzerSpore(sh);
            case "ProliferatingSpore" -> new ProliferatingSpore(sh);
            case "SlowerSpore" -> new SlowerSpore(sh);
            default -> null;
        };
    }

    private void getArrangeLine(String line){
        try {
            section = Section.valueOf(line.toUpperCase());
        } catch (IllegalArgumentException ignored) {}

        String[] parts = line.split(";");
        switch (section) {
            case TEKTONS -> {
                Tekton tekton = createTekton(parts[0]);
                tektonObjectNames.put(generateName(parts[0], objectCount), tekton);
            }
            case NEIGHBOURS -> {

                Tekton first = tektonObjectNames.get(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    Tekton neighbor = tektonObjectNames.get(parts[i]);
                    first.addNeighbour(neighbor);
                    neighbor.addNeighbour(first);
                }
            }
            case SHROOMERS -> {
                BiFunction<Shroomer, Tekton, Mushroom> mushroomctor;
                int hypaDieAfter;
                switch (parts[0].toLowerCase()){
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

                    default -> {
                        mushroomctor = (x, y)->new BoosterMushroom(x, y);
                        hypaDieAfter=4;
                    }
                }


                Shroomer shroomer = new Shroomer(mushroomctor, hypaDieAfter);
                String shroomerName = generateName("Shroomer", objectCount);
                playerObjectNames.put(shroomerName, shroomer);
            }
            case MUSHROOMS -> {
                Mushroom mushroom = createMushroom(parts[0], (Shroomer)playerObjectNames.get(parts[1]), tektonObjectNames.get(parts[2]));
                mushroomObjectNames.put(generateName(parts[0], objectCount), mushroom);
            }
            case BUGGERS -> {
                Bugger bugger = new Bugger();
                String buggerName = generateName("Bugger", objectCount);
                playerObjectNames.put(buggerName, bugger);
            }
            case STRATEGIES -> {
                Strategy strategy = createStrategy(parts[0]);
                strategyObjectNames.put(generateName(parts[0], objectCount), strategy);
            }
            case BUGS -> {
                Bug bug = new Bug((Bugger)playerObjectNames.get(parts[1]));
                bugObjectNames.put("Bug", bug);
                bug.setStrategy(strategyObjectNames.get(parts[0]));
                bug.setLocation(tektonObjectNames.get(parts[2]));
                tektonObjectNames.get(parts[2]).setBug(bug);

            }
            case HYPAS -> {
                Hypa hypa = new Hypa(tektonObjectNames.get(parts[0]),tektonObjectNames.get(parts[1]), (Shroomer) playerObjectNames.get(parts[2]));
                hypaObjectNames.put("Hypa", hypa);
                tektonObjectNames.get(parts[0]).connectHypa(hypa);
                tektonObjectNames.get(parts[1]).connectHypa(hypa);
            }
            case SPORES -> {
                Spore spore = createSpore(parts[0],(Shroomer) playerObjectNames.get(parts[1]));
                sporeObjectNames.put(generateName(parts[0], objectCount), spore);
                tektonObjectNames.get(parts[2]).storeSpore(spore);
            }
        }

    }

    private void getActLine(String line){
        String[] parts = line.split(" ");
        switch (parts[1].toLowerCase()){
            case "move" -> ((Bugger)playerObjectNames.get(parts[0])).move(bugObjectNames.get(parts[2]),tektonObjectNames.get(parts[3]));
            case "bite" -> ((Bugger)playerObjectNames.get(parts[0])).bite(bugObjectNames.get(parts[2]),hypaObjectNames.get(parts[3]));
            case "eat" -> ((Bugger)playerObjectNames.get(parts[0])).eat(bugObjectNames.get(parts[2]),sporeObjectNames.get(parts[3]));
            case "growhypa" -> ((Shroomer)playerObjectNames.get(parts[0])).growHypa(tektonObjectNames.get(parts[2]),tektonObjectNames.get(parts[3]));
            case "growhypafar" -> ((Shroomer)playerObjectNames.get(parts[0])).growHypaFar(tektonObjectNames.get(parts[2]),tektonObjectNames.get(parts[3]), tektonObjectNames.get(parts[4]));
            case "throwspore" -> ((Shroomer)playerObjectNames.get(parts[0])).throwSpore(mushroomObjectNames.get(parts[2]),tektonObjectNames.get(parts[3]));
            case "eatbug" -> ((Shroomer)playerObjectNames.get(parts[0])).eatBug(bugObjectNames.get(parts[2]));
            case "endturn" ->playerObjectNames.get(parts[0]).skip();
        }
    }

    private void getAssertLine(String line){
        String[] parts = line.split(";");
        switch (parts[1].toLowerCase()){
            case "bugs" -> {
                List<Bug> bugs;
                if (playerObjectNames.containsKey(parts[0])) {
                    bugs = ((Bugger) playerObjectNames.get(parts[0])).getBugs();
                    boolean identical = true;
                    for (int i = 0; i < bugs.size(); i++) {
                        if (bugs.get(i) != bugObjectNames.get(parts[2 + i])) identical = false;
                    }
                    if (identical)
                        System.out.println("SUCCESS");
                    else
                        System.out.println("FAIL");
                }
                else
                    System.out.println("ERROR");
            }
            case "location" ->{
                Tekton location;
                if (mushroomObjectNames.containsKey(parts[0]))
                    location = mushroomObjectNames.get(parts[0]).getLocation();
                else if (bugObjectNames.containsKey(parts[0]))
                    location = bugObjectNames.get(parts[0]).getLocation();
                else {
                    System.out.println("ERROR");
                    break;
                }
                if(location==tektonObjectNames.get(parts[2]))
                    System.out.println("SUCCESS");
                else System.out.println("FAIL");
            }
            case "strategy" -> {
                Strategy strategy;
                if (bugObjectNames.containsKey(parts[0])) {
                    strategy = bugObjectNames.get(parts[0]).getStrategy();
                    if (strategy == strategyObjectNames.get(parts[2]))
                        System.out.println("SUCCESS");
                    else System.out.println("FAIL");
                }
                else
                    System.out.println("ERROR");

            }
            case "undereffectsince" -> {
                int underEffectSince;
                if (bugObjectNames.containsKey(parts[0])) {
                    underEffectSince = bugObjectNames.get(parts[0]).getUnderEffectSince();
                    if (underEffectSince == Integer.parseInt(parts[2]))
                        System.out.println("SUCCESS");
                    else System.out.println("FAIL");
                }
                else
                    System.out.println("ERROR");
            }
            case "mushrooms" ->{
                List<Mushroom> mushrooms;
                if (playerObjectNames.containsKey(parts[0])) {
                    mushrooms = ((Shroomer) playerObjectNames.get(parts[0])).getMushrooms();
                    boolean identical = true;
                    for (int i = 0; i < mushrooms.size(); i++) {
                        if (mushrooms.get(i) != mushroomObjectNames.get(parts[2 + i])) identical = false;
                    }
                    if (identical)
                        System.out.println("SUCCESS");
                    else
                        System.out.println("FAIL");
                }
                else
                    System.out.println("ERROR");

            }
            case "hypas" -> {
                List<Hypa> hypas;
                if (playerObjectNames.containsKey(parts[0]))
                    hypas = ((Shroomer)playerObjectNames.get(parts[0])).getHypas();
                else if (tektonObjectNames.containsKey(parts[0]))
                    hypas = tektonObjectNames.get(parts[0]).getHypas();

                else {
                    System.out.println("ERROR");
                    break;
                }

                boolean identical = true;
                for (int i = 0; i < hypas.size(); i++) {
                    if (hypas.get(i)!=hypaObjectNames.get(parts[2+i])) identical = false;
                }
                if (identical)
                    System.out.println("SUCCESS");
                else
                    System.out.println("FAIL");


            }
            case "age" -> {
                int age;
                if (mushroomObjectNames.containsKey(parts[0]))
                    age=mushroomObjectNames.get(parts[0]).getAge();
                else if (hypaObjectNames.containsKey(parts[0]))
                    age=hypaObjectNames.get(parts[0]).getAge();

                else {
                    System.out.println("ERROR");
                    break;
                }
                if(age==Integer.parseInt(parts[2]))
                    System.out.println("SUCCESS");
                else System.out.println("FAIL");
            }
            case "numberofspores" ->{
                int numberOfSpores;
                if (mushroomObjectNames.containsKey(parts[0])) {
                    numberOfSpores = mushroomObjectNames.get(parts[0]).getNumberOfSpores();
                    if (numberOfSpores == Integer.parseInt(parts[2]))
                        System.out.println("SUCCESS");
                    else System.out.println("FAIL");
                }
                else System.out.println("ERROR");
            }
            case "sporesthrown" ->{
                int sporesThrown;
                if (mushroomObjectNames.containsKey(parts[0])) {
                    sporesThrown = mushroomObjectNames.get(parts[0]).getSporesThrown();
                    if (sporesThrown == Integer.parseInt(parts[2]))
                        System.out.println("SUCCESS");
                    else System.out.println("FAIL");
                }
                else System.out.println("ERROR");


            }
            case "shroomer" ->{
                Shroomer shroomer;
                if (mushroomObjectNames.containsKey(parts[0]))
                    shroomer=mushroomObjectNames.get(parts[0]).getShroomer();
                else if (sporeObjectNames.containsKey(parts[0]))
                    shroomer=sporeObjectNames.get(parts[0]).getShroomer();
                else if (hypaObjectNames.containsKey(parts[0]))
                    shroomer=hypaObjectNames.get(parts[0]).getShroomer();

                else {
                    System.out.println("ERROR");
                    break;
                }
                if(shroomer==playerObjectNames.get(parts[2]))
                    System.out.println("SUCCESS");
                else System.out.println("FAIL");
            }
            case "end1" ->{
                Tekton end1;
                if (hypaObjectNames.containsKey(parts[0])) {
                    end1 = hypaObjectNames.get(parts[0]).getEnd1();
                    if (end1 == tektonObjectNames.get(parts[2]))
                        System.out.println("SUCCESS");
                    else System.out.println("FAIL");
                }
                else System.out.println("ERROR");


            }
            case "end2" -> {
                Tekton end2;
                if (hypaObjectNames.containsKey(parts[0])) {
                    end2 = hypaObjectNames.get(parts[0]).getEnd1();
                    if (end2 == tektonObjectNames.get(parts[2]))
                        System.out.println("SUCCESS");
                    else System.out.println("FAIL");
                }
                else System.out.println("ERROR");

            }
            case "isdyingsincedisconnected" -> {
                int isDyingSinceDisconnected;
                if (hypaObjectNames.containsKey(parts[0])) {
                    isDyingSinceDisconnected = hypaObjectNames.get(parts[0]).getIsDyingSinceDisconnected();

                    if(isDyingSinceDisconnected==Integer.parseInt(parts[2]))
                        System.out.println("SUCCESS");
                    else System.out.println("FAIL");
                }else System.out.println("ERROR");
            }
            case "isdyingsincebitten" ->{
                int isDyingSinceBitten;
                if (hypaObjectNames.containsKey(parts[0])) {
                    isDyingSinceBitten = hypaObjectNames.get(parts[0]).getIsDyingSinceBitten();
                    if(isDyingSinceBitten==Integer.parseInt(parts[2]))
                        System.out.println("SUCCESS");
                    else System.out.println("FAIL");
                }
                else System.out.println("ERROR");
            }
            case "neighbours" ->{
                List<Tekton> neighbours;
                if (tektonObjectNames.containsKey(parts[0])) {
                    neighbours = tektonObjectNames.get(parts[0]).getNeighbours();
                    boolean identical = true;
                    for (int i = 0; i < neighbours.size(); i++) {
                        if (neighbours.get(i)!=tektonObjectNames.get(parts[2+i])) identical = false;
                    }
                    if (identical)
                        System.out.println("SUCCESS");
                    else
                        System.out.println("FAIL");
                }
                else System.out.println("ERROR");
            }
            case "spores" ->{
                List<Spore> spores = new ArrayList<>();
                if(tektonObjectNames.containsKey(parts[0])) {
                    spores = tektonObjectNames.get(parts[0]).getStoredSpores();
                    boolean identical = true;
                    for (int i = 0; i < spores.size(); i++) {
                        if (spores.get(i)!=sporeObjectNames.get(parts[2+i])) identical = false;
                    }
                    if (identical)
                        System.out.println("SUCCESS");
                    else
                        System.out.println("FAIL");
                }
                else System.out.println("ERROR");
            }
            case "bug" ->{
                Bug bug;
                if (tektonObjectNames.containsKey(parts[0])) {
                    bug = tektonObjectNames.get(parts[0]).getBug();
                    if(bug==bugObjectNames.get(parts[2]))
                        System.out.println("SUCCESS");
                    else System.out.println("FAIL");
                }
                else System.out.println("ERROR");
            }
        }
    }



}


