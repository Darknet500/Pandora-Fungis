package Controll;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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
    Map<String, Object> objectNames = new HashMap<>();
    Map<String, Integer> objectCount = new HashMap<>();
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
            continue;
        } catch (IllegalArgumentException ignored) {}

        String[] parts = line.split("; ");
        switch (section) {
            case TEKTONS -> {
                Tekton tekton = createTekton(parts[0]);
                objectNames.put(generateName(parts[0], objectCount), tekton);
            }
            case NEIGHBOURS -> {

                Tekton first = (Tekton) objectNames.get(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    Tekton neighbor = (Tekton) objectNames.get(parts[i]);
                    first.addNeighbour(neighbor);
                    neighbor.addNeighbour(first);
                }
            }
            case SHROOMERS -> {
                BiFunction<Shroomer, Tekton, Mushroom> mushroomctor;
                int hypaDieAfter;
                switch (parts[0]){
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
                objectNames.put(shroomerName, shroomer);
            }
            case MUSHROOMS -> {
                Mushroom mushroom = createMushroom(parts[0], (Shroomer)objectNames.get(parts[1]), (Tekton)objectNames.get(parts[2]));
                objectNames.put(generateName(parts[0], objectCount), mushroom);
            }
            case BUGGERS -> {
                Bugger bugger = new Bugger();
                String buggerName = generateName("Bugger", objectCount);
                objectNames.put(buggerName, bugger);
            }
            case STRATEGIES -> {
                Strategy strategy = createStrategy(parts[0]);
                objectNames.put(generateName(parts[0], objectCount), strategy);
            }
            case BUGS -> {
                Bug bug = new Bug((Bugger)objectNames.get(parts[1]));
                objectNames.put("Bug", bug);
                bug.setStrategy((Strategy) objectNames.get(parts[0]));
                bug.setLocation((Tekton) objectNames.get(parts[2]));
                ((Tekton) objectNames.get(parts[2])).setBug(bug);

            }
            case HYPAS -> {
                Hypa hypa = new Hypa((Tekton)objectNames.get(parts[0]),(Tekton)objectNames.get(parts[1]), (Shroomer)objectNames.get(parts[2]));
                objectNames.put("Hypa", hypa);
                ((Tekton)objectNames.get(parts[0])).connectHypa(hypa);
                ((Tekton)objectNames.get(parts[1])).connectHypa(hypa);
            }
            case SPORES -> {
                Spore spore = createSpore(parts[0], (Shroomer)objectNames.get(parts[1]));
                objectNames.put(generateName(parts[0], objectCount), spore);
                ((Tekton)objectNames.get(parts[2])).storeSpore(spore);
            }
        }

    }



}


