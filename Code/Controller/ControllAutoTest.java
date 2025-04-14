package Controller;
import Model.Tekton.*;
import Model.Shroomer.*;

import java.io.File;

public class ControllAutoTest extends Controller {
    private String tc;


    public ControllAutoTest(String testCase){
            super();
            tc = testCase;
    }

    @Override
    public void run() {
        if (tc == null){


        }else{



        }
    }


    private void initMap(){
        String path = "./test/"+tc+"arrange.txt";
        File file = new File(path);
        if (file.exists()) {
            view.readInputFile(file);
        }

    }

    @Override
    public void HandleInput(String input) {
        try {
            arrangeSection = arrangeSection.valueOf(input.toUpperCase());
            return;
        } catch (IllegalArgumentException ignored) {}

        String[] parts = input.split(";");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        switch (arrangeSection) {
            case TEKTONS -> {
                switch (parts[0]){
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
                //kérdés hogy ab objectNameMap.hez hogyab adjuk hozzá


            }
            case NEIGHBOURS -> {
                Tekton first; //végig megy a gameboard összes tektonján, és ha valamelyiknek a neve megegyezik az első argumentum névvel, akkor az a tekton
                for (int i=0;i<gameBoard.getTektons().size();i++){
                    if (gameBoard.getObjectNameByReference(gameBoard.getTektons().get(i)).equals(parts[0]))
                        first = gameBoard.getTektons().get(i);
                }
                for (int k = 1; k < parts.length; k++) {
                    Tekton neighbour;
                    for (int i=0;i<gameBoard.getTektons().size();i++){
                        if (gameBoard.getObjectNameByReference(gameBoard.getTektons().get(i)).equals(parts[k]))
                            neighbour = gameBoard.getTektons().get(i);
                    }
                    first.addNeighbour(neighbour);
                    neighbour.addNeighbour(first);
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
                bugObjectNames.put(generateName("Bug", objectCount), bug);
                bug.setStrategy(strategyObjectNames.get(parts[0]));
                bug.setLocation(tektonObjectNames.get(parts[2]));
                tektonObjectNames.get(parts[2]).setBug(bug);
                ((Bugger)playerObjectNames.get(parts[1])).addBug(bug);

            }
            case HYPAS -> {
                Hypa hypa = new Hypa(tektonObjectNames.get(parts[0]),tektonObjectNames.get(parts[1]), (Shroomer) playerObjectNames.get(parts[2]));
                hypaObjectNames.put(generateName("Hypa", objectCount), hypa);
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

}
