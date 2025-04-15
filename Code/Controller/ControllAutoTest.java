package Controller;
import Model.Bug.*;
import Model.Tekton.*;
import Model.Shroomer.*;

import java.io.File;
import java.util.function.BiFunction;

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

        state = State.ARRANGE;
        String path = "./test/"+tc+"arrange.txt";
        File file = new File(path);
        if (file.exists()) {
            view.readInputFile(file);
        }

    }

    @Override
    public void HandleInput(String input) {
        switch (state){
            case ARRANGE -> {
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
                        Tekton first = (Tekton)gameBoard.getReferenceByObjectName(parts[0]);
                        for(int i=1;i<parts.length;i++){
                            Tekton neighbour = (Tekton)gameBoard.getReferenceByObjectName(parts[i]);
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


                        gameBoard.addShroomer(new Shroomer(mushroomctor, hypaDieAfter));

                    }
                    case MUSHROOMS -> {
                        switch (parts[0].toLowerCase()){
                            case "boostermushroom" -> {
                                new BoosterMushroom( (Shroomer)gameBoard.getReferenceByObjectName(parts[1]), (Tekton)gameBoard.getReferenceByObjectName(parts[2]));
                            }
                            case "slowermushroom" -> {
                                new SlowerMushroom((Shroomer)gameBoard.getReferenceByObjectName(parts[1]), (Tekton)gameBoard.getReferenceByObjectName(parts[2]));
                            }
                            case "paralyzermushroom" -> {
                                new ParalyzerMushroom( (Shroomer)gameBoard.getReferenceByObjectName(parts[1]), (Tekton)gameBoard.getReferenceByObjectName(parts[2]));
                            }
                            case "biteblockermushroom" -> {
                                new BiteBlockerMushroom( (Shroomer)gameBoard.getReferenceByObjectName(parts[1]), (Tekton)gameBoard.getReferenceByObjectName(parts[2]));
                            }
                            case "proliferatingmushroom" -> {
                                new ProliferatingMushroom( (Shroomer)gameBoard.getReferenceByObjectName(parts[1]), (Tekton)gameBoard.getReferenceByObjectName(parts[2]));
                            }


                        }

                    }
                    case BUGGERS -> {
                        gameBoard.addBugger(new Bugger());
                    }
                    case STRATEGIES -> {
                        switch (parts[0].toLowerCase()){
                            case "normal" -> new Normal();

                            case "boosted" -> new Boosted();

                            case "slowed" ->  new Slowed();

                            case "paralyzed" -> new Paralyzed();

                            case "biteblocked" -> new BiteBlocked();

                        }
                    }
                    case BUGS -> {
                        Bug bug = new Bug((Bugger)gameBoard.getReferenceByObjectName(parts[1]));
                        bug.setStrategy((Strategy)gameBoard.getReferenceByObjectName(parts[0]));
                        bug.setLocation((Tekton)gameBoard.getReferenceByObjectName(parts[2]));
                        ((Bugger) gameBoard.getReferenceByObjectName(parts[1])).addBug(bug);
                    }
                    case HYPAS -> {
                        Hypa hypa = new Hypa((Tekton)gameBoard.getReferenceByObjectName(parts[0]),
                                (Tekton)gameBoard.getReferenceByObjectName(parts[1]),(Shroomer)gameBoard.getReferenceByObjectName(parts[2]) );
                        ((Tekton) gameBoard.getReferenceByObjectName(parts[0])).connectHypa(hypa);
                        ((Tekton) gameBoard.getReferenceByObjectName(parts[1])).connectHypa(hypa);
                    }
                    case SPORES -> {
                        Spore spore;
                        Shroomer shroomer = (Shroomer)gameBoard.getReferenceByObjectName(parts[1]);
                        switch (parts[0].toLowerCase()){
                            case "boosterspore" -> spore = new BoosterSpore(shroomer);
                            case "slowerspore" -> spore = new SlowerSpore(shroomer);
                            case "paralyzerspore" -> spore = new ParalyzerSpore(shroomer);
                            case "biteblockerspore" -> spore = new BiteBlockerSpore(shroomer);
                            case "proliferatingspore" -> spore = new ProliferatingSpore(shroomer);
                            default -> spore = null;

                        }
                        ((Tekton)gameBoard.getReferenceByObjectName(parts[2])).storeSpore(spore);

                    }
                }
            }

            case ACT -> {
                String[] parts = input.split(";");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }

                switch (parts[0].toLowerCase()){
                    case "move" -> {
                        if (gameBoard.getBugger().containsKey(actualPlayer)){
                            gameBoard.getBugger().get(actualPlayer).move(
                                    (Bug)gameBoard.getReferenceByObjectName(parts[1]),
                                    (Tekton)gameBoard.getReferenceByObjectName(parts[2])
                            );
                            actualPlayer++;
                        }
                    }
                    case "move" -> {
                        if (gameBoard.getBugger().containsKey(actualPlayer)){
                            gameBoard.getBugger().get(actualPlayer).move(
                                    (Bug)gameBoard.getReferenceByObjectName(parts[1]),
                                    (Tekton)gameBoard.getReferenceByObjectName(parts[2])
                            );
                            actualPlayer++;
                        }
                        view.serveResult("It is not a valid action for the actual player");
                    }
                    case "bite" -> {
                        if (gameBoard.getBugger().containsKey(actualPlayer)){
                            gameBoard.getBugger().get(actualPlayer).bite(
                                    (Bug)gameBoard.getReferenceByObjectName(parts[1]),
                                    (Hypa)gameBoard.getReferenceByObjectName(parts[2])
                            );
                            actualPlayer++;
                        }
                        view.serveResult("It is not a valid action for the actual player");
                    }
                    case "eat" -> {
                        if (gameBoard.getBugger().containsKey(actualPlayer)){
                            gameBoard.getBugger().get(actualPlayer).move(
                                    (Bug)gameBoard.getReferenceByObjectName(parts[1]),
                                    (Spore)gameBoard.getReferenceByObjectName(parts[2])
                            );
                            actualPlayer++;
                        }
                        view.serveResult("It is not a valid action for the actual player");
                    }
                    case "growhypa" -> {
                        if (gameBoard.getShroomer().containsKey(actualPlayer)){
                            gameBoard.getShroomer().get(actualPlayer).growHypa(
                                    (Tekton)gameBoard.getReferenceByObjectName(parts[1]),
                                    (Tekton)gameBoard.getReferenceByObjectName(parts[2])
                            );
                            actualPlayer++;
                        }
                        view.serveResult("It is not a valid action for the actual player");
                    }
                    case "growhypafar" -> {
                        if (gameBoard.getShroomer().containsKey(actualPlayer)){
                            gameBoard.getShroomer().get(actualPlayer).growHypaFar(
                                    (Tekton)gameBoard.getReferenceByObjectName(parts[1]),
                                    (Tekton)gameBoard.getReferenceByObjectName(parts[2]),
                                    (Tekton)gameBoard.getReferenceByObjectName(parts[3])
                            );
                            actualPlayer++;
                        }
                        view.serveResult("It is not a valid action for the actual player");
                    }
                    case "throwspore" -> {
                        if (gameBoard.getShroomer().containsKey(actualPlayer)){
                            gameBoard.getShroomer().get(actualPlayer).throwSpore(
                                    (Mushroom)gameBoard.getReferenceByObjectName(parts[1]),
                                    (Tekton)gameBoard.getReferenceByObjectName(parts[2])
                            );
                            actualPlayer++;
                        }
                        view.serveResult("It is not a valid action for the actual player");
                    }
                    case "eatbug" -> {
                        if (gameBoard.getShroomer().containsKey(actualPlayer)){
                            gameBoard.getShroomer().get(actualPlayer).eatBug(
                                    (Bug)gameBoard.getReferenceByObjectName(parts[1])
                            );
                            actualPlayer++;
                        }
                        view.serveResult("It is not a valid action for the actual player");
                    }
                    case "endturn" -> {
                        actualPlayer++;
                    }

                    case "break" -> {

                        try{
                            ((Tekton)gameBoard.getReferenceByObjectName(parts[1])).breakTekton();
                        } catch (ClassCastException e) {
                            view.serveResult("It is only possible the break a tekton");
                        }


                    }
                }
            }

            case ASSERT -> {

            }
        }


    }

}
