package Model.Tekton;

import Model.Bridge.GameBoard;
import Model.Shroomer.Hypa;

import java.util.Iterator;

public class Soil extends Tekton {

    public Soil() {
        super();
        GameBoard.addReferenceToMaps("soil", this);
    }

    @Override
    public void endOfRound() {

        for (Hypa hypa: connectedHypas){
            hypa.setIsDyingSinceDisconnected(-1);
        }
    }
}
