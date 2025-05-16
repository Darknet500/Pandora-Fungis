package View;

import Controller.Controller;
import Model.Bridge.GameBoard;
import Model.Shroomer.*;
import Model.Tekton.*;
import Model.Bug.*;
import View.Hitbox.Hitbox;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;

public class GraphicView extends JFrame implements IView{
    private GameBoard gameBoard;
    private Controller controller;

    private Dimension screensize;
    private DrawingSurface drawingsurface;

    private CardLayout layout;
    private JPanel cards;
    private JButton moveBtn;
    private JButton eatBtn;
    private JButton biteBtn;
    private JButton throwSporeBtn;
    private JButton growHypaBtn;
    private JButton growHypaFarBtn;
    private JButton eatBugBtn;
    private JButton skipBtn;
    private JLabel nextPlayerName;
    private JLabel messageDisplay;

    private SelectedAction selectedAction;
    private TektonBase[] selectedTektons;
    private Bug selectedBug;
    private Spore selectedSpore;
    private Hypa selectedHypa;
    private Mushroom selectedMushroom;

    public GraphicView(){
        super("Pandora-Fungorium");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        screensize = Toolkit.getDefaultToolkit().getScreenSize();

        layout = new CardLayout();
        cards = new JPanel(layout);

        this.setUndecorated(true);
        this.setResizable(false);

        gameBoard = null;
        controller = null;
        selectedAction = null;
        clearSelection();

        moveBtn = new PandoraButton("MOVE");
        moveBtn.addActionListener(e -> {
            clearSelection();
            selectedAction=SelectedAction.MOVE;
            displayMessage("MOVE action selected");
        });

        eatBtn = new PandoraButton("EAT");
        eatBtn.addActionListener(e -> {
            clearSelection();
            selectedAction=SelectedAction.EAT;
            displayMessage("EAT action selected");
        });

        biteBtn = new PandoraButton("BITE");
        biteBtn.addActionListener(e -> {
            clearSelection();
            selectedAction=SelectedAction.BITE;
            displayMessage("BITE action selected");
        });

        throwSporeBtn = new PandoraButton("THROWSPORE");
        throwSporeBtn.addActionListener(e -> {
            clearSelection();
            selectedAction=SelectedAction.THROWSPORE;
            displayMessage("THROWSPORE action selected");
        });

        growHypaBtn = new PandoraButton("GROWHYPA");
        growHypaBtn.addActionListener(e -> {
            clearSelection();
            selectedAction=SelectedAction.GROWHYPA;
            displayMessage("GROWHYPA action selected");
        });

        growHypaFarBtn = new PandoraButton("GROWHYPAFAR");
        growHypaFarBtn.addActionListener(e -> {
            clearSelection();
            selectedAction=SelectedAction.GROWHYPAFAR;
            displayMessage("GROWHYPAFAR action selected");
        });

        eatBugBtn = new PandoraButton("EATBUG");
        eatBugBtn.addActionListener(e -> {
            clearSelection();
            selectedAction=SelectedAction.EATBUG;
            displayMessage("EATBUG action selected");

        });

        skipBtn = new PandoraButton("SKIP");
        skipBtn.addActionListener(e -> {
            controller.endturn();
        });

        nextPlayerName = new JLabel();
        nextPlayerName.setFont(new Font("Arial", Font.PLAIN, 20));
        nextPlayerName.setForeground(Color.WHITE);

        messageDisplay = new JLabel();
        messageDisplay.setFont(new Font("Arial", Font.PLAIN, 20));
        messageDisplay.setText("");
        messageDisplay.setForeground(Color.WHITE);
        messageDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public int getDrawingSurfaceWidth(){
        return drawingsurface.getWidth();
    }

    @Override
    public int getDrawingSurfaceHeight(){
        return drawingsurface.getHeight();
    }

    @Override
    public void connectObjects(GameBoard gameBoard, Controller controller) {
        this.gameBoard = gameBoard;
        this.controller = controller;
        drawingsurface = new DrawingSurface(screensize.width,screensize.height-75, gameBoard);
        controller.connectObjects(this, gameBoard);
        gameBoard.connectToView(this);
        controller.setSeed(12345L);

    }

    @Override
    public void run(){
        if(gameBoard == null || controller == null){
            System.out.println("ERROR: cannot start without connecting to a Controller and a GameBoard");
            return;
        }

        cards.add(startMenuSetup(), "startmenu");
        cards.add(gameSettingsPanelSetup(), "gamesettingsmenu");
        cards.add(gameBoardPanel(), "gameboard");
        this.add(cards);
        this.setVisible(true);
    }

    private JPanel startMenuSetup(){
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
    private JPanel gameSettingsPanelSetup(){
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
            cards.add(gameSettingsPanelSetup(), "gamesettingsmenu");
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
            controller.gameCycle();
            controller.initMap();
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
            Color color = Color.BLACK;
            switch (selectedType) {
                case "booster": {
                    mushroomctor = (x, y) -> new BoosterMushroom(x, y);
                    hypaDieAfter = 4;
                    color = new Color(2, 43, 226);
                    break;
                }
                case "slower": {
                    mushroomctor = (x, y) -> new SlowerMushroom(x, y);
                    hypaDieAfter = 3;
                    color = new Color(250, 163, 0);
                    break;
                }
                case "paralyzer": {
                    mushroomctor = (x, y) -> new ParalyzerMushroom(x, y);
                    hypaDieAfter = 2;
                    color = new Color(93, 215, 82);
                    break;
                }
                case "biteblocker": {
                    mushroomctor = (x, y) -> new BiteBlockerMushroom(x, y);
                    hypaDieAfter = 3;
                    color = new Color(240, 232, 82);
                    break;
                }
                case "proliferating": {
                    mushroomctor = (x, y) -> new ProliferatingMushroom(x, y);
                    hypaDieAfter = 5;
                    color = new Color(255, 45, 198);
                    break;
                }
            }
            if(mushroomctor != null) {
                Shroomer newShroomer = new Shroomer(mushroomctor, hypaDieAfter);
                gameBoard.addShroomer(newShroomer, nameTf.getText(), color );
                String name = gameBoard.getPlayerName(newShroomer);
                shroomerModel.addElement(Map.entry(name, typeSelectorButton.getText()));
                availableTypes.remove(selectedType);
                typeSelectorButton.setText(availableTypes.get(0));
                nameTf.setText("");
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
            gameBoard.addBugger(newBugger, buggerNameTf.getText(), colorSelectorButton.getBackground());
            String name = gameBoard.getPlayerName(newBugger);
            buggerModel.addElement(Map.entry(name, colorSelectorButton.getBackground()));
            availableColors.remove(colorSelectorButton.getBackground());
            buggerNameTf.setText("");
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

    private JPanel gameBoardPanel(){
        JPanel gameBoardPanel = new JPanel(new BorderLayout());
        gameBoardPanel.setSize(this.getSize());

        /**
         * palya kirajzolo felulete
         * kattintasokra mouseListener figyel, kivalasztott akcio kontextusaban gyujti a parametereket,
         * ha megvan minden, hivja a controllert
         */

        drawingsurface.addMouseListener(new MouseAdapter() {


            @Override
            public void mouseClicked(MouseEvent e) {
                if(selectedAction==null){
                    System.out.println("ERROR: no action was selected");
                    return;
                }

                switch(selectedAction){
                    case MOVE: {
                        if(selectedBug==null){
                            selectBug(e);
                            if(selectedBug!=null){
                                displayMessage("Bug selected successfully");
                            }else{
                                displayMessage("Failed to select a bug");
                            }
                        } else {
                            selectTekton(e);
                            if(selectedTektons[0]!=null){
                                if(controller.move(selectedBug, selectedTektons[0])) {
                                    drawingsurface.repaint();
                                }
                                clearSelection();
                            }
                        }
                        break;
                    }
                    case EAT: {
                        if(selectedBug==null){
                            selectBug(e);
                            if(selectedBug!=null){
                                displayMessage("Bug selected successfully");
                            }else{
                                displayMessage("Failed to select a bug");
                            }
                        } else {
                            selectSpore(e);
                            if(selectedSpore!=null){
                                if(controller.eat(selectedBug, selectedSpore)) {
                                    drawingsurface.repaint();
                                }
                                clearSelection();
                            }
                        }
                        break;
                    }
                    case BITE: {
                        if(selectedBug==null){
                            selectBug(e);
                            if(selectedBug!=null){
                                displayMessage("Bug selected successfully");
                            }else{
                                displayMessage("Failed to select a bug");
                            }
                        } else {
                            selectHypa(e);
                            if(selectedHypa!=null){
                                if(controller.bite(selectedBug, selectedHypa)){
                                    drawingsurface.repaint();
                                }
                                clearSelection();
                            }
                        }
                        break;
                    }
                    case THROWSPORE: {
                        if(selectedMushroom==null){
                            selectMushroom(e);
                            if(selectedMushroom!=null){
                                displayMessage("Mushroom selected successfully");
                            }else{
                                displayMessage("Failed to select a mushroom");
                            }
                        } else {
                            selectTekton(e);
                            if(selectedTektons[0]!=null){
                                if(controller.throwspore(selectedMushroom, selectedTektons[0])){
                                    drawingsurface.repaint();
                                }
                                clearSelection();
                            }
                        }
                        break;
                    }
                    case GROWHYPA: {
                        if(selectedTektons[0]==null){
                            selectTekton(e);
                            if(selectedTektons[0]!=null){
                                displayMessage("Start Tekton selected successfully");
                            }else{
                                displayMessage("Failed to select a tekton");
                            }
                        } else if (selectedTektons[1]==null){
                            selectTekton(e);
                            if(selectedTektons[1]!=null){
                                if(controller.growhypa(selectedTektons[0], selectedTektons[1])){
                                    drawingsurface.repaint();
                                }
                                clearSelection();
                            }
                        }
                        break;
                    }
                    case GROWHYPAFAR: {
                        if(selectedTektons[0]==null){
                            selectTekton(e);
                            if(selectedTektons[0]!=null){
                                displayMessage("Start Tekton selected successfully");
                            }else{
                                displayMessage("Failed to select a tekton");
                            }
                        } else if (selectedTektons[1]==null){
                            selectTekton(e);
                            if(selectedTektons[1]!=null){
                                displayMessage("Middle Tekton selected successfully");
                            }else{
                                displayMessage("Failed to select a tekton");
                            }
                        } else if(selectedTektons[2]==null){
                            selectTekton(e);
                            if(selectedTektons[2]!=null){
                                if(controller.growhypafar(selectedTektons[0], selectedTektons[1], selectedTektons[2])){
                                    drawingsurface.repaint();
                                }
                                clearSelection();
                            }
                        }
                    }
                    case EATBUG: {
                        if(selectedBug==null){
                            selectBug(e);
                            if(selectedBug!=null){
                                if(controller.eatbug(selectedBug)){
                                    drawingsurface.repaint();
                                }
                                clearSelection();
                            }
                        }
                        break;
                    }
                }
            }
        });

        gameBoardPanel.add(drawingsurface, BorderLayout.NORTH);

        /**
         * kontroll panel a gomboknak, gombok a konstruktorban peldanyositva
         */
        JPanel outerControlPanel = new JPanel(new BorderLayout());
        JPanel innerControlPanelTop = new JPanel();
        outerControlPanel.setBackground(Color.BLACK);
        innerControlPanelTop.setLayout(new BoxLayout(innerControlPanelTop, BoxLayout.X_AXIS));
        innerControlPanelTop.setBackground(Color.BLACK);
        outerControlPanel.add(messageDisplay);
        messageDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel innerControlPanel = new JPanel();
        innerControlPanel.setLayout(new BoxLayout(innerControlPanel,BoxLayout.X_AXIS));
        innerControlPanel.setBackground(Color.BLACK);

        innerControlPanel.add(moveBtn);
        moveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        innerControlPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        innerControlPanel.add(eatBtn);
        eatBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        innerControlPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        innerControlPanel.add(biteBtn);
        biteBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        innerControlPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        innerControlPanel.add(throwSporeBtn);
        throwSporeBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        innerControlPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        innerControlPanel.add(growHypaBtn);
        growHypaBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        innerControlPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        innerControlPanel.add(growHypaFarBtn);
        growHypaFarBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        innerControlPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        innerControlPanel.add(eatBugBtn);
        eatBugBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        innerControlPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        innerControlPanel.add(skipBtn);
        skipBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        innerControlPanel.add(Box.createHorizontalGlue());

        innerControlPanel.add(nextPlayerName);
        nextPlayerName.setAlignmentX(Component.RIGHT_ALIGNMENT);

        outerControlPanel.setPreferredSize(new Dimension(gameBoardPanel.getWidth(), 75));
        outerControlPanel.add(innerControlPanel, BorderLayout.NORTH);
        outerControlPanel.add(innerControlPanel, BorderLayout.SOUTH);
        gameBoardPanel.add(outerControlPanel, BorderLayout.SOUTH);

        return gameBoardPanel;
    }

    private void clearSelection(){
        selectedAction = null;
        selectedTektons = new TektonBase[3];
        for(int i = 0; i < 3; i++){
            selectedTektons[i] = null;
        }
        selectedBug = null;
        selectedSpore = null;
        selectedHypa = null;
        selectedMushroom = null;
    }

    private void selectTekton(MouseEvent e){
        for(TektonBase t: gameBoard.getTektons()){
            Hitbox h = gameBoard.getObjectHitbox(t);
            if(h!=null && h.isHit(e.getPoint())){
                for(int i = 0; i<3; i++){
                    if(selectedTektons[i] == null){
                        selectedTektons[i] = t;
                        break;
                    }
                }
                return;
            }
        }
    }

    private void selectBug(MouseEvent e){
        for(TektonBase t: gameBoard.getTektons()){
            Bug b = t.getBug();
            if(b!=null){
                Hitbox h = gameBoard.getObjectHitbox(b);
                if(h!=null&&h.isHit(e.getPoint())){
                    selectedBug=b;
                    return;
                }
            }
        }
    }

    private void selectHypa(MouseEvent e){
        for(TektonBase t: gameBoard.getTektons()){
            for(Hypa h: t.getHypas()){
                Hitbox hitb = gameBoard.getObjectHitbox(h);
                if(hitb!=null && hitb.isHit(e.getPoint())){
                    selectedHypa=h;
                    return;
                }
            }
        }
    }

    private void selectSpore(MouseEvent e){
        for(TektonBase t: gameBoard.getTektons()){
            for(Spore s: t.getStoredSpores()){
                Hitbox h = gameBoard.getObjectHitbox(s);
                if(h!=null && h.isHit(e.getPoint())){
                    selectedSpore=s;
                    return;
                }
            }
        }
    }

    private void selectMushroom(MouseEvent e){
        for(TektonBase t: gameBoard.getTektons()){
            Mushroom m = t.getMushroom();
            if(m!=null){
                Hitbox h = gameBoard.getObjectHitbox(m);
                if(h!=null && h.isHit(e.getPoint())){
                    selectedMushroom=m;
                    return;
                }
            }
        }
    }

    @Override
    public void shroomerNext(String playerName, int roundNumber){
        moveBtn.setEnabled(false);
        eatBtn.setEnabled(false);
        biteBtn.setEnabled(false);
        throwSporeBtn.setEnabled(true);
        growHypaBtn.setEnabled(true);
        growHypaFarBtn.setEnabled(true);
        eatBugBtn.setEnabled(true);
        nextPlayerName.setText("ACTUAL PLAYER: "+playerName+", ROUND "+roundNumber);
        drawingsurface.repaint();
    }

    @Override
    public void buggerNext(String playerName, int roundNumber){
        moveBtn.setEnabled(true);
        eatBtn.setEnabled(true);
        biteBtn.setEnabled(true);
        throwSporeBtn.setEnabled(false);
        growHypaBtn.setEnabled(false);
        growHypaFarBtn.setEnabled(false);
        eatBugBtn.setEnabled(false);
        nextPlayerName.setText("ACTUAL PLAYER: "+playerName+", ROUND "+roundNumber);
        drawingsurface.repaint();
    }

    @Override
    public void displayMessage(String message){
        messageDisplay.setText(message);
    }
    @Override
    public void setEndOfGame(){
        JPanel endOfGamePanel = new JPanel(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(Box.createRigidArea(new Dimension(0, 50)));
        JLabel label = new JLabel("END OF GAME - SCOREBOARD");
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
        endOfGamePanel.add(titlePanel, BorderLayout.NORTH);

        JPanel scoreBoardPanel = new JPanel(new GridLayout(1,2));

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

        List<Map.Entry<String, Integer>> shroomerList = new ArrayList<>();
        for(Shroomer s: gameBoard.getShroomers().values()){
            shroomerList.add(Map.entry(gameBoard.getPlayerName(s), s.getScore()));
        }
        shroomerList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        Map.Entry<String, Integer>[] shroomerArray = shroomerList.toArray(new Map.Entry[0]);
        JList<Map.Entry<String, Integer>> shroomerJList = new JList<>(shroomerArray);

        /**
         * cellRenderer osszerak egy 1 soros, 3 oszlopos Grid layoutos panelt,
         * bal oldalon a helyezes, kozepen a jatekosnev, jobb oldalon a pontszam
         */
        shroomerJList.setCellRenderer((lst, val, idx, isSelected, hasFocus) -> {
            JPanel listItem = new JPanel(new GridLayout(1, 3, 10, 0));
            listItem.setBackground(Color.BLACK);
            JLabel pos = new JLabel(idx+1+".");
            pos.setForeground(Color.WHITE);
            pos.setBackground(Color.BLACK);
            pos.setOpaque(true);
            pos.setFont(new Font("Arial", Font.PLAIN, 20));
            listItem.add(pos);
            JLabel name = new JLabel(val.getKey());
            name.setForeground(Color.WHITE);
            name.setBackground(Color.BLACK);
            name.setOpaque(true);
            name.setFont(new Font("Arial", Font.PLAIN, 20));
            listItem.add(name);
            JLabel type = new JLabel(val.getValue().toString());
            type.setForeground(Color.WHITE);
            type.setBackground(Color.BLACK);
            type.setOpaque(true);
            type.setFont(new Font("Arial", Font.PLAIN, 20));
            listItem.add(type);
            listItem.setOpaque(true);
            return listItem;
        });
        shroomerJList.setFont(new Font("Arial", Font.PLAIN, 20));
        shroomerJList.setBackground(Color.BLACK);
        shroomerJList.setForeground(Color.WHITE);
        /**
         * no-op selection model, hogy ne lehessen kattintani, kivalasztani stb. a lista elemeit
         */
        shroomerJList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {}
        });
        shroomerJList.setFixedCellHeight(30);
        shroomerJList.setAlignmentX(Component.CENTER_ALIGNMENT);
        shroomersPanel.add(shroomerJList);
        scoreBoardPanel.add(shroomersPanel);

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

        List<Map.Entry<String, Integer>> buggerList = new ArrayList<>();
        for(Bugger b: gameBoard.getBuggers().values()){
            buggerList.add(Map.entry(gameBoard.getPlayerName(b), b.getScore()));
        }
        buggerList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        Map.Entry<String, Integer>[] buggerArray = buggerList.toArray(new Map.Entry[0]);
        JList<Map.Entry<String, Integer>> buggerJList = new JList<>(buggerArray);

        /**
         * cellRenderer osszerak egy 1 soros, 3 oszlopos Grid layoutos panelt,
         * bal oldalon a helyezes, kozepen a jatekosnev, jobb oldalon a pontszam
         */
        buggerJList.setCellRenderer((lst, val, idx, isSelected, hasFocus) -> {
            JPanel listItem = new JPanel(new GridLayout(1, 3, 10, 0));
            listItem.setBackground(Color.BLACK);
            JLabel pos = new JLabel(idx+1+".");
            pos.setForeground(Color.WHITE);
            pos.setBackground(Color.BLACK);
            pos.setOpaque(true);
            pos.setFont(new Font("Arial", Font.PLAIN, 20));
            listItem.add(pos);
            JLabel name = new JLabel(val.getKey());
            name.setForeground(Color.WHITE);
            name.setBackground(Color.BLACK);
            name.setOpaque(true);
            name.setFont(new Font("Arial", Font.PLAIN, 20));
            listItem.add(name);
            JLabel type = new JLabel(val.getValue().toString());
            type.setForeground(Color.WHITE);
            type.setBackground(Color.BLACK);
            type.setOpaque(true);
            type.setFont(new Font("Arial", Font.PLAIN, 20));
            listItem.add(type);
            listItem.setOpaque(true);
            return listItem;
        });
        buggerJList.setFont(new Font("Arial", Font.PLAIN, 20));
        buggerJList.setBackground(Color.BLACK);
        buggerJList.setForeground(Color.WHITE);
        /**
         * no-op selection model, hogy ne lehessen kattintani, kivalasztani stb. a lista elemeit
         */
        buggerJList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {}
        });
        buggerJList.setFixedCellHeight(30);
        buggerJList.setAlignmentX(Component.CENTER_ALIGNMENT);
        buggersPanel.add(buggerJList);
        scoreBoardPanel.add(buggersPanel);

        endOfGamePanel.add(scoreBoardPanel, BorderLayout.CENTER);


        JPanel bottomControlPanel = new JPanel();
        bottomControlPanel.setBackground(Color.BLACK);
        bottomControlPanel.setLayout(new BoxLayout(bottomControlPanel, BoxLayout.Y_AXIS));

        Dimension dim = new Dimension(300, 25);
        JButton exitButton = new PandoraButton("EXIT");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        bottomControlPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        exitButton.setPreferredSize(dim);
        exitButton.setMinimumSize(dim);
        exitButton.setMaximumSize(dim);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomControlPanel.add(exitButton);
        bottomControlPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        endOfGamePanel.add(bottomControlPanel, BorderLayout.SOUTH);
        cards.add(endOfGamePanel, "endofgame");
        layout.show(cards,"endofgame");
    }
}
