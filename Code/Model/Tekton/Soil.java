package Model.Tekton;

import Model.Bridge.GameBoard;
import Model.Shroomer.Hypa;

import java.util.Iterator;

public class Soil extends Tekton {

    private static int soilID = 0;
    private String name;

    public Soil() {
        super();
        soilID++;
        name = "soil" + soilID;
        GameBoard.nameObjectMap.put(name, this);
    }

    @Override
    public void endOfRound() {

        for (Hypa hypa: connectedHypas){
            hypa.setIsDyingSinceDisconnected(-1);
        }
    }
}
