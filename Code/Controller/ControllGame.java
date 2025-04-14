package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Model.Tekton.*;
import View.*;


public class ControllGame extends Controller {
    private int round;
    private State state;
    private Random rand;

    public ControllGame() {
        super();
        this.state = State.ARRANGE;
        this.round = 0;
        this.rand = new Random();

    }
    @Override
    public void run() {
        initMap();
        gameCycle();
    }

    private void initMap(){
        for (int k = 1; k <= 25; k++) {
            double r = rand.nextDouble();
            Tekton tekton;
            if (r < 0.48) {
                tekton = new Tekton();
            } else if (r < 0.61) {
                tekton = new Peat();
            } else if (r < 0.74) {
                tekton = new Stone();
            } else if (r < 0.87) {
                tekton = new Swamp();
            } else {
                tekton = new Soil();
            }
            gameBoard.addTekton(tekton);

            for (int i = 0; i < 25; i++) {
                for (int j = 0; j < 25; j++) {
                    if (i != j) {
                        if(!gameBoard.getTekton(i).isNeighbour(gameBoard.getTekton(j))) {
                            r = rand.nextDouble();
                            if(r < 0.035) {
                                gameBoard.getTekton(i).addNeighbour(gameBoard.getTekton(j));
                                }

                            }
                        }
                    }
                }
            }
    }

    private void gameCycle() {
        state = State.ACT;

        while (state == State.ACT) {
            String input = view.getInput();


            boolean inputValid = handleInput(input);

            if (inputValid) {

                if (gameBoard.getBuggers().containsKey(actualPlayer)) {
                    actualPlayer.endOfTurn();
                }


                if (actualPlayer == getLastPlayer()) {
                    endOfRound();

                    List<Tekton> list = new ArrayList<>();
                    for (Tekton t : gameBoard.getTektons()) {
                        if (!t.hasMushroom()) {
                            list.add(t);
                        }
                    }
                    List<Tekton> tektonsWithoutMushroom = list;

                    if (!tektonsWithoutMushroom.isEmpty()) {
                        Random random = new Random();
                        Tekton randomTekton = tektonsWithoutMushroom.get(
                                random.nextInt(tektonsWithoutMushroom.size()));
                        randomTekton.break();
                    }

                    actualPlayer = (actualPlayer + 1) % getNumberOfPlayers();
                }
            }
        }
    }

    @Override
    public void handleInput(String input) {
        switch(state){
            case ARRANGE:
                if (input.startsWith("Buggers ") || input.startsWith("Shroomer ") || input.equals("Start")){
                    view.displayGameBoard();
                }
            case ACT:
                if (isValidActCommand(input)) {
                    super.handleInput(input);  // Call parent class implementation
                    view.displayGameBoard();
                }

    }
    /**
     *  ACT parancsok vizsgálatához.
     */
    private boolean isValidActCommand(String input) {
    }
}