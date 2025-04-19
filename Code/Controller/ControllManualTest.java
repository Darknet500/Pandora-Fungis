package Controller;

import Model.Tekton.*;

import java.util.Arrays;

public class ControllManualTest extends Controller {

    State state;

    public ControllManualTest() {
        super(); // Meghívjuk az ősosztály konstruktorát
    }

    private void initMap() {
        state = State.ARRANGE;
        while (state == State.ARRANGE) {
            view.getInput();
        }
    }

    private void gameCycle() {

    }

    @Override
    public void HandleInput(String input) {
        switch (state) {
            case ARRANGE:
                switch (input) {
                    case "Tektons":
                        this.arrangeSection = ArrangeSection.TEKTONS;
                        break;
                    case "Neighbors":
                        this.arrangeSection = ArrangeSection.NEIGHBOURS;
                        break;
                    case "Shroomers":
                        this.arrangeSection = ArrangeSection.SHROOMERS;
                        break;
                    case "Mushrooms":
                        this.arrangeSection = ArrangeSection.MUSHROOMS;
                        break;
                    case "Buggers":
                        this.arrangeSection = ArrangeSection.TEKTONS;
                        break;
                    case "Strategies":
                        this.arrangeSection = ArrangeSection.NEIGHBOURS;
                        break;
                    case "Bugs":
                        this.arrangeSection = ArrangeSection.SHROOMERS;
                        break;
                    case "Hypas":
                        this.arrangeSection = ArrangeSection.MUSHROOMS;
                        break;
                    case "Spores":
                        this.arrangeSection = ArrangeSection.MUSHROOMS;
                        break;
                }
                switch (arrangeSection){
                    case TEKTONS:
                        if (input.equalsIgnoreCase("Tekton")){
                            gameBoard.addTekton(new Tekton());
                        }
                        else if (input.equalsIgnoreCase("Stone")){
                            gameBoard.addTekton(new Stone());
                        }
                        else if (input.equalsIgnoreCase("Soil")){
                            gameBoard.addTekton(new Soil());
                        }
                        else if (input.equalsIgnoreCase("Peat")){
                            gameBoard.addTekton(new Peat());
                        }
                        else if (input.equalsIgnoreCase("Swamp")){
                            gameBoard.addTekton(new Swamp());
                        }
                        else{
                            System.out.println("Invalid input");
                        }
                        break;
                    case NEIGHBOURS:

                        /** Tároljuk az input alapján a tektonok sorszámát, amik azalapján vannak megadva
                         * hogy hanyadikként kerültek be a tekton listbe**/
                        int[] tektonIdxs = Arrays.stream(input.split(";"))
                                .map(s -> s.substring(6))  // Extract the number part
                                .mapToInt(Integer::parseInt)  // Convert to int
                                .toArray();

                        /** A 0-ik tekton szomszédjaihoz hozzáadjuk az (idx - 1)-edik tektonokat **/
                        for(int i = 1; i < tektonIdxs.length; i++){
                            gameBoard.getTekton(tektonIdxs[0]).addNeighbour(gameBoard.getTekton(tektonIdxs[i]-1));
                        }
                        break;
                    case SHROOMERS:
                        if (input.equalsIgnoreCase("booster")){
                            //creates BoosterShroomer
                        }
                        else if (input.equalsIgnoreCase("Paralyzer")){
                            //creates ParalyzerShroomer
                        }
                        else if (input.equalsIgnoreCase("slower")){
                            //creates slowerShroomer
                        }
                        else if (input.equalsIgnoreCase("biteblocker")){
                            //creates biteblockerShroomer
                        }
                        else if (input.equalsIgnoreCase("proliferating")){
                            //creates proliferatingShroomer
                        }
                }
        }
    }
}