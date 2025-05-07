package View;

import Controller.Controller;
import Model.Bridge.GameBoard;
import Model.Shroomer.*;
import Model.Tekton.*;
import Model.Bug.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class GraphicView extends JFrame implements IView{
    private GameBoard gameBoard;
    private Controller controller;

    private Dimension screensize;

    private SelectedAction selectedAction;
    private Tekton[] selectedTekton;
    private Bug selectedBug;
    private Spore selectedSpore;
    private Hypa selectedHypa;
    private Mushroom selectedMushroom;

    public GraphicView(){
        super("Pandora-Fungorium");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screensize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setUndecorated(true);
        this.setResizable(false);

        gameBoard = null;
        controller = null;
        selectedAction = null;
        selectedTekton = new Tekton[3];
        selectedBug = null;
        selectedSpore = null;
        selectedHypa = null;
        selectedMushroom = null;
    }

    @Override
    public void connectObjects(GameBoard gameBoard, Controller controller) {
        this.gameBoard = gameBoard;
        this.controller = controller;
        controller.connectObjects(this, gameBoard);
        controller.setSeed(12345L);

    }

    @Override
    public void run(){
        if(gameBoard == null || controller == null){
            System.out.println("ERROR: cannot start without connecting to a Controller and a GameBoard");
            return;
        }
        CardLayout layout = new CardLayout();
        JPanel cards = new JPanel(layout);
        cards.add(startMenuSetup(layout, cards), "startmenu");
        cards.add(gameSettingsPanelSetup(layout, cards), "gamesettingsmenu");
        cards.add(gameBoardPanel(layout, cards), "gameboard");
        this.add(cards);
        this.setVisible(true);
    }

    private JPanel startMenuSetup(CardLayout layout, JPanel cards){
        JPanel startMenuPanel = new JPanel(new BorderLayout());
        startMenuPanel.setBackground(Color.BLACK);

        // Panel for vertically stacked components
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        Dimension btnSize = new Dimension(300, 60);

        JLabel label = new JLabel("PANDORA - FUNGORIUM");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startBtn = new PandoraButton("START");
        startBtn.setMaximumSize(btnSize);
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.addActionListener(e -> layout.show(cards, "gamesettingsmenu"));

        JButton exitStartMenuBtn = new PandoraButton("EXIT");
        exitStartMenuBtn.setMaximumSize(btnSize);
        exitStartMenuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitStartMenuBtn.addActionListener(e -> System.exit(0));

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(label);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        try{
            BufferedImage titleLogo = ImageIO.read(new File(System.getProperty("user.dir") + "\\Assets\\LOGOBIG.png"));
            JLabel imgLabel = new JLabel(new ImageIcon(titleLogo));
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonPanel.add(imgLabel);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        } catch(IOException e){
            System.out.println("ERROR: cannot load image");
        }

        buttonPanel.add(startBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        buttonPanel.add(exitStartMenuBtn);
        buttonPanel.add(Box.createVerticalGlue());

        startMenuPanel.add(buttonPanel, BorderLayout.CENTER);
        return startMenuPanel;
    }

    /**
     * gameSettingsMenuPanel: BorderLayout
     *      -->titlePanel: gameSettingsPanel tetejen (BorderLayout.NORTH), FlowLayout
     *              itt van a title es a logo
     *      -->addPlayersPanel: gameSettingsPanel kozepen (BorderLayout.CENTER), 1:2 GridLayout
     *              -->shroomersPanel: GridLayout 1.  oszlopaban, BoxLayout y tengely menten
     *                      -->lista : listaelemek JPanelek Grid 1:2 layouttal, bal oldalon nev, jobb oldalon tipus stringkent
     *                      -->addShroomerGridPanel: kozepre igazitva a shroomersPanelen, 1:2 GridLayout
     *                              --> textbox a nev megadasahoz a bal oldalon
     *                              --> tipus valasztas gomb a jobb oldalon
     *                      --> hozzaado gomb kozepre igazitva
     *              -->buggersPanel: GridLayout 2. oszlopaban, BoxLayout y tengely menten
     *                      itt vannak listazva a mar jatekhoz adott buggerek, itt van a + gomb
     *                      -->lista : listaelemek JPanelek Grid 1:2 layouttal, bal oldalon nev, jobb oldalon valasztott szinu sample labelek
     *                      -->addBuggerGridPanel: kozepre igazitva a buggersPanelen, 1:2 GridLayout
     *                              --> textbox a nev megadasahoz a bal oldalon
     *                              --> szin valasztas gomb a jobb oldalon
     *                      --> hozzaado gomb kozepre igazitva
     */
    private JPanel gameSettingsPanelSetup(CardLayout layout, JPanel cards){
        Dimension btnSize = new Dimension(300, 60);
        JPanel gameSettingsMenuPanel = new JPanel(new BorderLayout());
        gameSettingsMenuPanel.setBackground(Color.BLACK);

        /**
         * bottomControlPanel felhozva, hogy az add gombok event handler-jei lassak a start gombot
         */
        JPanel bottomControlPanel = new JPanel();
        bottomControlPanel.setBackground(Color.BLACK);
        bottomControlPanel.setLayout(new BoxLayout(bottomControlPanel, BoxLayout.X_AXIS));

        Dimension dim = new Dimension(300, 25);
        JButton exitButton = new PandoraButton("EXIT");
        exitButton.addActionListener(e -> {
            /**
             * gameboard torlese (a korabban hozzaadott jatekosokat toroljuk,
             * felulet ujrarajzolasa miatt 1x ujrahivja magat (a listakbol ki kell torolni a korabban hozzaadott playereket)
             */
            gameBoard.clear();
            layout.removeLayoutComponent(gameSettingsMenuPanel);
            cards.add(gameSettingsPanelSetup(layout, cards), "gamesettingsmenu");
            layout.show(cards, "startmenu");
        });
        bottomControlPanel.add(exitButton);
        exitButton.setPreferredSize(dim);
        exitButton.setMinimumSize(dim);
        exitButton.setMaximumSize(dim);
        exitButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        bottomControlPanel.add(Box.createHorizontalGlue()); // Pushes buttons apart

        JButton startButton = new PandoraButton("START");
        /**
         * kezdetben letiltva, akkor lesz engedelyezve ha van legalabb 2 shroomer es 2 bugger
         */
        startButton.setEnabled(false);
        /**
         * indul a game
         */
        startButton.addActionListener(e -> {
            //initmap konstruktorban
            layout.show(cards, "gameboard");
        });
        bottomControlPanel.add(startButton);
        startButton.setPreferredSize(dim);
        startButton.setMinimumSize(dim);
        startButton.setMaximumSize(dim);
        startButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        /**
         * felirat, logo egymas alatt
         */
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(Box.createRigidArea(new Dimension(0, 50)));
        JLabel label = new JLabel("GAME - START");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setForeground(Color.WHITE);
        titlePanel.add(label);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 50)));
        try{
            BufferedImage titleLogo = ImageIO.read(new File(System.getProperty("user.dir") + "\\Assets\\LOGOBIG.png"));
            JLabel imgLabel = new JLabel(new ImageIcon(titleLogo));
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(imgLabel);
            titlePanel.add(Box.createRigidArea(new Dimension(0, 25)));
        } catch(IOException e){
            System.out.println("ERROR: cannot load image");
        }
        gameSettingsMenuPanel.add(titlePanel, BorderLayout.NORTH);

        /**
         * container panel a jatekosok hozzaadasanak, egy sor, ket oszlop
         */
        JPanel addPlayersPanel = new JPanel(new GridLayout(1, 2));

        /**
         * bal oldali panel a shroomereknek
         */
        JPanel shroomersPanel = new JPanel();
        shroomersPanel.setLayout(new BoxLayout(shroomersPanel, BoxLayout.Y_AXIS));
        shroomersPanel.setBackground(Color.BLACK);
        shroomersPanel.add(Box.createVerticalGlue());
        JLabel shroomersLabel = new JLabel("SHROOMERS");
        shroomersLabel.setFont(new Font("Arial", Font.BOLD, 40));
        shroomersLabel.setForeground(Color.WHITE);
        shroomersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        shroomersPanel.add(shroomersLabel);
        shroomersPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        /**
         * jatekhoz adott shroomerek listazasa JList-tel
         */
        DefaultListModel<Map.Entry<String, String>> shroomerModel = new DefaultListModel<>();
        JList<Map.Entry<String, String>> shroomerList = new JList<>(shroomerModel);

        /**
         * cellRenderer osszerak egy 1 soros, 2 oszlopos Grid layoutos panelt,
         * bal oldalon a jatekosnev, jobb oldalon a gombatipus
         */
        shroomerList.setCellRenderer((lst, val, idx, isSelected, hasFocus) -> {
            JPanel listItem = new JPanel(new GridLayout(1, 2, 10, 0));
            listItem.setBackground(Color.BLACK);
            JLabel name = new JLabel(val.getKey());
            name.setForeground(Color.WHITE);
            name.setBackground(Color.BLACK);
            name.setOpaque(true);
            name.setFont(new Font("Arial", Font.PLAIN, 20));
            listItem.add(name);
            JLabel type = new JLabel(val.getValue());
            type.setForeground(Color.WHITE);
            type.setBackground(Color.BLACK);
            type.setOpaque(true);
            type.setFont(new Font("Arial", Font.PLAIN, 20));
            listItem.add(type);
            listItem.setOpaque(true);
            return listItem;
        });
        shroomerList.setFont(new Font("Arial", Font.PLAIN, 20));
        shroomerList.setBackground(Color.BLACK);
        shroomerList.setForeground(Color.WHITE);
        /**
         * no-op selection model, hogy ne lehessen kattintani, kivalasztani stb. a lista elemeit
         */
        shroomerList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {}
        });
        shroomerList.setFixedCellHeight(30);
        shroomerList.setAlignmentX(Component.CENTER_ALIGNMENT);
        shroomersPanel.add(shroomerList);
        shroomersPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        /**
         * ket oszlopos, egy soros Grid layoutos panel gombasz hozzaadasahoz, bal oldalon megadhato a nev,
         * jobb oldalon gombbal kivalaszthato a kepesseg a maradek elerhetoek kozul.
         */
        JPanel addShroomerGridpanel = new JPanel(new GridLayout(1, 2));
        addShroomerGridpanel.setMaximumSize(new Dimension(600, 60));
        List<String> availableTypes = new ArrayList<>();
        /**
         * elejen az osszes tipus elerheto
         */
        availableTypes.addAll(List.of("booster", "biteblocker", "slower", "paralyzer", "proliferating"));
        addShroomerGridpanel.setBackground(Color.BLACK);
        JTextField nameTf = new JTextField();
        nameTf.setBackground(Color.BLACK);
        nameTf.setForeground(Color.WHITE);
        nameTf.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        nameTf.setFont(new Font("Arial", Font.PLAIN, 20));
        addShroomerGridpanel.add(nameTf);

        /**
         * tipus kivalaszto gomb, felirat jelzi a kivalasztott tipust, kattintasra kovetkezo elerhetot valasztja ki
         */
        JButton typeSelectorButton = new PandoraButton(availableTypes.get(0));
        addShroomerGridpanel.add(typeSelectorButton);

        /**
         * kattintasra noveljuk a kivalasztott indexet 1-gíel mod az elerheto tipusok szama
         */
        typeSelectorButton.addActionListener(e -> {
            int idx = availableTypes.indexOf(typeSelectorButton.getText())+1;
            typeSelectorButton.setText(availableTypes.get(idx%(availableTypes.size())));
        });
        addShroomerGridpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        shroomersPanel.add(addShroomerGridpanel);
        shroomersPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        /**
         * hozzaado gomb, a gameBoard-ba hiv a megszokott modon
         */
        JButton addShroomerButton = new PandoraButton("+");
        addShroomerButton.addActionListener(e -> {
            BiFunction<Shroomer, TektonBase, Mushroom> mushroomctor = null;
            int hypaDieAfter = 0;
            String selectedType = typeSelectorButton.getText();
            switch (selectedType) {
                case "booster": {
                    mushroomctor = (x, y) -> new BoosterMushroom(x, y);
                    hypaDieAfter = 4;
                    break;
                }
                case "slower": {
                    mushroomctor = (x, y) -> new SlowerMushroom(x, y);
                    hypaDieAfter = 3;
                    break;
                }
                case "paralyzer": {
                    mushroomctor = (x, y) -> new ParalyzerMushroom(x, y);
                    hypaDieAfter = 2;
                    break;
                }
                case "biteblocker": {
                    mushroomctor = (x, y) -> new BiteBlockerMushroom(x, y);
                    hypaDieAfter = 3;
                    break;
                }
                case "proliferating": {
                    mushroomctor = (x, y) -> new ProliferatingMushroom(x, y);
                    hypaDieAfter = 5;
                    break;
                }
            }
            if(mushroomctor != null) {
                Shroomer newShroomer = new Shroomer(mushroomctor, hypaDieAfter);
                gameBoard.addShroomer(newShroomer, nameTf.getText());
                String name = gameBoard.getPlayerName(newShroomer);
                shroomerModel.addElement(Map.entry(name, typeSelectorButton.getText()));
                availableTypes.remove(selectedType);
                typeSelectorButton.setText(availableTypes.get(0));
                if(gameBoard.getShroomers().size() == 4){
                    addShroomerButton.setEnabled(false);
                }
                if(gameBoard.getShroomers().size()>=2 &&gameBoard.getBuggers().size()>=2){
                    startButton.setEnabled(true);
                }
            }
        });
        addShroomerButton.setMaximumSize(btnSize);
        addShroomerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        shroomersPanel.add(addShroomerButton);
        shroomersPanel.add(Box.createVerticalGlue());

        /**
         * ugyanez buggerekre, jobb oldali Grid panel
         */
        JPanel buggersPanel = new JPanel();
        buggersPanel.setLayout(new BoxLayout(buggersPanel, BoxLayout.Y_AXIS));
        buggersPanel.setBackground(Color.BLACK);
        buggersPanel.add(Box.createVerticalGlue());
        JLabel buggersLabel = new JLabel("BUGGERS");
        buggersLabel.setFont(new Font("Arial", Font.BOLD, 40));
        buggersLabel.setForeground(Color.WHITE);
        buggersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buggersPanel.add(buggersLabel);
        buggersPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        /**
         * a jatekhoz adott Buggerek listazasa, bal oldalon nev, jobb oldalon szin sample JLabel
         */
        DefaultListModel<Map.Entry<String, Color>> buggerModel = new DefaultListModel<>();
        JList<Map.Entry<String, Color>> buggerList = new JList<>(buggerModel);
        buggerList.setCellRenderer((lst, val, idx, isSelected, hasFocus) -> {
            JPanel listItem = new JPanel(new GridLayout(1, 2, 10, 0));
            listItem.setBackground(Color.BLACK);
            JLabel name = new JLabel(val.getKey());
            name.setForeground(Color.WHITE);
            name.setBackground(Color.BLACK);
            name.setOpaque(true);
            name.setFont(new Font("Arial", Font.PLAIN, 20));
            listItem.add(name);
            JLabel colorSample = new JLabel("");
            colorSample.setBackground(val.getValue());
            colorSample.setOpaque(true);
            listItem.add(colorSample);
            listItem.setOpaque(true);
            return listItem;
        });
        buggerList.setFont(new Font("Arial", Font.PLAIN, 20));
        buggerList.setBackground(Color.BLACK);
        buggerList.setForeground(Color.WHITE);
        buggerList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {}
        });
        buggerList.setFixedCellHeight(30);
        buggerList.setAlignmentX(Component.CENTER_ALIGNMENT);
        buggersPanel.add(buggerList);
        buggersPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel addBuggerGridpanel = new JPanel(new GridLayout(1, 2));
        addBuggerGridpanel.setMaximumSize(new Dimension(600, 25));
        addBuggerGridpanel.setPreferredSize(new Dimension(600, 25));
        List<Color> availableColors = new ArrayList<>();
        availableColors.addAll(List.of(Color.RED, Color.BLUE, Color.MAGENTA, Color.GREEN));
        addShroomerGridpanel.setBackground(Color.BLACK);
        JTextField buggerNameTf = new JTextField();
        buggerNameTf.setBackground(Color.BLACK);
        buggerNameTf.setForeground(Color.WHITE);
        buggerNameTf.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        buggerNameTf.setFont(new Font("Arial", Font.PLAIN, 20));
        addBuggerGridpanel.add(buggerNameTf);
        JButton colorSelectorButton = new PandoraButton("");
        colorSelectorButton.setBackground(availableColors.get(0));
        addBuggerGridpanel.add(colorSelectorButton);
        colorSelectorButton.addActionListener(e -> {
            int idx = availableColors.indexOf(colorSelectorButton.getBackground())+1;
            colorSelectorButton.setBackground(availableColors.get(idx%(availableColors.size())));
        });
        addBuggerGridpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buggersPanel.add(addBuggerGridpanel);
        buggersPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton addBuggerButton = new PandoraButton("+");
        addBuggerButton.addActionListener(e -> {
            Bugger newBugger = new Bugger();
            gameBoard.addBugger(newBugger, buggerNameTf.getText());
            String name = gameBoard.getPlayerName(newBugger);
            buggerModel.addElement(Map.entry(name, colorSelectorButton.getBackground()));
            availableColors.remove(colorSelectorButton.getBackground());
            if(gameBoard.getShroomers().size()>=2 &&gameBoard.getBuggers().size()>=2){
                startButton.setEnabled(true);
            }
            if(!availableColors.isEmpty()){
                colorSelectorButton.setBackground(availableColors.get(0));
            }
            if(gameBoard.getBuggers().size() == 4){
                addBuggerButton.setEnabled(false);
            }
        });
        addBuggerButton.setMaximumSize(btnSize);
        addBuggerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buggersPanel.add(addBuggerButton);
        buggersPanel.add(Box.createVerticalGlue());

        addPlayersPanel.add(shroomersPanel);
        addPlayersPanel.add(buggersPanel);
        gameSettingsMenuPanel.add(addPlayersPanel, BorderLayout.CENTER);

        gameSettingsMenuPanel.add(bottomControlPanel, BorderLayout.SOUTH);
        bottomControlPanel.setPreferredSize(new Dimension(gameSettingsMenuPanel.getWidth(), 60));
        return gameSettingsMenuPanel;
    }

    private JPanel gameBoardPanel(CardLayout layout, JPanel cards){
        JPanel gameBoardPanel = new JPanel(new BorderLayout());
        gameBoardPanel.setSize(this.getSize());
        DrawingSurface drawingsurface = new DrawingSurface(screensize.width,screensize.height-30, gameBoard);
        gameBoardPanel.add(drawingsurface, BorderLayout.NORTH);
        return gameBoardPanel;
    }

    @Override
    public void displayMessage(String message){}
    @Override
    public void setEndOfGame(){}

}
