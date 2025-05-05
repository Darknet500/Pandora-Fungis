package View;

import Controller.Controller;
import Model.Bridge.GameBoard;
import Model.Shroomer.*;
import Model.Tekton.*;
import Model.Bug.*;

import javax.swing.*;

public class GraphicView {
    private GameBoard gameBoard;
    private Controller controller;
    private DrawingSurface ds;

    private SelectedAction SelectedAction;
    private Tekton[] SelectedTekton;
    private Bug SelectedBug;
    private Spore SelectedSpore;
    private Hypa SelectedHypa;
    private Mushroom SelectedMushroom;

    public GraphicView(){
        gameBoard = null;
        controller = null;
    }

    public void connectObjects(GameBoard gameBoard, Controller controller) {
        this.gameBoard = gameBoard;
        this.controller = controller;
        controller.connectObjects(this, gameBoard);
        controller.setSeed(12345L);

    }

    public void run(){
        startMenu();
        gameSettingsMenu();
        gameRun();
    }

    private void startMenu(){}
    private void gameSettingsMenu(){}
    private void gameRun(){}
    
}
