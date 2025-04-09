package Tekton;

import Shroomer.Hypa;

import java.util.Iterator;

public class Soil extends Tekton {
    public Soil() {super();}

    @Override
    public void endOfRound() {

        for (Hypa hypa: connectedHypas){
            hypa.setIsDyingSinceDisconnected(-1);
        }
    }
}
